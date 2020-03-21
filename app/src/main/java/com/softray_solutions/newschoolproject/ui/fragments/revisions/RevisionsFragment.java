package com.softray_solutions.newschoolproject.ui.fragments.revisions;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.Subject;
import com.softray_solutions.newschoolproject.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class RevisionsFragment extends Fragment implements RevisionsFragmentView {
    List<String> classSubName = new ArrayList<>();
    @BindView(R.id.revision_spinner)
    Spinner spinner;
    @BindView(R.id.revision_button)
    Button button;
    Unbinder unbinder;
    RevisionsFragmentPresenter presenter;
    private SharedPreferences sharedPreferences;
    private String language, userId = "0", schoolId = "0";
    private boolean isHomework = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revisions, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        getLang();
        getArgs();
        initSharedPref();
        initPresenter();
        presenter.setCurrentView(getCurrentFragment());
    }


    private String getCurrentFragment() {
        Fragment fragment = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.student_revision_container);
        return fragment.getTag();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBind();
    }

    @Override
    public void setSpinnerData(List<Subject> subjectList) {
        if (isVisible()) {
            if (isTeacher()) {
                for (int i = 0; i < subjectList.size(); i++) {
                    classSubName.add(subjectList.get(i).getClassSubName() + " - " + subjectList.get(i).getmLevelName() + " - " + subjectList.get(i).getmRowName());
                }
            } else {
                for (int i = 0; i < subjectList.size(); i++) {
                    classSubName.add(subjectList.get(i).getClassSubName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, classSubName);
            spinner.setAdapter(dataAdapter);
            if (!subjectList.isEmpty()) {
                button.setEnabled(true);
            }
        }
    }

    @Override
    public void setError(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void showListOfLibraries(Fragment fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.student_revision_container, fragment).addToBackStack(null).commit();

    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBind() {
        unbinder.unbind();
    }

    private void initPresenter() {
        presenter = new RevisionsFragmentPresenter(this, sharedPreferences, language);
        if (isHomework) {
            if (schoolId.isEmpty()) {
                presenter.getTeacherSubject(userId);
            } else {
                presenter.getStudentSubjects(userId, schoolId);
            }
        } else {
            presenter.getSpinnerData();
        }
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            schoolId = bundle.getString("schoolId");
            isHomework = bundle.getBoolean("homework");
        }
    }

    @OnClick(R.id.revision_button)
    public void onButtonClick() {
        presenter.onButtonClicked(spinner.getSelectedItemPosition(), isHomework);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.clearList(classSubName);
    }


    @Override
    public void setView(int stringID) {
        button.setText(stringID);
    }

    private void getLang() {
        Locale current = this.getResources().getConfiguration().locale;
        language = current.getLanguage();
    }

    private void initSharedPref() {
        sharedPreferences = getContext().getSharedPreferences("userData", MODE_PRIVATE);
    }

    private boolean isTeacher() {
        User user = Customization.obtainUser(sharedPreferences);
        return user.getType().contains("E");
    }
}
