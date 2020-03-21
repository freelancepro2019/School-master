package com.softray_solutions.newschoolproject.ui.activities.homework;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.ui.fragments.revisions.RevisionsFragment;

import java.util.Locale;

class HomeworkPresenter {

    private HomeworkView view;
    private PrefsInteractor interactor;

    HomeworkPresenter(HomeworkView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void startHomeworkFragment(String userId, String schoolId) {
        Fragment fragment = new RevisionsFragment();

        if (!userId.equals("0")) {
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            bundle.putString("schoolId", schoolId);
            bundle.putBoolean("homework", true);
            fragment.setArguments(bundle);
        }

        view.startHomeworkFragment(fragment, "homework");
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
                view.changeLanguage(configuration);
            }
        });
    }
}
