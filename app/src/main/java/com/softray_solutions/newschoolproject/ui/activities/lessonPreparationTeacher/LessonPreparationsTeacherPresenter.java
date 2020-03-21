package com.softray_solutions.newschoolproject.ui.activities.lessonPreparationTeacher;

import android.content.res.Configuration;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.ui.fragments.teacherLessonPreparation.TeacherLessonPreparationFragment;

import java.util.Locale;

class LessonPreparationsTeacherPresenter {
    private LessonPreparationsTeacherView preparationsTeacherView;
    private PrefsInteractor interactor;

    public LessonPreparationsTeacherPresenter(LessonPreparationsTeacherView preparationsTeacherView, AppPrefsHelper appPrefsHelper) {
        this.preparationsTeacherView = preparationsTeacherView;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    public void setTeacherLessonPreparationFragment() {
        preparationsTeacherView.setFragment(new TeacherLessonPreparationFragment(), "TeacherLessonPreparationFragment");
    }

    public void getCurrentLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                String currentLanguage;
                if (result.equals("not selected")) {
                    currentLanguage = Locale.getDefault().getLanguage();
                } else {
                    currentLanguage = result;
                }
                Locale locale = new Locale(currentLanguage);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                preparationsTeacherView.changeLanguage(configuration);
            }
        });
    }
}
