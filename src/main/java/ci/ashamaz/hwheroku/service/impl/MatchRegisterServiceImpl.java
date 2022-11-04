package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.dto.ParseAndSaveDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.Prognosis;
import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.parser.BetCityEventParser;
import ci.ashamaz.hwheroku.service.BetCityEventService;
import ci.ashamaz.hwheroku.service.MatchRegisterService;
import ci.ashamaz.hwheroku.service.ResultDownloaderRecordService;
import ci.ashamaz.hwheroku.service.TripletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class MatchRegisterServiceImpl implements MatchRegisterService {
    private final BetCityEventParser betCityParser;
    private final BetCityEventService betCityEventService;
    private final ResultDownloaderRecordService resultService;
    private final TripletService tripletService;

    @Value("${result.url.template}")
    private String resultUrlTemplate;

    @Override
    public BetCityEventBaseDto getEventTemplate(String url) {
        return betCityParser.downloadAndParseBetCityEvent(url);
    }

    @Override
    public BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2, String comment) {
        BetCityEvent byId = betCityEventService.getById(eventDto.getId());
        if (byId != null) {
            throw new IllegalArgumentException("This event is registered");
        }
        BetCityEvent event = BetCityEvent.builder().id(eventDto.getId()).tournamentId(eventDto.getTournamentId()).tournament(eventDto.getTournament())
                .hostTeam(eventDto.getHostTeam()).guestTeam(eventDto.getGuestTeam()).startsAt(eventDto.getStartsAt())
                .build();
        Triplet triplet = Triplet.builder().event(event).draw(drawPosition).firstTeam(position1).secondTeam(position2)
                .comment(comment)
                .build();
        betCityEventService.addTriplet(event, triplet);

        LocalDateTime toStartCheck = event.getStartsAt().plus(110, ChronoUnit.MINUTES);
        ResultDownloaderRecord resultDownloaderRecord =
                ResultDownloaderRecord.builder().checkTime(toStartCheck).eventId(event.getId()).url(String.format(resultUrlTemplate, event.getId())).build();
        resultService.save(resultDownloaderRecord);
        return betCityEventService.save(event);
    }

    @Override
    public BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2, OddPositions odd,
            String comment) {
        makeTripletAndSave(eventDto, position1, drawPosition, position2, comment);
        return addPrognosis(eventDto, odd);
    }

    @Override
    public BetCityEvent addPrognosis(BetCityEventBaseDto eventDto, OddPositions position) {
        BetCityEvent event = betCityEventService.getById(eventDto.getId());
        Triplet triplet = tripletService.getByEvent(event);
        if (triplet == null) {
            throw new IllegalStateException("There is no triplet in this event. Prognosis is unavailable");
        }
        Prognosis prognosis = Prognosis.builder().choice(position).odd(eventDto.getOdds().get(position)).time(LocalDateTime.now()).build();
        betCityEventService.addTriplet(event, triplet, prognosis);
        return betCityEventService.getById(event.getId());
    }

    @Override
    public BetCityEvent addPrognosis(ParseAndSaveDto urlDto) {
        BetCityEventBaseDto eventTemplate = getEventTemplate(urlDto.getUrl());
        BetCityEvent event = null;
        if (urlDto.getOdd() != null) {
            event = makeTripletAndSave(eventTemplate, urlDto.getPosition1(), urlDto.getDrawPosition(), urlDto.getPosition2(), urlDto.getOdd(),
                    urlDto.getComment());
        } else {
            event = makeTripletAndSave(eventTemplate, urlDto.getPosition1(), urlDto.getDrawPosition(), urlDto.getPosition2(), urlDto.getComment());
        }
        log.info(" saved "+event);
        return event;
    }

}
