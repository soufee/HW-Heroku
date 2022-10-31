package ci.ashamaz.hwheroku.dto;

import ci.ashamaz.hwheroku.enums.OddPositions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetCityEventBaseDto {
    private Long id;
    private String tournament;
    private Long tournamentId;
    private LocalDateTime startsAt;
    private String hostTeam;
    private String guestTeam;
    private EnumMap<OddPositions, Double> odds;
}
