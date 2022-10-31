package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.BetCityEventResult;
import ci.ashamaz.hwheroku.entity.CardStat;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.TarotEnum;

public interface CardStatService {
    CardStat getCardStat(TarotEnum card);
    CardStat save(CardStat cardStat);
    void addStatData(Triplet triplet, BetCityEventResult result);
}
