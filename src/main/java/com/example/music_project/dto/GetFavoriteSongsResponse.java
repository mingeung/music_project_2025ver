package com.example.music_project.dto;

import com.example.music_project.domain.FavoriteSongs;

import java.util.List;

public class GetFavoriteSongsResponse {

    public List<FavoriteSongs> favoriteSongsList;


    public GetFavoriteSongsResponse(List<FavoriteSongs> favoriteSongsList) {
        this.favoriteSongsList = favoriteSongsList;
    }

}
