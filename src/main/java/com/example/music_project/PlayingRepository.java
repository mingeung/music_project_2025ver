package com.example.music_project;


import com.example.music_project.domain.Member;
import com.example.music_project.domain.Playing;
import com.example.music_project.dto.MonthMostPlaying;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public List<Playing> getAllPlaying(Long memberId) {
        List<Playing> allPlaying = em.createQuery("select f from Playing f where f.member.id = :memberId", Playing.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return allPlaying;
    }
    //월간 들은 곡
    public List<MonthMostPlaying> getMonthPlaying(Long memberId) {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<MonthMostPlaying> monthPlaying = em.createQuery(
                "select f.trackId, count(f.trackId) as playCount from Playing f " +
                        "where f.member.id = :memberId " +
                        "and YEAR(f.date) = :currentYear " +
                        "and MONTH(f.date) = :currentMonth " +
                        "group by f.trackId " +
                        "order by playCount desc"
                        , MonthMostPlaying.class)
                .setParameter("memberId", memberId)
                .setParameter("currentYear", currentYear)
                .setParameter("currentMonth", currentMonth)
                .setMaxResults(1) // 가장 많이 들은 곡 1개
                .getResultList();
        return monthPlaying;
    }

    //주간 들은 곡
    public List<Playing> getWeekPlaying(Long memberId) {
        LocalDate now = LocalDate.now();

        // 이번 주의 시작(월요일)과 끝(일요일) 계산
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay(); // 월요일 00:00:00
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);


        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<Playing> weekPlaying = em.createQuery(
                        "select f from Playing f " +
                                "where f.member.id = :memberId " +
                                "and f.date BETWEEN :startOfWeek AND :endOfWeek " +
                                "and YEAR(f.date) = :currentYear " +
                                "and MONTH(f.date) = :currentMonth", Playing.class)
                .setParameter("memberId", memberId)
                .setParameter("startOfWeek", startOfWeek)
                .setParameter("endOfWeek", endOfWeek)
                .setParameter("currentYear", currentYear)
                .setParameter("currentMonth", currentMonth)
                .getResultList();

        return weekPlaying;
    }
}
