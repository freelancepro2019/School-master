package com.softray_solutions.newschoolproject.ui.fragments.studentEvaluation;

import com.softray_solutions.newschoolproject.model.Lesson;
import com.softray_solutions.newschoolproject.model.Semester;
import com.softray_solutions.newschoolproject.model.Subject;

import java.util.List;

interface StudentEvaluationView {

    void setError(String errorMessage);

    void setSemesters(List<Semester> semesters);

    void setSubjects(List<Subject> subjects);

    void setLessons(List<Lesson> lessons);

    void setDate(String formattedDate);
}
