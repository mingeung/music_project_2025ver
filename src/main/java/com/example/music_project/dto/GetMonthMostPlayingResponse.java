package com.example.music_project.dto;

import java.util.List;

public class GetMonthMostPlayingResponse {
    public List<MostPlayedTrack> playingList;

    public GetMonthMostPlayingResponse(List<MostPlayedTrack> playingList) {
        this.playingList = playingList;
    }
}
