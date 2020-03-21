package com.softray_solutions.newschoolproject.ui.fragments.revisions;

import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.model.Subject;

import java.util.List;

public interface RevisionsFragmentView {
    void setSpinnerData(List<Subject> subjectList);

    void setError(String message);

    void showListOfLibraries(Fragment fragment);

    void setView(int stringID);
}
