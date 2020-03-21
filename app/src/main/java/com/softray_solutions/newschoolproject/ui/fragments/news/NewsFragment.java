package com.softray_solutions.newschoolproject.ui.fragments.news;

import android.content.Intent;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.NewsAdapter;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.softray_solutions.newschoolproject.ui.activities.newsContent.NewsContentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends Fragment implements NewsView {
    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    @BindView(R.id.main_swipe_layout)
    SwipeRefreshLayout mainSwipeRefresh;
    @BindView(R.id.news_progress_bar)
    ProgressBar progressBar;
    Unbinder unbinder;
    MainActivity mainActivity;

    private NewsPresenter newsPresenter;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsPresenter = new NewsPresenter(this);

        bindView(view);

        mainSwipeRefresh.setColorSchemeResources(R.color.colorAccent);
        mainSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearEventsList();
                getNews();
            }
        });

        newsPresenter.checkLanguage(getContext());

        initRecyclerView();
        clearEventsList();
        getNews();
    }

    private void clearEventsList() {
        newsPresenter.clearNewsList();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void rotateRecyclerView() {
        mainRecyclerView.setRotationY(180);
    }

    private void getNews() {
        newsPresenter.getNews();
    }

    private void initRecyclerView() {
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainRecyclerView.setAdapter(new NewsAdapter(newsPresenter));
    }

    @Override
    public void setAdapter(NewsAdapter adapter) {
        if (isVisible()) {
            mainRecyclerView.setAdapter(adapter);
            mainSwipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void setError(String errorMessage) {
        if (isVisible()) {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        newsPresenter.cancelCall();
    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void startNewsContentActivity(String token) {
        Intent intent = new Intent(mainActivity, NewsContentActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        mainActivity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
}