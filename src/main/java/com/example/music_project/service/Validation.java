package com.example.music_project.service;

import com.example.music_project.domain.Member;
import com.example.music_project.exception.CustomException;
import com.example.music_project.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Component
@AllArgsConstructor
public class Validation {
    TrackInfoService trackInfoService;
    public EntityManager em;

    //트랙 정보 확인하기
    public void validTrackId(String trackId) {
        try {
            trackInfoService.fetchFromSpotify("tracks/" + trackId);
        }
        catch(HttpClientErrorException.BadRequest e) {
            throw new CustomException((ErrorCode.TRACK_NOT_FOUND));
        }
    }

    // 멤버아이디가 유효한지 확인하는 예외
    @Transactional
    public Member validMemberId(Long memberId) {
        Member member = em.find(Member.class, memberId);
        if (member == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);  // member가 없으면 예외를 던짐
        }
        return member;
}}
