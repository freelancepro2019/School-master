package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluationFinal;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.PositivesStudentDataModel;
import com.softray_solutions.newschoolproject.model.StudentSkillsDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StudentEvaluationFinal extends Fragment implements StudentEvaluationFinalView {
    @BindView(R.id.participation_checkbox)
    CheckBox participationCheckbox;
    @BindView(R.id.skills_spinner)
    Spinner skillsSpinner;
    @BindView(R.id.positives_spinner)
    Spinner positivesSpinner;
    @BindView(R.id.home_work_degree_ET)
    EditText homeworkDegreeET;
    @BindView(R.id.max_home_degree_ET)
    EditText homeworkMaxDegreeET;
    @BindView(R.id.exam_degree_ET)
    EditText examDegreeET;
    @BindView(R.id.max_exam_degree_ET)
    EditText examMaxDegreeEt;
    @BindView(R.id.finish_button)
    Button button;
    private Unbinder unbinder;
    private StudentEvaluationFinalPresenter presenter;
    private String rowLevelID, subjectID, lessonID, teacherID, language, date, homeworkMaxDegreeString = "0", examMaxDegreeString = "0";
    private ArrayList<String> studentIdList = new ArrayList<>();

    public StudentEvaluationFinal() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_evaluation_final, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        getExtras();
        getLanguage();
        initPresenter();
    }


    private void initPresenter() {
        presenter = new StudentEvaluationFinalPresenter(this);
        presenter.getSkillsSpinnerData(rowLevelID, subjectID);
        presenter.getPositivesSpinnerData(language);
    }

    private void getExtras() {
        Bundle bundle = getArguments();
        rowLevelID = bundle.getString("rowLevelID");
        subjectID = bundle.getString("subjectID");
        studentIdList = bundle.getStringArrayList("selectedStudentsList");
        lessonID = bundle.getString("lessonID");
        teacherID = bundle.getString("teacherID");
        date = bundle.getString("currentDate");
        homeworkMaxDegreeString = bundle.getString("homeMaxDegree");
        examMaxDegreeString = bundle.getString("testMaxDegree");
        homeworkMaxDegreeET.setText(homeworkMaxDegreeString);
        examMaxDegreeEt.setText(examMaxDegreeString);
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unbindView() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }

    @Override
    public void setError(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void evalSuccess(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), R.string.eval_success, Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void setSkillsSpinnerData(List<StudentSkillsDataModel> data) {
        if (isVisible()) {

            if (data.isEmpty()) {
                data.add(new StudentSkillsDataModel());
                data.get(0).setSikllName(getContext().getResources().getString(R.string.no_skill));
                data.get(0).setSkillID("0");
            }
            ArrayAdapter<StudentSkillsDataModel> skillsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
            skillsSpinner.setAdapter(skillsAdapter);
            skillsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    presenter.setSelectedSkillID(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    public void PositivesSpinnerData(List<PositivesStudentDataModel> data) {
        if (isVisible()) {
            button.setEnabled(true);
            if (data.isEmpty()) {
                data.add(new PositivesStudentDataModel());
                data.get(0).setPositiveName(getContext().getResources().getString(R.string.no_positive));
                data.get(0).setPositiveID("0");
            }
            ArrayAdapter<PositivesStudentDataModel> positivesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
            positivesSpinner.setAdapter(positivesAdapter);
            positivesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    presenter.setSelectedPositiveID(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void getLanguage() {
        Locale current = this.getResources().getConfiguration().locale;
        language = current.getLanguage();
    }

    @OnClick(R.id.finish_button)
    public void finishEvaluation() {
        String homeworkDegreeString, examDegreeString;
        homeworkDegreeString = homeworkDegreeET.getText().toString().trim();
        examDegreeString = examDegreeET.getText().toString().trim();
        homeworkMaxDegreeString = homeworkMaxDegreeET.getText().toString().trim();
        examMaxDegreeString = examMaxDegreeEt.getText().toString().trim();
        if (homeworkDegreeString.isEmpty()) {
            homeworkDegreeET.setError(getString(R.string.field_required));
        } else if (homeworkMaxDegreeString.isEmpty()) {
            homeworkMaxDegreeET.setError(getString(R.string.field_required));
        } else if (Integer.valueOf(homeworkDegreeString) > Integer.valueOf(homeworkMaxDegreeString)) {
            homeworkDegreeET.setError(getString(R.string.invalid_mark));
        } else if (examDegreeString.isEmpty()) {
            examDegreeET.setError(getString(R.string.field_required));
        } else if (examMaxDegreeString.isEmpty()) {
            examMaxDegreeEt.setError(getString(R.string.field_required));
        } else if (Integer.valueOf(examDegreeString) > Integer.valueOf(examMaxDegreeString)) {
            examDegreeET.setError(getString(R.string.invalid_mark));
        } else {
            boolean isParticipated = participationCheckbox.isChecked();
            presenter.sendEvaluation(studentIdList, homeworkDegreeString, examDegreeString, isParticipated, lessonID, teacherID, subjectID, date, homeworkMaxDegreeString, examMaxDegreeString);
        }
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
