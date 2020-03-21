package com.softray_solutions.newschoolproject.ui.fragments.login;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.fragments.forgetPassword.ForgetPasswordFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class LoginFragmentPresenter {
    private LoginFragmentView view;
    private SharedPreferences sharedPreferences;

    public LoginFragmentPresenter(LoginFragmentView view, SharedPreferences sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
    }


    void checkLogin(String username, String password) {
        if (username.isEmpty()) {
            view.setUsernameError("ادخل اسم المستخدم");
        } else if (password.isEmpty()) {
            view.setPasswordError("ادخل كلمة المرور");
        } else {
            login(username, password);
        }
    }

    private void login(String userName, String userPassword) {
        view.showProgressDialog();

        MyInterface myInterface = Common.getMyInterface();
        myInterface.doLogin(userName, userPassword).enqueue(new Callback<ObjectDataModel<User>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<User>> call, Response<ObjectDataModel<User>> response) {
                view.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().getType().equals("U")) {
                            view.adminException();
                        } else {
                            view.loginSuccess(response.body());
                            saveUserData(response.body().getData());
                        }

                    } else {
                        view.loginFailed(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<User>> call, Throwable t) {
                view.loginFailed(t.getLocalizedMessage());
                view.hideProgressDialog();
            }
        });
    }

    private void saveUserData(User userData) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String user = gson.toJson(userData);
        prefsEditor.putBoolean("isLogged", true);
        prefsEditor.putString("user", user);
        prefsEditor.apply();
    }

    void startForgetPasswordFragment() {
        view.initForgetPasswordFragment(new ForgetPasswordFragment(), "ForgetPasswordFragment");
    }

}
