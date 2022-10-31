package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.Prognosis;

import java.time.LocalDateTime;
import java.util.List;

public interface PrognosisService {
    Prognosis getById(Long id);
    void delete(Prognosis prognosis);
    Prognosis save(Prognosis prognosis);
    List<Prognosis> getAll();
    List<Prognosis> getByResultIsNull();
    List<Prognosis> getByResultIsNotNull();
    List<Prognosis> getByTimeBetween(LocalDateTime from, LocalDateTime to);
    List<Prognosis> getLastTen();
    List<Prognosis> getAllByCurrentMoth();
}
