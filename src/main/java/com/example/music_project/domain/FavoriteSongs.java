package com.example.music_project.domain;

import jakarta.persistence.*;

@Entity
public class FavoriteSongs {
    @Id @Column(name="favorite_songs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public String trackId;

    @ManyToOne //다대1 관계를 맺을거다
    @JoinColumn(name = "member_id",nullable = false) //sql에 올라가는 테이블 외래키
    public Member member; //외래키 대신에 객체를 갖는다.
}