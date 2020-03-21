package com.softray_solutions.newschoolproject.ui.activities.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.softray_solutions.newschoolproject.ui.activities.onBoarding.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();

        SharedPreferences preferences = getSharedPreferences("OnBoarding", MODE_PRIVATE);
        boolean firstRun = preferences.getBoolean("FirstRun", true);
        if (firstRun) {
            startActivity(new Intent(this, OnBoardingActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        SplashPresenter presenter = new SplashPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
    }
}