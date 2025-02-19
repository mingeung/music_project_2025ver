package com.example.music_project;

import com.example.music_project.properties.AccessTokenStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2AuthorizedClientService authorizedClientService;

    private static String accessToken;  // 액세스 토큰을 저장할 변수
    @Autowired
    AccessTokenStore accessTokenStore;

//    @Override
//    @Transactional
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        log.info("OAuth 성공적");
//        getRedirectStrategy().sendRedirect(request, response,"http://localhost:3000/home");
//    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken) {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/home");

            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // OAuth2AuthorizedClient를 가져와서 accessToken 저장
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            );

            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                log.info("AccessTokenStore is null? {}", (accessTokenStore == null));
                accessTokenStore.setAccessToken(accessToken);  // 전역 저장소에 저장
                log.info("SuccessHandler's Access Token: {}", accessToken);
            }
        }
    }
}
