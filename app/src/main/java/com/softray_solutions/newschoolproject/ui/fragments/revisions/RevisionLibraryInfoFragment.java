package com.softray_solutions.newschoolproject.ui.fragments.revisions;


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


public class RevisionLibraryInfoFragment extends Fragment implements RevisionLibraryInfoView {
    Unbinder unbinder;
    RevisionLibraryInfoPresenter presenter;
    Bundle bundle = new Bundle();
    @BindView(R.id.teacher_name_text_view)
    TextView teacherNameTV;
    @BindView(R.id.revision_name_text_view)
    TextView revisionNameTV;
    @BindView(R.id.download_link_text_view)
    TextView downloadUrlTV;
    @BindView(R.id.teacher_name_title)
    TextView teacherNameTitle;
    String teacherName, revisionName, downloadUrl, userType, subjectName, youtubeLink;

    public RevisionLibraryInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revision_library_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();
        getBundleData();
        if (revisionName.isEmpty()) {
            revisionName = getString(R.string.revision_without_title);
        }
        if (userType.equals("E")) {
            teacherNameTitle.setText(R.string.subject_name);
            presenter.passTeacherData(revisionName, downloadUrl, subjectName, youtubeLink);
        } else if (userType.equals("S")) {

            presenter.passData(teacherName, revisionName, downloadUrl);
        }
    }

    private void initPresenter() {
        presenter = new RevisionLibraryInfoPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBind();
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBind() {
        unbinder.unbind();
    }

    private void getBundleData() {
        bundle = getArguments();
        teacherName = bundle.getString("teacherName");
        revisionName = bundle.getString("revisionName");
        downloadUrl = bundle.getString("fileUrl");
        userType = bundle.getString("userType");
        subjectName = bundle.getString("subjectName");
        youtubeLink = bundle.getString("youtubeLink");
    }

    @Override
    public void setTeacherName(String name) {
        teacherNameTV.setText(name);
    }

    @Override
    public void setSubjectName(String name) {
        revisionNameTV.setText(name);
    }

    @Override
    public void setDownloadUrl(String url) {
        if (url.isEmpty()) {
            downloadUrlTV.setText(R.string.download_link_not_found);
        } else {
            downloadUrlTV.setText(url);

        }
    }
}
