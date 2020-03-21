package com.softray_solutions.newschoolproject.ui.activities.askYourTeacher;

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

public class AskYourTeacherActivity extends BaseActivity implements AskYourTeacherActivityView {
    @BindView(R.id.revisions_toolbar)
    Toolbar toolbar;
    AskYourTeacherActivityPresenter presenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_revisions_activity, contentFrameLayout);
        bindView();
        initPresenter();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.ask_your_teacher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new AskYourTeacherActivityPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.setFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_revision_container, fragment, tag).commit();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBind() {
        unbinder.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind();
    }
}
