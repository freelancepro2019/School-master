package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.StudentPlanClass;
import com.softray_solutions.newschoolproject.ui.activities.weekplanschedule.PlanSchedulePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentPlanWeekAdapter extends RecyclerView.Adapter<StudentPlanWeekAdapter.MainViewHolder> {
    private ArrayList<StudentPlanClass> classes;
    private PlanSchedulePresenter presenter;

    public StudentPlanWeekAdapter(ArrayList<StudentPlanClass> classes, PlanSchedulePresenter presenter) {
        this.classes = classes;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public StudentPlanWeekAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_cell, parent, false);
        return new StudentPlanWeekAdapter.MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentPlanWeekAdapter.MainViewHolder holder, int position) {
        presenter.onBindStudentItem(holder, position, classes);
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