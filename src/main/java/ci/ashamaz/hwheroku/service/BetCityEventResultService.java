package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.BetCityEventResult;

public interface BetCityEventResultService {
    BetCityEventResult getById(Long id);
    BetCityEventResult save(BetCityEventResult result);
    void delete(BetCityEventResult event);
}
