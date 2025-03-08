package com.example.music_project.service;

import com.example.music_project.domain.ChatMessage;
import com.example.music_project.dto.PostChatMessage;
import com.example.music_project.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;


    public void addMessage(PostChatMessage postChatMessage, Long chatRoomId) {

        ChatMessage chatMessage = new ChatMessage();
        String content = postChatMessage.getContent();
        chatMessage.setContent(content); //private로 정의된 변수를 setter, getter를 이용해서 접근
        //1. 채팅 메시지 저장
         chatRepository.save(chatMessage);

        //2. 다른 사람에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/" + chatRoomId, content);



    }


}
