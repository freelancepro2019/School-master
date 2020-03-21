package com.softray_solutions.newschoolproject.ui.activities.login;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginView {
    Unbinder unbinder;
    LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        initPresenter();
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this,
                new AppPrefsHelper(getSharedPreferences("userData", MODE_PRIVATE)));
        loginPresenter.getCurrentLanguage();
        loginPresenter.startFragment();
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.login_container, fragment, tag).commit();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}