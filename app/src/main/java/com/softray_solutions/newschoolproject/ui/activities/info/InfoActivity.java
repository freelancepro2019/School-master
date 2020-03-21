package com.softray_solutions.newschoolproject.ui.activities.info;

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

public class InfoActivity extends BaseActivity implements InfoView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    InfoPresenter presenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_info, contentFrameLayout);
        bindView();
        setCheckedItem();
        initPresenter();
        initToolbar();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new InfoPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.setUpInfoFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.info_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setCheckedItem() {
        adapter.setCheckedItem(2);
    }

    @Override
    public void setInfoFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.info_container, fragment, tag).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckedItem();
    }
}
