package project.everyarchive.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final AnnouncementBatch announcementBatch;

    @Scheduled(cron = "0 0 6 * * *")
    public void runBatch() {
        log.info("============== Batch Job Start ==============");
        try {
            LocalDateTime now = LocalDateTime.now();
            String localDateTimeFormat
                    = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            jobLauncher.run(announcementBatch.announcementBatchJob(), new JobParametersBuilder()
                    .addString("dateTime", localDateTimeFormat)
                    .toJobParameters());

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        log.info("============== Batch Job End ==============");
    }
}
