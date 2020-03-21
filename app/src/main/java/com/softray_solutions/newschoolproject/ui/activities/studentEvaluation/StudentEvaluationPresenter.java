package com.softray_solutions.newschoolproject.ui.activities.studentEvaluation;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;

import java.util.Locale;

class StudentEvaluationPresenter {

    private StudentEvaluationView view;
    private PrefsInteractor interactor;

    StudentEvaluationPresenter(StudentEvaluationView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void startStudentEvaluationFragment() {
        view.startStudentEvaluationFragment();
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
