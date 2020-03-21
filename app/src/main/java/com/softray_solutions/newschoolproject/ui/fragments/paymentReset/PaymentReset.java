package com.softray_solutions.newschoolproject.ui.fragments.paymentReset;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.PaymentResetAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class PaymentReset extends Fragment implements PaymentResetFragmentView {
    @BindView(R.id.paymentReset_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_paymentReset_text_view)
    TextView textView;
    @BindView(R.id.paymentResetRecyclerView)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private PaymentResetFragmentPresenter presenter;


    public PaymentReset() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_reset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();

    }

    private void initPresenter() {

        presenter = new PaymentResetFragmentPresenter(this,
                Customization.obtainUser(getContext().getSharedPreferences("userData", MODE_PRIVATE)));
        presenter.getPaymentReset();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void hideProgress() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setupAdapter(PaymentResetAdapter paymentResetAdapter) {
        if (isVisible()) {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(paymentResetAdapter);
        }
    }

    @Override
    public void showToast(String localizedMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setEmptyView() {
        if (isVisible()) {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}