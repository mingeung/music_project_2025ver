package com.example.music_project.dto;

import java.util.List;

public class GetMonthMostPlayedArtistResponse {
    public List<MostPlayedArtist> playingList;

    public GetMonthMostPlayedArtistResponse(List<MostPlayedArtist> playingList) {
        this.playingList = playingList;
    }
}
