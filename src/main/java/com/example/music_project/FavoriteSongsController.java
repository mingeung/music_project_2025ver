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
public class FavoriteSongsController {

    FavoriteSongsRepository favoriteSongsRepository;

    @PostMapping("/favoritesongs")
    public ResponseEntity<?> addToFavoriteSongs(@RequestBody PostFavoriteSongRequest postFavoriteSongRequest) { //http 상태코드전달
        //데이터베이스에 2번 찍히는 문제
        String trackId = favoriteSongsRepository.addToFavoriteSongs(postFavoriteSongRequest.trackId, postFavoriteSongRequest.memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackId); //프론트에서 확인하는 내용
    }

    @GetMapping("/favoritesongs")
    public ResponseEntity<GetFavoriteSongsResponse> getAllFavoriteSongs(@RequestBody GetFavoriteSongsRequest getFavoriteSongsRequest) {

        List<FavoriteSongs> allFavoriteSongs = favoriteSongsRepository.getAllFavoriteSongs(getFavoriteSongsRequest.memberId);
        GetFavoriteSongsResponse getFavoriteSongsResponse = new GetFavoriteSongsResponse(allFavoriteSongs);

        return ResponseEntity.status(HttpStatus.OK).body(getFavoriteSongsResponse);
    }
    @GetMapping("/test")
    public HttpStatus getGet() {
        //예외
//        try {
//            favoriteSongsRepository.except();
//        } catch (RuntimeException e) {
//            return HttpStatus.OK;
//        }

        favoriteSongsRepository.except();
        //예외처리
        return HttpStatus.OK;
    }


//        List<FavoriteSongs> allFavoriteSongs = favoriteSongsRepository.getAllFavoriteSongs(getFavoriteSongsRequest.memberId);
//        GetFavoriteSongsResponse getFavoriteSongsResponse = new GetFavoriteSongsResponse(allFavoriteSongs);

//        return ResponseEntity.status(HttpStatus.OK).body(getFavoriteSongsResponse);
    @Transactional
    @DeleteMapping("favoritesongs")
    public ResponseEntity<?> deleteFromFavoriteSongs(@RequestBody DeleteFavoriteSongRequest deleteFavoriteSongRequest) {

        String result = favoriteSongsRepository.deleteFromFavoriteSongs(deleteFavoriteSongRequest.memberId, deleteFavoriteSongRequest.trackId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
