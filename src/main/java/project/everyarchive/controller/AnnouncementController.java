package project.everyarchive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.everyarchive.config.batch.BatchScheduler;
import project.everyarchive.dto.AnnouncementDto;
import project.everyarchive.entity.Announcement;
import project.everyarchive.service.AnnouncementService;

import java.util.List;

@Controller
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final BatchScheduler batchScheduler;

    @GetMapping("/list")
    public String list() {
        return "announcement/list";
    }

    @ResponseBody
    @GetMapping("/getList")
    public List<Announcement> getList() {
        return announcementService.getAnnouncements();
    }

    @ResponseBody
    @GetMapping("/batchStart")
    public String batchStart() {
        try {
            batchScheduler.runBatch();
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
