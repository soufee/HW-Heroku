package ci.ashamaz.hwheroku.service.impl;


import ci.ashamaz.hwheroku.entity.Prognosis;
import ci.ashamaz.hwheroku.repo.PrognosisRepo;
import ci.ashamaz.hwheroku.service.PrognosisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PrognosisServiceImpl implements PrognosisService {
    private final PrognosisRepo prognosisRepo;

    @Override
    public Prognosis getById(Long id) {
        return prognosisRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Prognosis prognosis) {
        prognosisRepo.delete(prognosis);
    }

    @Override
    public Prognosis save(Prognosis prognosis) {
        return prognosisRepo.save(prognosis);
    }

    @Override
    public List<Prognosis> getAll() {
        return prognosisRepo.findAll();
    }

    @Override
    public List<Prognosis> getByResultIsNull() {
        return prognosisRepo.getByResultIsNull();
    }

    @Override
    public List<Prognosis> getByResultIsNotNull() {
        return prognosisRepo.getByResultIsNotNull();
    }

    @Override
    public List<Prognosis> getByTimeBetween(LocalDateTime from, LocalDateTime to) {
        return prognosisRepo.getByTimeBetween(from, to);
    }

    @Override
    public List<Prognosis> getLastTen() {
        return prognosisRepo.findWithPageable(PageRequest.of(0, 10, Sort.Direction.DESC, "time"));
    }

    @Override
    public List<Prognosis> getAllByCurrentMoth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        return getByTimeBetween(firstDayOfMonth, now);
    }
}
