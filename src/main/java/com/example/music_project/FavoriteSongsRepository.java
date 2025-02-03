package com.example.music_project;

import com.example.music_project.domain.FavoriteSongs;
import com.example.music_project.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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


}
