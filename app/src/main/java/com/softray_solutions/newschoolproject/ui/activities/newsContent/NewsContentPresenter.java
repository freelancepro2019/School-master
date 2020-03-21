package com.softray_solutions.newschoolproject.ui.activities.newsContent;

import android.content.Context;
import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.News;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class NewsContentPresenter {

    private ContentView view;
    private String language = "en";
    private PrefsInteractor interactor;

    NewsContentPresenter(ContentView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void getNewsContent(String token) {
        Common.getMyInterface().getNewsContent(token, language).enqueue(new Callback<ObjectDataModel<News>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<News>> call, Response<ObjectDataModel<News>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setContentData(response.body().getData());
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<News>> call, Throwable t) {
                view.setError(t.getLocalizedMessage());
                view.hideProgressBar();
            }
        });
    }

    void getLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        language = current.getLanguage();
        view.prefLanguage(language);
        if (language.equals("ar")) {
            language = "ar";
        }
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
