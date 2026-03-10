package com.example.demo.dto;

public class ChatBotResponse {

    private String reply;

    public ChatBotResponse(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }
}