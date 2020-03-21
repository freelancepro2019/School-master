package com.softray_solutions.newschoolproject.ui.fragments.teacherLessonPreparation;

import com.softray_solutions.newschoolproject.ui.fragments.addLessonPreparation.AddLessonPreparation;

class TeacherLessonPreparationFragmentPresenter {
    private TeacherLessonPreparationView view;

    public TeacherLessonPreparationFragmentPresenter(TeacherLessonPreparationView view) {
        this.view = view;
    }

    void viewLessonsClicked() {
        view.openLessonPreparationViewActivity();
    }

    void addLessonsClicked() {
        view.openFragment(new AddLessonPreparation(), "AddLessonPreparation");
    }
}
