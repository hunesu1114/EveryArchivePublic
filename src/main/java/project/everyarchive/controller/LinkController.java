package project.everyarchive.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.everyarchive.dto.LinkDto;
import project.everyarchive.entity.Link;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.Role;
import project.everyarchive.service.LinkService;
import project.everyarchive.util.session_login.SessionAuthCheckUtil;

import java.util.List;

@Controller
@RequestMapping("/linkArchive")
@RequiredArgsConstructor
@Slf4j
public class LinkController {

    private final LinkService linkService;
    private final SessionAuthCheckUtil authCheckUtil;

    @GetMapping("/list")
    public String list(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role == null) {
            return "404";
        }
        return "link_archive/list";
    }


    /**
     * ============================= APIs Below ==================================
     * ============================= APIs Below ==================================
     */
    @ResponseBody
    @GetMapping("/getLinks")
    public List<LinkDto> getLinks(HttpServletRequest request) {
        return linkService.getLinks(request);
    }

    @ResponseBody
    @GetMapping("/getOneLink")
    public LinkDto getLink(@RequestParam Long linkId) {
        return linkService.getLink(linkId);
    }


    @ResponseBody
    @PostMapping("/save")
    public String saveLink(@RequestBody LinkDto dto, HttpServletRequest request) {
        try {
            linkService.saveLink(dto, request);
            return "ok";
        } catch (Exception e) {
            if (e.getMessage().contains("long")) {
                return "ERROR: value too long for type character varying(255)";
            }
            return "fail";
        }
    }

    @ResponseBody
    @PostMapping("/updateLink")
    public String updateLink(@RequestBody LinkDto dto, @RequestParam Long linkId, HttpServletRequest request) {
        try {
            linkService.updateLink(linkId, dto, request);
            return "ok";
        } catch (Exception e) {
            return "fail";
        }
    }

    @ResponseBody
    @PostMapping("/deleteLink")
    public String deleteLink(@RequestParam Long id, HttpServletRequest request) {
        try {
            linkService.deleteLink(id, request);
            return "ok";
        } catch (Exception e) {
            return "fail";
        }
    }

}
