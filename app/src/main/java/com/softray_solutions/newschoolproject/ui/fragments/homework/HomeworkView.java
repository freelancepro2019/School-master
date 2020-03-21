package com.softray_solutions.newschoolproject.ui.fragments.homework;

import com.softray_solutions.newschoolproject.adapters.homework.HomeworkAdapter;

public interface HomeworkView {

    void setError(String errorMessage);

    void setAdapter(HomeworkAdapter adapter);

    void setEmptyView();

    void showProgress();

    void hideProgress();
}
