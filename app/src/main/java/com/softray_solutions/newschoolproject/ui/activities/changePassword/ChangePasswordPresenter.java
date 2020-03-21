package com.softray_solutions.newschoolproject.ui.activities.changePassword;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ChangePasswordPresenter {
    private ChangePasswordView view;
    private PrefsInteractor interactor;

    ChangePasswordPresenter(ChangePasswordView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    public void getCurrentLanguage() {
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
            view.changeLanguage(configuration);
        });
    }

    void setNewPassword(String oldPW, String confirmNewPW, String id) {
        view.showDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.setNewPassword(oldPW, confirmNewPW, id).enqueue(new Callback<ArrayDataModel>() {
            @Override
            public void onResponse(Call<ArrayDataModel> call, Response<ArrayDataModel> response) {
                view.hideDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.success();
                    } else {
                        view.setToast(response.body().getMessage());
                    }
                } else {
                    view.setToast("Null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel> call, Throwable t) {
                view.hideDialog();
                view.setToast(t.getLocalizedMessage());
            }
        });
    }
}
