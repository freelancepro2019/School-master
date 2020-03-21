package com.softray_solutions.newschoolproject.ui.activities.paymentReset;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PaymentResetActivity extends BaseActivity implements PaymentResetActivityView {
    Unbinder unbinder;
    @BindView(R.id.payment_reset_activity_toolbar)
    Toolbar toolbar;
    PaymentResetActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_payment_reset, contentFrameLayout);
        bindView();
        initToolbar();
        initPresenter();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.payment_receipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new PaymentResetActivityPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.startFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void setPaymentResetFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.payment_reset_activity_container, fragment, tag).commit();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }
}
