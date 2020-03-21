package com.softray_solutions.newschoolproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;

import java.util.Locale;

public class BaseApp extends Application {
    public static String schoolNameVariable;
    private PrefsInteractor interactor;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        AppPrefsHelper appPrefsHelper = new AppPrefsHelper(preferences);
        interactor = new PrefsInteractor(appPrefsHelper);
        schoolNameVariable = getSharedPreferences("code", MODE_PRIVATE).getString("selectedCode", Customization.demo);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocal();

    }

    private void setLocal() {
        interactor.getSelectedLanguage(result -> {
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
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        });
    }
}
