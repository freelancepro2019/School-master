package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;
import java.util.List;

public class MessageDataModel implements Serializable {
    private List<MessageModel> records;
    private Count count;
    private String base;

    public List<MessageModel> getRecords() {
        return records;
    }

    public Count getCount() {
        return count;
    }

    public class Count implements Serializable {
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

    public String getBase() {
        return base;
    }
}
