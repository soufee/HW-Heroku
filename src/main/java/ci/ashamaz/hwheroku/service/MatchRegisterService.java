package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.dto.ParseAndSaveDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;

public interface MatchRegisterService {
    BetCityEventBaseDto getEventTemplate(String url);
    BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2, String comment);
    BetCityEvent makeTripletAndSave(BetCityEventBaseDto eventDto, TarotEnum position1, TarotEnum drawPosition, TarotEnum position2, OddPositions odd, String comment);
    BetCityEvent addPrognosis(BetCityEventBaseDto event, OddPositions position);
    BetCityEvent addPrognosis(ParseAndSaveDto urlDto);
}
