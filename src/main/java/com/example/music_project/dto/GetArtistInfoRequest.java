package com.example.music_project.dto;

public class GetArtistInfoRequest {
    public String accessToken;
    public String artistId;

    public GetArtistInfoRequest(String accessToken, String artistId) {
        this.accessToken = accessToken;
        this.artistId = artistId;
    }

}
