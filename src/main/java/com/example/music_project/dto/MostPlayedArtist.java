package com.example.music_project.dto;

public class MostPlayedArtist {
    public String artistName;
    public String artistId;
    public Long count;


    public MostPlayedArtist(String artistName, String artistId, Long count) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.count = count;
    }
}
