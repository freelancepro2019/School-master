package com.softray_solutions.newschoolproject.ui.fragments.info;

import android.content.Context;

import com.softray_solutions.newschoolproject.ui.activities.info.DetailedInfo;

import java.util.Locale;

public class InfoFragmentPresenter {
    private InfoFragmentView view;

    public InfoFragmentPresenter(InfoFragmentView view) {
        this.view = view;
    }

    public void checkLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        String language = current.getLanguage();

        view.getDeviceLanguage(language);
    }

    public void openDetailsFragment() {
        view.openNewFragment(new DetailedInfo(), "detailedInfo");

    }
}
