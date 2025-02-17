package com.example.music_project.controller;

import com.example.music_project.domain.Member;
import com.example.music_project.dto.GetMemberInfoResponse;
import com.example.music_project.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController//controller라는 걸 알려줘야 함
@AllArgsConstructor
@Log4j2
public class MemberController {

    MemberRepository memberRepository;

    @GetMapping("/member")
    public ResponseEntity<GetMemberInfoResponse> getMember(@AuthenticationPrincipal OAuth2User oAuth2User){
        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        List<Member> memberInfo = memberRepository.getMemberInfo(memberId);
        GetMemberInfoResponse getMemberInfoResponse = new GetMemberInfoResponse(memberInfo);

        return ResponseEntity.status(HttpStatus.OK).body(getMemberInfoResponse);
    }

    @PatchMapping("/image")
    public String updateMemberImage(
            @RequestParam("profileImage") MultipartFile file, @AuthenticationPrincipal OAuth2User oAuth2User) {

        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        if (file.isEmpty()) {
            return "파일이 선택되지 않았습니다.";
        }
        String memberImageUpload = memberRepository.uploadImage(file, memberId);

        return memberImageUpload;
    }
    @PatchMapping("nickname")
    public ResponseEntity<String> updateMemberNickname(@RequestParam("nickname") String nickname, @AuthenticationPrincipal OAuth2User oAuth2User){

        Map<String, Object> map = oAuth2User.getAttributes();
        String memberId = map.get("id").toString();

        String memberNickname = memberRepository.nicknameUpdate(memberId, nickname);

        return ResponseEntity.status(HttpStatus.OK).body(memberNickname);
    }
}


