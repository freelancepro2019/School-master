package com.softray_solutions.newschoolproject.ui.activities.classSchedule;


import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.model.StudentClass;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.TeacherDay;

import java.util.List;

public interface ClassScheduleView {

    void setClasses(List<StudentDay<StudentClass>> data);

    void setTeacherClasses(List<TeacherDay> teacherClasses);

    void setError(String errorMessage);

    void showClassTeacher(String teacher);

    void hideProgressBar();


    void changeLanguage(Configuration configuration);
}
