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
        log.info("tracks:" + tracks);

        return tracks;
    }

    public List<JsonNode> getTrackItems(String jsonResponse) {
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
                // items 배열에서 각각의 트랙 아이템을 리스트에 추가
                for (JsonNode item : itemsNode) {
                    trackItems.add(item);
                }
            }
        } catch (Exception e) {
            log.error("Error parsing JSON response: ", e);
        }
        return trackItems;
    }
    //음원 재생하기
//    public List<JsonNode> getTrackPlay(String trackId, boolean isPlaying) {
//
//        String accessToken = spotifyAuthService.accessToken();
//
//        RestTemplate rest = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Host", "api.spotify.com");
//        headers.add("Content-Type", "application/json");
//        String body = "";
//
//        //추가한 부분
//        String encodedTrackName = URLEncoder.encode(trackId, StandardCharsets.UTF_8);
//        String url = "https://api.spotify.com/v1/tracks/" + trackId;
//
//
//        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
//
//        ResponseEntity<String> responseEntity = rest.exchange(
//                url, HttpMethod.GET, requestEntity, String.class
//        );
//
//
//        String response = responseEntity.getBody();
//
//        try {
//            // JSON 응답을 JsonNode로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(response);
//
//            // preview_url을 추출
//            String previewUrl = rootNode.path("preview_url").asText();
//
//            // previewUrl이 있는 경우, 재생/정지 처리
//            if (previewUrl != null && !previewUrl.isEmpty()) {
//                if (isPlaying) {
//                    playAudio(previewUrl);  // playAudio 메서드는 음원을 재생하는 로직
//                } else {
//                    stopAudio();  // stopAudio 메서드는 음원을 멈추는 로직
//                }
//            } else {
//                System.out.println("No preview URL available for the selected track");
//            }
//        } catch (Exception e) {
//            System.out.println("Error parsing the response: " + e.getMessage());
//        }
//
//    }

}

