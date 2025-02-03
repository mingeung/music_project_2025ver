package com.example.music_project;

import com.example.music_project.domain.Playing;
import com.example.music_project.dto.GetPlayingRequest;
import com.example.music_project.dto.GetPlayingResponse;
import com.example.music_project.dto.PostPlayingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //이 파일이 컨트롤러라는 것을 알려줌
@AllArgsConstructor //생성자를 모두 만든 것과 같은 것

public class PlayingController {

    PlayingRepository playingRepository;

    @PostMapping("/playing")
    public ResponseEntity<?> addToPlaying(@RequestBody PostPlayingRequest postPlayingRequest) {
        String trackId = playingRepository.addToPlaying(postPlayingRequest.trackId, postPlayingRequest.memberId, postPlayingRequest.date);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId);
    }

    @GetMapping("/playing")
    public ResponseEntity<GetPlayingResponse> getAllPlaying(@RequestBody GetPlayingRequest getPlayingRequest) {
        List<Playing> allPlaying = playingRepository.getAllPlaying(getPlayingRequest.memberId);
        GetPlayingResponse getPlayingResponse = new GetPlayingResponse(allPlaying);

        return ResponseEntity.status(HttpStatus.OK).body(getPlayingResponse);
    }

}
