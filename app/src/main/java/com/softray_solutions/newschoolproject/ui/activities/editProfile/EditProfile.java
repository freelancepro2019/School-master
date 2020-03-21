package com.softray_solutions.newschoolproject.ui.activities.editProfile;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditProfile extends AppCompatActivity implements EditProfileView {
    Unbinder unbinder;
    SharedPreferences sharedPreferences;
    EditProfilePresenter presenter;
    User user;
    @BindView(R.id.edit_name)
    EditText editNameET;
    @BindView(R.id.edit_email)
    EditText editEmailET;
    @BindView(R.id.edit_phone)
    EditText editPhoneET;
    String id, type;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        bindView();
        initProgressDialog();
        initSharedPref();
        getUserData();
        initPresenter();
        fillFields();
    }

    @OnClick(R.id.save_button)
    public void saveData() {
        String name, emailAddress, phone;
        name = editNameET.getText().toString().trim();
        emailAddress = editEmailET.getText().toString().trim();
        phone = editPhoneET.getText().toString().trim();
        presenter.updateData(type, id, name, emailAddress, phone);
    }

    private void fillFields() {
        if (user.getName() != null) {
            editNameET.setText(user.getName());
        }
        if (user.getMail() != null) {
            editEmailET.setText(user.getMail());
        }
        if (user.getMobile() != null) {
            editPhoneET.setText(user.getMobile());
        }
    }

    private void getUserData() {
        user = Customization.obtainUser(sharedPreferences);
        id = user.getId();
        type = user.getType();

    }

    private void initPresenter() {
        presenter = new EditProfilePresenter(this, new AppPrefsHelper(sharedPreferences));
    }

    private void initSharedPref() {
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void setSuccess(String message, User data) {
        setToast(message);
        user.setName(data.getName());
        user.setMail(data.getMail());
        user.setMobile(data.getMobile());
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        sharedPreferences.edit().putString("user", userString).apply();
        BaseActivity.textView.setText(data.getName());

        startActivity(new Intent(EditProfile.this, MainActivity.class));
        finish();
    }

    @Override
    public void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initProgressDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.loading));
    }
}