package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.BetCityEventResult;
import ci.ashamaz.hwheroku.entity.Prognosis;
import ci.ashamaz.hwheroku.entity.Triplet;

import java.util.List;

public interface BetCityEventService {
    BetCityEvent getById(Long id);

    BetCityEvent save(BetCityEvent event);

    void delete(BetCityEvent event);

    List<BetCityEvent> getAllWithNoResults();

    BetCityEventResult addResult(BetCityEvent event, BetCityEventResult result);

    BetCityEventResult addResult(BetCityEvent event, String score);

    void addTriplet(BetCityEvent event, Triplet triplet);

    void addTriplet(BetCityEvent event, Triplet triplet, Prognosis prognosis);

}
