package com.softray_solutions.newschoolproject;


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.model.User;

public class Customization {
    public final static String demo = "التجربية";
    public final static String ibnRushdSchools = "مدارس بن رشد";
    public final static String beetElQeemSchools = "مدارس بيت القيم";
    public final static String zaidSchools = "مدارس زيد";
    public final static String andaulsSchools = "مدارس الأندلس";
    public final static String abhaSchools = "مدارس ابها";
    public final static String ahdSchools = "مدارس عهد";

    public static User obtainUser(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        return gson.fromJson(userString, User.class);
    }
}