package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetCityEventRepo extends JpaRepository<BetCityEvent, Long> {
    List<BetCityEvent> findAllByResultIsNull();
}
