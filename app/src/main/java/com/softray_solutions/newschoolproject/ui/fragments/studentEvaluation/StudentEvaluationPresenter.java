package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.Lesson;
import com.softray_solutions.newschoolproject.model.Semester;
import com.softray_solutions.newschoolproject.model.Subject;
import com.softray_solutions.newschoolproject.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StudentEvaluationPresenter {

    private StudentEvaluationView view;
    private SharedPreferences sharedPreferences;
    private String userID, schoolID, classID;
    private MyInterface myInterface = Common.getMyInterface();
    private List<Semester> semesters;
    private List<Subject> subjects;
    private Semester selectedSemester;
    private List<Lesson> lessons = new ArrayList<>();
    private String currentLesson = "";
    private Subject selectedSubject;
    private String currentDate;
    private Context context;

    StudentEvaluationPresenter(StudentEvaluationView view, SharedPreferences sharedPreferences, Context context) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
    }

    void getUserId() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        schoolID = user.getSchoolID();
        userID = user.getId();
    }

    void getSemesters() {
        myInterface.getSemesters(userID).enqueue(new Callback<ArrayDataModel<Semester>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Semester>> call, Response<ArrayDataModel<Semester>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        semesters = response.body().getData();
                        view.setSemesters(semesters);
                    } else {
                        view.setError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Semester>> call, Throwable t) {
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    void setSelectedLesson(int position) {
        currentLesson = lessons.get(position).getLessonID();
    }

    Bundle getCurrentData() {
        Bundle bundle = new Bundle();

        bundle.putString("classID", selectedSemester.getClassID());
        bundle.putString("rowLevelID", selectedSemester.getRowLevelID());
        bundle.putString("lessonID", currentLesson);
        bundle.putString("subjectID", selectedSubject.getmSubjectID());
        bundle.putString("teacherID", userID);
        bundle.putString("currentDate", currentDate);

        return bundle;
    }

    void getSubjects(int position) {
        selectedSemester = semesters.get(position);

        Log.e("teacherid",userID);
        Log.e("rowlevel",selectedSemester.getRowLevelID());
        Log.e("classid",selectedSemester.getClassID());

        myInterface.getSubjects(userID, selectedSemester.getRowLevelID(), selectedSemester.getClassID()).enqueue(new Callback<ArrayDataModel<Subject>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Subject>> call, Response<ArrayDataModel<Subject>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        subjects = response.body().getData();
                        view.setSubjects(subjects);
                    } else {
                        Toast.makeText(context, R.string.no_subject, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Subject>> call, Throwable t) {
                Toast.makeText(context, R.string.no_subject, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getLessons(int position) {
        selectedSubject = subjects.get(position);
        classID = selectedSubject.getClassID();
        myInterface.getTeacherLessons(selectedSemester.getRowLevelID(), selectedSubject.getmSubjectID(), userID, classID, schoolID)
                .enqueue(new Callback<ArrayDataModel<Lesson>>() {
                    @Override
                    public void onResponse(Call<ArrayDataModel<Lesson>> call, Response<ArrayDataModel<Lesson>> response) {
                        lessons.clear();
                        if (response.body() != null) {
                            if (response.body().getSuccess() == 1) {
                                if (response.body().getData().isEmpty()) {
                                    Lesson emptyLesson = new Lesson();
                                    emptyLesson.setLessonTitle(context.getString(R.string.empty_lesson));
                                    lessons.add(emptyLesson);
                                    view.setLessons(lessons);
                                } else {
                                    lessons = response.body().getData();
                                    view.setLessons(lessons);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayDataModel<Lesson>> call, Throwable t) {

                    }
                });
    }

    void getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
        Calendar c = Calendar.getInstance();
        String formattedDate = df.format(c.getTime());
        view.setDate(formattedDate);
    }

    public void setDate(String date) {
        currentDate = date;
    }
}
