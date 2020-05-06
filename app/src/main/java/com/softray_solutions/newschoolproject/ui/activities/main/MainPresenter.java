package com.softray_solutions.newschoolproject.ui.activities.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.model.TeacherNotificationDataModel;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.fragments.main.MainFragment;
import com.softray_solutions.newschoolproject.ui.fragments.messages.MessagesFragment;
import com.softray_solutions.newschoolproject.ui.fragments.notifications.NotificationsFragment;
import com.softray_solutions.newschoolproject.ui.fragments.settings.SettingsFragment;
import com.softray_solutions.newschoolproject.ui.fragments.tools.ToolsFragment;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainPresenter {

    private MainView view;
    private SharedPreferences sharedPreferences;
    private PrefsInteractor interactor;

    MainPresenter(MainView view, Context context, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
        sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
    }

    void openFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                view.openNewFragment(false, new MainFragment(), "main");
                break;
          /*  case R.id.bottom_nav_notifications:
                view.openNewFragment(false, new NotificationsFragment(), "notifications");
                break;*/
            case R.id.bottom_nav_tools:
                view.openNewFragment(false, new ToolsFragment(), "tools");
                break;
            case R.id.bottom_nav_messages:
                view.openNewFragment(false, new MessagesFragment(), "messages");
                break;
            case R.id.bottom_nav_settings:
                view.openNewFragment(false, new SettingsFragment(), "settings");
                break;
        }

        setTitle(item);

        view.setCurrentItemID(item.getItemId());
    }

    void setTitle(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                view.setActionBarTitle(R.string.main_activity_title);
                break;
           /* case R.id.bottom_nav_notifications:
                view.setActionBarTitle(R.string.notifications);
                break;*/
            case R.id.bottom_nav_tools:
                view.setActionBarTitle(R.string.tools_action_bar_title);
                break;
            case R.id.bottom_nav_messages:
                view.setActionBarTitle(R.string.messging_actionbar_title);
                break;
            case R.id.bottom_nav_settings:
                view.setActionBarTitle(R.string.settings);
                break;
        }
    }

    void checkLogin() {
        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);
        if (isLogged) {
            view.showBottomBar();
        } else {
            view.hideBottomBar();
        }
    }

    void backPressed() {
        view.backPressed();
        view.setCurrentFragment();
    }

    void getNotifications() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        if (user != null) {
            String userType = user.getType();
            switch (userType) {
                case "E":
                    getTeacherNotifications(user);
                    break;
                case "S":
                    getStudentNotifications(user);
                    break;
                case "F":
                    getFatherNotifications(user);
                    break;
                default:
                    break;
            }
        }


    }

    private void getFatherNotifications(User user) {
        String fatherID = user.getId();
        String schoolID = user.getSchoolID();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getParentNotifications(fatherID, schoolID).enqueue(new Callback<ArrayDataModel<ParentNotificationDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<ParentNotificationDataModel>> call, Response<ArrayDataModel<ParentNotificationDataModel>> response) {
                if (!response.body().getData().isEmpty()) {
                    int count = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (!response.body().getData().get(i).ismIsOpen()) {
                            count += 1;
                        }
                    }
                    if (count != 0) {
                        view.setNotificationBadge(count);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<ParentNotificationDataModel>> call, Throwable t) {

            }
        });
    }

    private void getStudentNotifications(User user) {
        String studentID = user.getId();
        String schooldId = user.getSchoolID();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getStudentNotifications(studentID, schooldId).enqueue(new Callback<ArrayDataModel<StudentNotificaionDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentNotificaionDataModel>> call, Response<ArrayDataModel<StudentNotificaionDataModel>> response) {
                if (response.body() != null && !response.body().getData().isEmpty()) {
                    int count = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (!response.body().getData().get(i).getIsOpen()) {
                            count += 1;
                        }
                    }
                    if (count != 0) {
                        view.setNotificationBadge(count);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentNotificaionDataModel>> call, Throwable t) {

            }
        });

    }

    private void getTeacherNotifications(User user) {
        String teacherID = user.getId();
        String schoolID = user.getSchoolID();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getTeacherNotification(teacherID, schoolID).enqueue(new Callback<ArrayDataModel<TeacherNotificationDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<TeacherNotificationDataModel>> call, Response<ArrayDataModel<TeacherNotificationDataModel>> response) {
                if (response.body() != null && !response.body().getData().isEmpty()) {
                    int notificationsCount = 0;
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (!response.body().getData().get(i).getmIsOpen()) {
                            notificationsCount++;
                        }
                    }
                    if (notificationsCount != 0) {
                        view.setNotificationBadge(notificationsCount);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayDataModel<TeacherNotificationDataModel>> call, Throwable t) {

            }
        });
    }

    public void getCurrentLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                String currentLanguage;
                if (result.equals("not selected")) {
                    currentLanguage = Locale.getDefault().getLanguage();
                } else {
                    currentLanguage = result;
                }
                Locale locale = new Locale(currentLanguage);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                view.changeLanguage(configuration);
            }
        });
    }
}
