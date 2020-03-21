package com.softray_solutions.newschoolproject.ui.fragments.forgetPassword;

import androidx.fragment.app.Fragment;

interface ForgetPasswordView {
    void setUsernameError(int message);

    void setEmptyPhoneAndEmailAddressError(int message);

    void setToast(String message);

    void showDialog();

    void dismissDialog();

    void setSuccess(int id);

    void startCodeValidateFragment(Fragment fragment, String tag, int id);
}
