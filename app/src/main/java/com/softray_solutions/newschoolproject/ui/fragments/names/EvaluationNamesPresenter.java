package com.softray_solutions.newschoolproject.ui.fragments.names;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.adapters.Students.StudentsAdapter;
import com.softray_solutions.newschoolproject.adapters.Students.StudentsHolder;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.StudentEvaluationNames;
import com.softray_solutions.newschoolproject.model.StudentList;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.fragments.studentEvaluationFinal.StudentEvaluationFinal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvaluationNamesPresenter {

    private SharedPreferences sharedPreferences;
    private EvaluationNamesView namesView;
    private String schoolID, homeDegree, testDegree;
    private List<StudentList> students = new ArrayList<>();
    private List<String> selectedStudents = new ArrayList<>();

    EvaluationNamesPresenter(EvaluationNamesView view, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.namesView = view;
    }

    void getSchoolID() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        schoolID = user.getSchoolID();
    }

    void getStudentClass(String classID, String rowLevelID) {
        namesView.showPregress();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getStudentsClass(classID, rowLevelID, schoolID).enqueue(new Callback<ObjectDataModel<StudentEvaluationNames>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<StudentEvaluationNames>> call, Response<ObjectDataModel<StudentEvaluationNames>> response) {
                namesView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (!response.body().getData().getStudentList().isEmpty()) {
                            students = response.body().getData().getStudentList();
                            homeDegree = response.body().getData().getDefulatDeg().getHomeDegree();
                            testDegree = response.body().getData().getDefulatDeg().getTestMark();
                            setAdapter();
                        } else {
                            namesView.setEmptyView();
                        }
                    } else {
                        namesView.setEmptyView();
                        namesView.setError(response.body().getMessage());
                    }
                } else {
                    namesView.setEmptyView();
                    namesView.setError("Null response");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<StudentEvaluationNames>> call, Throwable t) {
                namesView.hideProgress();
                namesView.setEmptyView();
                namesView.setError(t.getLocalizedMessage());
            }
        });
    }

    private void setAdapter() {
        StudentsAdapter adapter = new StudentsAdapter(this);
        namesView.setAdapter(adapter);
    }

    public void onBindItem(final StudentsHolder holder, int position) {
        final StudentList student = students.get(position);
        holder.setStudentName(student.getStudentName());
        holder.setStudentImage(student.getProfileImage());
        holder.itemView.setBackgroundColor(student.isSelected() ? Color.LTGRAY : Color.WHITE);
        holder.itemView.setOnClickListener(view -> {
            student.setSelected(!student.isSelected());
            holder.itemView.setBackgroundColor(student.isSelected() ? Color.LTGRAY : Color.WHITE);

        });
    }

    void onButtonClicked(String rowLevelId, String subjectID, String lessonID, String teacherID, String date) {
        selectedStudents.clear();
        for (StudentList student : students) {
            if (student.isSelected()) {
                selectedStudents.add(student.getStudentID());
            }
        }

        if (!selectedStudents.isEmpty()) {
            Fragment fragment = new StudentEvaluationFinal();
            Bundle bundle = new Bundle();
            bundle.putString("rowLevelID", rowLevelId);
            bundle.putString("subjectID", subjectID);
            bundle.putString("lessonID", lessonID);
            bundle.putString("teacherID", teacherID);
            bundle.putString("currentDate", date);
            bundle.putString("homeMaxDegree", homeDegree);
            bundle.putString("testMaxDegree", testDegree);
            bundle.putStringArrayList("selectedStudentsList", (ArrayList<String>) selectedStudents);
            fragment.setArguments(bundle);
            namesView.startFragment(fragment, "StudentEvaluationFinal");
        }
    }

    public int getNamesCount() {
        return students.size();
    }
}