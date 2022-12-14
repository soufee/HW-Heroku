package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.downloader.BetCityResultDownloader;
import ci.ashamaz.hwheroku.dto.ResultDisplayDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;
import ci.ashamaz.hwheroku.repo.BetCityEventRepo;
import ci.ashamaz.hwheroku.service.BetCityEventService;
import ci.ashamaz.hwheroku.service.ResultDownloaderRecordService;
import ci.ashamaz.hwheroku.service.ResultService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultDownloaderRecordService resultDownloaderRecordService;
    private final BetCityResultDownloader resultDownloader;
    private final BetCityEventService betCityEventService;
    private final BetCityEventRepo betCityEventRepo;

    @Override
    public void checkResults() {
        List<ResultDownloaderRecord> allByCheckTimeAfterNow = resultDownloaderRecordService.getAllByCheckTimeBeforeNow();
        log.info("found " + allByCheckTimeAfterNow.size() + " events to check results...");
        for (ResultDownloaderRecord record : allByCheckTimeAfterNow) {
            JsonObject jsonObject = resultDownloader.downloadResult(record.getUrl());
            if (jsonObject != null) {
                boolean ok = jsonObject.get("ok").getAsBoolean();
                if (ok) {
                    String score = resultDownloader.parseEventResult(jsonObject);
                    log.info("Found results for " + record.getEventId() + " = " + score);
                    if (StringUtils.hasLength(score)) {
                        BetCityEvent event = betCityEventService.getById(record.getEventId());
                        betCityEventService.addResult(event, score);
                        resultDownloaderRecordService.delete(record);
                        log.info(record.getId() + " deleted as completed");
                    }
                }
            }
        }
    }

    @Override
    public List<ResultDisplayDto> getTodaysEvents() {
        return betCityEventRepo.getTodaysEvents();
    }

    @Override
    public List<ResultDisplayDto> getLastWeekEvents() {
        return betCityEventRepo.getEventsByPeriod(LocalDateTime.now().minus(7, ChronoUnit.DAYS), LocalDateTime.now().withHour(23).withMinute(59));
    }

    @Override
    public List<ResultDisplayDto> getLastMonthEvents() {
        return betCityEventRepo.getEventsByPeriod(LocalDateTime.now().minus(30, ChronoUnit.DAYS), LocalDateTime.now().withHour(23).withMinute(59));
    }

    @Override
    public List<ResultDisplayDto> getAllEvents() {
        return betCityEventRepo.getAllEvents();
    }
}
