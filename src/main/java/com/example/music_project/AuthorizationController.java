package com.example.music_project;

import com.example.music_project.service.SpotifyAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class AuthorizationController {

    @GetMapping("/spotifyLogin")
    public  String login() {
        // 상태값을 랜덤으로 생성
        String state = UUID.randomUUID().toString().substring(0, 16);
        String scope = "user-read-private user-read-email";
        String CLIENT_ID = "8a0fd00ee8c6474d8f0152da30661e0b";
        String REDIRECT_URI = "http://localhost:3000/callback";

        // 쿼리 문자열을 생성
        String redirectUrl = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("scope", scope)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("state", state)
                .toUriString();
        log.info("redirectUrl: {}", redirectUrl);
        return redirectUrl;
    }
}
