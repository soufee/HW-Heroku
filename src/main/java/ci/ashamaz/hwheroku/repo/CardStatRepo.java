package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.CardStat;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardStatRepo extends JpaRepository<CardStat, TarotEnum> {
    CardStat getCardStatByCard(TarotEnum card);

}
