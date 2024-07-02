package project.everyarchive.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.everyarchive.dto.MemoDto;
import project.everyarchive.entity.Memo;
import project.everyarchive.entity.Role;
import project.everyarchive.service.MemoService;
import project.everyarchive.util.session_login.SessionAuthCheckUtil;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/memoArchive")
@RequiredArgsConstructor
@Slf4j
public class MemoController {

    private final MemoService memoService;
    private final SessionAuthCheckUtil authCheckUtil;

    @GetMapping("/list")
    public String list(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role == null) {
            return "404";
        } else if (role == Role.USER) {
            return "404";
        }
        return "memo_archive/list";
    }

    @ResponseBody
    @GetMapping("/getMemo")
    public List<MemoDto> getMemo(HttpServletRequest request) {
        return memoService.findAllByMemberIdDesc(request);
    }

    @ResponseBody
    @PostMapping("/update")
    public String updateMemo(@RequestBody MemoDto dto) {
        log.info("dto.getId().getClass() => {}", dto.getId().getClass());
        try {
            memoService.updateMemo(dto);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public String deleteMemo(@RequestParam Long id) {
        try {
            memoService.deleteMemo(id);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ResponseBody
    @PostMapping("/save")
    public String saveMemo(@RequestBody MemoDto dto, HttpServletRequest request) {
        try {
            memoService.save(dto,request);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
