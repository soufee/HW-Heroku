package ci.ashamaz.hwheroku.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetCityEventResult {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Integer hostTeamScore;
    private Integer guestTeamScore;
    private Integer total;

}
