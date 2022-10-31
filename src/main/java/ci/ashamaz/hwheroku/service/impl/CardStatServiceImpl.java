package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.entity.BetCityEventResult;
import ci.ashamaz.hwheroku.entity.CardStat;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.repo.CardStatRepo;
import ci.ashamaz.hwheroku.service.CardStatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CardStatServiceImpl implements CardStatService {
    private final CardStatRepo cardStatRepo;

    @Override
    public CardStat getCardStat(TarotEnum card) {
        return cardStatRepo.findById(card).orElse(null);
    }

    @Override
    public CardStat save(CardStat cardStat) {
        return cardStatRepo.save(cardStat);
    }

    @Override
    public void addStatData(Triplet triplet, BetCityEventResult result) {
        final Integer hostTeamScore = result.getHostTeamScore();
        final Integer guestTeamScore = result.getGuestTeamScore();
        final Integer total = result.getTotal();
        TarotEnum firstTeamCard = triplet.getFirstTeam();
        TarotEnum secondTeamCard = triplet.getSecondTeam();
        TarotEnum drawCard = triplet.getDraw();
        CardStat hostTeamStat = getCardStat(firstTeamCard);
        CardStat guestTeamStat = getCardStat(secondTeamCard);
        CardStat drawStat = getCardStat(drawCard);
        if (hostTeamStat == null) {
            CardStat build = CardStat.builder().card(firstTeamCard).build();
            hostTeamStat = cardStatRepo.save(build);
        }
        if (guestTeamStat == null) {
            CardStat build = CardStat.builder().card(secondTeamCard).build();
            guestTeamStat = cardStatRepo.save(build);
        }
        if (drawStat == null) {
            CardStat build = CardStat.builder().card(drawCard).build();
            drawStat = cardStatRepo.save(build);
        }

        hostTeamStat.setTimesShown(hostTeamStat.getTimesShown() + 1);
        guestTeamStat.setTimesShown(guestTeamStat.getTimesShown() + 1);
        drawStat.setTimesShown(drawStat.getTimesShown() + 1);
        drawStat.setOnDrawPosition(drawStat.getOnDrawPosition() + 1);
        hostTeamStat.setOnTeamPosition(hostTeamStat.getOnTeamPosition() + 1);
        guestTeamStat.setOnTeamPosition(guestTeamStat.getOnTeamPosition() + 1);

        drawStat.setGoalsInMatch(drawStat.getGoalsInMatch() + total);
        hostTeamStat.setGoalsInMatch(hostTeamStat.getGoalsInMatch() + total);
        guestTeamStat.setGoalsInMatch(guestTeamStat.getGoalsInMatch() + total);

        hostTeamStat.setGotGoals(hostTeamStat.getGotGoals() + guestTeamScore);
        hostTeamStat.setScoredGoals(hostTeamStat.getScoredGoals() + hostTeamScore);

        guestTeamStat.setGotGoals(guestTeamStat.getGotGoals() + hostTeamScore);
        guestTeamStat.setScoredGoals(guestTeamStat.getScoredGoals() + guestTeamScore);

        if (hostTeamScore > guestTeamScore) {
            hostTeamStat.setWonOnTeamPosition(hostTeamStat.getWonOnTeamPosition() + 1);
            guestTeamStat.setLostOnTeamPosition(guestTeamStat.getLostOnTeamPosition() + 1);
            drawStat.setNotDrawOnDrawPosition(drawStat.getNotDrawOnDrawPosition() + 1);
            String guestSuit = secondTeamCard.getSuit();
            switch (guestSuit) {
            case "Аркан":
                hostTeamStat.setBeatsArcane(hostTeamStat.getBeatsArcane() + 1);
                break;
            case "Жезлы":
                hostTeamStat.setBeatsWands(hostTeamStat.getBeatsWands() + 1);
                break;
            case "Мечи":
                hostTeamStat.setBeatsSwords(hostTeamStat.getBeatsSwords() + 1);
                break;
            case "Пентакли":
                hostTeamStat.setBeatsDiamonds(hostTeamStat.getBeatsDiamonds() + 1);
                break;
            case "Чаши":
                hostTeamStat.setBeatsCups(hostTeamStat.getBeatsCups() + 1);
                break;
            }

            String hostSuite = firstTeamCard.getSuit();
            switch (hostSuite) {
            case "Аркан":
                guestTeamStat.setBeatsArcane(guestTeamStat.getBeatsArcane() - 1);
                break;
            case "Жезлы":
                guestTeamStat.setBeatsWands(guestTeamStat.getBeatsWands() - 1);
                break;
            case "Мечи":
                guestTeamStat.setBeatsSwords(guestTeamStat.getBeatsSwords() - 1);
                break;
            case "Пентакли":
                guestTeamStat.setBeatsDiamonds(guestTeamStat.getBeatsDiamonds() - 1);
                break;
            case "Чаши":
                guestTeamStat.setBeatsCups(guestTeamStat.getBeatsCups() - 1);
                break;
            }

            String hostTeamWeight = firstTeamCard.getWeight();
            String guestTeamWeight = secondTeamCard.getWeight();
            if (guestTeamWeight.equals("Ч")) {
                hostTeamStat.setBeatsNumbers(hostTeamStat.getBeatsNumbers() + 1);
            } else if (guestTeamWeight.equals("Ф")) {
                hostTeamStat.setBeatsFigures(hostTeamStat.getBeatsFigures() + 1);
            }

            if (hostTeamWeight.equals("Ч")) {
                guestTeamStat.setBeatsNumbers(guestTeamStat.getBeatsNumbers() - 1);
            } else if (hostTeamWeight.equals("Ф")) {
                guestTeamStat.setBeatsFigures(guestTeamStat.getBeatsFigures() - 1);
            }

        } else if (hostTeamScore < guestTeamScore) {
            hostTeamStat.setLostOnTeamPosition(hostTeamStat.getLostOnTeamPosition() + 1);
            guestTeamStat.setWonOnTeamPosition(guestTeamStat.getWonOnTeamPosition() + 1);
            drawStat.setNotDrawOnDrawPosition(drawStat.getNotDrawOnDrawPosition() + 1);

            String guestSuit = secondTeamCard.getSuit();
            switch (guestSuit) {
            case "Аркан":
                hostTeamStat.setBeatsArcane(hostTeamStat.getBeatsArcane() - 1);
                break;
            case "Жезлы":
                hostTeamStat.setBeatsWands(hostTeamStat.getBeatsWands() - 1);
                break;
            case "Мечи":
                hostTeamStat.setBeatsSwords(hostTeamStat.getBeatsSwords() - 1);
                break;
            case "Пентакли":
                hostTeamStat.setBeatsDiamonds(hostTeamStat.getBeatsDiamonds() - 1);
                break;
            case "Чаши":
                hostTeamStat.setBeatsCups(hostTeamStat.getBeatsCups() - 1);
                break;
            }

            String hostSuite = firstTeamCard.getSuit();
            switch (hostSuite) {
            case "Аркан":
                guestTeamStat.setBeatsArcane(guestTeamStat.getBeatsArcane() + 1);
                break;
            case "Жезлы":
                guestTeamStat.setBeatsWands(guestTeamStat.getBeatsWands() + 1);
                break;
            case "Мечи":
                guestTeamStat.setBeatsSwords(guestTeamStat.getBeatsSwords() + 1);
                break;
            case "Пентакли":
                guestTeamStat.setBeatsDiamonds(guestTeamStat.getBeatsDiamonds() + 1);
                break;
            case "Чаши":
                guestTeamStat.setBeatsCups(guestTeamStat.getBeatsCups() + 1);
                break;
            }

            String hostTeamWeight = firstTeamCard.getWeight();
            String guestTeamWeight = secondTeamCard.getWeight();
            if (guestTeamWeight.equals("Ч")) {
                hostTeamStat.setBeatsNumbers(hostTeamStat.getBeatsNumbers() - 1);
            } else if (guestTeamWeight.equals("Ф")) {
                hostTeamStat.setBeatsFigures(hostTeamStat.getBeatsFigures() - 1);
            }

            if (hostTeamWeight.equals("Ч")) {
                guestTeamStat.setBeatsNumbers(guestTeamStat.getBeatsNumbers() + 1);
            } else if (hostTeamWeight.equals("Ф")) {
                guestTeamStat.setBeatsFigures(guestTeamStat.getBeatsFigures() + 1);
            }

        } else {
            hostTeamStat.setDrawOnTeamPosition(hostTeamStat.getDrawOnTeamPosition() + 1);
            guestTeamStat.setDrawOnTeamPosition(guestTeamStat.getDrawOnTeamPosition() + 1);
            drawStat.setDrawOnDrawPosition(drawStat.getDrawOnDrawPosition() + 1);
        }

        save(hostTeamStat);
        save(guestTeamStat);
        save(drawStat);
    }
}
