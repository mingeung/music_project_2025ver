package com.example.music_project.dto;

import java.time.LocalDateTime;


public class PostPlayingRequest {

    public String trackId;
//    public String memberId;
    public LocalDateTime date;
    public String artistName;
    public String trackName;
    public String artistId;

    public PostPlayingRequest(String trackId,  LocalDateTime date, String artistName, String artistId,String trackName) {
        this.trackId = trackId;
        this.date = date;
        this.artistName = artistName;
        this.artistId = artistId;
        this.trackName = trackName;
    }
}
