package com.softray_solutions.newschoolproject.adapters.lessonDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.lessonDetails.LessonDetailsPresenter;

public class LessonDetailsAdapter extends RecyclerView.Adapter<LessonDetailsHolder> {

    private LessonDetailsPresenter presenter;

    public LessonDetailsAdapter(LessonDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public LessonDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson_details, parent, false);
        return new LessonDetailsHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull LessonDetailsHolder holder, int position) {
        presenter.onBindItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }
}
