package com.softray_solutions.newschoolproject.ui.fragments.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.adapters.ParentNotificationAdapter;
import com.softray_solutions.newschoolproject.adapters.StudentNotificationAdapter;
import com.softray_solutions.newschoolproject.adapters.TeacherNotificationAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.model.TeacherNotificationDataModel;
import com.softray_solutions.newschoolproject.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter {

    private NotificationsView notificationsView;
    private SharedPreferences preferences;
    private String teacherId, SchoolId, studentID, fatherID;
    private MyInterface myInterface;

    NotificationPresenter(NotificationsView view, SharedPreferences sharedPreferences) {
        this.notificationsView = view;
        this.preferences = sharedPreferences;
    }

    public void checkLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        String language = current.getLanguage();
        notificationsView.setNotificationsAdapter(language);
    }

    void getNotifications() {
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
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
        fatherID = user.getId();
        SchoolId = user.getSchoolID();
        myInterface = Common.getMyInterface();
        myInterface.getParentNotifications(fatherID, SchoolId).enqueue(new Callback<ArrayDataModel<ParentNotificationDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<ParentNotificationDataModel>> call, Response<ArrayDataModel<ParentNotificationDataModel>> response) {
                notificationsView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            notificationsView.onSuccessParent(response.body().getData());
                        } else {
                            notificationsView.emptyView();
                        }
                    } else {
                        notificationsView.onError(response.body().getMessage());
                    }
                } else {
                    notificationsView.onError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<ParentNotificationDataModel>> call, Throwable t) {
                notificationsView.hideProgressBar();
                notificationsView.onError(t.getLocalizedMessage());
            }
        });
    }

    private void getStudentNotifications(User user) {
        studentID = user.getId();
        SchoolId = user.getSchoolID();

        myInterface = Common.getMyInterface();
        myInterface.getStudentNotifications(studentID, SchoolId).enqueue(new Callback<ArrayDataModel<StudentNotificaionDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentNotificaionDataModel>> call, Response<ArrayDataModel<StudentNotificaionDataModel>> response) {
                notificationsView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            notificationsView.onSuccessStudent(response.body().getData());
                        } else {
                            notificationsView.emptyView();
                        }
                    } else {
                        notificationsView.onError(response.body().getMessage());
                    }

                } else {
                    notificationsView.onError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentNotificaionDataModel>> call, Throwable t) {
                notificationsView.hideProgressBar();
                notificationsView.onError(t.getLocalizedMessage());
            }
        });
    }

    private void getTeacherNotifications(User user) {
        teacherId = user.getId();
        SchoolId = user.getSchoolID();
        myInterface = Common.getMyInterface();

        Log.e("teacherId",teacherId);
        Log.e("SchoolId",SchoolId);


        myInterface.getTeacherNotification(teacherId, SchoolId).enqueue(new Callback<ArrayDataModel<TeacherNotificationDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<TeacherNotificationDataModel>> call, Response<ArrayDataModel<TeacherNotificationDataModel>> response) {
                notificationsView.hideProgressBar();

                Log.e("data","code"+response.code()+"____"+response.body().getData().size()+"__");

                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            notificationsView.onSuccessTeacher(response.body().getData());
                        } else {
                            notificationsView.emptyView();
                        }
                    } else {
                        notificationsView.onError(response.body().getMessage());
                    }
                } else {
                    notificationsView.onError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<TeacherNotificationDataModel>> call, Throwable t) {
                notificationsView.hideProgressBar();
                notificationsView.onError(t.getLocalizedMessage());
            }
        });
    }


    private void updateNotification(String notificationID) {
        myInterface.updateNotification(teacherId, notificationID).enqueue(new Callback<ObjectDataModel>() {
            @Override
            public void onResponse(Call<ObjectDataModel> call, Response<ObjectDataModel> response) {
            }

            @Override
            public void onFailure(Call<ObjectDataModel> call, Throwable t) {
            }
        });
    }

    public void bindTeacherNotificationToViews(final TeacherNotificationAdapter.MainViewHolder holder, final int position, final List<TeacherNotificationDataModel> dataSet) {
        holder.notificationContent.setText("قام الطالب " + dataSet.get(position).getStudentName() + " بإضافة سؤال");
        String date = dataSet.get(position).getAskDate();
        String[] modifiedDate = date.split("\\s+");
        holder.notificationDate.setText(modifiedDate[1] + "\n" + modifiedDate[0]);
        Picasso.get().load(dataSet.get(position).getmImageUser()).fit().centerInside().into(holder.imageView);
        if (!dataSet.get(position).getmIsOpen()) {
            holder.badge.setBadgeText(" ");
        }
        holder.itemView.setOnClickListener(view -> {
            if (!dataSet.get(position).getmIsOpen()) {
                updateNotification(dataSet.get(position).getNotificationID());
            }
        });
    }

    public void bindStudentNotificationsToView(StudentNotificationAdapter.MainViewHolder holder, int position, List<StudentNotificaionDataModel> dataModels) {
        holder.notificationContent.setText("قام المعلم " + dataModels.get(position).getTeacherName() + " بإضافة " + dataModels.get(position).getType() + " جديد");
        String[] date = dataModels.get(position).getDate().split("\\s+");
        holder.notificationDate.setText(date[1] + "\n" + date[0]);
        Picasso.get().load(dataModels.get(position).getmProImage()).fit().centerInside().into(holder.imageView);
        if (!dataModels.get(position).getIsOpen()) {
            holder.badge.setBadgeText(" ");
        }
        holder.itemView.setOnClickListener(view -> {
            if (!dataModels.get(position).getIsOpen()) {
                updateNotification(dataModels.get(position).getNotificationID());
            }
        });
    }

    public void bindParentNotificationsToView(ParentNotificationAdapter.MainViewHolder holder, int position, List<ParentNotificationDataModel> parentDataList) {
        holder.notificationContent.setText("قام المعلم " + parentDataList.get(position).getTeacherName() + " بإضافة " + parentDataList.get(position).getType() + " للطالب " + parentDataList.get(position).getStudentName());
        String[] date = parentDataList.get(position).getTestDate().split("\\s+");
        holder.notificationDate.setText(date[1] + "\n" + date[0]);
        Picasso.get().load(parentDataList.get(position).getmProfileImage()).fit().centerInside().into(holder.imageView);
        if (!parentDataList.get(position).ismIsOpen()) {
            holder.badge.setBadgeText(" ");
        }
        holder.itemView.setOnClickListener(view -> {
            if (!parentDataList.get(position).ismIsOpen()) {
                updateNotification(parentDataList.get(position).getNotificationID());
            }
        });
    }
}