package com.softray_solutions.newschoolproject.ui.fragments.settings;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.User;

public interface SettingsView {

    void setUserData(User userData);

    void setToast(String message);

    void Logout();

    void updateImage(String data);

    void hideProgressDialog();

    void showProgressDialog();

    void requestPermissionFromUser();

    void startGalleryIntent();

    void changeLanguage(Configuration configuration);
}
