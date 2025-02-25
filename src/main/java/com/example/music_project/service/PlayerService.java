package com.example.music_project.service;

import com.example.music_project.properties.AccessTokenStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Log4j2

public class PlayerService {
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

    private String sendRequest(String url,  HttpMethod method, HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getRecentlyPlayed() {
        String url = "https://api.spotify.com/v1/me/player/recently-played?limit=3";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.GET, entity);
    }

    public String skipToPrevious(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/previous?device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        String response = sendRequest(url, HttpMethod.POST, entity);  // POST 요청을 보냄
        return response;
    }
    public String skipToNext(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/next?device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        String response = sendRequest(url, HttpMethod.POST, entity);  // POST 요청을 보냄
        return response;
    }

    public String getPlaybackState() {
        String url = "https://api.spotify.com/v1/me/player";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.GET, entity);
    }

    public String getAlbum(String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        String response = sendRequest(url, HttpMethod.GET, entity);
        return albumEncoding(response);
    }

    public String getAllInfo(String href) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(href, HttpMethod.GET, entity);
    }

    public String getUserQueue() {
        String url = "https://api.spotify.com/v1/me/player/queue";
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.GET, entity);
    }

    public String postUserQueue(String uri, String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/queue?uri=" + uri + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.POST, entity);
    }

    public String playPause(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/pause?device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.PUT, entity);
    }

    public String  seekToPosition( Integer positionMs, String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/seek?position_ms=" + positionMs + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.PUT, entity);
    }

    public String playStart(String deviceId, String uris, Integer positionMs) {
        String url = "https://api.spotify.com/v1/me/player/play?device_id=" + deviceId;
        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris.split(","));
        if (positionMs != null) {
            requestBody.put("position_ms", positionMs);
        }
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), createHeaders());
        return sendRequest(url, HttpMethod.PUT, entity);
    }



    public String repeatMode(String deviceId, String state) {
        String url = "https://api.spotify.com/v1/me/player/repeat?state=" + state + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.PUT, entity);
    }

    public String shuffleMode(String deviceId, String state) {
        String url = "https://api.spotify.com/v1/me/player/shuffle?state=" + state + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendRequest(url, HttpMethod.PUT, entity);
    }

    private String albumEncoding(String response) {
        String albumHref = "Unknown Device";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode deviceIdNode = rootNode.path("tracks").path("href");

            if (deviceIdNode != null && !deviceIdNode.isNull()) {
                albumHref = deviceIdNode.asText();
            }
        } catch (Exception e) {
            log.error("Error parsing JSON response: ", e);
        }
        return albumHref;
    }
}