package project.everyarchive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.Role;
import project.everyarchive.repository.QMemberRepository;
import project.everyarchive.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/member/setRole")
    public String setRole(@RequestParam Long kakaoId, @RequestParam String role) {
        try {
            memberService.updateRole(kakaoId, role);
            return "Role Has Been Updated";
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
