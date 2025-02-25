package com.example.music_project.controller;

import com.example.music_project.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AlbumController {
    AlbumService albumService;

    @GetMapping("/newReleasedAlbum")
    public ResponseEntity<?> getNewReleasedAlbum() {
        String newReleases = albumService.getNewReleasedAlbum();
        return ResponseEntity.status(HttpStatus.OK).body(newReleases);
    }
}
