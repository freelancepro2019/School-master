package com.softray_solutions.newschoolproject.ui.activities.changePassword;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePassword extends AppCompatActivity implements ChangePasswordView {
    ChangePasswordPresenter presenter;
    Unbinder unbinder;
    Dialog dialog;
    SharedPreferences sharedPreferences;
    String oldPW, newPW, confirmNewPW, id;
    @BindView(R.id.old_password)
    EditText oldPasswordET;
    @BindView(R.id.password_new)
    EditText newPasswordET;
    @BindView(R.id.confirm_new_password)
    EditText confirmNewPasswordET;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bindView();
        initProgressDialog();
        initSharedPref();
        initPresenter();
        getUserData();
    }

    @OnClick(R.id.save_new_password)
    public void saveNewPassword() {
        oldPW = oldPasswordET.getText().toString();
        newPW = newPasswordET.getText().toString();
        confirmNewPW = confirmNewPasswordET.getText().toString();
        if (oldPW.isEmpty()) {
            oldPasswordET.setError(getString(R.string.field_required));
        } else if (newPW.isEmpty()) {
            newPasswordET.setError(getString(R.string.field_required));
        } else if (confirmNewPW.isEmpty()) {
            confirmNewPasswordET.setError(getString(R.string.field_required));
        } else if (newPW.length() < 6) {
            newPasswordET.setError(getString(R.string.short_password));
        } else if (oldPW.equals(newPW)) {
            newPasswordET.setError(getString(R.string.old_password_cant_be_new_password));
        } else if (!confirmNewPW.equals(newPW)) {
            confirmNewPasswordET.setError(getString(R.string.password_doesnt_match));
        } else {
            presenter.setNewPassword(oldPW, confirmNewPW, id);
        }
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
    public void success() {
        setToast(getString(R.string.password_changed));
        startActivity(new Intent(ChangePassword.this, MainActivity.class));
        finish();
    }

    @Override
    public void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getUserData() {
        user = Customization.obtainUser(sharedPreferences);
        id = user.getId();
    }

    private void initPresenter() {
        presenter = new ChangePasswordPresenter(this, new AppPrefsHelper(sharedPreferences));
        presenter.getCurrentLanguage();
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

    private void initSharedPref() {
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

    }

    private void initProgressDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.loading));
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}