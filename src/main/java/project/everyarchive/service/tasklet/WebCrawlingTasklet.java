package project.everyarchive.service.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import project.everyarchive.entity.Announcement;
import project.everyarchive.service.AnnouncementService;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebCrawlingTasklet implements Tasklet {

    private final AnnouncementService announcementService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Date date = new Date();
        List<Announcement> entityList = new ArrayList<>();

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        log.info("execute IN");
        announcementService.deleteAll();

        int cnt = 0;
        int pageNo = 1;
        while (true) {
            try {
                Document springBootSearchDoc =
                        Jsoup.connect("https://www.saramin.co.kr/zf_user/search?searchType=search&searchword=SPRING+BOOT&exp_cd=1%2C2&exp_min=0&exp_max=2&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&loc_mcd=101000%2C102000&panel_type=&search_optional_item=y&search_done=y&panel_count=y&preview=y&recruitPage="
                                + pageNo
                                + "&recruitSort=relation&recruitPageCount=3&inner_com_type=&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=y").get();
                int childNo = 0;


                if (springBootSearchDoc.getElementsByClass("item_recruit").get(childNo).select("a").isEmpty()) {
                    break;
                }

                while (true) {
                    try {
                        Element element = springBootSearchDoc.getElementsByClass("item_recruit").get(childNo);
                        String title = element.select("div.area_job > h2 > a").attr("title");
                        String company = element.select("div.area_corp > strong > a").first().text();
                        String href = element.select("div.area_job > h2.job_tit > a").attr("abs:href");
                        String jHref = "https://www.jobplanet.co.kr/search?query=" + URLEncoder.encode(company, "UTF-8") + "&category=search_new&search_keyword_hint_id=&_rs_con=seach&_rs_act=keyword_search";
                        log.info("title : {}", title);
                        log.info("company : {}", company);
                        log.info("href : {}", href);
                        log.info("잡플 : {}", jHref);
//                        Document jpDoc = Jsoup.connect("https://www.jobplanet.co.kr/search?query=" + URLEncoder.encode(element.select("div.area_corp > strong > a").first().text(), "UTF-8") + "&category=search_new&search_keyword_hint_id=&_rs_con=seach&_rs_act=keyword_search").get();
//                        log.info("rate : {}", jpDoc.getElementsByClass("tit").first().text() + "/" + jpDoc.getElementsByClass("rate_ty02").first().val());
                        Announcement entity=Announcement.builder()
                                .title(title)
                                .company(company)
                                .slink(href)
                                .jlink(jHref)
                                .date(formattedDate)
                                .build();
                        entityList.add(entity);
                        childNo++;
                        cnt++;
                    } catch (Exception e) {
                        break;
                    }

                }
                pageNo++;
                log.info("CNT : {}", cnt);

            } catch (Exception e) {
                log.info(e.getMessage());
                break;
            }
        }

        announcementService.saveAll(entityList);

        log.info("총 공고 수 : {}", cnt);

        return RepeatStatus.FINISHED;
    }
}
