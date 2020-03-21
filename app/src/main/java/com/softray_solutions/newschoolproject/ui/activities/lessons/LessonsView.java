package com.softray_solutions.newschoolproject.ui.activities.lessons;


import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface LessonsView {

    void startLessonsFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
