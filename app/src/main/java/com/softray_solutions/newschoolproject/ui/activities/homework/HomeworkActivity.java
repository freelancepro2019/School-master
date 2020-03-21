package com.softray_solutions.newschoolproject.ui.activities.homework;

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

public class HomeworkActivity extends BaseActivity implements HomeworkView {
    @BindView(R.id.revisions_toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    private String userId = "0";
    private String schoolId = "0";
    private HomeworkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_revisions_activity, contentFrameLayout);
        bindView();
        initPresenter();
        initToolbar();
        getExtras();
        presenter.startHomeworkFragment(userId, schoolId);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new HomeworkPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.homework));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void startHomeworkFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_revision_container, fragment, tag).commit();
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString("userId");
            schoolId = bundle.getString("schoolId");
        }
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }
}
