package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;

public interface MatchRegisterService {
    BetCityEventBaseDto getEventTemplate(String url);
    BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2);
    BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2, OddPositions odd);
    BetCityEvent addPrognosis(BetCityEventBaseDto event, OddPositions position);
}
