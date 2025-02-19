package com.example.music_project.repository;

import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.domain.Member;
import com.example.music_project.exception.CustomException;
import com.example.music_project.exception.ErrorCode;
import com.example.music_project.service.Validation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@AllArgsConstructor
@Repository
public class FavoriteSongsRepository {
    MemberRepository memberRepository;

    public EntityManager em;
    Validation validationService;
    @Transactional
    public String addToFavoriteSongs(String trackId, String memberId) {

            FavoriteSongs favoriteSongs = new FavoriteSongs();

            //memberId가 유효한지 확인
//            Member member = validationService.validMemberId(memberId);
            Member member = memberRepository.findById(memberId);
            favoriteSongs.member = member;

            //trackId가 유효한지 확인
            validationService.validTrackId(trackId);
            favoriteSongs.trackId = trackId;

            em.persist(favoriteSongs);

            return trackId;
    }

    public List<FavoriteSongs> getAllFavoriteSongs(String memberId) {
        //member가 유효한지 확인 - exception
//        validationService.validMemberId(memberId);
        //jpql
        List<FavoriteSongs> allFavoriteSongs = em.createQuery("select f from FavoriteSongs f where f.member.id = :memberId",FavoriteSongs.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return allFavoriteSongs;
    }



    public String deleteFromFavoriteSongs(String memberId, String trackId) {

        //memberId와 trackId가 유효한지 확인
        validationService.validMemberId(memberId);
        validationService.validTrackId(trackId);

        Query query = em.createQuery("DELETE FROM FavoriteSongs f WHERE f.trackId = :trackId AND f.member.id = :memberId")
                 .setParameter("memberId", memberId)
                 .setParameter("trackId", trackId);

        int deletedCount = query.executeUpdate();

        if (deletedCount <= 0) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

    return "삭제 성공";
    }


    //이미 있는 노래인지 확인
    public boolean isAlreadyFavoriteSong(String trackId, String memberId) {
        //memberId와 trackId가 유효한지 확인
        validationService.validMemberId(memberId);
        validationService.validTrackId(trackId);

        String jpql = "SELECT COUNT(f) FROM FavoriteSongs f WHERE f.member.id = :memberId AND f.trackId = :trackId";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("memberId", memberId);
        query.setParameter("trackId", trackId);

        Long count = query.getSingleResult();
        return count > 0;  // 값이 존재하면 true, 없으면 false
    }


}
