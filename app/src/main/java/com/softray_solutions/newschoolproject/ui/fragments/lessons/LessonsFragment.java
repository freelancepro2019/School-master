package com.softray_solutions.newschoolproject.ui.fragments.lessons;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.softray_solutions.newschoolproject.adapters.lessons.LessonsAdapter;
import com.softray_solutions.newschoolproject.ui.fragments.lessonDetails.LessonDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LessonsFragment extends Fragment implements LessonsView {
    @BindView(R.id.lessons_recycler)
    RecyclerView lessonsRecycler;
    @BindView(R.id.lesson_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_lessons_text_view)
    TextView textView;
    private String subjectID, rowLevelID, subjectName, type, userID, schoolID, classID;
    private Unbinder unbinder;

    private LessonsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lessons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
        getArgs();
        initPresenter();
        presenter.getLessons(subjectID, rowLevelID, userID, schoolID, classID);
    }

    private void initPresenter() {
        presenter = new LessonsPresenter(this, subjectName, type);
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        subjectID = bundle.getString("subjectId");
        rowLevelID = bundle.getString("rowLevelId");
        subjectName = bundle.getString("subjectName");
        type = bundle.getString("userType");
        userID = bundle.getString("userIDForLessons");
        schoolID = bundle.getString("schoolID");
        classID = bundle.getString("classID");
    }

    @Override
    public void setError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startLessonFragment(String lessonToken) {
        Fragment fragment = new LessonDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lessonToken", lessonToken);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.student_revision_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEmptyView() {
        if (isVisible()) {
            lessonsRecycler.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAdapter(LessonsAdapter adapter) {
        if (isVisible()) {
            lessonsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            lessonsRecycler.setAdapter(adapter);
        }
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }
}
