package ci.ashamaz.hwheroku.entity;

import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.enums.TripletResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Triplet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @NonNull
    private BetCityEvent event;
    @Enumerated(EnumType.STRING)
    private TarotEnum firstTeam;
    @Enumerated(EnumType.STRING)
    private TarotEnum draw;
    @Enumerated(EnumType.STRING)
    private TarotEnum secondTeam;
    @Enumerated(EnumType.STRING)
    private TripletResult matchResult;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prognosis_id", referencedColumnName = "id")
    private Prognosis prognosis;
}
