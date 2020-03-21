package com.softray_solutions.newschoolproject.ui.fragments.homework;


import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.homework.HomeworkAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class HomeworkFragment extends Fragment implements HomeworkView {
    @BindView(R.id.homeworkRecycler)
    RecyclerView homeworkRecycler;
    @BindView(R.id.empty_homework_view)
    TextView homeworkText;
    @BindView(R.id.homeworkSwipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.lessons_progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;

    private String userId, subjectId;
    private String rowLevelId = "0";
    private HomeworkPresenter presenter = new HomeworkPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homework, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        getArgs();

        initSwipeRefresh();

        getHomework();
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomework();
            }
        });
    }

    private void getHomework() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userData", MODE_PRIVATE);

        presenter.getHomework(sharedPreferences, userId, subjectId, rowLevelId);
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            subjectId = bundle.getString("subjectId");
            rowLevelId = bundle.getString("rowLevelId");
        }
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setAdapter(HomeworkAdapter adapter) {
        if (isVisible()) {
            swipeRefresh.setRefreshing(false);
            homeworkText.setVisibility(View.GONE);
            homeworkRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            homeworkRecycler.setAdapter(adapter);
        }
    }

    @Override
    public void setEmptyView() {
        if (isVisible()) {
            homeworkText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgress() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
