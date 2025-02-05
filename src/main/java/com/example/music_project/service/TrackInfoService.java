package com.example.music_project.service;


import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor

public class TrackInfoService {
    public String getArtistInfo(String accessToken, String artistId) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");//이건 왜 붙이는지 ?
        headers.add("Content-Type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://api.spotify.com/v1/artists/" + artistId, HttpMethod.GET, requestEntity, String.class);
        HttpStatusCode httpstatus = responseEntity.getStatusCode();
        int status = httpstatus.value();//상태코드가 들어갈 status 변수
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public String getTrackInfo(String accessToken, String trackId) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Host", "api.spotify.com");//이건 왜 붙이는지 ?
        headers.add("Content-Type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://api.spotify.com/v1/tracks/" + trackId, HttpMethod.GET, requestEntity, String.class);
        HttpStatusCode httpstatus = responseEntity.getStatusCode();
        int status = httpstatus.value();//상태코드가 들어갈 status 변수
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }
}
