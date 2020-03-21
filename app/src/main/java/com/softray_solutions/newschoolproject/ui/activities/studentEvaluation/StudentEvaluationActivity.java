package com.softray_solutions.newschoolproject.ui.activities.studentEvaluation;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.softray_solutions.newschoolproject.ui.fragments.studentEvaluation.StudentEvaluationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StudentEvaluationActivity extends BaseActivity implements StudentEvaluationView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private StudentEvaluationPresenter presenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_student_evaluation, contentFrameLayout);

        bindView();
        initPresenter();
        initToolbar();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);

        presenter = new StudentEvaluationPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.startStudentEvaluationFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void startStudentEvaluationFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_evaluation_container, new StudentEvaluationFragment(), "studentEvaluation").commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
