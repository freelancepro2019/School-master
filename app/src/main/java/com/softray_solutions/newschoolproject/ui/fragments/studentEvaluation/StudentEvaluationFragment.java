package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluation;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.Lesson;
import com.softray_solutions.newschoolproject.model.Semester;
import com.softray_solutions.newschoolproject.model.Subject;
import com.softray_solutions.newschoolproject.ui.fragments.names.EvaluationNamesFragment;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StudentEvaluationFragment extends Fragment implements StudentEvaluationView {
    @BindView(R.id.picked_date_tv)
    TextView pickedDateTv;
    @BindView(R.id.subject_spinner)
    Spinner subjectsSpinner;
    @BindView(R.id.semester_spinner)
    Spinner semesterSpinner;
    @BindView(R.id.lesson_spinner)
    Spinner lessonSpinner;
    @BindView(R.id.next_button)
    Button nextButton;
    String date;
    private StudentEvaluationPresenter presenter;
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_evaluation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
        initPresenter();
        initView();
        getSemesters();
    }


    private void initView() {
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.getSubjects(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.getLessons(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lessonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.setSelectedLesson(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date != null) {
                    Bundle bundle = presenter.getCurrentData();

                    EvaluationNamesFragment fragment = new EvaluationNamesFragment();
                    fragment.setArguments(bundle);

                    Objects.requireNonNull(getFragmentManager()).beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                            , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.student_evaluation_container, fragment, "studentEvaluation").addToBackStack(null).commit();

                } else {
                    Toast.makeText(getContext(), R.string.date_required, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getSemesters() {
        presenter.getSemesters();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void initPresenter() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("userData", Context.MODE_PRIVATE);
        presenter = new StudentEvaluationPresenter(this, sharedPreferences, getContext());
        presenter.getUserId();
        presenter.getDate();
    }

    @Override
    public void setLessons(List<Lesson> lessons) {
        if (isVisible()) {
            ArrayAdapter<Lesson> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, lessons);
            lessonSpinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setSemesters(List<Semester> semesters) {
        if (isVisible()) {

            ArrayAdapter<Semester> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, semesters);
            semesterSpinner.setAdapter(dataAdapter);
            nextButton.setEnabled(true);
        }
    }

    @Override
    public void setSubjects(List<Subject> subjects) {
        if (isVisible()) {
            ArrayAdapter<Subject> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, subjects);
            subjectsSpinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public void setDate(String formattedDate) {
        date = formattedDate;
        pickedDateTv.setText(formattedDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.date_picker_button)
    public void pickDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = i + "-" + (i1 + 1) + "-" + i2;
                presenter.setDate(date);
                pickedDateTv.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}