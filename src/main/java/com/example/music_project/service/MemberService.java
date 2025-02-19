package com.example.music_project.service;

import com.example.music_project.OAuth2AuthenticationSuccessHandler;
import com.example.music_project.properties.AccessTokenStore;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor

public class MemberService {
    SpotifyAuthService spotifyAuthService;
    AccessTokenStore accessTokenStore;

    public String getMyInfo() {

//        String accessToken = spotifyAuthService.accessToken();
        String accessToken = accessTokenStore.getAccessToken();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");
        headers.add("Accept-Language", "ko-KR");

        String body = "";

        String url = "https://api.spotify.com/v1/me";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(
                url, HttpMethod.GET, requestEntity, String.class
        );


        String response = responseEntity.getBody();

        return response;

    }
}
