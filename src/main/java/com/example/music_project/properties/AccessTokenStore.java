package com.example.music_project.properties;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenStore {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
