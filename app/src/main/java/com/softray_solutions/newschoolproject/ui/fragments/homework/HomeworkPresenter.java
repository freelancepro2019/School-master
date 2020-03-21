package com.softray_solutions.newschoolproject.ui.fragments.homework;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.adapters.homework.HomeworkAdapter;
import com.softray_solutions.newschoolproject.adapters.homework.HomeworkHolder;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.Homework;
import com.softray_solutions.newschoolproject.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeworkPresenter {

    private HomeworkView view;
    private List<Homework> homeworkList;
    private MyInterface myInterface = Common.getMyInterface();

    HomeworkPresenter(HomeworkView view) {
        this.view = view;
    }

    void getHomework(SharedPreferences preferences, String userId, String subjectId, String rowLevelId) {
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);

        if (user.getType().equals("E")) {
            getTeacherHomework(userId, subjectId, rowLevelId);
        } else {
            getStudentHomework(userId, subjectId);
        }
    }

    private void getStudentHomework(String studentId, String subjectId) {
        view.showProgress();
        myInterface.getStudentHomework(studentId, subjectId).enqueue(new Callback<ArrayDataModel<Homework>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Homework>> call, Response<ArrayDataModel<Homework>> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            homeworkList = response.body().getData();
                            HomeworkAdapter adapter = new HomeworkAdapter(HomeworkPresenter.this);
                            view.setAdapter(adapter);
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
            public void onFailure(Call<ArrayDataModel<Homework>> call, Throwable t) {
                view.hideProgress();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void getTeacherHomework(String teacherId, String subjectId, String rowLevelId) {
        view.showProgress();
        myInterface.getTeacherHomework(teacherId, rowLevelId, subjectId).enqueue(new Callback<ArrayDataModel<Homework>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Homework>> call, Response<ArrayDataModel<Homework>> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().isEmpty()) {
                            homeworkList = response.body().getData();
                            HomeworkAdapter adapter = new HomeworkAdapter(HomeworkPresenter.this);
                            view.setAdapter(adapter);
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
            public void onFailure(Call<ArrayDataModel<Homework>> call, Throwable t) {
                view.hideProgress();
                view.setError(t.getLocalizedMessage());
            }
        });
    }


    public void onBindItemView(HomeworkHolder holder, int position) {
        Homework homework = homeworkList.get(position);
        holder.setSubject(homework.getSubject());
        holder.setDateText(homework.getDate());
        holder.setAttachment(homework.getAttach());
        holder.setContentText(homework.getContent());
        holder.setTitleText(homework.getTitle());
    }

    public int getHomeworkCount() {
        return homeworkList.size();
    }

}
