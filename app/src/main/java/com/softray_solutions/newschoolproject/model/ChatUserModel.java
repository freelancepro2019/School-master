package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;

public class ChatUserModel implements Serializable {

    private String from_id;
    private String from_name;
    private String from_image;
    private String chat_id;
    private String conversation_id;

    public ChatUserModel(String from_id, String from_name, String from_image, String chat_id, String conversation_id) {
        this.from_id = from_id;
        this.from_name = from_name;
        this.from_image = from_image;
        this.chat_id = chat_id;
        this.conversation_id = conversation_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getFrom_image() {
        return from_image;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getConversation_id() {
        return conversation_id;
    }
}
