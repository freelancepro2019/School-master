package com.softray_solutions.newschoolproject.ui.fragments.login;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.ui.activities.code.CodeActivity;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment implements LoginFragmentView {
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.forgot_password)
    TextView forgetPassword;
    private Unbinder unbinder;
    private LoginFragmentPresenter presenter;
    private Dialog dialog;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initProgressDialog();
        initPresenter();
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

    private void initPresenter() {
        presenter = new LoginFragmentPresenter(this, getContext().getSharedPreferences("userData", MODE_PRIVATE));
    }

    private void initProgressDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.logging_in));
    }

    @OnClick(R.id.reenter_code)
    public void reEnterCode() {
        Intent codeIntent = new Intent(getContext(), CodeActivity.class);
        codeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(codeIntent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @OnClick(R.id.forgot_password)
    public void resetPassword() {
        presenter.startForgetPasswordFragment();
    }

    @OnClick(R.id.login_button)
    public void login() {
        String username = etUserName.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();
        presenter.checkLogin(username, password);
    }

    @Override
    public void setUsernameError(String errorMessage) {
        etUserName.setError(errorMessage);
    }

    @Override
    public void setPasswordError(String errorMessage) {
        etUserPassword.setError(errorMessage);
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (isVisible()) {
            dialog.cancel();
        }
    }

    @Override
    public void loginFailed(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void adminException() {
        if (isVisible()) {
            Toast.makeText(getContext(), getString(R.string.admin_login_exception), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginSuccess(ObjectDataModel dataModel) {
        if (isVisible()) {
            Intent signOutIntent = new Intent(getContext(), MainActivity.class);
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getContext()).startActivity(signOutIntent);
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    public void initForgetPasswordFragment(Fragment fragment, String tag) {
        Objects.requireNonNull(getFragmentManager()).beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.login_container, fragment, tag).addToBackStack(null).commit();
    }
}