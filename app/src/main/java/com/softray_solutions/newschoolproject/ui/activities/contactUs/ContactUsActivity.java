package com.softray_solutions.newschoolproject.ui.activities.contactUs;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContactUsActivity extends BaseActivity implements ContactUsView {

    Unbinder unbinder;
    ContactUsPresenter presenter;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.messge_content)
    EditText messageContentEditText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_contact_us, contentFrameLayout);

        bindView();
        setCheckedItem();
        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new ContactUsPresenter(this, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.feedback_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, getString(R.string.message_sent), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.button_send)
    public void sendMessage() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String content = messageContentEditText.getText().toString().trim();

        presenter.checkInformation(name, email, content);
    }

    @Override
    public void showProgressDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.please_wait));

        dialog.show();
    }

    private void setCheckedItem() {
        adapter.setCheckedItem(1);
    }

    @Override
    public void hideProgressDialog() {
        dialog.cancel();
    }

    @Override
    public void setNameError() {
        nameEditText.setError(getString(R.string.invalid_username));
    }

    @Override
    public void setEmailError() {
        emailEditText.setError(getString(R.string.invalid_email));
    }

    @Override
    public void setMessageError() {
        messageContentEditText.setError(getString(R.string.invalid_content));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCheckedItem();
    }

}
