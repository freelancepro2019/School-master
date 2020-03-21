package com.softray_solutions.newschoolproject.ui.fragments.login;

import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.model.ObjectDataModel;

interface LoginFragmentView {
    void setUsernameError(String errorMessage);

    void setPasswordError(String errorMessage);

    void showProgressDialog();

    void hideProgressDialog();

    void loginFailed(String message);

    void adminException();

    void loginSuccess(ObjectDataModel dataModel);

    void initForgetPasswordFragment(Fragment fragment, String tag);
}
