package com.softray_solutions.newschoolproject.ui.fragments.validateCode;

import androidx.fragment.app.Fragment;

interface ValidateCodeView {
    void showDialog();

    void hideDialog();

    void setToast(String message);

    void setSuccess();

    void startResetPasswordFragment(Fragment fragment, String tag, String code, int id);
}
