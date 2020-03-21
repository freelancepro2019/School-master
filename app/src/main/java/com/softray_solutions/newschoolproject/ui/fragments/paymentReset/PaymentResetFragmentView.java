package com.softray_solutions.newschoolproject.ui.fragments.paymentReset;

import com.softray_solutions.newschoolproject.adapters.PaymentResetAdapter;

interface PaymentResetFragmentView {
    void hideProgress();

    void setupAdapter(PaymentResetAdapter paymentResetAdapter);

    void showToast(String localizedMessage);

    void setEmptyView();
}
