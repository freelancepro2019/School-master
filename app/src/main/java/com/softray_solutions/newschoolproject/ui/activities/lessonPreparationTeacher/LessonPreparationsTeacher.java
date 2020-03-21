package com.softray_solutions.newschoolproject.ui.activities.lessonPreparationTeacher;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LessonPreparationsTeacher extends BaseActivity implements LessonPreparationsTeacherView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    private LessonPreparationsTeacherPresenter presneter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_lesson_preparations_teacher, contentFrameLayout);
        bindView();
        initToolbar();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindView();
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);

        presneter = new LessonPreparationsTeacherPresenter(this, new AppPrefsHelper(userPref));
        presneter.getCurrentLanguage();
        presneter.setTeacherLessonPreparationFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.lessons_preparation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unbindView() {
        unbinder.unbind();
    }


    @Override
    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.teacher_lesson_preparation_container, fragment, tag).commit();
    }
}
