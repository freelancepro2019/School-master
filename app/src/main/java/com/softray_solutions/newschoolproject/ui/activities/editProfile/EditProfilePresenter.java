package com.softray_solutions.newschoolproject.ui.activities.editProfile;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.User;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class EditProfilePresenter {
    private EditProfileView view;
    private PrefsInteractor interactor;

    EditProfilePresenter(EditProfileView view, AppPrefsHelper appPrefsHelper) {
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

    void updateData(String type, String id, String name, String emailAddress, String phone) {
        view.showDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.updateData(type, id, name, emailAddress, phone).enqueue(new Callback<ObjectDataModel<User>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<User>> call, Response<ObjectDataModel<User>> response) {
                view.hideDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setSuccess(response.body().getMessage(), response.body().getData());
                    } else {
                        view.setToast(response.body().getMessage());
                    }
                } else {
                    view.setToast("Null response");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<User>> call, Throwable t) {
                view.hideDialog();
                view.setToast(t.getLocalizedMessage());
            }
        });

    }
}