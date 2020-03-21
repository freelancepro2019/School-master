package com.softray_solutions.newschoolproject.ui.activities.sons;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.Son;

import java.util.List;

public interface SonsView {
    void setSons(List<Son> list);

    void setError(String errorMessage);

    void hideProgressBar();

    void StartClassSchedule(String Id);

    void startStudentEvaluationActivity();

    void startWeeklyPlanActivity(String id);

    void startHomeworkActivity(String id, String schoolId);

    void startAccountBalanceActivity();

    void changeLanguage(Configuration configuration);
}