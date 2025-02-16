package com.example.music_project.repository;


import com.example.music_project.domain.Member;
import com.example.music_project.domain.Playing;
import com.example.music_project.dto.MostPlayedArtist;
import com.example.music_project.dto.MostPlayedTrack;
import com.example.music_project.service.Validation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository // 이 파일이 리포지토리라는 것을 표시해줌
@AllArgsConstructor //생성자가 모두 있는 것과 같은 효과
@Log4j2
public class PlayingRepository {

    Validation validationService;
    public EntityManager em; //entity(테이블)을 관리

    @Transactional //오류가 나면 처음부터 다시
    public String addToPlaying(String trackId, String memberId, LocalDateTime date, String artistName, String trackName) {
        //유효한 값인지 확인
        validationService.validTrackId(trackId);
        validationService.validMemberId(memberId);

        Playing playing = new Playing(); //테이블 클래스 인스턴스 만들기
        playing.trackId = trackId;
        playing.trackName = trackName;
        playing.artistName = artistName;

        log.info("trackId: "+ trackId);
        log.info("회원Id: "+ memberId);
        Member member = em.find(Member.class, memberId); //String으로 받아온 memberId를 Member class로 바꿔주기
        playing.member = member;

        playing.date = date;

        em.persist(playing); //테이블에 데이터 추가

        return trackId;
    }

    public List<Playing> getAllPlaying(String memberId) {
        validationService.validMemberId(memberId);

        List<Playing> allPlaying = em.createQuery("select f from Playing f where f.member.id = :memberId", Playing.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return allPlaying;
    }
    //이번달 가장 많이 들은 곡
    public List<MostPlayedTrack> getMonthPlayedTrack(String memberId) {

        validationService.validMemberId(memberId);

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<MostPlayedTrack> monthPlayedTrack = em.createQuery(
                "select f.trackName, count(f.trackName) as playCount from Playing f " +
                        "where f.member.id = :memberId " +
                        "and YEAR(f.date) = :currentYear " +
                        "and MONTH(f.date) = :currentMonth " +
                        "group by f.trackName " +
                        "order by playCount desc"
                        , MostPlayedTrack.class)
                .setParameter("memberId", memberId)
                .setParameter("currentYear", currentYear)
                .setParameter("currentMonth", currentMonth)
                .setMaxResults(1) // 가장 많이 들은 곡 1개
                .getResultList();
        return monthPlayedTrack;
    }

    //이번달 가장 많이 들은 아티스트
    public List<MostPlayedArtist> getMonthPlayedArtist(String memberId) {
        validationService.validMemberId(memberId);

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        List<MostPlayedArtist> monthPlayedArtist = em.createQuery(
                        "select f.artistName, count(f.artistName) as playCount from Playing f " +
                                "where f.member.id = :memberId " +
                                "and YEAR(f.date) = :currentYear " +
                                "and MONTH(f.date) = :currentMonth " +
                                "group by f.artistName " +
                                "order by playCount desc"
                        , MostPlayedArtist.class)
                .setParameter("memberId", memberId)
                .setParameter("currentYear", currentYear)
                .setParameter("currentMonth", currentMonth)
                .setMaxResults(1) // 가장 많이 들은 곡 1개
                .getResultList();
        return monthPlayedArtist;
    }


    //주간 들은 곡
//    public List<MostPlaying> getWeekPlaying(Long memberId) {
//        LocalDate now = LocalDate.now();
//
//        // 이번 주의 시작(월요일)과 끝(일요일) 계산
//        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay(); // 월요일 00:00:00
//        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
//
//
//        int currentMonth = LocalDate.now().getMonthValue();
//        int currentYear = LocalDate.now().getYear();
//
//        List<MostPlaying> weekPlaying = em.createQuery(
//                        "select f.trackId, count(f.trackId) as playCount from Playing f " +
//                                "where f.member.id = :memberId " +
//                                "and f.date BETWEEN :startOfWeek AND :endOfWeek " +
//                                "and YEAR(f.date) = :currentYear " +
//                                "and MONTH(f.date) = :currentMonth " +
//                                "group by f.trackId " +
//                                "order by playCount desc"
//                        , MostPlaying.class)
//                .setParameter("memberId", memberId)
//                .setParameter("startOfWeek", startOfWeek)
//                .setParameter("endOfWeek", endOfWeek)
//                .setParameter("currentYear", currentYear)
//                .setParameter("currentMonth", currentMonth)
//                .setMaxResults(1)
//                .getResultList();
//
//        return weekPlaying;
//    }
}
