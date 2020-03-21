package com.softray_solutions.newschoolproject.ui.fragments.revisions;

import android.os.Bundle;

import com.softray_solutions.newschoolproject.model.RevisionLibrary;
import com.softray_solutions.newschoolproject.model.TeacherLibraryModel;

import java.util.List;

public interface LibraryListView {
    void setData(List<RevisionLibrary> userData);

    void setError(String error);

    void hideProgressBar();

    void onEmptyList();

    void startRevisionLibraryInfoFragment(Bundle bundle);

    void setTeacherData(List<TeacherLibraryModel> teacherLibraryModelList);
}
