package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluationFinal;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.PositivesStudentDataModel;
import com.softray_solutions.newschoolproject.model.StudentSkillsDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StudentEvaluationFinalPresenter {
    private StudentEvaluationFinalView studentView;
    private List<StudentSkillsDataModel> studentSkills = new ArrayList<>();
    private List<PositivesStudentDataModel> positivesList = new ArrayList<>();
    private String skillID, positivesID;

    public StudentEvaluationFinalPresenter(StudentEvaluationFinalView view) {
        this.studentView = view;
    }

    void getSkillsSpinnerData(String rowLevelId, String subjectID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getStudentSkillsList(rowLevelId, subjectID).enqueue(new Callback<ArrayDataModel<StudentSkillsDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentSkillsDataModel>> call, Response<ArrayDataModel<StudentSkillsDataModel>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        studentSkills = response.body().getData();
                        studentView.setSkillsSpinnerData(studentSkills);
                    } else {
                        studentView.setError(response.body().getMessage());
                    }
                } else {
                    studentView.setError("server error");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentSkillsDataModel>> call, Throwable t) {
                studentView.setError(t.getLocalizedMessage());
            }
        });
    }

    void getPositivesSpinnerData(String lang) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getPositivesList(lang).enqueue(new Callback<ArrayDataModel<PositivesStudentDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<PositivesStudentDataModel>> call, Response<ArrayDataModel<PositivesStudentDataModel>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        positivesList = response.body().getData();
                        studentView.PositivesSpinnerData(positivesList);
                    } else {
                        studentView.setError(response.body().getMessage());
                    }
                } else {
                    studentView.setError("server error");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<PositivesStudentDataModel>> call, Throwable t) {
                studentView.setError(t.getLocalizedMessage());
            }
        });
    }

    void setSelectedSkillID(int position) {
        skillID = studentSkills.get(position).getSkillID();
    }

    void setSelectedPositiveID(int position) {
        positivesID = positivesList.get(position).getPositiveID();
    }


    void sendEvaluation(ArrayList<String> studentIdList, String homeworkDegreeString, String examDegreeString, boolean isParticipated, String lessonID, String teacherID, String subjectID, String date, String homeMaxDegree, String examMaxDegree) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.submitEval(studentIdList, skillID, positivesID, homeworkDegreeString, examDegreeString, isParticipated, teacherID, lessonID, subjectID, date, homeMaxDegree, examMaxDegree).enqueue(new Callback<ArrayDataModel<StudentSkillsDataModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<StudentSkillsDataModel>> call, Response<ArrayDataModel<StudentSkillsDataModel>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        studentView.evalSuccess("تم التقييم");
                    } else {
                        studentView.setError("failed");
                    }
                } else {
                    studentView.setError("server error");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<StudentSkillsDataModel>> call, Throwable t) {
                studentView.setError(t.getLocalizedMessage());

            }
        });
    }
}
