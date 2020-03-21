package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArrayDataModel<T> {

    private String message = "";
    private int success = 0;

    @SerializedName(value = "user_data", alternate = {"homework_data"})
    private List<T> data;

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public List<T> getData() {
        return data;
    }
}
