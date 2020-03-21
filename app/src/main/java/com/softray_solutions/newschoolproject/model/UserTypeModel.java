package com.softray_solutions.newschoolproject.model;

public class UserTypeModel {

    private String ar_name;
    private String en_name;
    private String type;

    public UserTypeModel(String ar_name, String en_name, String type) {
        this.ar_name = ar_name;
        this.en_name = en_name;
        this.type = type;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
