package com.example.music_project.dto;

public class MostPlaying {
    public String trackId;
    public Long count;

    public MostPlaying(String trackId, Long count) {
        this.trackId = trackId;
        this.count = count;
    }
}
