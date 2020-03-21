package com.softray_solutions.newschoolproject.ui.activities.onBoarding;

import android.content.res.Configuration;

public interface OnBoardingView {

    void startCodeActivity();

    void rotateViewPager();

    void setNextFragment();

    void changeLanguage(Configuration configuration);
}
