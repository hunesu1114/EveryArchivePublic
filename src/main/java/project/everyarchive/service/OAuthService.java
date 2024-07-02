package project.everyarchive.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import project.everyarchive.dto.KakaoTokenResponse;
import project.everyarchive.dto.KakaoUserInfoResponse;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {
    /**
     * RestTemplate은 maintenece mode로 들어간다고 해서 WebClient 사용
     * WebClient 는 RestTemplate과 달리 non-blocking과 비동기화가 가능함.
     * 내 프로젝트에서는 RestTemplate을 써도 상관 없으나 스프링이 밀어주는게 WebClient이기도 하고 해서 WebClient 씀
     */
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String USER_INFO_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    /**
     * clientSecret 활성화 가능 -> KakaoDevelopers
     */

    public KakaoTokenResponse getToken(String code) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(TOKEN_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", code);

        // webClient.post() : 비동기 방식으로 HTTP POST 요청을 보냄
        // retrieve() : 응답을 받음
        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(builder.toUriString())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);
        return response.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String token) {
        String uri = USER_INFO_URI;

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }


}
