package com.softray_solutions.newschoolproject.data.sharedPref;

public class PrefsInteractor {
    private AppPrefsHelper appPrefsHelper;

    public PrefsInteractor(AppPrefsHelper appPrefsHelper) {
        this.appPrefsHelper = appPrefsHelper;
    }

    public void getSelectedLanguage(PrefsHandler<String> prefsHandler) {
        String language = appPrefsHelper.getSelectedLanguage();
        prefsHandler.dataSaved(language);
    }

    public void setSelectedLanguage(String language) {
        appPrefsHelper.setSelectedLanguage(language);
    }
}
