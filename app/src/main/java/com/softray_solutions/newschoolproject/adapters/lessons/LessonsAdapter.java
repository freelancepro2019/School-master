package com.softray_solutions.newschoolproject.adapters.lessons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.lessons.LessonsPresenter;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsHolder> {

    private LessonsPresenter presenter;

    public LessonsAdapter(LessonsPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public LessonsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new LessonsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsHolder holder, int position) {
        presenter.onBindItem(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getLessonsCount();
    }
}
