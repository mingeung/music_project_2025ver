package com.example.music_project;

import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.domain.Member;
import com.example.music_project.exception.CustomException;
import com.example.music_project.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Repository
public class FavoriteSongsRepository {

    public EntityManager em;
    @Transactional

    public String addToFavoriteSongs(String trackId, Long memberId) {
        FavoriteSongs favoriteSongs = new FavoriteSongs();
        favoriteSongs.trackId = trackId;

        //find(테이블 = 엔티티, pk)
        Member member = em.find(Member.class,memberId);
        favoriteSongs.member = member;

        em.persist(favoriteSongs);

        return trackId;
    }

    public List<FavoriteSongs> getAllFavoriteSongs(Long memberId) {
        //jpql
        List<FavoriteSongs> allFavoriteSongs = em.createQuery("select f from FavoriteSongs f where f.member.id = :memberId",FavoriteSongs.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return allFavoriteSongs;
    }



    public String deleteFromFavoriteSongs(String memberId, String trackId) {

        Query query = em.createQuery("DELETE FROM FavoriteSongs f WHERE f.trackId = :trackId AND f.member.id = :memberId")
                 .setParameter("memberId", memberId)
                 .setParameter("trackId", trackId);

        int deletedCount = query.executeUpdate();

        if (deletedCount <= 0) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

    return "삭제 성공";
        //        return deletedCount > 0 ? "삭제 성공" : "삭제 실패";
    }

    public HttpStatus except() {
//        try {
//            throw new IllegalArgumentException("test");
//        } catch (IllegalArgumentException e) {
//            return HttpStatus.OK;
//        }
        throw new RuntimeException("test");
    }

    //이미 있는 노래인지 확인
    public boolean isAlreadyFavoriteSong(String trackId, Long memberId) {
        String jpql = "SELECT COUNT(f) FROM FavoriteSongs f WHERE f.member.id = :memberId AND f.trackId = :trackId";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("memberId", memberId);
        query.setParameter("trackId", trackId);

        Long count = query.getSingleResult();
        return count > 0;  // 값이 존재하면 true, 없으면 false 返還
    }



}
