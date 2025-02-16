package com.example.music_project;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.SessionFixationConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Getter
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //기본 설정 => 나중에 차차 공부
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //requestMatchers()에 적는 url은 로그인 됐는지 검사를 하지 않겠다.
        httpSecurity.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/api").permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated());
        //중요 -> 소셜로그인 버튼을 누르면 여기서부터 로직이 시작됨
        //oauth2Login을 실행하면 => code를 받아서 accessToken을 받아오는 것까지를 자동으로 실행하고 userInfoEndpoint() 안에 들어갈 oAuth2UserService가 자동실행.
        //그 중에서도 loadUser가 자동으로 실행
        httpSecurity.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(oAuth2UserService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
                //위의 과정이 다 끝나면 successHandler 안에 있는 로직이 실행됨. 정확히는 그 안에 있는 onAuthenticationSuccess가 자동 실행됨
        );
        //세션 고정 방지 => 나중에 설명
        httpSecurity.sessionManagement((session) -> session
                .sessionFixation(SessionFixationConfigurer::newSession));

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        //이 코드를 적으면 @CrossOrigin은 전부 제거를 해야 한다.
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setExposedHeaders(Arrays.asList("Location", "Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
