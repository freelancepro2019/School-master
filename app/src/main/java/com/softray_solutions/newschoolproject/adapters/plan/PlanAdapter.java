package com.softray_solutions.newschoolproject.adapters.plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.TeacherPlanClass;
import com.softray_solutions.newschoolproject.ui.activities.weekplanschedule.PlanSchedulePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MainViewHolder> {
    private ArrayList<TeacherPlanClass> classes;
    private PlanSchedulePresenter presenter;

    public PlanAdapter(ArrayList<TeacherPlanClass> classes, PlanSchedulePresenter presenter) {
        this.classes = classes;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_cell, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        presenter.onBindItem(holder, position, classes);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_name)
        public
        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}