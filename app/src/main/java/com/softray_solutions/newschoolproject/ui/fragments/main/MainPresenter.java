package com.softray_solutions.newschoolproject.ui.fragments.main;

import android.content.Context;

import java.util.Locale;

class MainPresenter {

    MainView view;

    MainPresenter(MainView view) {
        this.view = view;
    }

    void checkLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        String language = current.getLanguage();

        if (language.equals("ar")) {
            view.rotateViewPager();
        }
    }

}
