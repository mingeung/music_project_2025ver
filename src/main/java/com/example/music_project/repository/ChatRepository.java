package com.example.music_project.repository;

import com.example.music_project.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatMessage, String> {

}
