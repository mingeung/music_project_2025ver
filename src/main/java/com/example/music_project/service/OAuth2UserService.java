package com.example.music_project.service;

import java.util.ArrayList;
import java.util.Map;

import com.example.music_project.repository.MemberRepository;
import com.example.music_project.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        log.info("oAuth2UserRequest: ",oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        Map<String, Object> map = oAuth2User.getAttributes();

        //이미 가입된 회원인지 검증
        String memberId =  map.get("id").toString();
        log.info("memberId: ",memberId);
        Member alreadyMember = memberRepository.findById(memberId);

        //display_name, email, id, images,
        //가입되지 않은 경우에만 회원가입
        if (alreadyMember == null) {
            log.info("회원가입진행");
            Member member = new Member();
            member.id = map.get("id").toString(); //get은 key에 해당하는 value를 가져와줌
            member.email = map.get("email").toString();
            member.nickname = map.get("display_name").toString();
            if (map.get("images") != null) {
                member.imageUrl = (String)(((Map<String, Object>) ((ArrayList<?>) map.get("images")).get(0)).get("url"));
            }
            log.info("member 정보 확인", map);
            memberRepository.save(member);
        } else {
            log.info("이미 가입된 회원");
        }
        return oAuth2User;
    }
}
