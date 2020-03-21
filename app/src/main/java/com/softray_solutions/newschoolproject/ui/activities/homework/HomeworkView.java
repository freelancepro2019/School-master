package com.softray_solutions.newschoolproject.ui.activities.homework;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface HomeworkView {

    void startHomeworkFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
