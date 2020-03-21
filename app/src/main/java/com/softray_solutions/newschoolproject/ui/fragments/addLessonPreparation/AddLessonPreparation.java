package com.softray_solutions.newschoolproject.ui.fragments.addLessonPreparation;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.AddLessonPreparationSubject;
import com.softray_solutions.newschoolproject.model.Skill;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class AddLessonPreparation extends Fragment implements AddLessonPreparationView {
    private final int PICK_FILE_CODE = 20;
    Unbinder unbinder;
    @BindView(R.id.subject_spinner_add_preparation)
    Spinner subjectSpinner;
    @BindView(R.id.skills_spinner_add_preparation)
    Spinner skillsSpinner;
    @BindView(R.id.date_preview)
    TextView datePreview;
    @BindView(R.id.attachment_preview)
    TextView attachmentPreview;
    @BindView(R.id.title_edit)
    EditText titleEdit;
    @BindView(R.id.strategy_edit)
    EditText strategyEdit;
    @BindView(R.id.goals_edit)
    EditText goalsEdit;
    @BindView(R.id.means_edit)
    EditText meansEdit;
    @BindView(R.id.preface_edit)
    EditText prefaceEditText;
    @BindView(R.id.eval_edit)
    EditText evalEdit;
    @BindView(R.id.homeworks_edit)
    EditText homeworksEdit;
    Dialog dialog;
    private List<File> fileList = new ArrayList<>();
    private AddLessonPreparationPresenter preparationPresenter;
    private String lessonTitle, dayDate, lessonStrategy,
            lessonGoals, lessonMeans, lessonPreface, lessonEval, lessonHomeworks, filePreviewString;
    private File file;

    public AddLessonPreparation() {
    }

    @Override
    public void requestPermission() {
        int PERMISSION_REQUEST = 100;
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_lesson_preparation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                preparationPresenter.getSkills(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        skillsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                preparationPresenter.getSelectedSkill(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getDate();
        initDialog();
        preparationPresenter.checkPermission(getContext());
    }

    private void initPresenter() {
        preparationPresenter = new AddLessonPreparationPresenter(this, Objects.requireNonNull(getActivity())
                .getSharedPreferences("userData", Context.MODE_PRIVATE));
        preparationPresenter.getSubjects(getLanguage());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }


    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unbindView() {
        unbinder.unbind();
    }


    @Override
    public void setSubjects(List<AddLessonPreparationSubject> data) {
        if (isVisible()) {
            if (data.isEmpty()) {
                AddLessonPreparationSubject emptySubject = new AddLessonPreparationSubject();
                emptySubject.setRowName("");
                emptySubject.setSubjectName("");
                emptySubject.setLevelName(getString(R.string.no_subject));
                data.add(emptySubject);
            }
            ArrayAdapter<AddLessonPreparationSubject> adapter = new ArrayAdapter<>(
                    Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, data);
            subjectSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void setSkills(List<Skill> data) {
        if (isVisible()) {
            if (data.isEmpty()) {
                Skill emptySkill = new Skill();
                emptySkill.setSkillId("0");
                emptySkill.setSkillName(getString(R.string.no_skill));
                data.add(emptySkill);
            }
            ArrayAdapter<Skill> adapter = new ArrayAdapter<>(
                    Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, data);
            skillsSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void setDate(String formattedDate) {
        if (isVisible()) {
            dayDate = formattedDate;
            datePreview.setText(formattedDate);
        }

    }

    @Override
    public void showToast(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.date_selector)
    public void pickDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                preparationPresenter.pickDate(i, i1, i2);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.attachment_selector)
    public void pickFile() {
        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRootPath("/storage/")
                .withRequestCode(PICK_FILE_CODE)
                .withFilter(Pattern.compile("^.*.(pdf|txt|mp3|png|jpg|jpeg|MP4|docx|mp4|doc)$"))
                .withTitle(getString(R.string.attachment))
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (path != null) {
                file = new File(path);
                fileList.add(file);
                if (filePreviewString == null || attachmentPreview.getText().equals(getString(R.string.no_file_selected))) {
                    filePreviewString = file.getName();
                } else {
                    filePreviewString += "\n" + file.getName();
                }
                attachmentPreview.setText(filePreviewString);
            }
        }
    }

    private void initDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        TextView progressText = dialog.findViewById(R.id.progress_text);
        progressText.setText(R.string.uploading_lesson);
    }

    @Override
    public void showDialog() {
        if (isVisible()) dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (isVisible()) dialog.dismiss();
    }

    @Override
    public void clearFields() {
        if (isVisible()) {
            filePreviewString = getString(R.string.no_file_selected);
            attachmentPreview.setText(filePreviewString);
            fileList.clear();
            file = null;
            lessonTitle = null;
            lessonStrategy = null;
            lessonGoals = null;
            lessonMeans = null;
            lessonPreface = null;
            lessonEval = null;
            lessonHomeworks = null;
            titleEdit.setText("");
            strategyEdit.setText("");
            goalsEdit.setText("");
            meansEdit.setText("");
            prefaceEditText.setText("");
            evalEdit.setText("");
            homeworksEdit.setText("");
        }
    }

    private void getDate() {
        preparationPresenter.getDate();
    }

    @OnClick(R.id.next_button)
    public void send() {
        lessonTitle = preparationPresenter.extractStringFromEditText(titleEdit);
        lessonStrategy = preparationPresenter.extractStringFromEditText(strategyEdit);
        lessonGoals = preparationPresenter.extractStringFromEditText(goalsEdit);
        lessonMeans = preparationPresenter.extractStringFromEditText(meansEdit);
        lessonPreface = preparationPresenter.extractStringFromEditText(prefaceEditText);
        lessonEval = preparationPresenter.extractStringFromEditText(evalEdit);
        lessonHomeworks = preparationPresenter.extractStringFromEditText(homeworksEdit);

        if (lessonTitle.isEmpty()) {
            titleEdit.setError(getString(R.string.lesson_title_required));
        } else if (lessonStrategy.isEmpty()) {
            strategyEdit.setError(getString(R.string.lesson_strategy_required));
        } else if (lessonGoals.isEmpty()) {
            goalsEdit.setError(getString(R.string.lesson_goals_required));
        } else if (lessonMeans.isEmpty()) {
            meansEdit.setError(getString(R.string.lesson_means_required));
        } else if (lessonPreface.isEmpty()) {
            prefaceEditText.setError(getString(R.string.lesson_preface_required));
        } else if (lessonEval.isEmpty()) {
            evalEdit.setError(getString(R.string.lesson_evaluation_required));
        } else if (lessonHomeworks.isEmpty()) {
            homeworksEdit.setError(getString(R.string.lesson_homework_required));
        } else if (dayDate.isEmpty()) {
            Toast.makeText(getContext(), R.string.lesson_date_required, Toast.LENGTH_SHORT).show();
        } else if (file != null) {
            preparationPresenter.uploadFile(getContext(), fileList, lessonTitle, dayDate, lessonStrategy, lessonGoals, lessonMeans
                    , lessonPreface, lessonEval, lessonHomeworks);
        } else {
            preparationPresenter.uploadLesson(lessonTitle, dayDate, lessonStrategy, lessonGoals, lessonMeans
                    , lessonPreface, lessonEval, lessonHomeworks);
        }
    }

    private String getLanguage() {
        Locale locale = getContext().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }
}