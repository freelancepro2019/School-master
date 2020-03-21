package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;

class SubAskYourTeacherFragmentPresenter {
    SubAskYourTeacherFragmentView view;

    public SubAskYourTeacherFragmentPresenter(SubAskYourTeacherFragmentView view) {
        this.view = view;
    }

    public void setTextViews(String title, String content, String answer) {
        view.setTitle(title);
        view.setContent(content);
        view.setAnswer(answer);
    }
}
