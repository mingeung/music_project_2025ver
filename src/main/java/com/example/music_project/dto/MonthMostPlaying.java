package com.example.music_project.dto;

public class MonthMostPlaying {
    public String trackId;
    public Long count;

    public MonthMostPlaying(String trackId, Long count) {
        this.trackId = trackId;
        this.count = count;
    }
}
