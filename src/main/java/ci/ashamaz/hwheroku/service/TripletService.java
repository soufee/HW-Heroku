package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.TarotEnum;

import java.util.List;

public interface TripletService {
    Triplet getById(Long id);
    Triplet save(Triplet triplet);
    void delete(Triplet triplet);
    List<Triplet> getAll();
    List<Triplet> getAllByFirstTeamOrSecondTeamOrDraw(TarotEnum card);
    List<Triplet> getAllByPrognosisIsNull();
    List<Triplet> getAllByPrognosisIsNotNull();
    List<Triplet> getAllByMatchResultIsNull();
    List<Triplet> getAllByMatchResultIsNotNull();
    Triplet getByEvent(BetCityEvent event);
}
