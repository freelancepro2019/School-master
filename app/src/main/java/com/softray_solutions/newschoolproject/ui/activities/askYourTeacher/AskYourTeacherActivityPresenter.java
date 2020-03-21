package com.softray_solutions.newschoolproject.ui.activities.askYourTeacher;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.ui.fragments.revisions.RevisionsFragment;

import java.util.Locale;

class AskYourTeacherActivityPresenter {
    private AskYourTeacherActivityView askYourTeacherActivityView;
    private PrefsInteractor interactor;

    AskYourTeacherActivityPresenter(AskYourTeacherActivityView askYourTeacherActivityView, AppPrefsHelper appPrefsHelper) {
        this.askYourTeacherActivityView = askYourTeacherActivityView;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void setFragment() {
        askYourTeacherActivityView.startFragment(new RevisionsFragment(), "AskYourTeacher");
    }

    void getCurrentLanguage() {
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
                askYourTeacherActivityView.changeLanguage(configuration);
            }
        });
    }
}
