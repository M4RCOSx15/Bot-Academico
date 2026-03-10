package com.example.demo.service;
import com.example.demo.entity.ChatBotEntity;
import com.example.demo.repository.ChatRepository;
import com.google.api.client.util.Value;
import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatModel chatModel;
    private final Client client;
    public String conversarGemini(String mensagem){
        try{Client client = new Client();
            List<ChatBotEntity> historico = chatRepository.findTop10ByOrderByIdDesc();
            StringBuilder promptContextualizando = new StringBuilder();
            promptContextualizando.append("Você é o aT3o, um assistente acadêmico. Use o histórico abaixo para manter a continuidade da conversa e:\\n\\n\"");
            java.util.Collections.reverse(historico);
            for(ChatBotEntity memoria : historico){
                promptContextualizando.append("Usuario").append(memoria.getMensagem_user()).append("\n");
                promptContextualizando.append("aT3o").append(memoria.getRespostaIA()).append("\n");
            }
            promptContextualizando.append("Usuario").append(mensagem).append("\n");
            promptContextualizando.append("aT3o");
            promptContextualizando.append("Quero que aja como um pesquisador minuncioso e faça uma rigorosa pesquisa sobre esse tema e que me entregue resultados embasados nas pesquisas e eu não quero que voce invente nada , caso precise demorar na pesquisa pode demorar ,mas responda com qualidade ");
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
    }
    public String conversarGroq(String mensagem) {

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
            promptContextualizando.append("Quero que aja como um pesquisador minuncioso e faça uma rigorosa pesquisa sobre esse tema e que me entregue resultados embasados nas pesquisas e eu não quero que voce invente nada , caso precise demorar na pesquisa pode demorar ,mas responda com qualidade ");

            return chatModel.call(mensagem);
        } catch (Exception e) {
            return "Serviço temporariamente indisponível. Tente novamente em instantes.";
        }
    }
    public String JuntarRespostasIA(String mensagem){
        String gemini =conversarGemini(mensagem);
        String groq = conversarGroq(mensagem);
        StringBuilder resultado = new StringBuilder();
        resultado.append(gemini);
        resultado.append(groq);
        resultado.append("Você é o meu agente academico , desejo que você pegue essas duas respostas e analise ambas , após isso junte as duas de forma que a resposta final seja uma resposta completa , precisa e embasada e se possivel me mostre as referencias e me de sugestoes para estudar , seja canais , artigos ou algum site e não precisa me explicar o processo de juntar falando o que cada uma falava , quero apenas a resposta final ");
        String respostaMesclada = chatModel.call(resultado.toString());
        return respostaMesclada;
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