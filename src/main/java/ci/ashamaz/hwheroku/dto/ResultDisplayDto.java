package ci.ashamaz.hwheroku.dto;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.Prognosis;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.ResultState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResultDisplayDto {
    public ResultDisplayDto(BetCityEvent event, Triplet triplet, Prognosis prognosis) {
        this.teams = event.getHostTeam() + " - " + event.getGuestTeam();
        this.score = event.getResultScore();
        this.startsAt = event.getStartsAt();
        this.cards = triplet.getFirstTeam().getShortTitle() + " - " + triplet.getDraw().getShortTitle() + " - " + triplet.getSecondTeam().getShortTitle();
        if (prognosis != null) {
            this.choice = prognosis.getChoice();
            this.resultState = prognosis.getResult();
        }
        this.comment = triplet.getComment();
    }

    private String teams;
    private String score;
    private LocalDateTime startsAt;
    private String cards;
    private OddPositions choice;
    private ResultState resultState;
    private String comment;
}
