package com.softray_solutions.newschoolproject.ui.fragments.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.User;

import java.io.File;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SettingsPresenter {

    private SettingsView view;
    private SharedPreferences sharedPreferences;
    private Cursor cursor;
    private AppPrefsHelper appPrefsHelper;
    private PrefsInteractor interactor;

    SettingsPresenter(SettingsView view, Context context, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.appPrefsHelper = appPrefsHelper;
        interactor = new PrefsInteractor(appPrefsHelper);
        sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
    }

    void getUserData() {
        User user = getCurrentUser();
        view.setUserData(user);
    }

    private User getCurrentUser() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        return gson.fromJson(userString, User.class);
    }

    void logout() {
        view.Logout();
    }


    public void updateUserImage(Intent data, String userId, Context context) {
        view.showProgressDialog();
        Uri uri = data.getData();
        File userImage = new File(getImagePath(uri, context));
        cursor.close();
        RequestBody file = RequestBody.create(MediaType.parse("image/*"), userImage);
        MultipartBody.Part uploadThis = MultipartBody.Part.createFormData("userFiles", userImage.getName(), file);
        RequestBody user = RequestBody.create(MediaType.parse("text/plain"), userId);


        MyInterface myInterface = Common.getMyInterface();
        myInterface.updateUserImage(uploadThis, user).enqueue(new Callback<ObjectDataModel>() {
            @Override
            public void onResponse(Call<ObjectDataModel> call, Response<ObjectDataModel> response) {
                view.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        view.setToast(response.body().getMessage());
                        view.updateImage(response.body().getData().toString());
                        User user = getCurrentUser();
                        user.setImg(response.body().getData().toString());
                        Gson gson = new Gson();
                        String userString = gson.toJson(user);
                        sharedPreferences.edit().putString("user", userString).apply();
                    } else {
                        view.setToast(response.body().getMessage());
                    }
                } else {
                    view.setToast("server error");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel> call, Throwable t) {
                view.hideProgressDialog();
                view.setToast(t.getLocalizedMessage());
            }
        });

    }


    private String getImagePath(Uri uri, Context context) {
        cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            return cursor.getString(idx);
        }
    }

    void checkPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            view.requestPermissionFromUser();
        } else {
            view.startGalleryIntent();
        }
    }

    public void changeLanguage() {
        String currentLanguage = getCurrentLanguage();
        String newLanguage;
        if (currentLanguage.equals("en")) {
            newLanguage = "ar";
        } else {
            newLanguage = "en";
        }
        Locale locale = new Locale(newLanguage);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        saveCurrentLanguage(newLanguage);
        view.changeLanguage(configuration);
    }

    private void saveCurrentLanguage(String newLanguage) {
        interactor.setSelectedLanguage(newLanguage);
    }

    private String getCurrentLanguage() {
        final String[] currentLanguage = {"en"};

        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                if (result.equals("not selected")) {
                    currentLanguage[0] = Locale.getDefault().getLanguage();
                } else {
                    currentLanguage[0] = result;
                }
            }
        });

        return currentLanguage[0];
    }
}
