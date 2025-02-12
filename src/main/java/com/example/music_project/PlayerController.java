package com.example.music_project;


import com.example.music_project.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

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
}
