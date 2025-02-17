package com.example.music_project.service;


import com.example.music_project.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.music_project.exception.CustomException;

@Service
@AllArgsConstructor

public class TrackInfoService {
    private final SpotifyAuthService spotifyAuthService;
    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 인스턴스 생성

    // 공통 API 요청 메서드
    public String fetchFromSpotify(String endpoint) {
        String accessToken = spotifyAuthService.accessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://api.spotify.com/v1/" + endpoint, HttpMethod.GET, requestEntity, String.class
        );

        System.out.println("Response status: " + responseEntity.getStatusCode().value());
        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();
    }

    // 아티스트 정보 가져오기
    public String getArtistInfo(String artistId) {
        try {
            return fetchFromSpotify("artists/" + artistId);
        } catch(HttpClientErrorException.BadRequest e) {
            throw new CustomException(ErrorCode.ARTIST_NOT_FOUND);
        }
    }

    // 트랙 정보 가져오기
    public String getTrackInfo(String trackId) {
        try {
//            return fetchFromSpotify("tracks/" + trackId);
            return fetchFromSpotify("tracks?ids=" + trackId);
        }
        catch(HttpClientErrorException.BadRequest e) {
            throw new CustomException((ErrorCode.TRACK_NOT_FOUND));
        }
    }
    //여러 트랙 한번에 가져오기
    public String getSeveralTrackInfo(String trackIds) {
        return fetchFromSpotify("tracks?ids=" + trackIds);
    }
}