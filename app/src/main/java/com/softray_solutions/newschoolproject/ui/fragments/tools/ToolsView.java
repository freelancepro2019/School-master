package com.softray_solutions.newschoolproject.ui.fragments.tools;

import com.softray_solutions.newschoolproject.model.Tools;

import java.util.ArrayList;

public interface ToolsView {
    void fillList(ArrayList<Tools> tools);

    void startSonsActivity(int extra);

    void startActivity(Class intent);

    void startHomeworkActivity(String studentId, String schoolId);
}
