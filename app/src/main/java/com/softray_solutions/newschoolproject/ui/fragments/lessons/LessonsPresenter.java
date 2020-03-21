package com.softray_solutions.newschoolproject.ui.fragments.lessons;


import android.view.View;

import com.softray_solutions.newschoolproject.adapters.lessons.LessonsAdapter;
import com.softray_solutions.newschoolproject.adapters.lessons.LessonsHolder;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.Lesson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonsPresenter {

    private String subjectName;
    private LessonsView view;
    private List<Lesson> lessonsList = new ArrayList<>();
    private String type;

    LessonsPresenter(LessonsView view, String subjectName, String type) {
        this.view = view;
        this.subjectName = subjectName;
        this.type = type;
    }

    void getLessons(String subjectID, String rowLevelID, String userID, String schoolID, String classID) {
        if (type.equals("S")) {
            getStudentLessons(rowLevelID, subjectID, schoolID, classID);

        } else if (type.equals("E")) {
            getTeacherLessons(rowLevelID, subjectID, userID, classID, schoolID);
        }
    }

    private void getTeacherLessons(String rowLevelID, String subjectID, String userID, String classID, String schoolID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getTeacherLessons(rowLevelID, subjectID, userID, classID, schoolID).enqueue(new Callback<ArrayDataModel<Lesson>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Lesson>> call, Response<ArrayDataModel<Lesson>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().size() > 0) {
                            lessonsList = response.body().getData();
                            setAdapter();
                        } else {
                            view.setEmptyView();
                        }
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Lesson>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void getStudentLessons(String rowLevelID, String subjectID, String schoolID, String classID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getLessons(rowLevelID, subjectID, schoolID, classID).enqueue(new Callback<ArrayDataModel<Lesson>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Lesson>> call, Response<ArrayDataModel<Lesson>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().size() > 0) {
                            lessonsList = response.body().getData();
                            setAdapter();
                        } else {
                            view.setEmptyView();
                        }
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Lesson>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void setAdapter() {
        LessonsAdapter adapter = new LessonsAdapter(this);
        view.setAdapter(adapter);
    }

    public void onBindItem(int position, LessonsHolder holder) {
        final Lesson lesson = lessonsList.get(position);
        holder.setDateText(lesson.getLessonDate());
        holder.setLessonTitle(lesson.getLessonTitle());
        holder.setTeacherName(lesson.getTeacherName());
        holder.setGradText(lesson.getLevelName());
        holder.setSkills(lesson.getSkillsName());
        holder.setSubjectName(subjectName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LessonsPresenter.this.view.startLessonFragment(lesson.getLessonToken());
            }
        });
    }


    public int getLessonsCount() {
        return lessonsList.size();
    }

}
