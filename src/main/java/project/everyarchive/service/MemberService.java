package project.everyarchive.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.Role;
import project.everyarchive.repository.MemberRepository;
import project.everyarchive.repository.QMemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final QMemberRepository qMemberRepository;
//    private final BCryptPasswordEncoder pwEncoder;

    public Member findMemberByKakaoId(Long kakaoId) {
        return qMemberRepository.findMemberByKakaoId(kakaoId);
    }

    public Member createMemberFromKakao(Long kakaoId, String nickname, String email) {
        Member member = Member.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .email(email)
                .role(Role.USER)
//                .password(pwEncoder.encode(String.valueOf(kakaoId)))
                .build();

        return memberRepository.save(member);
    }

    @Transactional
    public void updateRole(Long kakaoId, String role) {
        Member member = qMemberRepository.findMemberByKakaoId(kakaoId);
        if (role.equals("ADMIN")) {
            member.updateRole(Role.ADMIN);
        } else if (role.equals("TESTER")) {
            member.updateRole(Role.TESTER);
        } else {
            member.updateRole(Role.USER);
        }
    }
    public void kakaoLogin(Member member, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("member", member);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("member", null);
        log.info("session : {}", session.getAttribute("member"));
    }

}
