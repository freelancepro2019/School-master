package com.softray_solutions.newschoolproject.ui.fragments.addLessonPreparation;

import com.softray_solutions.newschoolproject.model.AddLessonPreparationSubject;
import com.softray_solutions.newschoolproject.model.Skill;

import java.util.List;

interface AddLessonPreparationView {
    void setSubjects(List<AddLessonPreparationSubject> data);

    void showToast(String message);

    void setSkills(List<Skill> data);

    void setDate(String formattedDate);

    void showDialog();

    void dismissDialog();

    void clearFields();

    void requestPermission();
}