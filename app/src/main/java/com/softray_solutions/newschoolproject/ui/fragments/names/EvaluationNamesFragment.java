package com.softray_solutions.newschoolproject.ui.fragments.names;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.Students.StudentsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EvaluationNamesFragment extends Fragment implements EvaluationNamesView {
    @BindView(R.id.names_recycler)
    RecyclerView namesRecycler;
    @BindView(R.id.next_button)
    Button button;
    @BindView(R.id.empty_names_text_view)
    TextView emptyView;
    @BindView(R.id.names_progress_bar)
    ProgressBar progressBar;
    private Unbinder unbinder;
    private EvaluationNamesPresenter presenter;
    private String classID, rowLevelID, lessonID, subjectID, teacherID, date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_names_evaluation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
        initRecyclerView();
        initPresenter();
        presenter.getSchoolID();
        getExtras();
        presenter.getStudentClass(classID, rowLevelID);
        button.setOnClickListener(view1 ->
                presenter.onButtonClicked(rowLevelID, subjectID, lessonID, teacherID, date));
    }

    private void initRecyclerView() {
        namesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initPresenter() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        presenter = new EvaluationNamesPresenter(this, sharedPreferences);
    }

    @Override
    public void setAdapter(StudentsAdapter adapter) {
        if (isVisible()) {
            namesRecycler.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            namesRecycler.setAdapter(adapter);
        }
    }

    private void getExtras() {
        Bundle bundle = getArguments();

        classID = bundle.getString("classID");
        rowLevelID = bundle.getString("rowLevelID");
        lessonID = bundle.getString("lessonID");
        subjectID = bundle.getString("subjectID");
        teacherID = bundle.getString("teacherID");
        date = bundle.getString("currentDate");
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.student_evaluation_container, fragment, tag).addToBackStack(null).commit();
    }

    @Override
    public void setEmptyView() {
        if (isVisible()) {

            namesRecycler.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showPregress() {
        if (isVisible()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }


    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
