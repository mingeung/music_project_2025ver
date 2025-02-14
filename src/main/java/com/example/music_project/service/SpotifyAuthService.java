package com.example.music_project.service;

import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.util.UUID;



@Service
@AllArgsConstructor

public class  SpotifyAuthService {
    private static final String CLIENT_ID = "2f769a7d229a45eb8337e6be85c7fdce";
    private static final String CLIENT_SECRET = "a997c15df42a481ca0b0376bd48a6abc";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();

    public String accessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredential = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredential.getAccessToken());
            System.out.println("access token: " + spotifyApi.getAccessToken());
            return spotifyApi.getAccessToken();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
             System.out.println("Error: " + e.getMessage());
             return "error";
         }
    }

//    public ResponseEntity<String> userAuth() {
//        String REDIRECT_URI = "http://localhost:3000/callback";
//        String state = UUID.randomUUID().toString().substring(0, 16);
//        String scope = "user-read-private user-read-email";
//
//
//        // 상태값을 랜덤으로 생성
//        String state = UUID.randomUUID().toString().substring(0, 16);
//        String scope = "user-read-private user-read-email";
//
//        // 쿼리 문자열을 생성
//        String redirectUrl = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize")
//                .queryParam("response_type", "code")
//                .queryParam("client_id", CLIENT_ID)
//                .queryParam("scope", scope)
//                .queryParam("redirect_uri", REDIRECT_URI)
//                .queryParam("state", state)
//                .toUriString();
//
//        return "redirect:" + redirectUrl;
//    }

    }
