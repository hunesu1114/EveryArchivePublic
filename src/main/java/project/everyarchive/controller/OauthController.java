package project.everyarchive.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.everyarchive.dto.KakaoTokenResponse;
import project.everyarchive.dto.KakaoUserInfoResponse;
import project.everyarchive.entity.Member;
import project.everyarchive.service.MemberService;
import project.everyarchive.service.OAuthService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    private final OAuthService oAuthService;
    private final MemberService memberService;


    //    "회원이 소셜 로그인을 마치면 자동으로 실행되는 API.
    //    인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다.사용자 정보를 이용하여 서비스에 회원가입."
    @GetMapping("/oauth")
    public String kakaoOauth(@RequestParam("code") String code, HttpServletRequest request) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = oAuthService.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = oAuthService.getUserInfo(kakaoTokenResponse.getAccess_token());
        log.info("회원 정보 입니다.{}",userInfo);

        Member memberByKakaoId = memberService.findMemberByKakaoId(userInfo.getId());
        if(memberByKakaoId==null){
            memberByKakaoId=memberService.createMemberFromKakao(userInfo.getId(), userInfo.getKakao_account().getProfile().getNickname(), userInfo.getKakao_account().getEmail());
        }

        // 세션 처리
        memberService.kakaoLogin(memberByKakaoId,request);

        return "redirect:/";
    }


    @ResponseBody
    @GetMapping("/getOAuthParam")
    public Map<String, String> getOAuthParam() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("restapikey", CLIENT_ID);
        responseMap.put("redirectUri", REDIRECT_URI);
        return responseMap;
    }
}
