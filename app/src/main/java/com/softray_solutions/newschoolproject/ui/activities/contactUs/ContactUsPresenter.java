package com.softray_solutions.newschoolproject.ui.activities.contactUs;

import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Patterns;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ContactUsPresenter {
    private ContactUsView view;
    private MyInterface myInterface;
    private PrefsInteractor interactor;

    ContactUsPresenter(ContactUsView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        myInterface = Common.getMyInterface();
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    private void sendFeedback(String emailAddress, String name, String content) {
        view.showProgressDialog();

        myInterface.sendFeedback(emailAddress, name, content).enqueue(new Callback<ObjectDataModel<String>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<String>> call, Response<ObjectDataModel<String>> response) {
                view.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.onSuccess();
                    } else {
                        view.setErrorMessage(response.body().getMessage());
                    }
                } else {
                    view.setErrorMessage("null response");
                }

            }

            @Override
            public void onFailure(Call<ObjectDataModel<String>> call, Throwable t) {
                view.hideProgressDialog();
                view.setErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    void checkInformation(String name, String email, String content) {

        if (name.isEmpty() || name.length() > 120) {
            view.setEmailError();
        } else if (!isValidEmail(email)) {
            view.setEmailError();
        } else if (content.isEmpty()) {
            view.setMessageError();
        } else {
            sendFeedback(email, name, content);
        }

    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
