package com.example.music_project.dto;

public class DeleteFavoriteSongRequest {
    public String trackId;
    public Long memberId;

    public DeleteFavoriteSongRequest(String trackId, Long memberId) {
        this.trackId = trackId;
        this.memberId = memberId;
    }

}
