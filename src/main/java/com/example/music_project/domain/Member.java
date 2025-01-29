package com.example.music_project.domain;


import jakarta.persistence.*;

@Entity
public class Member {
    @Id @Column(name="member_id") //sql의 id 이름을 member_id로 변경
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public String nickname;
    public String password;

}
