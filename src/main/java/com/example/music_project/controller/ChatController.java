package com.example.music_project.controller;


import com.example.music_project.domain.ChatMessage;
import com.example.music_project.dto.PostChatMessage;
import com.example.music_project.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {
    ChatService chatService;

    @MessageMapping("/chat.sendMessage/{chatRoomId}")
    public ResponseEntity<?> postChatMessage(@DestinationVariable("chatRoomId") Long chatRoomId,
                                             @Payload PostChatMessage postChatMessage){

        chatService.addMessage(postChatMessage,chatRoomId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
