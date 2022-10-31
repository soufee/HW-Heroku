package ci.ashamaz.hwheroku.service.impl;

import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;
import ci.ashamaz.hwheroku.repo.ResultDownloaderRecordRepo;
import ci.ashamaz.hwheroku.service.ResultDownloaderRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ResultDownloaderRecordServiceImpl implements ResultDownloaderRecordService {
    private final ResultDownloaderRecordRepo resultDownloaderRecordRepo;

    @Override
    public List<ResultDownloaderRecord> getAllByCheckTimeBeforeNow() {
        return getAllByCheckTimeBefore(LocalDateTime.now());
    }

    @Override
    public List<ResultDownloaderRecord> getAllByCheckTimeBefore(LocalDateTime time) {
        return resultDownloaderRecordRepo.getAllByCheckTimeBefore(time);
    }

    @Override
    public ResultDownloaderRecord save(ResultDownloaderRecord record) {
        ResultDownloaderRecord byEventId = getByEventId(record.getEventId());
        if (byEventId != null) {
            throw new IllegalArgumentException("Result downloader for this event is already registered");
        }
        return resultDownloaderRecordRepo.save(record);
    }

    @Override
    public void delete(ResultDownloaderRecord record) {
        resultDownloaderRecordRepo.delete(record);
    }

    @Override
    public ResultDownloaderRecord getByEventId(Long event) {
        return resultDownloaderRecordRepo.getByEventId(event);
    }
}
