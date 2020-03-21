package com.softray_solutions.newschoolproject.ui.activities.splash;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;

import java.util.Locale;

class SplashPresenter {
    private SplashView splashView;
    private PrefsInteractor prefsInteractor;

    SplashPresenter(SplashView splashView, AppPrefsHelper appPrefsHelper) {
        this.splashView = splashView;
        this.prefsInteractor = new PrefsInteractor(appPrefsHelper);
    }

    void getCurrentLanguage() {
        prefsInteractor.getSelectedLanguage(result -> {
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
            splashView.changeLanguage(configuration);
        });
    }
}