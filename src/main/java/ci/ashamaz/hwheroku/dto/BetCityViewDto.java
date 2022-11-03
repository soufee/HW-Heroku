package ci.ashamaz.hwheroku.dto;

import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BetCityViewDto {
    private Long id;
    private String tournament;
    private Long tournamentId;
    private LocalDateTime startsAt;
    private String hostTeam;
    private String guestTeam;
    private OddPositions odd;
    private TarotEnum firstPosition;
    private TarotEnum drawPosition;
    private TarotEnum secondPosition;
}
