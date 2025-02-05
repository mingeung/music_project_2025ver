package com.example.music_project.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Playing {

    @Id
    @Column(name="playing_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    public LocalDateTime date;
    public String trackId;
    public String artistId;

    @ManyToOne //다대1 관계를 맺을거다
    @JoinColumn(name = "member_id", nullable = false) //sql에 올라가는 테이블 외래키
    public Member member; //외래키 대신에 객체를 갖는다.
}
