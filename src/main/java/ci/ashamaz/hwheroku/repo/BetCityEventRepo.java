package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.dto.ResultDisplayDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BetCityEventRepo extends JpaRepository<BetCityEvent, Long> {
    List<BetCityEvent> findAllByResultIsNull();

    @Query("select new ci.ashamaz.hwheroku.dto.ResultDisplayDto(" + "e, t, p" + ") "
            + "from Triplet t inner join t.event e left join t.prognosis p "
            + "where cast(e.startsAt as date) = cast(now() as date) ")
    List<ResultDisplayDto> getTodaysEvents();

    @Query("select new ci.ashamaz.hwheroku.dto.ResultDisplayDto(" + "e, t, p" + ") "
            + "from Triplet t inner join t.event e left join t.prognosis p "
            + "where e.startsAt between :from and :to ")
    List<ResultDisplayDto> getEventsByPeriod(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select new ci.ashamaz.hwheroku.dto.ResultDisplayDto(" + "e, t, p" + ") "
            + "from Triplet t inner join t.event e left join t.prognosis p ")
    List<ResultDisplayDto> getAllEvents();
}
