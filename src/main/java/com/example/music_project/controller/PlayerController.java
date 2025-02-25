package com.example.music_project.controller;

import com.example.music_project.dto.PutPlayStart;
import com.example.music_project.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        Integer positionMs = putPlayStart.position_ms;
        log.info("position: " + positionMs);
        String playstart = playerService.playStart(deviceId, uris, positionMs);
        return ResponseEntity.status(HttpStatus.OK).body(playstart);
    }
    //play 멈추기
    @PutMapping("playPause/{deviceId}")
    public ResponseEntity<?> pausePlaying(@PathVariable String deviceId) {
        String playpause = playerService.playPause(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(playpause);
    }
    //play를 원하는 곳으로 이동하기
    @PutMapping("seekToPositon/{positionMs}/{deviceId}")
    public ResponseEntity<?> seekToPositon(@PathVariable Integer positionMs, @PathVariable String deviceId) {
        String seekToPosition = playerService.seekToPosition(positionMs, deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(seekToPosition);
    }
    //반복 실행
    @PutMapping("repeatMode/{deviceId}/{state}")
    public ResponseEntity<?> repeatPlaying(@PathVariable String deviceId, @PathVariable String state) {
        String repeatMode = playerService.repeatMode(deviceId, state);
        return ResponseEntity.status(HttpStatus.OK).body(repeatMode);
    }

    //셔플 실행
    @PutMapping("/shuffle/{deviceId}/{state}")
    public ResponseEntity<?> shufflePlaying(@PathVariable String deviceId, @PathVariable String state) {
        String shuffleMode = playerService.shuffleMode(deviceId, state);
        return ResponseEntity.status(HttpStatus.OK).body(shuffleMode);
    }

    //이전 곡으로 이동
    @PostMapping("/skipToPrevious/{deviceId}")
    public ResponseEntity<?> skipToPrevious(@PathVariable String deviceId) {
        String skipToPrevious = playerService.skipToPrevious(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(skipToPrevious);
    }

    //다음 곡으로 이동
    @PostMapping("/skipToNext/{deviceId}")
    public ResponseEntity<?> skipToNext(@PathVariable String deviceId) {
        String skipToNext = playerService.skipToNext(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(skipToNext);
    }
    //사용자 큐 가져오기
    @GetMapping("/userQueue")
    public ResponseEntity<?> getUserQueue() {
        String userQueue = playerService.getUserQueue();
        return ResponseEntity.status(HttpStatus.OK).body(userQueue);
    }

    //큐에 곡 추가하기
    @PostMapping("/userQueue/{uri}/{deviceId}")
    public ResponseEntity<?> postUserQueue(@PathVariable String uri, @PathVariable String deviceId) {
        String userQueue = playerService.postUserQueue(uri, deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(userQueue);
    }

    //최근 들은 곡 불러오기
    @GetMapping("/recentlyPlayed")
    public ResponseEntity<?> getRecentlyPlayed() {
        String recentlyPlayList = playerService.getRecentlyPlayed();
        return ResponseEntity.status(HttpStatus.OK).body(recentlyPlayList);
    }
}


