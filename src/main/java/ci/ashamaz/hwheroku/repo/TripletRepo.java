package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripletRepo extends JpaRepository<Triplet, Long> {
    @Query(value="select t from Triplet t where t.firstTeam = :card or t.secondTeam = :card or t.draw = :card")
    List<Triplet> getAllByFirstTeamOrSecondTeamOrDraw(TarotEnum card);
    List<Triplet> getAllByPrognosisIsNull();
    List<Triplet> getAllByPrognosisIsNotNull();
    List<Triplet> getAllByMatchResultIsNull();
    List<Triplet> getAllByMatchResultIsNotNull();
    Triplet getByEvent(BetCityEvent event);
}
