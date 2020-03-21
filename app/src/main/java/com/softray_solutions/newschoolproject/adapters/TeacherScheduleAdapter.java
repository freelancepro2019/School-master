package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.TeacherClass;
import com.softray_solutions.newschoolproject.ui.activities.classSchedule.ClassSchedulePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherScheduleAdapter extends RecyclerView.Adapter<TeacherScheduleAdapter.MainViewHolder> {
    private ArrayList<TeacherClass> data;
    private ClassSchedulePresenter presenter;

    public TeacherScheduleAdapter(ArrayList<TeacherClass> data, ClassSchedulePresenter presenter) {
        this.data = data;
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
        presenter.onBindItemTeacher(holder, position, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_name)
        public
        TextView textView;

        private MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
