package com.example.music_project.dto;

public class GetTrackInfoRequest {
    public String accessToken;
    public String trackId;

    public GetTrackInfoRequest(String accessToken, String trackId) {
        this.accessToken = accessToken;
        this.trackId = trackId;
    }

}
