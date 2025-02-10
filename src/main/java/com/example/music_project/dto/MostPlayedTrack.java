package com.example.music_project.dto;

public class MostPlayedTrack {
    public String trackName;
    public Long count;

    public MostPlayedTrack(String trackName, Long count) {
        this.trackName = trackName;
        this.count = count;
    }
}
