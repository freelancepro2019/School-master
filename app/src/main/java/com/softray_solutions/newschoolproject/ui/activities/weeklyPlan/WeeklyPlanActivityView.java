package com.softray_solutions.newschoolproject.ui.activities.weeklyPlan;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

interface WeeklyPlanActivityView {
    void startFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
