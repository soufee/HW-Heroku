package ci.ashamaz.hwheroku.entity;

import ci.ashamaz.hwheroku.enums.TarotEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardStat {
    @Id
    @Enumerated(EnumType.STRING)
    private TarotEnum card;
    int timesShown;
    int onTeamPosition;
    int onDrawPosition;

    int wonOnTeamPosition;
    int lostOnTeamPosition;
    int drawOnTeamPosition;

    int drawOnDrawPosition;
    int notDrawOnDrawPosition;

    int goalsInMatch;
    int gotGoals;
    int scoredGoals;

    int beatsArcane;
    int beatsSwords;
    int beatsDiamonds;
    int beatsWands;
    int beatsCups;
    int beatsFigures;
    int beatsNumbers;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getDisplayName() + " выпадал " + timesShown + " раз.\n");
        sb.append("Из них на позиции команды " + onTeamPosition + " раз и выиграл " + " " + wonOnTeamPosition + " раз. " + "Продул " + lostOnTeamPosition
                + " раз и показал ничью " + drawOnTeamPosition + " раз. \n");
        sb.append("На позиции ничьи выпадала " + onDrawPosition + " раз, из которых в ничью закончилось " + drawOnDrawPosition + " матчей"
                + " и не в ничью заканчивалось " + notDrawOnDrawPosition + " матчей. Общее количество ничей с этой картой в раскладе "
                + (drawOnTeamPosition + drawOnDrawPosition) + " матчей.\n");
        sb.append("Среднее количество голов в матчах с этой картой в раскладе " + (goalsInMatch/timesShown) + ", при этом в среднем на позиции команды "
                + "с этой картой пропущено " + (gotGoals/onTeamPosition) + " голов, " + " и забито " + (scoredGoals/onTeamPosition) + " голов\n");
        sb.append("Данная карта в раскладах побеждала старшие арканы " + beatsArcane + " раз\n");
        sb.append("Данная карта в раскладах побеждала масть мечей " + beatsSwords + " раз\n");
        sb.append("Данная карта в раскладах побеждала масть пентаклей " + beatsDiamonds + " раз\n");
        sb.append("Данная карта в раскладах побеждала масть жезлов " + beatsWands + " раз\n");
        sb.append("Данная карта в раскладах побеждала масть чаш " + beatsCups + " раз\n");
        sb.append("Данная карта в раскладах побеждала фигурные карты  " + beatsFigures + " раз\n");
        sb.append("Данная карта в раскладах побеждала числовые карты " + beatsNumbers + " раз\n");
        return sb.toString();
    }
}
