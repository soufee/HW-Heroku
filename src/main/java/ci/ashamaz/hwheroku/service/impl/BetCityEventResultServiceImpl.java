package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.entity.BetCityEventResult;
import ci.ashamaz.hwheroku.repo.BetCityEventResultRepo;
import ci.ashamaz.hwheroku.service.BetCityEventResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BetCityEventResultServiceImpl implements BetCityEventResultService {
    private final BetCityEventResultRepo betCityEventResultRepo;

    @Override
    public BetCityEventResult getById(Long id) {
        return betCityEventResultRepo.findById(id).orElse(null);
    }

    @Override
    public BetCityEventResult save(BetCityEventResult result) {
        return betCityEventResultRepo.save(result);
    }

    @Override
    public void delete(BetCityEventResult event) {
        betCityEventResultRepo.delete(event);
    }

}
