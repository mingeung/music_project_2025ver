package com.example.music_project;

import com.example.music_project.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class SearchController {
    SearchService searchService;

    @GetMapping("/searchResult/trackName={trackName}") //url 이렇게 쓰는 거 맞나?
    public ResponseEntity<?> getSearchResult(@PathVariable String trackName) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.getSearchResult(trackName));
    }

//    @GetMapping("/searchResult/trackId={trackId}/play?{isPlaying}")
//    //url이름이 고민된다. 나중에 보관함에서도 play를 할텐데 같이 쓸 걸 생각하면 searchResult를 빼는 게 맞나? 근데 searchResult 페이지에서 플레이를 하는거라...
//    public ResponseEntity<?> getTrackPlay(@PathVariable String trackId, @PathVariable boolean isPlaying) {
//        return ResponseEntity.status(HttpStatus.OK).body(searchService)
//    }


}
