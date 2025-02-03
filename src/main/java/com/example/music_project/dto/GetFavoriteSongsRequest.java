package com.example.music_project.dto;

public class GetFavoriteSongsRequest {
    public Long memberId;

    public GetFavoriteSongsRequest(String trackId, Long memberId) {
        this.memberId = memberId;
    }
}
