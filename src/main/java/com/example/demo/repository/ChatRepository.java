package com.example.demo.repository;

import com.example.demo.entity.ChatBotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatBotEntity,Long>{
    List<ChatBotEntity> findTop10ByOrderByIdDesc();
}
