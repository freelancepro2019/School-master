package com.softray_solutions.newschoolproject.ui.activities.lessonPreparationTeacher;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

interface LessonPreparationsTeacherView {

    void setFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
