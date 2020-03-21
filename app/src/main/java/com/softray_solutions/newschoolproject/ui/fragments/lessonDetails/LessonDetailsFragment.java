package com.softray_solutions.newschoolproject.ui.fragments.lessonDetails;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.lessonDetails.LessonDetailsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class LessonDetailsFragment extends Fragment implements LessonDetailsView {
    @BindView(R.id.lesson_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.lesson_details_progress_bar)
    ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private Unbinder unbinder;
    private LessonDetailsPresenter presenter;
    private String lessonToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        getArgs();
        initSharedPref();
        presenter = new LessonDetailsPresenter(this, sharedPreferences);
        presenter.getLessonDetails(lessonToken);
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        lessonToken = bundle.getString("lessonToken");
    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAdapter(LessonDetailsAdapter adapter) {
        if (isVisible()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initSharedPref() {
        sharedPreferences = getContext().getSharedPreferences("userData", MODE_PRIVATE);
    }
}
