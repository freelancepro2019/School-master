package com.softray_solutions.newschoolproject.model;

import com.google.gson.annotations.SerializedName;

public class ObjectDataModel<T> {

    private int success = 0;
    private String message = "";

    @SerializedName(value = "user_data")
    private T data;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
