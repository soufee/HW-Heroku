package ci.ashamaz.hwheroku.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetCityEvent implements Serializable {
    @Id
    private Long id;
    private String tournament;
    private Long tournamentId;
    private LocalDateTime startsAt;
    private String hostTeam;
    private String guestTeam;
    private String resultScore;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private BetCityEventResult result;


}
