package com.softray_solutions.newschoolproject.ui.fragments.weeklyPlan;

import com.softray_solutions.newschoolproject.model.SemesterSample;
import com.softray_solutions.newschoolproject.model.Week;

import java.util.List;

public interface WeeklyPlanFragmentView {

    void setError(String errorMessage);

    void setSemesters(List<SemesterSample> semesters);

    void setWeeks(List<Week> weeks);

}
