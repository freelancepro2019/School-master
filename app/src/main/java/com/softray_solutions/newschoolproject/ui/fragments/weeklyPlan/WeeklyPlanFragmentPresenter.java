package com.softray_solutions.newschoolproject.ui.fragments.weeklyPlan;

import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.SemesterSample;
import com.softray_solutions.newschoolproject.model.Week;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class WeeklyPlanFragmentPresenter {

    private WeeklyPlanFragmentView view;
    private MyInterface myInterface = Common.getMyInterface();
    private String language = "ar";
    private String displayLanguage = "arabic";
    private List<SemesterSample> semesters;
    private List<Week> weeks;

    WeeklyPlanFragmentPresenter(WeeklyPlanFragmentView weeklyPlanFragmentView) {
        view = weeklyPlanFragmentView;
    }

    void getSemesters() {
        myInterface.getSemestersSamples(language).enqueue(new Callback<ArrayDataModel<SemesterSample>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<SemesterSample>> call, Response<ArrayDataModel<SemesterSample>> response) {
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
            public void onFailure(Call<ArrayDataModel<SemesterSample>> call, Throwable t) {
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    void getWeeks() {
        myInterface.getWeeks(displayLanguage).enqueue(new Callback<ArrayDataModel<Week>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Week>> call, Response<ArrayDataModel<Week>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        weeks = response.body().getData();
                        view.setWeeks(weeks);
                    } else {
                        view.setError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Week>> call, Throwable t) {
                view.setError(t.getLocalizedMessage());
            }
        });
    }


}
