package com.example.music_project.dto;

import com.example.music_project.domain.Member;

import java.util.List;

public class GetMemberInfoResponse {

    public List<Member> memberList;

    public GetMemberInfoResponse(List<Member> memberList) {
        this.memberList = memberList;
    }
}
