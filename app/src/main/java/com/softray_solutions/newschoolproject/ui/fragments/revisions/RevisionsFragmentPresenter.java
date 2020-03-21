package com.softray_solutions.newschoolproject.ui.fragments.revisions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.Subject;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher.AskYourTeacherFragment;
import com.softray_solutions.newschoolproject.ui.fragments.homework.HomeworkFragment;
import com.softray_solutions.newschoolproject.ui.fragments.lessons.LessonsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RevisionsFragmentPresenter {
    private RevisionsFragmentView view;
    private SharedPreferences preferences;
    private Bundle bundle = new Bundle();
    private List<Subject> subjectIdList = new ArrayList<>();
    private String tag = "";
    private MyInterface myInterface;
    private String lang;
    private String studentId;
    private User user;

    RevisionsFragmentPresenter(RevisionsFragmentView view, SharedPreferences preferences, String lang) {
        this.view = view;
        this.preferences = preferences;
        this.lang = lang;
    }

    void setCurrentView(String tag) {
        this.tag = tag;

        switch (tag) {
            case "RevisionsFragment":
                view.setView(R.string.revision_button);
                break;
            case "lessons":
                view.setView(R.string.lessons_button);
                break;
            case "AskYourTeacher":
                view.setView(R.string.ask_your_teacher);
                break;
            case "homework":
                view.setView(R.string.homework);
        }
    }

    void getSpinnerData() {
        User user = Customization.obtainUser(preferences);
        bundle.putString("schoolID", user.getSchoolID());
        bundle.putString("userID", user.getId());
        bundle.putString("userType", user.getType());
        switch (user.getType()) {
            case "S":
                getStudentSubjects(user.getId(), user.getSchoolID());
                break;
            case "E":
                getTeacherSubject(user.getId());
                break;
        }

    }

    void onButtonClicked(int position, boolean homework) {
        Fragment fragment;
        user = Customization.obtainUser(preferences);
        bundle.putString("userIDForLessons", user.getId());
        bundle.putString("subjectId", subjectIdList.get(position).getmSubjectID());
        bundle.putString("rowLevelId", subjectIdList.get(position).getSRowLevelID());
        bundle.putString("schoolID", user.getSchoolID());
        bundle.putString("classID", subjectIdList.get(position).getClassID());
        if (homework) {
            fragment = new HomeworkFragment();
            bundle.putString("userId", studentId);
        } else {
            bundle.putString("subjectName", subjectIdList.get(position).getClassSubName());

            switch (tag) {
                case "RevisionsFragment":
                    fragment = new LibraryListFragment();
                    break;
                case "lessons":
                    Log.e("RevisionsFragment", user.toString());
                    fragment = new LessonsFragment();
                    break;
                case "AskYourTeacher":
                    fragment = new AskYourTeacherFragment();
                    break;
                default:
                    fragment = new LibraryListFragment();
            }
        }
        fragment.setArguments(bundle);

        view.showListOfLibraries(fragment);

    }

    void getTeacherSubject(String teacherId) {
        this.studentId = teacherId;
        myInterface = Common.getMyInterface();
        myInterface.getTeacherSubject(teacherId, lang).enqueue(new Callback<ArrayDataModel<Subject>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Subject>> call, Response<ArrayDataModel<Subject>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        subjectIdList = response.body().getData();
                        view.setSpinnerData(response.body().getData());
                    } else {
                        view.setError("failed to get data");
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Subject>> call, Throwable t) {
                view.setError(t.getLocalizedMessage());
            }
        });
    }


    void getStudentSubjects(String studentId, String schoolId) {
        this.studentId = studentId;
        myInterface = Common.getMyInterface();
        myInterface.getRevisions(schoolId, studentId).enqueue(new Callback<ArrayDataModel<Subject>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayDataModel<Subject>> call, @NonNull Response<ArrayDataModel<Subject>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        subjectIdList = response.body().getData();
                        view.setSpinnerData(response.body().getData());
                    } else {
                        view.setError("failed to get data");
                    }
                } else {
                    view.setError("null response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayDataModel<Subject>> call, @NonNull Throwable t) {
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    void clearList(List data) {
        data.clear();
    }
}
