package com.example.music_project.dto;

import java.time.LocalDateTime;

public class PostPlayingRequest {

    public String trackId;
    public Long memberId;
    public LocalDateTime date;
    public String artistId;

    public PostPlayingRequest(String trackId, Long memberId, LocalDateTime date, String artistId) {
        this.trackId = trackId;
        this.memberId = memberId;
        this.date = date;
        this.artistId = artistId;
    }
}
