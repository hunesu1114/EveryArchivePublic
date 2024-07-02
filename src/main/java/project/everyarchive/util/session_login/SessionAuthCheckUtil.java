package project.everyarchive.util.session_login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.Role;

@Component
public class SessionAuthCheckUtil {

    public Role checkSessionAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");
        if (member == null) {
            return null;
        } else {
            return member.getRole();
        }

    }
}
