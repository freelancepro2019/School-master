package com.softray_solutions.newschoolproject.ui.activities.info;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface InfoView {

    void setInfoFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
