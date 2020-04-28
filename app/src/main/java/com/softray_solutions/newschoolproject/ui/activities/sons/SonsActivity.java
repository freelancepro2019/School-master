package com.softray_solutions.newschoolproject.ui.activities.sons;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.SonsAdapter;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.Son;
import com.softray_solutions.newschoolproject.ui.activities.accountBalance.AccountBalanceActivity;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.softray_solutions.newschoolproject.ui.activities.classSchedule.ClassScheduleActivity;
import com.softray_solutions.newschoolproject.ui.activities.homework.HomeworkActivity;
import com.softray_solutions.newschoolproject.ui.activities.studentEvaluation.StudentEvaluationActivity;
import com.softray_solutions.newschoolproject.ui.activities.weeklyPlan.WeeklyPlanActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SonsActivity extends BaseActivity implements SonsView {
    @BindView(R.id.sonsRecyclerView)
    RecyclerView sonsRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sons_progress_bar)
    ProgressBar progressBar;
    private SonsPresenter presenter;
    private SonsAdapter adapter;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_sons, contentFrameLayout);
        getIntentExtras();
        bindView();
        initPresenter();
        initToolbar();
        initRecycler();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.sons_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sonsRecyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new SonsPresenter(this, userPref, position, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.getExtras();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void setSons(List<Son> list) {
        adapter = new SonsAdapter(list, presenter);
        sonsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void StartClassSchedule(String Id) {

        Intent intent = new Intent(SonsActivity.this, ClassScheduleActivity.class);
        intent.putExtra("id", Id);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void startStudentEvaluationActivity() {
        startActivity(new Intent(SonsActivity.this, StudentEvaluationActivity.class));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    private void getIntentExtras() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
    }

    @Override
    public void startHomeworkActivity(String id, String schoolId) {
        Intent intent = new Intent(this, HomeworkActivity.class);
        intent.putExtra("userId", id);
        intent.putExtra("schoolId", schoolId);
        startActivity(intent);
    }

    @Override
    public void startWeeklyPlanActivity(String id) {
        Intent intent = new Intent(SonsActivity.this, WeeklyPlanActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void startAccountBalanceActivity() {
        startActivity(new Intent(SonsActivity.this, AccountBalanceActivity.class));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
