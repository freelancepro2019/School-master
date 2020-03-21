package com.softray_solutions.newschoolproject.ui.activities.weeklyPlan;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.ui.fragments.weeklyPlan.WeeklyPlanFragment;

import java.util.Locale;

class WeeklyPlanActivityPresenter {
    private WeeklyPlanActivityView weeklyPlanActivityView;
    private PrefsInteractor interactor;

    WeeklyPlanActivityPresenter(WeeklyPlanActivityView weeklyPlanActivityView, AppPrefsHelper appPrefsHelper) {
        this.weeklyPlanActivityView = weeklyPlanActivityView;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void setMainFragment() {
        weeklyPlanActivityView.startFragment(new WeeklyPlanFragment(), "WeeklyPlanFragment");
    }

    public void getCurrentLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                String currentLanguage;
                if (result.equals("not selected")) {
                    currentLanguage = Locale.getDefault().getLanguage();
                } else {
                    currentLanguage = result;
                }
                Locale locale = new Locale(currentLanguage);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                weeklyPlanActivityView.changeLanguage(configuration);
            }
        });
    }
}
