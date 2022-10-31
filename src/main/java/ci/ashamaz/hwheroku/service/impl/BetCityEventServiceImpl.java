package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.BetCityEventResult;
import ci.ashamaz.hwheroku.entity.Prognosis;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.ResultState;
import ci.ashamaz.hwheroku.enums.TripletResult;
import ci.ashamaz.hwheroku.repo.BetCityEventRepo;
import ci.ashamaz.hwheroku.service.BetCityEventService;
import ci.ashamaz.hwheroku.service.CardStatService;
import ci.ashamaz.hwheroku.service.TripletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BetCityEventServiceImpl implements BetCityEventService {
    private final BetCityEventRepo betCityEventRepo;
    private final CardStatService cardStatService;
    private final TripletService tripletService;
    @Override
    public BetCityEvent getById(Long id) {
        return betCityEventRepo.findById(id).orElse(null);
    }

    @Override
    public BetCityEvent save(BetCityEvent event) {
        return betCityEventRepo.save(event);
    }

    @Override
    public void delete(BetCityEvent event) {
        betCityEventRepo.delete(event);
    }

    @Override
    public List<BetCityEvent> getAllWithNoResults() {
        return betCityEventRepo.findAllByResultIsNull();
    }

    @Override
    public BetCityEventResult addResult(BetCityEvent event, BetCityEventResult result) {
        event.setResult(result);
        Integer hostScore = result.getHostTeamScore();
        Integer guestScore = result.getGuestTeamScore();
        Triplet triplet = tripletService.getByEvent(event);
        if (triplet != null) {
            if (guestScore.equals(hostScore)) {
                triplet.setMatchResult(TripletResult.DRAW);
            } else if (hostScore > guestScore) {
                triplet.setMatchResult(TripletResult.WIN1);
            } else {
                triplet.setMatchResult(TripletResult.WIN2);
            }
            cardStatService.addStatData(triplet, result);
            Prognosis prognosis = triplet.getPrognosis();
            if (prognosis != null) {
                OddPositions choice = prognosis.getChoice();
                final Set<OddPositions> won = new HashSet<>();
                final Set<OddPositions> returns = new HashSet<>();
                for (OddPositions position : OddPositions.values()) {
                    if (position.getPredicateWin().test(hostScore, guestScore)) {
                        won.add(position);
                    }
                    if (position.getPredicateReturn().test(hostScore, guestScore)) {
                        returns.add(position);
                    }
                }
                if (won.contains(choice)) {
                    prognosis.setResult(ResultState.WIN);
                    log.info("Прогноз по "+event.getId()+" выиграл!!");
                } else if (returns.contains(choice)) {
                    log.info("Ставка по "+event.getId()+" возвращается!!");
                    prognosis.setResult(ResultState.RETURN);
                } else {
                    log.info("Прогноз по "+event.getId()+" Не зашел!!");
                    prognosis.setResult(ResultState.LOOSE);
                }
            }
            triplet.setPrognosis(prognosis);
            triplet.setEvent(event);
            save(event);
            tripletService.save(triplet);
        }
        return event.getResult();
    }

    @Override
    public BetCityEventResult addResult(BetCityEvent event, String score) {
        if (!StringUtils.hasLength(score)) {
            return null;
        }
        String[] split = score.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Could not parse score " + score);
        }
        Integer guestScore = Integer.parseInt(split[1].trim());
        Integer hostScore = Integer.parseInt(split[0].trim());
        event.setResultScore(score);

        BetCityEventResult result =
                BetCityEventResult.builder().guestTeamScore(guestScore).hostTeamScore(hostScore).total(guestScore + hostScore).build();
        return addResult(event, result);
    }

    @Override
    public void addTriplet(BetCityEvent event, Triplet triplet) {
        triplet.setEvent(event);
        tripletService.save(triplet);
    }

    @Override
    public void addTriplet(BetCityEvent event, Triplet triplet, Prognosis prognosis) {
        triplet.setPrognosis(prognosis);
        triplet.setEvent(event);
        tripletService.save(triplet);
    }
}
