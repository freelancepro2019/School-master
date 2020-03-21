package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class Homework {

    @SerializedName("ID")
    private String id = "";

    @SerializedName("title")
    private String title = "";

    @SerializedName("content")
    private String content = "";

    @SerializedName("attach")
    private String attach = "";

    @SerializedName("date_homework")
    private String date = "";

    @SerializedName("token")
    private String token = "";

    @SerializedName("emp")
    private String teacher = "";

    @SerializedName("subject")
    private String subject;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAttach() {
        return attach;
    }

    public String getDate() {
        return date;
    }

    public String getToken() {
        return token;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }
}
