package com.softray_solutions.newschoolproject.ui.activities.askYourTeacher;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

interface AskYourTeacherActivityView {
    void startFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
