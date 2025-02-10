package com.example.music_project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Log4j2

public class SearchService {
    SpotifyAuthService spotifyAuthService;

    public List<JsonNode> getSearchResult(String trackName) {

        String accessToken = spotifyAuthService.accessToken();
        log.info("accessToken:" + accessToken);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");
        headers.add("Content-Type", "application/json");
        String body = "";

        //추가한 부분
        String encodedTrackName = URLEncoder.encode(trackName, StandardCharsets.UTF_8);
        String url = "https://api.spotify.com/v1/search?q=" + encodedTrackName + "&type=track";


        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(
                url, HttpMethod.GET, requestEntity, String.class
        );


        String response = responseEntity.getBody();
        List<JsonNode> tracks = getTrackItems(response);

        return tracks;
    }

    public List<JsonNode> getTrackItems(String jsonResponse)  {

        // JSON 배열을 리스트로 변환
        List<JsonNode> trackItems = new ArrayList<>();
        try {
            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // "tracks.items"에 해당하는 JsonNode 가져오기
            JsonNode itemsNode = rootNode.path("tracks").path("items");

        if (itemsNode.isArray()) {
            for (JsonNode item : itemsNode) {
                trackItems.add(item);
            }
        }
        } catch (Exception e) {
            log.error("Error parsing JSON response: ", e);
        }
        return trackItems;

    }


}
