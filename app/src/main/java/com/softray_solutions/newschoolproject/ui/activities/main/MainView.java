package com.softray_solutions.newschoolproject.ui.activities.main;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface MainView {


    void setCurrentFragment();

    void openNewFragment(boolean firstFragment, Fragment fragment, String tag);

    void setCurrentItemID(int id);

    void setActionBarTitle(int title);

    void backPressed();

    void hideBottomBar();

    void showBottomBar();

    void setNotificationBadge(int notificationsCount);

    void changeLanguage(Configuration configuration);
}
