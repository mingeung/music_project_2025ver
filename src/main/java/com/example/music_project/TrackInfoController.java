package com.example.music_project;

import com.example.music_project.dto.GetArtistInfoRequest;
import com.example.music_project.dto.GetTrackInfoRequest;
import com.example.music_project.service.TrackInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class TrackInfoController {
    TrackInfoService trackInfoService;

    @GetMapping("/artist-info/{artistId}")
    public ResponseEntity<?> getArtistInfo(@PathVariable String artistId) {
        return ResponseEntity.status(HttpStatus.OK).body(trackInfoService.getArtistInfo(artistId));
    }

    @GetMapping("/track-info/{trackId}")
    public ResponseEntity<?> getTrackInfo(@PathVariable String trackId) {
        return ResponseEntity.status(HttpStatus.OK).body(trackInfoService.getTrackInfo(trackId));
    }
}
