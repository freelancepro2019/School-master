package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.AskYourTeacherAdapter;
import com.softray_solutions.newschoolproject.model.AskYourTeacher;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AskYourTeacherFragment extends Fragment implements AskYourTeacherFragmentView {
    Unbinder unbinder;
    @BindView(R.id.askYourTeacherCardsRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ask_your_teacher_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_ask_your_teacher_text_view)
    TextView textView;
    String language, teacherID, subjectID, schoolID, rowLevelId;
    private AskYourTeacherFragmentPresenter presenter;

    public AskYourTeacherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_your_teacher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLang();
        bindView(view);
        getArgs();
        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBind();
    }

    private void initPresenter() {
        presenter = new AskYourTeacherFragmentPresenter(this, language);
        presenter.askYourTeacher(teacherID, subjectID, schoolID, rowLevelId);
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBind() {
        unbinder.unbind();
    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEmptyResponse() {
        if (isVisible()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setResponse(List<AskYourTeacher> listOfQuestions) {
        if (isVisible()) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            AskYourTeacherAdapter adapter = new AskYourTeacherAdapter(listOfQuestions, presenter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void setError(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startNextFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.student_revision_container, fragment).addToBackStack(tag).commit();
    }

    private void getLang() {
        Locale current = this.getResources().getConfiguration().locale;
        language = current.getLanguage();
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        teacherID = bundle.getString("userID");
        schoolID = bundle.getString("schoolID");
        rowLevelId = bundle.getString("rowLevelId");
        subjectID = bundle.getString("subjectId");
    }
}
