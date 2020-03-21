package com.softray_solutions.newschoolproject.ui.fragments.notifications;


import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.ParentNotificationAdapter;
import com.softray_solutions.newschoolproject.adapters.StudentNotificationAdapter;
import com.softray_solutions.newschoolproject.adapters.TeacherNotificationAdapter;
import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.model.TeacherNotificationDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationsFragment extends Fragment implements NotificationsView {
    @BindView(R.id.notifications_progressBar)
    ProgressBar notificationsProgressBar;
    @BindView(R.id.empty_notifications_view)
    TextView emptyNotificationsTextview;
    @BindView(R.id.notifications_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.notification_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    List<TeacherNotificationDataModel> teacherDataList = new ArrayList<>();
    List<StudentNotificaionDataModel> studentDataList = new ArrayList<>();
    List<ParentNotificationDataModel> parentDataList = new ArrayList<>();
    private Unbinder unbinder;
    private NotificationPresenter notificationPresenter;
    private StudentNotificationAdapter studentAdapter;
    private TeacherNotificationAdapter teacherAdapter;
    private ParentNotificationAdapter parentAdapter;
    private String language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLang();
        initPresenter();
        bindView(view);
        setUpSwipeToRefresh();
        checkLanguage();
        getNotifications();
    }

    private void initPresenter() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        notificationPresenter = new NotificationPresenter(this, sharedPreferences);
    }


    @Override
    public void setNotificationsAdapter(String language) {

    }

    @Override
    public void hideProgressBar() {
        if (isVisible()) {
            notificationsProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessTeacher(List<TeacherNotificationDataModel> dataSet) {
        if (isVisible()) {
            swipeRefreshLayout.setRefreshing(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
            emptyNotificationsTextview.setVisibility(View.GONE);
            teacherDataList = dataSet;
            teacherAdapter = new TeacherNotificationAdapter(getContext(), teacherDataList, notificationPresenter, language);
            recyclerView.setAdapter(teacherAdapter);
        }
    }

    @Override
    public void onSuccessStudent(List<StudentNotificaionDataModel> data) {
        if (isVisible()) {
            swipeRefreshLayout.setRefreshing(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
            emptyNotificationsTextview.setVisibility(View.GONE);
            studentDataList = data;
            studentAdapter = new StudentNotificationAdapter(studentDataList, notificationPresenter, getContext(), language);
            recyclerView.setAdapter(studentAdapter);
        }
    }

    @Override
    public void onSuccessParent(List<ParentNotificationDataModel> data) {
        if (isVisible()) {
            swipeRefreshLayout.setRefreshing(false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.VISIBLE);
            emptyNotificationsTextview.setVisibility(View.GONE);
            parentDataList = data;
            parentAdapter = new ParentNotificationAdapter(parentDataList, notificationPresenter, getContext(), language);
            recyclerView.setAdapter(parentAdapter);

        }
    }

    @Override
    public void onError(String message) {
        if (isVisible()) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void emptyView() {
        if (isVisible()) {
            recyclerView.setVisibility(View.GONE);
            emptyNotificationsTextview.setVisibility(View.VISIBLE);
        }
    }

    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void checkLanguage() {
        notificationPresenter.checkLanguage(getContext());
    }

    private void getNotifications() {
        notificationPresenter.getNotifications();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setUpSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (teacherAdapter != null) {
                    teacherDataList.clear();
                    teacherAdapter.notifyDataSetChanged();
                }
                if (studentAdapter != null) {
                    studentDataList.clear();
                    studentAdapter.notifyDataSetChanged();
                }
                getNotifications();
            }
        });
    }

    private void getLang() {
        Locale current = this.getResources().getConfiguration().locale;
        language = current.getLanguage();
    }
}
