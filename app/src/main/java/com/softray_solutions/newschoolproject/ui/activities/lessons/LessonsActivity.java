package com.softray_solutions.newschoolproject.ui.activities.lessons;

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

public class LessonsActivity extends BaseActivity implements LessonsView {
    @BindView(R.id.revisions_toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    private LessonsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_revisions_activity, contentFrameLayout);
        bindView();
        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);

        presenter = new LessonsPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
        presenter.startLessonsFragment();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void startLessonsFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_revision_container, fragment, tag).commit();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.lessons_preparation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
