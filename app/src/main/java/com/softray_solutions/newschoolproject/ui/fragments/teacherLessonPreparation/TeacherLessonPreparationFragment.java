package com.softray_solutions.newschoolproject.ui.fragments.teacherLessonPreparation;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.activities.lessons.LessonsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TeacherLessonPreparationFragment extends Fragment implements TeacherLessonPreparationView {

    Unbinder unbinder;
    private TeacherLessonPreparationFragmentPresenter preparationFragmentPresenter;

    public TeacherLessonPreparationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_lesson_preparation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    @OnClick(R.id.view_lesson_tv)
    public void viewClicked() {
        preparationFragmentPresenter.viewLessonsClicked();
    }

    @OnClick(R.id.add_lesson_tv)
    public void addClicked() {
        preparationFragmentPresenter.addLessonsClicked();
    }

    private void initPresenter() {
        preparationFragmentPresenter = new TeacherLessonPreparationFragmentPresenter(this);
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void openLessonPreparationViewActivity() {
        startActivity(new Intent(getActivity(), LessonsActivity.class));
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void openFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.teacher_lesson_preparation_container, fragment).addToBackStack(tag).commit();

    }
}
