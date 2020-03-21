package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.AskYourTeacher;
import com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher.AskYourTeacherFragmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AskYourTeacherAdapter extends RecyclerView.Adapter<AskYourTeacherAdapter.AskYourTeacherViewHolder> {
    private List<AskYourTeacher> dataSet;
    private AskYourTeacherFragmentPresenter presenter;

    public AskYourTeacherAdapter(List<AskYourTeacher> dataSet, AskYourTeacherFragmentPresenter presenter) {
        this.dataSet = dataSet;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public AskYourTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ask_your_teacher_list_item, parent, false);
        return new AskYourTeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AskYourTeacherViewHolder holder, int position) {
        presenter.bindDataFromAdapter(holder, dataSet, position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class AskYourTeacherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.question_title)
        public
        TextView titleTextView;
        @BindView(R.id.answer_body)
        public
        TextView answerTextView;
        @BindView(R.id.read_more_arrow)
        public
        ImageView arrow;

        public AskYourTeacherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
