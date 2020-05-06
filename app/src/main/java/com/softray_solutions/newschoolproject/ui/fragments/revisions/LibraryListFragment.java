package com.softray_solutions.newschoolproject.ui.fragments.revisions;


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
import com.softray_solutions.newschoolproject.adapters.LibraryAdapter;
import com.softray_solutions.newschoolproject.adapters.TeacherLibraryAdapter;
import com.softray_solutions.newschoolproject.model.RevisionLibrary;
import com.softray_solutions.newschoolproject.model.TeacherLibraryModel;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LibraryListFragment extends Fragment implements LibraryListView {
    Bundle bundle = new Bundle();
    @BindView(R.id.libraryRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.library_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_list_text_view)
    TextView emptyTextView;
    Unbinder unbinder;
    LibraryListPresenter presenter;

    String schoolID, subjectID, userID, language, rowlevelID, userType;
    LibraryAdapter adapter;
    TeacherLibraryAdapter teacherAdapter;

    public LibraryListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDeviceLanguage();
        bindView(view);
        getBundleData();
        initPresenter();
        initRecyclerView();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    private void getBundleData() {
        bundle = getArguments();
        userID = bundle.getString("userID");
        subjectID = bundle.getString("subjectId");
        schoolID = bundle.getString("schoolID");
        rowlevelID = bundle.getString("rowLevelId");
        userType = bundle.getString("userType");
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    private void initPresenter() {
        presenter = new LibraryListPresenter(this, language);
        if (userType.equals("E")) {
            presenter.getTeacherLibraryList(rowlevelID, subjectID, userID);
        } else {
            presenter.getLibraryList(userID, subjectID, schoolID);
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setData(List<RevisionLibrary> userData) {
        if (isVisible()) {
            if (userData.size() > 0) {
                adapter = new LibraryAdapter(userData, presenter);
                recyclerView.setAdapter(adapter);
            }

        }
    }


    @Override
    public void setError(String error) {
        if (isVisible()) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEmptyList() {
        if (isVisible()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void startRevisionLibraryInfoFragment(Bundle bundle) {
        RevisionLibraryInfoFragment fragment = new RevisionLibraryInfoFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.student_revision_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setTeacherData(List<TeacherLibraryModel> teacherLibraryModelList) {
        if (isVisible()) {
            teacherAdapter = new TeacherLibraryAdapter(teacherLibraryModelList, presenter);
            recyclerView.setAdapter(teacherAdapter);
        }
    }

    private void getDeviceLanguage() {
        Locale current = this.getResources().getConfiguration().locale;
        language = current.getLanguage();
    }
}