package project.everyarchive.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import project.everyarchive.service.AnnouncementService;
import project.everyarchive.service.tasklet.WebCrawlingTasklet;

//@EnableBatchProcessing
@Configuration
@Slf4j
@RequiredArgsConstructor
public class AnnouncementBatch {
    private final AnnouncementService announcementService;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job announcementBatchJob() {
        log.info("announcementBatchJob IN");
        return new JobBuilder("announcementBatchJob", jobRepository)
                .start(step1(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,PlatformTransactionManager platformTransactionManager) {
        log.info("step1 IN");
        return new StepBuilder("webCrawling", jobRepository)
                .tasklet(new WebCrawlingTasklet(announcementService), platformTransactionManager)
                .build();

    }
}
