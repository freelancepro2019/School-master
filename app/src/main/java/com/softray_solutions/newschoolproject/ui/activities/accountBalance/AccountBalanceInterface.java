package com.softray_solutions.newschoolproject.ui.activities.accountBalance;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface AccountBalanceInterface {
    void setAccountBalanceFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
