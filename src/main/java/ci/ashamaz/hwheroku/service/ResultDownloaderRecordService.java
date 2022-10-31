package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultDownloaderRecordService {
    List<ResultDownloaderRecord> getAllByCheckTimeBeforeNow();
    List<ResultDownloaderRecord> getAllByCheckTimeBefore(LocalDateTime time);
    ResultDownloaderRecord save(ResultDownloaderRecord record);
    void delete(ResultDownloaderRecord record);
    ResultDownloaderRecord getByEventId(Long event);

}
