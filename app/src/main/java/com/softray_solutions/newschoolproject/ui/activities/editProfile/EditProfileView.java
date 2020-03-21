package com.softray_solutions.newschoolproject.ui.activities.editProfile;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.User;

interface EditProfileView {
    void changeLanguage(Configuration configuration);

    void showDialog();

    void hideDialog();

    void setSuccess(String message, User data);

    void setToast(String message);
}
