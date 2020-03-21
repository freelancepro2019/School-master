package com.softray_solutions.newschoolproject.ui.fragments.validateCode;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.ui.fragments.resetPassword.ResetPassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ValidateCodePresenter {
    private ValidateCodeView view;

    ValidateCodePresenter(ValidateCodeView view) {
        this.view = view;
    }

    void confirmCode(int id, String code) {
        view.showDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.checkActivation(id, code).enqueue(new Callback<ArrayDataModel>() {
            @Override
            public void onResponse(Call<ArrayDataModel> call, Response<ArrayDataModel> response) {
                view.hideDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setSuccess();
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

    void startSetNewPasswordFragment(String code, int id) {
        view.startResetPasswordFragment(new ResetPassword(), "ResetPasswordFragment", code, id);
    }
}
