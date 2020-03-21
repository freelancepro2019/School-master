package com.softray_solutions.newschoolproject.adapters.lessons;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonsHolder extends RecyclerView.ViewHolder implements LessonsItemView {
    @BindView(R.id.lesson_title_text)
    TextView lessonTitleText;
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.subject_text)
    TextView subjectText;
    @BindView(R.id.teacher_text)
    TextView teacherText;
    @BindView(R.id.grad_text)
    TextView gradText;
    @BindView(R.id.skills_text)
    TextView skillsText;

    public LessonsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setDateText(String date) {
        dateText.setText(date);
    }

    @Override
    public void setLessonTitle(String title) {
        lessonTitleText.setText(title);
    }

    @Override
    public void setSubjectName(String subjectName) {
        subjectText.setText(subjectName);
    }

    @Override
    public void setTeacherName(String teacher) {
        teacherText.setText(teacher);
    }

    @Override
    public void setGradText(String grad) {
        gradText.setText(grad);
    }

    @Override
    public void setSkills(String skills) {
        skillsText.setText(skills);
    }
}
