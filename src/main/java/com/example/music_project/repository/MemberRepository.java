package com.example.music_project.repository;

import com.example.music_project.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberRepository {
    public EntityManager em;

    @Transactional
    public String save(Member member) {
        em.persist(member);

        return member.id;
    }


    //이미 있는 건지 확인
    public Member findById(String memberId) {
       Member member = em.find(Member.class, memberId); //find는 pk만 가능
        return member;
    }
}
