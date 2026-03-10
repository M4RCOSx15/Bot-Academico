package com.example.demo.service;
import com.example.demo.entity.ChatBotEntity;

import com.example.demo.repository.ChatRepository;

import com.google.genai.Client;

import com.google.genai.types.GenerateContentResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.openai.OpenAiChatModel;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.ai.chat.model.ChatModel;

import org.springframework.stereotype.Service;





import java.util.Collection;

import java.util.List;

import java.util.Optional;



@Service
@RequiredArgsConstructor
public class ChatService {
    private String apiKey;
    private final ChatRepository chatRepository;
    private final ChatModel chatModel;
    private final Client client;
    public String conversarGemini(String mensagem){
        try{Client client = new Client();
            List<ChatBotEntity> historico = chatRepository.findTop10ByOrderByIdDesc();
            StringBuilder promptContextualizando = new StringBuilder();
            promptContextualizando.append("Você é o aT3o, um assistente acadêmico. Use o histórico abaixo para manter a continuidade da conversa:\\n\\n\"");
            java.util.Collections.reverse(historico);
            for(ChatBotEntity memoria : historico){
                promptContextualizando.append("Usuario").append(memoria.getMensagem_user()).append("\n");
                promptContextualizando.append("aT3o").append(memoria.getRespostaIA()).append("\n");
            }
            promptContextualizando.append("Usuario").append(mensagem).append("\n");
            promptContextualizando.append("aT3o");
            String respostaIa = chatModel.call(promptContextualizando.toString());
            ChatBotEntity novainteracao = new ChatBotEntity();
            novainteracao.setMensagem_user(mensagem);
            novainteracao.setRespostaIA(respostaIa);
            chatRepository.save(novainteracao);
            return respostaIa;}
        catch (Exception e) {
            e.printStackTrace();
            return "ocorreu um problema no envio de contexto ";
        }

/*GenerateContentResponse response =
client.models.generateContent(
"gemini-3-flash-preview",
"Aja como um expecialista Sênior de Computação e me ensine com os melhores metodos de estudos , " +
"não foque na sua apresentação como profissional , o meu foco total é o aprendizado :"+mensagem,
null);
return response.text();*/
    }
    public String conversar(String mensagem) {

        try {
            List<ChatBotEntity> historico = chatRepository.findTop10ByOrderByIdDesc();
            StringBuilder promptContextualizando = new StringBuilder();
            promptContextualizando.append("Você é o aT3o, um assistente acadêmico. Use o histórico abaixo para manter a continuidade da conversa:\\n\\n\"");
            java.util.Collections.reverse(historico);
            for(ChatBotEntity memoria : historico){
                promptContextualizando.append("Usuario").append(memoria.getMensagem_user()).append("\n");
                promptContextualizando.append("aT3o").append(memoria.getRespostaIA()).append("\n");
            }
            promptContextualizando.append("Usuario").append(mensagem).append("\n");
            promptContextualizando.append("aT3o");
            System.out.println("API KEY CARREGADA: " + apiKey);
            return chatModel.call(mensagem);
        } catch (Exception e) {
            System.out.println("API KEY CARREGADA: " + apiKey);
            return "Serviço temporariamente indisponível. Tente novamente em instantes.";
        }
    }
    public ChatBotEntity Salvar(ChatBotEntity chatBotEntity) {
        return chatRepository.save(chatBotEntity);
    }
    public Optional<ChatBotEntity> Buscar(Long id) {
        return chatRepository.findById(id);
    }



    public void Deletar(Long id) {
        if (!chatRepository.existsById(id)) {
            throw new EntityNotFoundException("Bot não encontrado");
        }
        chatRepository.deleteById(id);

    }

}