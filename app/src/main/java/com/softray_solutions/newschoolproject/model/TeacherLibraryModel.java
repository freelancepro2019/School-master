package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class TeacherLibraryModel {


    private String File_url;
    private String ID;
  //  private String Lesson_Title;
    private String link_youtube;
    private String SubName;
    private String Title;

    public String getFile_url() {
        return File_url;
    }

    public void setFile_url(String file_url) {
        File_url = file_url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

   /* public String getLesson_Title() {
        return Lesson_Title;
    }

    public void setLesson_Title(String lesson_Title) {
        Lesson_Title = lesson_Title;
    }*/

    public String getLink_youtube() {
        return link_youtube;
    }

    public void setLink_youtube(String link_youtube) {
        this.link_youtube = link_youtube;
    }

    public String getSubName() {
        return SubName;
    }

    public void setSubName(String subName) {
        SubName = subName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}