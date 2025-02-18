package com.example.music_project.service;


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
    SpotifyAuthService spotifyAuthService;
    public String getPlaybackState(){

        String accessToken = spotifyAuthService.accessToken();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        String url = "https://api.spotify.com/v1/me/player";
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(
                url, HttpMethod.GET, requestEntity, String.class
        );


        String response = responseEntity.getBody();
        String deviceId = playerEncoding(response);

        return deviceId;
    }

    public String playerEncoding(String response) {
        {
            String deviceId = "Unknown Device"; // 기본값 설정
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                // "device.id" 값 가져오기
                JsonNode deviceIdNode = rootNode.path("device").path("id");

                // 값이 존재하고 null이 아닐 경우 문자열로 설정
                if (deviceIdNode != null && !deviceIdNode.isNull()) {
                    deviceId = deviceIdNode.asText();
                }
            } catch (Exception e) {
                log.error("Error parsing JSON response: ", e);
            }
            return deviceId;
    }

    /// /////

    }
    public String getAlbum(String albumId){

        String accessToken = spotifyAuthService.accessToken();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        String url = "https://api.spotify.com/v1/albums/" + albumId;
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(
                url, HttpMethod.GET, requestEntity, String.class
        );


        String response = responseEntity.getBody();
        String albumHref = albumEncoding(response);

        return albumHref;
    }

    public String albumEncoding(String response) {
        {
            String albumHref = "Unknown Device"; // 기본값 설정
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                // "device.id" 값 가져오기
                JsonNode deviceIdNode = rootNode.path("tracks").path("href");

                // 값이 존재하고 null이 아닐 경우 문자열로 설정
                if (deviceIdNode != null && !deviceIdNode.isNull()) {
                    albumHref = deviceIdNode.asText();
                }
            } catch (Exception e) {
                log.error("Error parsing JSON response: ", e);
            }
            return albumHref;
        }
    }
    //request에 대한 모든 정보 받기
    public String getAllInfo(String href){

        String accessToken = spotifyAuthService.accessToken();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        String url = href;
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(
                url, HttpMethod.GET, requestEntity, String.class
        );


        String response = responseEntity.getBody();

        return response;
    }

    //음원 재생하기
    public String playStart(String deviceId,String accessToken, String uris) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        String token = spotifyAuthService.accessToken();
        // 1. JSON 객체 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("uris", uris.split(","));  // uris가 쉼표로 구분된 문자열로 전달된다고 가정

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        String url = "https://api.spotify.com/v1/me/player/play?device_id=" + deviceId;

        String body = "{\"uris\": [" + uris + "]}";

        System.out.println("Access Token: " + accessToken);
        System.out.println("Request URL: " + url);
        System.out.println("Request Body: " + body);

        // 3. HttpEntity 객체에 헤더와 본문 설정
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // 4. RestTemplate 사용하여 PUT 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    //음원 정지
    public String playPause(String deviceId,String accessToken) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");

        String url = "https://api.spotify.com/v1/me/player/pause?device_id=" + deviceId;

        // 3. HttpEntity 객체에 헤더와 본문 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 4. RestTemplate 사용하여 PUT 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
