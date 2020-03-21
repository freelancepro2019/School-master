package com.softray_solutions.newschoolproject.ui.fragments.accountBalance;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.AccountBalanceDataModel;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AccountBalanceFragmentPresenter {
    private AccountBalanceFragmentView view;
    private String sessionToken;

    AccountBalanceFragmentPresenter(AccountBalanceFragmentView view, User userData) {
        this.view = view;
        this.sessionToken = userData.getSessionToken();
    }

    void getPaymentStatement() {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getParentPaymentStatement(sessionToken).enqueue(new Callback<ObjectDataModel<AccountBalanceDataModel>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<AccountBalanceDataModel>> call, Response<ObjectDataModel<AccountBalanceDataModel>> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setData(response.body().getData());
                    } else {
                        view.setEmptyView();
                    }
                } else {
                    view.showToast("Null response");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<AccountBalanceDataModel>> call, Throwable t) {
                view.hideProgress();
                view.setEmptyView();
                view.showToast(t.getLocalizedMessage());
            }
        });
    }
}