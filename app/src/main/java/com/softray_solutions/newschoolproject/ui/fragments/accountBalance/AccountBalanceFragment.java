package com.softray_solutions.newschoolproject.ui.fragments.accountBalance;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.AccountBalanceDataModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class AccountBalanceFragment extends Fragment implements AccountBalanceFragmentView {
    @BindView(R.id.previous_balance)
    TextView previousBalance;
    @BindView(R.id.totalBenefits)
    TextView totalBenefits;
    @BindView(R.id.totalDiscounts)
    TextView totalDiscounts;
    @BindView(R.id.netReceivable)
    TextView netReceivable;
    @BindView(R.id.netPayable)
    TextView netPayable;
    @BindView(R.id.remained)
    TextView remained;
    @BindView(R.id.accountBalance_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_accountBalance_text_view)
    TextView emptyTextView;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.name_textView)
    TextView nameTextView;
    @BindView(R.id.row_level_textView)
    TextView rowLevelTextView;
    @BindView(R.id.mobile_textView)
    TextView mobilTextView;
    private Unbinder unbinder;
    private AccountBalanceFragmentPresenter presenter;

    public AccountBalanceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        presenter.getPaymentStatement();
    }

    private void initPresenter() {
        presenter = new AccountBalanceFragmentPresenter(this,
                Customization.obtainUser(getContext().getSharedPreferences("userData", MODE_PRIVATE)));
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    @Override
    public void hideProgress() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(AccountBalanceDataModel data) {
        if (isVisible()) {
            emptyTextView.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            nameTextView.setText(data.getName());
            rowLevelTextView.setText(data.getRowLavel());
            mobilTextView.setText(data.getPhone().trim());
            previousBalance.setText(String.valueOf(data.getPreviousBalance()));
            totalBenefits.setText(String.valueOf(data.getTotalBenefits()));
            totalDiscounts.setText(String.valueOf(data.getTotalDeductions()));
            netReceivable.setText(String.valueOf(data.getNetDue()));
            netPayable.setText(String.valueOf(data.getNetPayout()));
            remained.setText(String.valueOf(data.getResidual()));
        }
    }

    @Override
    public void setEmptyView() {
        if (isVisible()) {
            cardView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
