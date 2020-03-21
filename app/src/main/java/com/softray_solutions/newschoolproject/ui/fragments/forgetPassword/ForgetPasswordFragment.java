package com.softray_solutions.newschoolproject.ui.fragments.forgetPassword;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgetPasswordFragment extends Fragment implements ForgetPasswordView {
    @BindView(R.id.userName_ET)
    EditText usernameET;
    @BindView(R.id.emailAddress_ET)
    EditText emailAddressET;
    @BindView(R.id.phone_ET)
    EditText phoneET;
    Unbinder unbinder;
    ForgetPasswordPresenter presenter;
    private Dialog dialog;

    public ForgetPasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        initProgressDialog();
        emailAddressET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    phoneET.setVisibility(View.VISIBLE);
                } else {
                    phoneET.setVisibility(View.INVISIBLE);
                }
            }
        });
        phoneET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    emailAddressET.setVisibility(View.VISIBLE);
                } else {
                    emailAddressET.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initProgressDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.sending_code));
    }

    private void initPresenter() {
        presenter = new ForgetPasswordPresenter(this);
    }

    @OnClick(R.id.send_code_button)
    public void sendCode() {
        presenter.validateFields(usernameET, emailAddressET, phoneET);
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

    @Override
    public void setUsernameError(int message) {
        if (isVisible()) {
            usernameET.setError(getString(message));
        }
    }

    @Override
    public void setEmptyPhoneAndEmailAddressError(int message) {
        if (isVisible()) {
            if (emailAddressET.getVisibility() == View.VISIBLE) {
                emailAddressET.setError(getString(message));
            } else {
                phoneET.setError(getString(message));
            }
        }
    }

    @Override
    public void setToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showDialog() {
        if (isVisible()) {
            dialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (isVisible()) {
            dialog.dismiss();
        }
    }

    @Override
    public void setSuccess(int id) {
        if (isVisible()) {
            presenter.startCodeValidateFragment(id);
        }
    }

    @Override
    public void startCodeValidateFragment(Fragment fragment, String tag, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("ID", id);
        fragment.setArguments(bundle);
        Objects.requireNonNull(getFragmentManager()).beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.login_container, fragment, tag).addToBackStack(null).commit();
    }
}