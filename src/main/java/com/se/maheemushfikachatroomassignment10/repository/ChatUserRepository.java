package com.se.maheemushfikachatroomassignment10.repository;

import com.se.maheemushfikachatroomassignment10.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, String> {
    public ChatUser findByUsername(String username);
}
