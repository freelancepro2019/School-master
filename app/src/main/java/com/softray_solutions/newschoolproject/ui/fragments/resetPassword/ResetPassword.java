package com.softray_solutions.newschoolproject.ui.fragments.resetPassword;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.activities.login.LoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ResetPassword extends Fragment implements ResetPasswordView {
    Unbinder unbinder;
    @BindView(R.id.new_password)
    EditText passwordET;
    @BindView(R.id.confirm_password)
    EditText confirmPasswordET;
    @BindView(R.id.confirm_password_button)
    Button confirm;
    String password, confirmPassword, code;
    ResetPasswordPresenter presenter;
    int id;
    Bundle bundle;
    Dialog dialog;

    public ResetPassword() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initProgressDialog();
        initPresenter();
        getArgs();
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        });
        confirmPasswordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPassword = s.toString();
                if (s.toString().equals("")) {
                    confirm.setEnabled(false);
                } else {
                    confirm.setEnabled(true);
                }
            }
        });
    }

    @OnClick(R.id.confirm_password_button)
    public void confirm() {
        if (!password.equals(confirmPassword) && !password.equals("")) {
            confirmPasswordET.setError(getString(R.string.password_doesnt_match));
        } else if (password.length() < 6) {
            passwordET.setError(getString(R.string.short_password));
        } else {
            presenter.setNewPassword(id, password, code);
        }
    }

    private void initPresenter() {
        presenter = new ResetPasswordPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    private void getArgs() {
        bundle = getArguments();
        id = bundle.getInt("id");
        code = bundle.getString("code");
    }

    private void initProgressDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.loading));
    }

    @Override
    public void showDialog() {
        if (isVisible()) {
            dialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if (isVisible()) {
            dialog.dismiss();
        }
    }

    @Override
    public void setSuccess() {
        if (isVisible()) {
            Toast.makeText(getContext(), getString(R.string.password_changed), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getContext()).startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    public void setToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}