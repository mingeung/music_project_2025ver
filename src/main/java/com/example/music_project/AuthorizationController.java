package com.example.music_project;

import com.example.music_project.service.SpotifyAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class AuthorizationController {
    // 상태값을 랜덤으로 생성
    private final String state = UUID.randomUUID().toString().substring(0, 16);
    private final String scope = "user-read-private user-read-email";
    private final String CLIENT_ID = "2f769a7d229a45eb8337e6be85c7fdce";
    private final String REDIRECT_URI = "http://localhost:3000/callback";
    private final String CLIENT_SECRET = "a997c15df42a481ca0b0376bd48a6abc";

    @GetMapping("/spotifyLogin")
    public  String login() {
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

    @GetMapping("/callback") //여기함수는 실행이 안 되는듯
    public void callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        // 받은 값을 출력 (디버깅용)
        System.out.println("Received code: " + code);
        System.out.println("Received state: " + state);

        try {
            // URL 준비 및 연결
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 요청 메서드와 헤더 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String authHeader = "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
            connection.setRequestProperty("Authorization", authHeader);

            // POST 파라미터 설정
            String postData = "grant_type=authorization_code"
                    + "&code=" + code
                    + "&redirect_uri=" + REDIRECT_URI;

            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 처리
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // 응답 출력
                    System.out.println("Response: " + response.toString());
                }
            } else {
                System.out.println("Error: " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

