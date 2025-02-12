package com.example.music_project.service;

import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;


@Service
@AllArgsConstructor

public class  SpotifyAuthService {
    private static final String CLIENT_ID = "8a0fd00ee8c6474d8f0152da30661e0b";
    private static final String CLIENT_SECRET = "788ec06786be408bad8b494c42ed4d56";

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

}
