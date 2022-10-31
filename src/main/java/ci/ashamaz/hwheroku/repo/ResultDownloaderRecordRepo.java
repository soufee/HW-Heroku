package ci.ashamaz.hwheroku.repo;

import ci.ashamaz.hwheroku.entity.ResultDownloaderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultDownloaderRecordRepo extends JpaRepository<ResultDownloaderRecord, Long> {
    List<ResultDownloaderRecord> getAllByCheckTimeBefore(LocalDateTime time);
    ResultDownloaderRecord getByEventId(Long event);
}
