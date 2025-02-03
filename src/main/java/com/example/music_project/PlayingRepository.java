package com.example.music_project;


import com.example.music_project.domain.Member;
import com.example.music_project.domain.Playing;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository // 이 파일이 리포지토리라는 것을 표시해줌
@AllArgsConstructor //생성자가 모두 있는 것과 같은 효과

public class PlayingRepository {

    public EntityManager em; //entity(테이블)을 관리
    @Transactional //오류가 나면 처음부터 다시

    public String addToPlaying(String trackId, Long memberId, LocalDateTime date) {
        Playing playing = new Playing(); //테이블 클래스 인스턴스 만들기
        playing.trackId = trackId;

        Member member = em.find(Member.class, memberId); //String으로 받아온 memberId를 Member class로 바꿔주기
        playing.member = member;

        playing.date = date;

        em.persist(playing); //테이블에 데이터 추가

        return trackId;
    }

//    public List<Playing> getAllPlaying(Long memberId) {
//        List<Playing> allPlaying = em.createQuery("select f from Playing f where f.member.id = :memberId", Playing.class)
//                .setParameter("memberId", memberId)
//                .getResultList();
//
//        return allPlaying;
//    }
    //월간 많이 들은 곡
    public List<Playing> getMonthPlaying(Long memberId) {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<Playing> monthPlaying = em.createQuery(
                "select f from Playing f " +
                        "where f.member.id = :memberId " +
                        "and YEAR(f.date) = :currentYear " +
                        "and MONTH(f.date) = :currentMonth", Playing.class)
                .setParameter("memberId", memberId)
                .setParameter("currentYear", currentYear)
                .setParameter("currentMonth", currentMonth)
                .getResultList();

        return monthPlaying;
    }
}
