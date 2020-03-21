package com.softray_solutions.newschoolproject.ui.fragments.notifications;

import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.model.TeacherNotificationDataModel;

import java.util.List;

public interface NotificationsView {

    void setNotificationsAdapter(String language);

    void hideProgressBar();


    void onError(String message);

    void emptyView();

    void onSuccessTeacher(List<TeacherNotificationDataModel> data);

    void onSuccessStudent(List<StudentNotificaionDataModel> data);

    void onSuccessParent(List<ParentNotificationDataModel> data);
}
