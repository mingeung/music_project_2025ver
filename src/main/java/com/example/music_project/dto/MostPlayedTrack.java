package com.example.music_project.dto;

public class MostPlayedTrack {
    public String trackId;
    public Long count;

    public MostPlayedTrack(String trackId, Long count) {
        this.trackId = trackId;
        this.count = count;
    }
}
