package com.example.demo.controller;

import com.example.demo.dto.ChatBotRequest;
import com.example.demo.dto.ChatBotResponse;
import com.example.demo.entity.ChatBotEntity;
import com.example.demo.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bot")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;

    }

    @PostMapping("/pergunta")
    public ResponseEntity<ChatBotResponse> fazerPergunta(@RequestBody ChatBotRequest chatBotRequest) {
        String msg = chatService.conversarGemini(chatBotRequest.getMensagem());
        return ResponseEntity.ok(new ChatBotResponse(msg));
    }
    @PostMapping("/chat")
    public ResponseEntity<?> chat(ChatBotRequest chatBotRequest){
        String mensagem = chatBotRequest.getMensagem();
        return ResponseEntity.ok(new ChatBotResponse(mensagem));
    }
    @PostMapping("/salvar")
    public ResponseEntity<ChatBotEntity> salvar(@RequestBody ChatBotEntity chatBotEntity){ // @RequestBody é importante para JSON
        ChatBotEntity salvo = chatService.Salvar(chatBotEntity);
        return ResponseEntity.ok().body(salvo);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ChatBotEntity> buscar(@PathVariable Long id){
        return chatService.Buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        chatService.Deletar(id);
        return ResponseEntity.noContent().build();
    }
}