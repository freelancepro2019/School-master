package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;

public class MessageModel implements Serializable {
    private String id;
    private String chat_id;
    private String conversation_id;
    private String msg_text;
    private String attachment;
    private String read;
    private String created_at;
    private String from_user;
    private String to_user;
    private String base;

    public String getId() {
        return id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public String getMsg_text() {
        return msg_text;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getRead() {
        return read;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFrom_user() {
        return from_user;
    }

    public String getTo_user() {
        return to_user;
    }

    public String getBase() {
        return base;
    }
}