package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.downloader.BetCityResultDownloader;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;
import ci.ashamaz.hwheroku.service.BetCityEventService;
import ci.ashamaz.hwheroku.service.ResultDownloaderRecordService;
import ci.ashamaz.hwheroku.service.ResultService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ResultDownloaderRecordService resultDownloaderRecordService;
    private final BetCityResultDownloader resultDownloader;
    private final BetCityEventService betCityEventService;

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
}
