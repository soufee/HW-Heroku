package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.Triplet;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.repo.TripletRepo;
import ci.ashamaz.hwheroku.service.TripletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TripletServiceImpl implements TripletService {
    private final TripletRepo tripletRepo;

    @Override
    public Triplet getById(Long id) {
        return tripletRepo.findById(id).orElse(null);
    }

    @Override
    public Triplet save(Triplet triplet) {
        return tripletRepo.save(triplet);
    }

    @Override
    public void delete(Triplet triplet) {
        tripletRepo.delete(triplet);
    }

    @Override
    public List<Triplet> getAll() {
        return tripletRepo.findAll();
    }

    @Override
    public List<Triplet> getAllByFirstTeamOrSecondTeamOrDraw(TarotEnum card) {
        return tripletRepo.getAllByFirstTeamOrSecondTeamOrDraw(card);
    }

    @Override
    public List<Triplet> getAllByPrognosisIsNull() {
        return tripletRepo.getAllByPrognosisIsNull();
    }

    @Override
    public List<Triplet> getAllByPrognosisIsNotNull() {
        return tripletRepo.getAllByPrognosisIsNotNull();
    }

    @Override
    public List<Triplet> getAllByMatchResultIsNull() {
        return tripletRepo.getAllByMatchResultIsNull();
    }

    @Override
    public List<Triplet> getAllByMatchResultIsNotNull() {
        return tripletRepo.getAllByMatchResultIsNotNull();
    }

    @Override
    public Triplet getByEvent(BetCityEvent event) {
        return tripletRepo.getByEvent(event);
    }
}
