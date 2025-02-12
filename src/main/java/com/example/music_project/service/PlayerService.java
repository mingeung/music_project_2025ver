package com.example.music_project.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

}
