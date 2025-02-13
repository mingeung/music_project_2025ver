package com.example.music_project;


import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.dto.DeleteFavoriteSongRequest;
import com.example.music_project.dto.GetFavoriteSongsRequest;
import com.example.music_project.dto.GetFavoriteSongsResponse;
import com.example.music_project.dto.PostFavoriteSongRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class FavoriteSongsController {

    FavoriteSongsRepository favoriteSongsRepository;

    @PostMapping("/favoritesongs")
    public ResponseEntity<?> addToFavoriteSongs(@RequestBody PostFavoriteSongRequest postFavoriteSongRequest) { //http 상태코드전달
        //데이터베이스에 2번 찍히는 문제
        String trackId = favoriteSongsRepository.addToFavoriteSongs(postFavoriteSongRequest.trackId, postFavoriteSongRequest.memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId); //프론트에서 확인하는 내용
    }

    @GetMapping("/favoritesongs/{memberId}")
    public ResponseEntity<GetFavoriteSongsResponse> getAllFavoriteSongs(@PathVariable Long memberId) {

        List<FavoriteSongs> allFavoriteSongs = favoriteSongsRepository.getAllFavoriteSongs(memberId);
        GetFavoriteSongsResponse getFavoriteSongsResponse = new GetFavoriteSongsResponse(allFavoriteSongs);

        return ResponseEntity.status(HttpStatus.OK).body(getFavoriteSongsResponse);
    }

    @Transactional
    @DeleteMapping("/favoritesongs/{trackId}/{memberId}")
    public ResponseEntity<?> deleteFromFavoriteSongs(@PathVariable String trackId, @PathVariable Long memberId) {

        String result = favoriteSongsRepository.deleteFromFavoriteSongs(memberId, trackId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //이미 보관함에 있는 노래인지 확인
    @GetMapping("/favoritesongs/{trackId}/{memberId}")
    public ResponseEntity<?> isAlreadyFavoriteSong(@PathVariable String trackId, @PathVariable Long memberId) {
        boolean result = favoriteSongsRepository.isAlreadyFavoriteSong(trackId, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
