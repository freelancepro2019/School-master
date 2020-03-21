package com.softray_solutions.newschoolproject.ui.fragments.news;

import com.softray_solutions.newschoolproject.adapters.NewsAdapter;

public interface NewsView {


    void rotateRecyclerView();

    void setAdapter(NewsAdapter adapter);

    void setError(String errorMessage);

    void startNewsContentActivity(String token);

    void hideProgressBar();

}
