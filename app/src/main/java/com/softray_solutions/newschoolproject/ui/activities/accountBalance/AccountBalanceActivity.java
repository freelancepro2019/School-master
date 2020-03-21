package com.softray_solutions.newschoolproject.ui.activities.accountBalance;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountBalanceActivity extends BaseActivity implements AccountBalanceInterface {
    @BindView(R.id.accountBalance_toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    Unbinder unbinder;
    AccountBalancePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_account_balance, contentFrameLayout);
        bindView();
        initPresenter();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.account_statement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new AccountBalancePresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.startActivity();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void setAccountBalanceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.accountBalance_container, fragment, tag).commit();
    }
}