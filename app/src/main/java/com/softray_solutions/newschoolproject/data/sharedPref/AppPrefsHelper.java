package com.softray_solutions.newschoolproject.data.sharedPref;

import android.content.SharedPreferences;

public class AppPrefsHelper {
    private SharedPreferences preferences;

    public AppPrefsHelper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getSelectedLanguage() {
        return preferences.getString("language", "not selected");
    }

    public void setSelectedLanguage(String language) {
        preferences.edit().putString("language", language).apply();
    }
}
