package com.softray_solutions.newschoolproject.ui.fragments.paymentReset;

import com.softray_solutions.newschoolproject.adapters.PaymentResetAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.ParentResetDataModel;
import com.softray_solutions.newschoolproject.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentResetFragmentPresenter {
    private PaymentResetFragmentView view;
    private String sessionToken;
    private List<ParentResetDataModel> result;

    PaymentResetFragmentPresenter(PaymentResetFragmentView view, User userData) {
        this.view = view;
        this.sessionToken = userData.getSessionToken();
    }

    void getPaymentReset() {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getParentReset(sessionToken).enqueue(new Callback<ArrayDataModel<ParentResetDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<ParentResetDataModel>> call, Response<ArrayDataModel<ParentResetDataModel>> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            result = response.body().getData();
                            view.setupAdapter(new PaymentResetAdapter(PaymentResetFragmentPresenter.this));
                        } else {
                            view.setEmptyView();
                        }
                    } else {
                        view.setEmptyView();
                    }
                } else {
                    view.showToast("Null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<ParentResetDataModel>> call, Throwable t) {
                view.hideProgress();
                view.showToast(t.getLocalizedMessage());
            }
        });
    }

    public int getSize() {
        return result.size();
    }

    public void setReceiptData(PaymentResetAdapter.MyViewHolder holder, int position) {
        holder.receiptID.setText(String.valueOf(result.get(position).getID()));
        holder.paymentMethod.setText(result.get(position).getPaymentMethod().trim());
        holder.number.setText(result.get(position).getNumber().trim());
        holder.amount.setText(String.valueOf(result.get(position).getAmount()));
        holder.year.setText(result.get(position).getYearName().trim());
        String date = result.get(position).getDate().trim();
        String[] realDate = date.split("T");
        holder.date.setText(realDate[0]);
    }
}