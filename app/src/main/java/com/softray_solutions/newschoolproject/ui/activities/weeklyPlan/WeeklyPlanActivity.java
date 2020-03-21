package com.softray_solutions.newschoolproject.ui.activities.weeklyPlan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeeklyPlanActivity extends BaseActivity implements WeeklyPlanActivityView {
    Unbinder unbinder;
    WeeklyPlanActivityPresenter planActivityPresenter;
    @BindView(R.id.weekly_plan_toolbar)
    Toolbar toolbar;
    SharedPreferences userPref;
    String sonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_weekly_plan, contentFrameLayout);
        bindView();
        userPref = getSharedPreferences("userData", MODE_PRIVATE);
        if (Customization.obtainUser(userPref).getType().equals("F")) {
            sonID = getExtra();
        }
        initPresenter();
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        planActivityPresenter.getCurrentLanguage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    private void initPresenter() {
        planActivityPresenter = new WeeklyPlanActivityPresenter(this, new AppPrefsHelper(userPref));
        planActivityPresenter.getCurrentLanguage();
        planActivityPresenter.setMainFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.week_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    private String getExtra() {
        Intent intent = getIntent();
        return intent.getStringExtra("ID");
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("sonID", sonID);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.weekly_plan_container, fragment, tag).commit();
    }
}