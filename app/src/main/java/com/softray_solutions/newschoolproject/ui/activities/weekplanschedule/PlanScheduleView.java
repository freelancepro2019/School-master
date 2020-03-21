package com.softray_solutions.newschoolproject.ui.activities.weekplanschedule;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.StudentPlanClass;
import com.softray_solutions.newschoolproject.model.TeacherPlanClass;

import java.util.List;

interface PlanScheduleView {
    void showEmptyView();

    void hideEmptyView();

    void showRecycler();

    void hideRecycler();

    void showProgressBar();

    void hideProgressBar();

    void setClasses(List<StudentDay<TeacherPlanClass>> data);

    void showToast(String planContent);

    void setError(String errorMessage);

    void changeLanguage(Configuration configuration);

    void setStudentClasses(List<StudentDay<StudentPlanClass>> data);
}
