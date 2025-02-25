package com.example.music_project.service;

import com.example.music_project.properties.AccessTokenStore;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AlbumService {
    AccessTokenStore accessTokenStore;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = accessTokenStore.getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");
        headers.add("Accept-Language", "ko-KR");
        return headers;
    }

    private String sendRequest(String url, HttpMethod method, HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getNewReleasedAlbum() {
        String url = "https://api.spotify.com/v1/browse/new-releases?limit=5";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.GET, entity);
    }
}
