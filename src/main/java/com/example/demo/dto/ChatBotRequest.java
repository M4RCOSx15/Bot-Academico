package com.example.demo.dto;

public class ChatBotRequest {
    private String mensagem;
    public ChatBotRequest(String mensagem){
        this.mensagem = mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
