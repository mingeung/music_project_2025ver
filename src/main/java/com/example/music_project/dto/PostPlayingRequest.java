package com.example.music_project.dto;

import java.time.LocalDateTime;


public class PostPlayingRequest {

    public String trackId;
//    public String memberId;
    public LocalDateTime date;
    public String artistName;
    public String trackName;

    public PostPlayingRequest(String trackId, String memberId, LocalDateTime date, String artistName, String trackName) {
        this.trackId = trackId;
//        this.memberId = memberId;
        this.date = date;
        this.artistName = artistName;
        this.trackName = trackName;
    }
}
