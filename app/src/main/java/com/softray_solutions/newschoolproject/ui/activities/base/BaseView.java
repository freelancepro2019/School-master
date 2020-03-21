package com.softray_solutions.newschoolproject.ui.activities.base;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.User;
import com.yarolegovich.slidingrootnav.SlideGravity;

public interface BaseView {
    void logout();

    void setUserData(User userData);

    void removeUserDetails();

    void setSlider(SlideGravity slideGravity);

    void closeSlidNav();

    void startDrawerActivity(Class activity);

    void changeLanguage(Configuration configuration);
}
