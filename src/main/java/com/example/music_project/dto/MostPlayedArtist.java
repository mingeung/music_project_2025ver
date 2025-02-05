package com.example.music_project.dto;

public class MostPlayedArtist {
    public String artistId;
    public Long count;

    public MostPlayedArtist(String artistId, Long count) {
        this.artistId = artistId;
        this.count = count;
    }
}
