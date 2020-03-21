package com.softray_solutions.newschoolproject.ui.fragments.lessons;

import com.softray_solutions.newschoolproject.adapters.lessons.LessonsAdapter;

interface LessonsView {
    void hideProgressBar();

    void setEmptyView();

    void setAdapter(LessonsAdapter adapter);

    void setError(String errorMessage);

    void startLessonFragment(String lessonToken);

}
