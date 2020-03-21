package com.softray_solutions.newschoolproject.ui.activities.base;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.contactUs.ContactUsActivity;
import com.softray_solutions.newschoolproject.ui.activities.info.InfoActivity;
import com.softray_solutions.newschoolproject.ui.activities.login.LoginActivity;
import com.yarolegovich.slidingrootnav.SlideGravity;

import java.util.Locale;

class BasePresenter {

    private BaseView view;
    private SharedPreferences sharedPreferences;
    private PrefsInteractor interactor;

    BasePresenter(BaseView view, SharedPreferences sharedPreferences, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        interactor = new PrefsInteractor(appPrefsHelper);
    }

    void getLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                String language;
                if (result.equals("not selected")) {
                    language = Locale.getDefault().getLanguage();
                } else {
                    language = result;
                }
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                view.changeLanguage(configuration);
                SlideGravity slideGravity;
                if (language.equals("ar")) {
                    slideGravity = SlideGravity.RIGHT;
                } else {
                    slideGravity = SlideGravity.LEFT;
                }
                view.setSlider(slideGravity);
            }
        });
    }

    void openNavDrawerFragment(int position, SharedPreferences preferences) {
        view.closeSlidNav();
        boolean isLogged = preferences.getBoolean("isLogged", false);
        switch (position) {
            case 0:
                if (!isLogged) {
                    view.startDrawerActivity(LoginActivity.class);
                } else {
                    view.logout();
                }
                break;
            case 1:
                view.startDrawerActivity(ContactUsActivity.class);
                break;
            case 2:
                view.startDrawerActivity(InfoActivity.class);
                break;
        }
    }

    void checkLogin() {
        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);
        Log.i("isLogged", isLogged + "");
        if (isLogged) {
            Gson gson = new Gson();
            String userString = sharedPreferences.getString("user", "");
            User user = gson.fromJson(userString, User.class);
            view.setUserData(user);
        } else {
            view.removeUserDetails();
        }
    }

    void closeMenu() {
        view.closeSlidNav();
    }
}
