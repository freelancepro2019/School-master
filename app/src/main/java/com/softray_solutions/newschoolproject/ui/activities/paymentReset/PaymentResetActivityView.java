package com.softray_solutions.newschoolproject.ui.activities.paymentReset;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

interface PaymentResetActivityView {
    void changeLanguage(Configuration configuration);

    void setPaymentResetFragment(Fragment fragment, String tag);
}
