package com.softray_solutions.newschoolproject.ui.fragments.news;

import android.content.Context;

import androidx.annotation.NonNull;

import com.softray_solutions.newschoolproject.adapters.NewsAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter {

    private NewsView view;
    private MyInterface myInterface;
    private String language = "en";
    private Call<ArrayDataModel<News>> call;
    private List<News> news = new ArrayList<>();

    NewsPresenter(NewsView view) {
        this.view = view;
        myInterface = Common.getMyInterface();
    }

    void getAllNews() {
        call = myInterface.getNews(1, language);
        call.enqueue(new Callback<ArrayDataModel<News>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayDataModel<News>> call, @NonNull Response<ArrayDataModel<News>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        news = response.body().getData();
                        setAdapter();
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("Null response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayDataModel<News>> call, @NonNull Throwable t) {
                view.setError(t.getLocalizedMessage());
                view.hideProgressBar();
            }
        });
    }

    void getNews() {
        call = myInterface.getNews(2, language);
        call.enqueue(new Callback<ArrayDataModel<News>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayDataModel<News>> call, @NonNull Response<ArrayDataModel<News>> response) {
                view.hideProgressBar();
                if (response.body() != null) {

                    if (response.body().getSuccess() == 1) {
                        news = response.body().getData();
                        setAdapter();
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("Null response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayDataModel<News>> call, @NonNull Throwable t) {
                view.setError(t.getLocalizedMessage());
                view.hideProgressBar();
            }
        });
    }

    void getEvents() {
        call = myInterface.getNews(3, language);
        call.enqueue(new Callback<ArrayDataModel<News>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayDataModel<News>> call, @NonNull Response<ArrayDataModel<News>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        news = response.body().getData();
                        setAdapter();
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("Null response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayDataModel<News>> call, @NonNull Throwable t) {
                view.setError(t.getLocalizedMessage());
                view.hideProgressBar();
            }
        });
    }

    private void setAdapter() {
        NewsAdapter adapter = new NewsAdapter(this);
        view.setAdapter(adapter);
    }

    void checkLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        language = current.getLanguage();

        if (language.equals("ar")) {
            view.rotateRecyclerView();
        }
    }

    void clearNewsList() {
        news.clear();
    }

    public void onBindItemView(NewsAdapter.NewsHolder holder, int position) {
        final News news = this.news.get(position);
        holder.setTitleText(news.getTitle().trim());
        holder.setContentText(news.getContent().trim());
        holder.setDateText(news.getDate());
        holder.setTimeText(news.getTime());
        holder.setNewsImage(news.getImagePath());
        holder.itemView.setOnClickListener(view -> onItemClicked(news.getToken()));
    }

    public int getNewsCount() {
        return news.size();
    }

    private void onItemClicked(String token) {
        startContentFragment(token);
    }


    private void startContentFragment(String token) {
        view.startNewsContentActivity(token);
    }

    void cancelCall() {
        call.cancel();
    }
}