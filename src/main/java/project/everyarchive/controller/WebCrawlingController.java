package project.everyarchive.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.everyarchive.config.batch.AnnouncementBatch;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class WebCrawlingController {

    private final AnnouncementBatch announcementBatch;
    private final JobLauncher jobLauncher;

    @ResponseBody
    @GetMapping("/crawl")
    public void test() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(announcementBatch.announcementBatchJob(), new JobParameters(new HashMap<>()));
    }
}
