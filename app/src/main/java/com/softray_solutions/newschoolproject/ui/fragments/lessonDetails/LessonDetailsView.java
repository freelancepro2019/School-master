package com.softray_solutions.newschoolproject.ui.fragments.lessonDetails;

import com.softray_solutions.newschoolproject.adapters.lessonDetails.LessonDetailsAdapter;

interface LessonDetailsView {
    void hideProgressBar();

    void setAdapter(LessonDetailsAdapter adapter);

    void setError(String errorMessage);

}
