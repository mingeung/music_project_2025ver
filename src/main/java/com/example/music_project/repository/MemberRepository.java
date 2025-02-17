package com.example.music_project.repository;

import com.example.music_project.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.MetaMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
@Log4j2
public class MemberRepository {
    public EntityManager em;

    @Transactional
    public String save(Member member) {

        em.persist(member);

        return member.id;
    }

    //이미 있는 건지 확인
    public Member findById(String memberId) {
       Member member = em.find(Member.class, memberId); //find는 pk만 가능
        return member;
    }

    public List<Member> getMemberInfo(String memberId) {
        List<Member> memberInfo = em.createQuery("select f from Member f where f.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
        return memberInfo;

    }

    // 업로드 폴더 경로 선언 (클래스 필드로 선언하거나 메소드 내에 선언)
    private final String uploadDir = "/Users/jeongminseung/IdeaProjects/music_project/src/main/java/com/example/music_project/uploads";
    @Transactional
    public String uploadImage(MultipartFile file, String memberId) {
        try {
            // 파일 저장 경로 설정
            Path uploadPath = Paths.get(uploadDir);

            // 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // 고유 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String newFilename = System.currentTimeMillis() + "_" + originalFilename;

            // 최종 파일 경로 설정
            Path filePath = uploadPath.resolve(newFilename);

            // 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 파일 URL
            String fileUrl = "/uploads/" + newFilename;

            em.createQuery("UPDATE Member m SET m.imageUrl = :fileUrl WHERE m.id = :memberId")
                    .setParameter("fileUrl", fileUrl)
                    .setParameter("memberId", memberId)
                    .executeUpdate();

            return fileUrl;
        }catch (IOException e) {
            e.printStackTrace();
            // ❗ 반환 타입에 맞게 빈 리스트 반환
            return "오류발생";
        }
    }
@Transactional
    public String nicknameUpdate(String memberId, String nickname) {
        em.createQuery("UPDATE Member m SET m.nickname = :nickname WHERE m.id = :memberId")
                .setParameter("nickname", nickname)
                .setParameter("memberId", memberId)
                .executeUpdate();

        return nickname;
    }
}
