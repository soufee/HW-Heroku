package ci.ashamaz.hwheroku.scheduler;

import ci.ashamaz.hwheroku.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {
    @Value("${result.downloader.enabled:false}")
    boolean taskEnabled;
    private final ResultService resultService;

    @Scheduled(cron = "0 */5 * * * ?")
    public void run() {
        if (taskEnabled) {
            log.info("==>checkResults сработал " + LocalDateTime.now());
            resultService.checkResults();
        } else {
            log.info("result download task is disabled");
        }
    }
}
