package com.softray_solutions.newschoolproject.adapters.Students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.names.EvaluationNamesPresenter;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsHolder> {

    private EvaluationNamesPresenter presenter;

    public StudentsAdapter(EvaluationNamesPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public StudentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_name, parent, false);
        return new StudentsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsHolder holder, int position) {
        presenter.onBindItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getNamesCount();
    }
}
