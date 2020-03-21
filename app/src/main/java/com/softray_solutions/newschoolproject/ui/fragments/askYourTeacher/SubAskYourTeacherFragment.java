package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubAskYourTeacherFragment extends Fragment implements SubAskYourTeacherFragmentView {
    @BindView(R.id.title_question)
    TextView titleTV;
    @BindView(R.id.content_question)
    TextView contentTV;
    @BindView(R.id.answer_question)
    TextView answerTV;
    Bundle bundle;
    private Unbinder unbinder;
    private SubAskYourTeacherFragmentPresenter presenter;

    public SubAskYourTeacherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_ask_your_teacher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }

    private void initPresenter() {
        presenter = new SubAskYourTeacherFragmentPresenter(this);
        bundle = getArguments();
        presenter.setTextViews(bundle.getString("title"), bundle.getString("content"), bundle.getString("answer"));
    }


    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unbindView() {
        unbinder.unbind();
    }

    @Override
    public void setTitle(String title) {
        titleTV.setText(title);
    }

    @Override
    public void setContent(String content) {
        contentTV.setText(content);
    }

    @Override
    public void setAnswer(String answer) {
        answerTV.setText(answer);
    }
}
