package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.Prognosis;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PrognosisRepo extends JpaRepository<Prognosis, Long> {
    List<Prognosis> getByResultIsNull();
    List<Prognosis> getByResultIsNotNull();
    List<Prognosis> getByTimeBetween(LocalDateTime from, LocalDateTime to);
    @Query(value="select p from Prognosis p")
    List<Prognosis> findWithPageable(Pageable pageable);

}
