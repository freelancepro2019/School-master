package com.softray_solutions.newschoolproject.ui.activities.newsContent;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.News;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsContentActivity extends BaseActivity implements ContentView {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.profile_job_text)
    TextView jobTextView;
    @BindView(R.id.profile_name_text)
    TextView nameTextView;
    @BindView(R.id.toolbar_background)
    ImageView toolbarImageView;
    @BindView(R.id.content_text)
    TextView contextTextView;
    @BindView(R.id.title_text)
    TextView titleTextView;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    private NewsContentPresenter newsContentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_news_content, contentFrameLayout);

        initPresenter();
        bindView();
        initToolbar();
        getLanguage();
        getNewsContent(getToken());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);

        newsContentPresenter = new NewsContentPresenter(this, new AppPrefsHelper(userPref));
        newsContentPresenter.getCurrentLanguage();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void getNewsContent(String token) {
        newsContentPresenter.getNewsContent(token);
    }

    private void getLanguage() {
        newsContentPresenter.getLanguage(this);
    }

    private String getToken() {
        return Objects.requireNonNull(getIntent().getExtras()).getString("token");
    }

    @Override
    public void setContentData(News userDatum) {
        try {
            Picasso.get().load(userDatum.getContactImage()).into(profileImage);
            Picasso.get().load(userDatum.getImagePath()).into(toolbarImageView);
            jobTextView.setText(userDatum.getJobTitle());
            nameTextView.setText(userDatum.getContactName());
            contextTextView.setText(userDatum.getContent());
            titleTextView.setText(userDatum.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prefLanguage(String language) {
        if (language.equals("en"))
            frameLayout.setRotationY(180);
    }

    @Override
    public void setError(String errorMessage) {
        try {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgressBar() {
        try {
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}