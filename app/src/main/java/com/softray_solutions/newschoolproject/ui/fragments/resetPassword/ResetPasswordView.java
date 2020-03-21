package com.softray_solutions.newschoolproject.ui.fragments.resetPassword;

interface ResetPasswordView {
    void showDialog();

    void hideDialog();

    void setSuccess();

    void setToast(String message);
}
