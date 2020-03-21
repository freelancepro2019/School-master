package com.softray_solutions.newschoolproject.ui.activities.contactUs;

import android.content.res.Configuration;

public interface ContactUsView {
    void onSuccess();

    void setErrorMessage(String message);

    void setNameError();

    void setEmailError();

    void setMessageError();

    void showProgressDialog();

    void hideProgressDialog();

    void changeLanguage(Configuration configuration);

}
