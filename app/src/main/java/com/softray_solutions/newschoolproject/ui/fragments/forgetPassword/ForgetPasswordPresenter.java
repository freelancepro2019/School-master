package com.softray_solutions.newschoolproject.ui.fragments.forgetPassword;

import android.view.View;
import android.widget.EditText;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.ui.fragments.validateCode.ValidateCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ForgetPasswordPresenter {
    private ForgetPasswordView view;
    private String username = "", emailAddress = "", phone = "";

    ForgetPasswordPresenter(ForgetPasswordView view) {
        this.view = view;
    }

    void validateFields(EditText usernameET, EditText emailAddressET, EditText phoneET) {

        username = usernameET.getText().toString().trim();

        if (emailAddressET.getVisibility() == View.VISIBLE) {
            emailAddress = emailAddressET.getText().toString().trim();
        }

        if (phoneET.getVisibility() == View.VISIBLE) {
            phone = phoneET.getText().toString().trim();
        }

        if (username.equals("")) {
            view.setUsernameError(R.string.invalid_username);
        } else if (phone.equals("") && emailAddress.equals("")) {
            view.setEmptyPhoneAndEmailAddressError(R.string.invalid_email);
        } else {
            getCode();
        }
    }

    private void getCode() {
        view.showDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getResetCode(username, phone, emailAddress).enqueue(new Callback<ObjectDataModel<Integer>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<Integer>> call, Response<ObjectDataModel<Integer>> response) {
                view.dismissDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setSuccess(response.body().getData());
                    } else {
                        view.setToast(response.body().getMessage());
                    }
                } else {
                    view.setToast("Null response");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<Integer>> call, Throwable t) {
                view.dismissDialog();
                view.setToast(t.getLocalizedMessage());
            }
        });
    }

    void startCodeValidateFragment(int id) {
        view.startCodeValidateFragment(new ValidateCode(), "ValidateCodeFragment", id);
    }
}
