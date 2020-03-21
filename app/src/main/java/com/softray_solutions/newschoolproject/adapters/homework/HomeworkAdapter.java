package com.softray_solutions.newschoolproject.adapters.homework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.homework.HomeworkPresenter;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkHolder> {

    private HomeworkPresenter presenter;

    public HomeworkAdapter(HomeworkPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public HomeworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homework, parent, false);
        return new HomeworkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkHolder holder, int position) {
        presenter.onBindItemView(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getHomeworkCount();
    }
}
