package com.example.demo.config;

import com.google.genai.Client;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    @Bean
    public Client google(@Value("${spring.ai.google.ai.gemini.api-key}") String apiKey){
        return Client.builder().apiKey(apiKey).build();
    }

}
