package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;


import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.model.AskYourTeacher;

import java.util.List;

interface AskYourTeacherFragmentView {
    void hideProgressBar();

    void setEmptyResponse();

    void setResponse(List<AskYourTeacher> listOfQuestions);

    void setError(String message);

    void startNextFragment(Fragment fragment, String tag);
}
