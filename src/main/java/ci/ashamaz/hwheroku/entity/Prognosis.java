package ci.ashamaz.hwheroku.entity;

import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.ResultState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prognosis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OddPositions choice;
    @Enumerated(EnumType.STRING)
    private ResultState result;
    private LocalDateTime time;
    private Double odd;

}
