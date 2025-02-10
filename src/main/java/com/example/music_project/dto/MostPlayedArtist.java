package com.example.music_project.dto;

public class MostPlayedArtist {
    public String artistName;
    public Long count;

    public MostPlayedArtist(String artistName, Long count) {
        this.artistName = artistName;
        this.count = count;
    }
}
