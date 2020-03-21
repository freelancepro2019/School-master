package com.softray_solutions.newschoolproject.ui.activities.changePassword;

import android.content.res.Configuration;

interface ChangePasswordView {
    void changeLanguage(Configuration configuration);

    void showDialog();

    void hideDialog();

    void success();

    void setToast(String message);
}
