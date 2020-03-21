package com.softray_solutions.newschoolproject.ui.activities.newsContent;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.News;

public interface ContentView {

    void setContentData(News userDatum);

    void prefLanguage(String language);

    void setError(String errorMessage);

    void hideProgressBar();

    void changeLanguage(Configuration configuration);
}
