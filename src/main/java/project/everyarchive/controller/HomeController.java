package project.everyarchive.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import project.everyarchive.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @ResponseBody
    @PostMapping("/logout/custom")
    public String logout(HttpServletRequest request) {
        try {
            memberService.logout(request);
            return "ok";
        } catch (Exception e) {
            return "fail";
        }
    }


    @ResponseBody
    @GetMapping("/checkAuth")
    public String checkAuth(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("member") != null) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            return "false";
        }
    }


}
