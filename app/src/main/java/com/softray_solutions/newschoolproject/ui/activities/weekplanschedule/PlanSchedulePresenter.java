package com.softray_solutions.newschoolproject.ui.activities.weekplanschedule;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.adapters.StudentPlanWeekAdapter;
import com.softray_solutions.newschoolproject.adapters.plan.PlanAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.StudentPlanClass;
import com.softray_solutions.newschoolproject.model.TeacherPlanClass;
import com.softray_solutions.newschoolproject.model.User;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanSchedulePresenter {

    private SharedPreferences sharedPreferences;
    private PlanScheduleView view;
    private int textColor = Color.parseColor("#4CBBA9");
    private PrefsInteractor interactor;
    private MyInterface myInterface;

    PlanSchedulePresenter(PlanScheduleView view, SharedPreferences sharedPreferences, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    void getSchedule(String week, String semester, String sonID) {
        view.showProgressBar();
        String userType = getSavedUser().getType();
        String userId = getSavedUser().getId();
        myInterface = Common.getMyInterface();

        switch (userType) {
            case "E":
                getTeacherPlanWeek(userId, week, semester);
                break;
            case "S":
                getStudentPlanWeek(userId, week, semester);
                break;

            case "F":
                getSonPlanWeek(sonID, week, semester);
                break;

            default:
                break;
        }
    }


    private void getTeacherPlanWeek(String userId, String week, String semester) {

        Log.e("teche_id",userId+"__"+week+"_"+semester);
        myInterface.getPlanClass(userId, week, semester).enqueue(new Callback<ArrayDataModel<StudentDay<TeacherPlanClass>>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentDay<TeacherPlanClass>>> call, Response<ArrayDataModel<StudentDay<TeacherPlanClass>>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            view.hideEmptyView();
                            view.showRecycler();
                            view.setClasses(response.body().getData());
                        } else {
                            view.hideRecycler();
                            view.showEmptyView();
                        }
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentDay<TeacherPlanClass>>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void getStudentPlanWeek(String userId, String week, String semester) {
        Log.e("id",userId+"_"+week+"_"+semester);
        myInterface.studentPlanWeek(userId, week, semester).enqueue(new Callback<ArrayDataModel<StudentDay<StudentPlanClass>>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentDay<StudentPlanClass>>> call, Response<ArrayDataModel<StudentDay<StudentPlanClass>>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            view.hideEmptyView();
                            view.showRecycler();
                            view.setStudentClasses(response.body().getData());
                        } else {
                            view.hideRecycler();
                            view.showEmptyView();
                        }
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentDay<StudentPlanClass>>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void getSonPlanWeek(String sonID, String week, String semester) {

        Log.e("son_id",sonID+"_"+week+"_"+semester);


        myInterface.studentPlanWeek(sonID, week, semester).enqueue(new Callback<ArrayDataModel<StudentDay<StudentPlanClass>>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentDay<StudentPlanClass>>> call, Response<ArrayDataModel<StudentDay<StudentPlanClass>>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            view.hideEmptyView();
                            view.showRecycler();
                            view.setStudentClasses(response.body().getData());
                        } else {
                            view.hideRecycler();
                            view.showEmptyView();
                        }
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentDay<StudentPlanClass>>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    public void onBindItem(PlanAdapter.MainViewHolder holder, int position, ArrayList<TeacherPlanClass> classes) {
        final TeacherPlanClass teacherPlanClass = classes.get(position);

        if (teacherPlanClass.getmObject().equals("mafesh")) {
            holder.textView.setText("لا يوجد");
        } else {
            holder.textView.setTextColor(Color.BLACK);
            holder.textView.setText(teacherPlanClass.getmObject());
        }

        if (position == 0) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (teacherPlanClass.isColored()) {
            holder.textView.setTextColor(textColor);
        }

        if (teacherPlanClass.getContent() != null) {
            if (!teacherPlanClass.getContent().equals("mafesh")) {
                holder.itemView.setOnClickListener(v -> view.showToast(teacherPlanClass.getContent()));

            } else if (!teacherPlanClass.getmObject().equals("mafesh")) {
                holder.itemView.setOnClickListener(v -> view.showToast("لا يوجد محتوي"));
            }
        }
    }

    private User getSavedUser() {
        return Customization.obtainUser(sharedPreferences);
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

    public void onBindStudentItem(StudentPlanWeekAdapter.MainViewHolder holder, int position, ArrayList<StudentPlanClass> classes) {
        final StudentPlanClass studentPlanClass = classes.get(position);

        if (studentPlanClass.getObject().equals("mafesh")) {
            holder.textView.setText("لا يوجد");
        } else {
            holder.textView.setTextColor(Color.BLACK);
            holder.textView.setText(studentPlanClass.getObject());
        }

        if (position == 0) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (studentPlanClass.isColored()) {
            holder.textView.setTextColor(textColor);
        }
        if (studentPlanClass.getContent() != null) {
            if (!studentPlanClass.getContent().equals("mafesh")) {
                holder.itemView.setOnClickListener(v -> view.showToast(studentPlanClass.getContent()));

            } else if (!studentPlanClass.getObject().equals("mafesh")) {
                holder.itemView.setOnClickListener(v -> view.showToast("لا يوجد محتوي"));
            }
        }
    }
}