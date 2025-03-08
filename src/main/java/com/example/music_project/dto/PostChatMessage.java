package com.example.music_project.dto;

import com.example.music_project.domain.ChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostChatMessage {
    private String content;

    public PostChatMessage(String content)
    {
        this.content = content;
    }
}
