package com.example.music_project.dto;

public class PostFavoriteSongRequest {

    public String trackId;
    public Long memberId;
;

    public PostFavoriteSongRequest(String trackId, Long memberId) {
        this.trackId = trackId;
        this.memberId = memberId;



}}
