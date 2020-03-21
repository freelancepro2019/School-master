package com.softray_solutions.newschoolproject.ui.fragments.weeklyPlan;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.SemesterSample;
import com.softray_solutions.newschoolproject.model.Week;
import com.softray_solutions.newschoolproject.ui.activities.weekplanschedule.PlanScheduleActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class WeeklyPlanFragment extends Fragment implements WeeklyPlanFragmentView {
    @BindView(R.id.week_spinner)
    Spinner weekSpinner;
    @BindView(R.id.semester_spinner)
    Spinner semesterSpinner;

    private Unbinder unbinder;
    private WeeklyPlanFragmentPresenter presenter = new WeeklyPlanFragmentPresenter(this);

    private String weekId = "0", semesterId = "0", sonID = "0";
    private List<SemesterSample> semesters;
    private List<Week> weeks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        if (Customization.obtainUser(getActivity().getSharedPreferences("userData", MODE_PRIVATE)).getType().equals("F")) {
            sonID = getArguments().getString("sonID", "");
        }
        presenter.getWeeks();
        presenter.getSemesters();
        initSpinners();
    }

    private void initSpinners() {
        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weekId = weeks.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semesterId = semesters.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.week_plan_button)
    public void getWeekPlan() {
        Intent intent = new Intent(getContext(), PlanScheduleActivity.class);
        intent.putExtra("week", weekId);
        intent.putExtra("semester", semesterId);
        intent.putExtra("sonId", sonID);
        startActivity(intent);
    }

    @Override
    public void setSemesters(List<SemesterSample> semesters) {
        if (isVisible()) {
            this.semesters = semesters;
            ArrayAdapter<SemesterSample> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, semesters);
            semesterSpinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void setWeeks(List<Week> weeks) {
        if (isVisible()) {
            this.weeks = weeks;
            ArrayAdapter<Week> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, weeks);
            weekSpinner.setAdapter(dataAdapter);
        }
    }
}
