package com.softray_solutions.newschoolproject.ui.fragments.names;

import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.adapters.Students.StudentsAdapter;

public interface EvaluationNamesView {

    void setAdapter(StudentsAdapter adapter);

    void setError(String errorMessage);

    void startFragment(Fragment fragment, String tag);

    void setEmptyView();

    void showPregress();

    void hideProgress();
}
