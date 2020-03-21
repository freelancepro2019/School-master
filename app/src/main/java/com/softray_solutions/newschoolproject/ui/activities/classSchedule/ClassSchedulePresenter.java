package com.softray_solutions.newschoolproject.ui.activities.classSchedule;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.View;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.adapters.TeacherScheduleAdapter;
import com.softray_solutions.newschoolproject.adapters.TimeTableAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.StudentClass;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.TeacherClass;
import com.softray_solutions.newschoolproject.model.TeacherDay;
import com.softray_solutions.newschoolproject.model.User;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassSchedulePresenter {

    private ClassScheduleView classScheduleView;
    private SharedPreferences sharedPreferences;
    private int textColor = Color.parseColor("#4CBBA9");
    private PrefsInteractor interactor;

    ClassSchedulePresenter(ClassScheduleView classScheduleView, SharedPreferences sharedPreferences, AppPrefsHelper appPrefsHelper) {
        this.classScheduleView = classScheduleView;
        this.sharedPreferences = sharedPreferences;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    private void getStudentClasses(String id) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getSchedule(id).enqueue(new Callback<ArrayDataModel<StudentDay<StudentClass>>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentDay<StudentClass>>> call, Response<ArrayDataModel<StudentDay<StudentClass>>> response) {
                classScheduleView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        classScheduleView.setClasses(response.body().getData());
                    } else {
                        classScheduleView.setError(response.body().getMessage());
                    }
                } else {
                    classScheduleView.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentDay<StudentClass>>> call, Throwable t) {
                classScheduleView.hideProgressBar();
                classScheduleView.setError(t.getLocalizedMessage());
            }
        });
    }

    private void getTeacherClasses(String id) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getTeacherSchedule(id).enqueue(new Callback<ArrayDataModel<TeacherDay>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<TeacherDay>> call, Response<ArrayDataModel<TeacherDay>> response) {
                classScheduleView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        classScheduleView.setTeacherClasses(response.body().getData());
                    } else {
                        classScheduleView.setError(response.body().getMessage());
                    }
                } else {
                    classScheduleView.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<TeacherDay>> call, Throwable t) {
                classScheduleView.hideProgressBar();
                classScheduleView.setError(t.getLocalizedMessage());
            }
        });
    }

    public void onBindItemTeacher(TeacherScheduleAdapter.MainViewHolder holder, int position, ArrayList<TeacherClass> teacherData) {
        final TeacherClass teacherDatum = teacherData.get(position);
        if (!teacherDatum.getObject().equals("mafesh")) {
            holder.textView.setTextColor(Color.BLACK);
            holder.textView.setText(teacherData.get(position).getObject());
            if (teacherDatum.getLevel() != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        classScheduleView.showClassTeacher(teacherDatum.getLevel() + " " + teacherDatum.getRow() + "  " + teacherDatum.getTeacherClass());
                    }
                });
            }
        } else {
            holder.textView.setText("لا يوجد");
        }

        if (position == 0) {
            holder.itemView.setVisibility(View.GONE);
        }
        if (teacherDatum.getTeacherClass() == null) {
            holder.textView.setTextColor(textColor);
        }
    }

    public void onBindItem(TimeTableAdapter.MainViewHolder holder, int position, ArrayList<StudentClass> classes) {
        final StudentClass dayDatum = classes.get(position);

        if (!dayDatum.getObject().equals("mafesh")) {
            holder.textView.setTextColor(Color.BLACK);
            holder.textView.setText(classes.get(position).getObject());

            if (dayDatum.getTeach() != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        classScheduleView.showClassTeacher(dayDatum.getTeach());
                    }
                });
            }
        } else {
            holder.textView.setText("لا يوجد");
        }

        if (position == 0) {
            holder.itemView.setVisibility(View.GONE);
        }

        if (dayDatum.getTeach() == null) {
            holder.textView.setTextColor(textColor);
        }
    }

    void getExtras(String ChildId) {
        if (getUserType().equals("E")) {
            getTeacherClasses(getSavedID());
        } else {
            if (ChildId == null) {
                getStudentClasses(getSavedID());
            } else {
                getStudentClasses(ChildId);
            }
        }

    }

    private String getSavedID() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        return user.getId();
    }

    private String getUserType() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        return user.getType();
    }

    void getCurrentLanguage() {
        interactor.getSelectedLanguage(result -> {
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
            classScheduleView.changeLanguage(configuration);
        });
    }
}
