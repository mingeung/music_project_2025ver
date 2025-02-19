package com.example.music_project.controller;


import com.example.music_project.dto.PutPlayStart;
import com.example.music_project.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
public class PlayerController {
    PlayerService playerService;

    @GetMapping("/playbackState")
    public ResponseEntity<?> getPlaybackState() {
        String result = playerService.getPlaybackState();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //앨범에서 track부분 불러오기
    @GetMapping("/album/{albumId}")
    public ResponseEntity<?> getAlbum(@PathVariable String albumId) {
        String href = playerService.getAlbum(albumId);
        //여기서 가지고 온 href를 다시 해야하나?
        String info = playerService.getAllInfo(href);
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }


    //play 시작하기
    @PutMapping("playStart/{deviceId}")
    public ResponseEntity<?> startPlaying(@PathVariable String deviceId, @RequestBody PutPlayStart putPlayStart) {
        String uris = putPlayStart.uris;
        String playstart = playerService.playStart(deviceId, uris);
        return ResponseEntity.status(HttpStatus.OK).body(playstart);
    }


    //play 멈추기
    @PutMapping("playPause/{deviceId}")
    public ResponseEntity<?> pausePlaying(@PathVariable String deviceId) {
        String playpause = playerService.playPause(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(playpause);
    }
}
