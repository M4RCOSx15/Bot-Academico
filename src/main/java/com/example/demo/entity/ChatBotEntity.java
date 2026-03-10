    package com.example.demo.entity;

    import jakarta.persistence.*;
    import org.springframework.beans.BeanUtils;

    @Entity
    @Table(name = "dtbase-bot")
    public class ChatBotEntity {
        @Id()
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private Long id_user;
        @Column(columnDefinition = "TEXT")
        private String mensagem_user;
        @Column(columnDefinition = "TEXT")
        private String resposta_ia;
        @Column
        private String timeStamp;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Long getId_user() {
            return id_user;
        }
        public void setId_user(Long id_user) {
            this.id_user = id_user;
        }
        public String getMensagem_user() {
            return mensagem_user;
        }
        public void setMensagem_user(String mensagem_user) {
            this.mensagem_user = mensagem_user;
        }
        public String getRespostaIA() {
            return resposta_ia;
        }
        public void setRespostaIA(String respostaIA) {
            this.resposta_ia = respostaIA;
        }
        public String getTimeStamp() {
            return timeStamp;
        }
        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
        public ChatBotEntity(Long id, Long id_user, String mensagem_user, String respostaIA, String timeStamp) {
            this.id = id;
            this.id_user = id_user;
            this.mensagem_user = mensagem_user;
            this.resposta_ia = respostaIA;
            this.timeStamp = timeStamp;
        }
        public ChatBotEntity() {
        }
    }