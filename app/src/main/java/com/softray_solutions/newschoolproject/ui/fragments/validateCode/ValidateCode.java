package com.softray_solutions.newschoolproject.ui.fragments.validateCode;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaos.view.PinView;
import com.softray_solutions.newschoolproject.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ValidateCode extends Fragment implements ValidateCodeView {
    Unbinder unbinder;
    @BindView(R.id.pin_view)
    PinView pinView;
    @BindView(R.id.confirm_button)
    Button button;
    Bundle bundle;
    int id;
    Dialog dialog;
    private ValidateCodePresenter presenter;

    public ValidateCode() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_validate_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        initProgressDialog();
        getArgs();
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.confirm_button)
    public void confirmCode() {
        presenter.confirmCode(id, pinView.getText().toString().trim());
    }

    private void initPresenter() {
        presenter = new ValidateCodePresenter(this);
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
        id = bundle.getInt("ID");
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
    public void setToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSuccess() {
        if (isVisible()) {
            presenter.startSetNewPasswordFragment(pinView.getText().toString().trim(), id);
        }
    }

    @Override
    public void startResetPasswordFragment(Fragment fragment, String tag, String code, int id) {
        if (isVisible()) {
            Bundle bundle = new Bundle();
            bundle.putString("code", code);
            bundle.putInt("id", id);
            fragment.setArguments(bundle);
            Objects.requireNonNull(getFragmentManager()).beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                    , R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.login_container, fragment, tag).addToBackStack(null).commit();
        }
    }

    private void initProgressDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(getString(R.string.loading));
    }
}