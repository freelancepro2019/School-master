package com.softray_solutions.newschoolproject.adapters.homework;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeworkHolder extends RecyclerView.ViewHolder implements HomeworkItemView {
    @BindView(R.id.homework_title_text)
    TextView titleTextView;
    @BindView(R.id.subject_text)
    TextView subjectTextView;
    @BindView(R.id.content_text)
    TextView contentTextView;
    @BindView(R.id.date_text)
    TextView dateTextView;
    @BindView(R.id.attachment_text)
    TextView attachmentTextView;

    Unbinder unbinder;

    public HomeworkHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }

    @Override
    public void setTitleText(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setContentText(String content) {
        contentTextView.setText(content);
    }

    @Override
    public void setDateText(String date) {
        dateTextView.setText(date);
    }

    @Override
    public void setSubject(String subject) {
        subjectTextView.setText(subject);
    }

    @Override
    public void setAttachment(String attachment) {
        attachmentTextView.setText(attachment);
    }
}
