package com.softray_solutions.newschoolproject.model;

import java.io.Serializable;
import java.util.List;

public class AskTeacherDataModel implements Serializable {

    private String message;
    private int success;
    private List<AskYourTeacher> user_data;

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public List<AskYourTeacher> getUser_data() {
        return user_data;
    }
}
