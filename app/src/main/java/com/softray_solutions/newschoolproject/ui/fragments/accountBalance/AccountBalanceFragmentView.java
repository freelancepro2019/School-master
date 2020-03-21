package com.softray_solutions.newschoolproject.ui.fragments.accountBalance;

import com.softray_solutions.newschoolproject.model.AccountBalanceDataModel;

interface AccountBalanceFragmentView {
    void hideProgress();

    void setData(AccountBalanceDataModel data);

    void setEmptyView();

    void showToast(String message);
}
