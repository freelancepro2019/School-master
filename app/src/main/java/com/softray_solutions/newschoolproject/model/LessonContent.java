package com.softray_solutions.newschoolproject.model;

public class LessonContent {

    private int title;
    private String content;

    public LessonContent(int title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
