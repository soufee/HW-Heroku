package ci.ashamaz.hwheroku.dto;

import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.EnumMap;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BetCityEventBaseWideDto {
    private TarotEnum position1;
    private TarotEnum drawPosition;
    private TarotEnum position2;
    private Long id;
    private String tournament;
    private Long tournamentId;
    private LocalDateTime startsAt;
    private String hostTeam;
    private String guestTeam;
    private EnumMap<OddPositions, Double> odds;
}
