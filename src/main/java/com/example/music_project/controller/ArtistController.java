package com.example.music_project.controller;

import com.example.music_project.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ArtistController {

    ArtistService artistService;

    @GetMapping("/artistTopTracks/{artistId}")
    public ResponseEntity<?> getArtistTopTracks(@PathVariable String artistId) {
        String artistTopTracks = artistService.getArtistTopTracks(artistId);
        return ResponseEntity.status(HttpStatus.OK).body(artistTopTracks);
    }
}
