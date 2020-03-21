package com.softray_solutions.newschoolproject.ui.fragments.tools;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.ToolsAdapter;
import com.softray_solutions.newschoolproject.model.Tools;
import com.softray_solutions.newschoolproject.ui.activities.homework.HomeworkActivity;
import com.softray_solutions.newschoolproject.ui.activities.sons.SonsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ToolsFragment extends Fragment implements ToolsView {
    @BindView(R.id.recycler_view_tools)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private ArrayList<Tools> tools;
    private ToolsPresenter presenter;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tools, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPrefs();
        setUpPresenter();
        initRecyclerView();
    }

    private void initPrefs() {
        prefs = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unBindView() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    private void initRecyclerView() {
        presenter.setUpLists();
        ToolsAdapter toolsAdapter = new ToolsAdapter(getContext(), presenter, tools);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(toolsAdapter);
    }

    private void setUpPresenter() {
        presenter = new ToolsPresenter(this, getContext(), prefs);
    }

    @Override
    public void startSonsActivity(int extra) {
        Intent intent = new Intent(getContext(), SonsActivity.class);
        intent.putExtra("position", extra);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void startActivity(Class intent) {
        startActivity(new Intent(getContext(), intent));
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    @Override
    public void startHomeworkActivity(String studentId, String schoolId) {
        Intent intent = new Intent(getContext(), HomeworkActivity.class);
        intent.putExtra("userId", studentId);
        intent.putExtra("schoolId", schoolId);
        startActivity(intent);
    }

    @Override
    public void fillList(ArrayList<Tools> tools) {
        this.tools = tools;
    }

}
