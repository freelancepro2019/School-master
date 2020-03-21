package com.softray_solutions.newschoolproject.ui.activities.login;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface LoginView {
    void startFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
