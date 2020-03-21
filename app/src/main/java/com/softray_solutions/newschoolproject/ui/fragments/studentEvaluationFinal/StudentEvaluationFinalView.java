package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluationFinal;

import com.softray_solutions.newschoolproject.model.PositivesStudentDataModel;
import com.softray_solutions.newschoolproject.model.StudentSkillsDataModel;

import java.util.List;

interface StudentEvaluationFinalView {
    void setError(String message);

    void evalSuccess(String message);

    void setSkillsSpinnerData(List<StudentSkillsDataModel> data);

    void PositivesSpinnerData(List<PositivesStudentDataModel> data);
}
