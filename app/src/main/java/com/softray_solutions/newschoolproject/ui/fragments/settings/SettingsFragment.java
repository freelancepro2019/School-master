package com.softray_solutions.newschoolproject.ui.fragments.settings;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.softray_solutions.newschoolproject.ui.activities.changePassword.ChangePassword;
import com.softray_solutions.newschoolproject.ui.activities.editProfile.EditProfile;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment implements SettingsView {
    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_PERMISSION_REQUEST = 100;

    @BindView(R.id.language_spinner)
    Spinner languageSpinner;
    @BindView(R.id.username_header_text)
    TextView usernameHeaderTextView;
    @BindView(R.id.username_body_text)
    TextView usernameBodyTextView;
    @BindView(R.id.email_header_text)
    TextView emailHeaderTextView;
    @BindView(R.id.email_body_text)
    TextView emailBodyTextView;
    @BindView(R.id.profile_image)
    CircleImageView profileImageView;
    @BindView(R.id.phone_body_text)
    TextView phoneBody;
    @BindView(R.id.edit_profile_image)
    FloatingActionButton editProfileImage;
    @BindView(R.id.change_password)
    ImageView changePasswordIV;
    Unbinder unbinder;
    private String userId;
    private SettingsPresenter settingsPresenter;
    private Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        getUserData();
        initProgressDialog();
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    settingsPresenter.changeLanguage();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editProfileImage.setOnClickListener(view1 -> settingsPresenter.checkPermission(getContext()));
        changePasswordIV.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

        });
    }

    private void initPresenter() {
        SharedPreferences preferences = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        settingsPresenter = new SettingsPresenter(this, getContext(), new AppPrefsHelper(preferences));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            settingsPresenter.updateUserImage(data, userId, getContext());
        } else {
            Toast.makeText(getContext(), R.string.no_image_picked, Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.edit_button)
    public void editData() {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

    }

    @OnClick(R.id.logout_button)
    public void logoutClick() {
        settingsPresenter.logout();
    }


    private void getUserData() {
        settingsPresenter.getUserData();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    @Override
    public void Logout() {
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            main.logout();
        }
    }

    @Override
    public void updateImage(String data) {
        if (isVisible()) {
            Glide.with(this).load(data).apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(profileImageView);
            Glide.with(this).load(data).apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                    .into(BaseActivity.circleImageView);
        }
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void showProgressDialog() {

        dialog.show();
    }

    @Override
    public void requestPermissionFromUser() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST);
    }

    @Override
    public void startGalleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/");
        startActivityForResult(Intent.createChooser(openGalleryIntent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void setUserData(User userData) {
        userId = userData.getId();
        usernameHeaderTextView.setText(userData.getName());
        usernameBodyTextView.setText(userData.getName());
        emailHeaderTextView.setText(userData.getMail());
        emailBodyTextView.setText(userData.getMail());
        phoneBody.setText(userData.getMobile());
        Glide.with(this).load(userData.getImg()).apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                .into(profileImageView);
    }

    @Override
    public void setToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void initProgressDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(R.string.upload_image_dialog_message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GALLERY_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGalleryIntent();
                }
                break;
            default:
                break;
        }
    }
}