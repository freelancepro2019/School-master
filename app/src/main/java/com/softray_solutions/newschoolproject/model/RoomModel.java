package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;
import java.util.List;

public class RoomModel implements Serializable {

    private List<Records> records;
    private Count count;

    public List<Records> getRecords() {
        return records;
    }

    public Count getCount() {
        return count;
    }

    public class Records implements Serializable
    {
        private String id;
        private String chat_id;
        private String conversation_id;
        private String msg_text;
        private String attachment;
        private String audio;
        private String read;
        private String created_at;
        private String from_user;
        private String to_user;
        private String to_userName;
        private String from_userName;
        private String to_userType;
        private String from_userType;

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

        public String getTo_userName() {
            return to_userName;
        }

        public String getFrom_userName() {
            return from_userName;
        }

        public String getTo_userType() {
            return to_userType;
        }

        public String getFrom_userType() {
            return from_userType;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getAudio() {
            return audio;
        }
    }

    public class Count implements Serializable
    {
        private String total;
        private String father;
        private String student;
        private String employer;

        public String getTotal() {
            return total;
        }

        public String getFather() {
            return father;
        }

        public String getStudent() {
            return student;
        }

        public String getEmployer() {
            return employer;
        }
    }
}
