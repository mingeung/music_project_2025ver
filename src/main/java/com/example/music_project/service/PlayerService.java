package com.example.music_project.service;


import com.example.music_project.OAuth2AuthenticationSuccessHandler;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Log4j2


public class PlayerService {
    AccessTokenStore accessTokenStore;

    private String sendRequest(String url, HttpMethod method, String body) {
        String accessToken = accessTokenStore.getAccessToken();
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, method, requestEntity, String.class);
        return responseEntity.getBody();
    }


    public String skipToPrevious(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/previous?device_id=" + deviceId;
        String response = sendRequest(url, HttpMethod.POST, "");  // POST 요청을 보냄
        return response;
    }
    public String skipToNext(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/next?device_id=" + deviceId;
        String response = sendRequest(url, HttpMethod.POST, "");  // POST 요청을 보냄
        return response;
    }


    public String getPlaybackState() {
        String url = "https://api.spotify.com/v1/me/player";
        String response = sendRequest(url, HttpMethod.GET, "");
        return playerEncoding(response);
    }

    private String playerEncoding(String response) {
        String deviceId = "Unknown Device";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode deviceIdNode = rootNode.path("device").path("id");

            if (deviceIdNode != null && !deviceIdNode.isNull()) {
                deviceId = deviceIdNode.asText();
            }
        } catch (Exception e) {
            log.error("Error parsing JSON response: ", e);
        }
        return deviceId;
    }

    public String getAlbum(String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId;
        String response = sendRequest(url, HttpMethod.GET, "");
        return albumEncoding(response);
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

    public String getAllInfo(String href) {
        return sendRequest(href, HttpMethod.GET, "");
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = accessTokenStore.getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private String sendPutRequest(String url, HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            log.info(response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Error with PUT request: ", e);
            return "Error: " + e.getMessage();
        }
    }

    public String playStart(String deviceId, String uris) {
        String url = "https://api.spotify.com/v1/me/player/play?device_id=" + deviceId;
        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris.split(","));

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), createHeaders());
        return sendPutRequest(url, entity);
    }

    public String playPause(String deviceId) {
        String url = "https://api.spotify.com/v1/me/player/pause?device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendPutRequest(url, entity);
    }

    public String repeatMode(String deviceId, String state) {
        String url = "https://api.spotify.com/v1/me/player/repeat?state=" + state + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendPutRequest(url, entity);
    }

    public String shuffleMode(String deviceId, String state) {
        String url = "https://api.spotify.com/v1/me/player/shuffle?state=" + state + "&device_id=" + deviceId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return sendPutRequest(url, entity);
    }

}