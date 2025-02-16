package com.example.music_project.domain;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Member {
    @Id @Column(name="member_id") //sql의 id 이름을 member_id로 변경
    public String id;
    public String email;
    public String nickname;
    public String imageUrl;

}
//display_name, email, id, images