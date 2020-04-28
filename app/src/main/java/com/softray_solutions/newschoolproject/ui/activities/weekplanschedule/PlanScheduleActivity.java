package com.softray_solutions.newschoolproject.ui.activities.weekplanschedule;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.SpanningGridLayoutManager;
import com.softray_solutions.newschoolproject.adapters.StudentPlanWeekAdapter;
import com.softray_solutions.newschoolproject.adapters.plan.PlanAdapter;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.StudentPlanClass;
import com.softray_solutions.newschoolproject.model.TeacherPlanClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlanScheduleActivity extends AppCompatActivity implements PlanScheduleView {
    @BindView(R.id.schedule_progress)
    ProgressBar progressBar;
    @BindView(R.id.schedule_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.empty_plan_schedule_text_view)
    TextView emptyView;
    Unbinder unbinder;
    private PlanSchedulePresenter presenter;
    private Toast toast;

    private ArrayList<TeacherPlanClass> classes = new ArrayList<>();
    private ArrayList<StudentPlanClass> studentPlanClasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plan_schedule);
        unbinder = ButterKnife.bind(this);
        customToast();
        initPresenter();
        getData();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String weekId = bundle.getString("week");
            String semesterId = bundle.getString("semester");
            String sonID = bundle.getString("sonId");
            presenter.getSchedule(weekId, semesterId, sonID);
        }
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new PlanSchedulePresenter(this, userPref, new AppPrefsHelper(userPref));
        presenter.getCurrentLanguage();

    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRecycler() {
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRecycler() {
        if (recyclerView != null) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setClasses(List<StudentDay<TeacherPlanClass>> data) {
        try {
            setupTableHeader();
            for (int i = 0; i < data.size(); i++) {

                String day = data.get(i).getmDay();

                if (day.toLowerCase().equals("sunday"))
                {
                    classes.add(new TeacherPlanClass(getString(R.string.sunday), true, null));

                }

                if (day.toLowerCase().equals("monday"))
                {
                    classes.add(new TeacherPlanClass(getString(R.string.monday), true, null));

                }

                if (day.toLowerCase().equals("tuesday"))
                {
                    classes.add(new TeacherPlanClass(getString(R.string.tuesday), true, null));

                }

                if (day.toLowerCase().equals("wednesday"))
                {
                    classes.add(new TeacherPlanClass(getString(R.string.wednesday), true, null));

                }

                if (day.toLowerCase().equals("thursday"))
                {
                    classes.add(new TeacherPlanClass(getString(R.string.thursday), true, null));

                }

                /*switch (i) {
                    case 0:
                        classes.add(new TeacherPlanClass(getString(R.string.sunday), true, null));
                        break;
                    case 1:
                        classes.add(new TeacherPlanClass(getString(R.string.monday), true, null));
                        break;
                    case 2:
                        classes.add(new TeacherPlanClass(getString(R.string.tuesday), true, null));
                        break;
                    case 3:
                        classes.add(new TeacherPlanClass(getString(R.string.wednesday), true, null));
                        break;
                    case 4:
                        classes.add(new TeacherPlanClass(getString(R.string.thursday), true, null));
                        break;
                }*/
                classes.addAll(data.get(i).getmDayData());
            }

            PlanAdapter adapter = new PlanAdapter(classes, presenter);

            setupRecyclerView(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStudentClasses(List<StudentDay<StudentPlanClass>> data) {
        try {
            setupStudentTableHeader();
            for (int i = 0; i < data.size(); i++) {


                String day = data.get(i).getmDay();

                if (day.toLowerCase().equals("sunday"))
                {
                    studentPlanClasses.add(new StudentPlanClass(getString(R.string.sunday), true, null));

                }

                if (day.toLowerCase().equals("monday"))
                {
                    studentPlanClasses.add(new StudentPlanClass(getString(R.string.monday), true, null));

                }

                if (day.toLowerCase().equals("tuesday"))
                {
                    studentPlanClasses.add(new StudentPlanClass(getString(R.string.tuesday), true, null));

                }

                if (day.toLowerCase().equals("wednesday"))
                {
                    studentPlanClasses.add(new StudentPlanClass(getString(R.string.wednesday), true, null));

                }

                if (day.toLowerCase().equals("thursday"))
                {
                    studentPlanClasses.add(new StudentPlanClass(getString(R.string.thursday), true, null));

                }


/*
                switch (i) {
                    case 0:
                        studentPlanClasses.add(new StudentPlanClass(getString(R.string.sunday), true, null));
                        break;
                    case 1:
                        studentPlanClasses.add(new StudentPlanClass(getString(R.string.monday), true, null));
                        break;
                    case 2:
                        studentPlanClasses.add(new StudentPlanClass(getString(R.string.tuesday), true, null));
                        break;
                    case 3:
                        studentPlanClasses.add(new StudentPlanClass(getString(R.string.wednesday), true, null));
                        break;
                    case 4:
                        studentPlanClasses.add(new StudentPlanClass(getString(R.string.thursday), true, null));
                        break;
                }
*/
                studentPlanClasses.addAll(data.get(i).getmDayData());
            }

            StudentPlanWeekAdapter adapter = new StudentPlanWeekAdapter(studentPlanClasses, presenter);

            setupRecyclerView(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setupStudentTableHeader() {
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.classes) + "\n" + getString(R.string.days), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.first_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.second_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.third_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.forth_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.fifth_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.sixth_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.seventh_class), true, null));
        studentPlanClasses.add(new StudentPlanClass(getString(R.string.eighth_class), true, null));
    }

    private void setupTableHeader() {
        classes.add(new TeacherPlanClass(getString(R.string.classes) + "\n" + getString(R.string.days), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.first_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.second_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.third_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.forth_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.fifth_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.sixth_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.seventh_class), true, null));
        classes.add(new TeacherPlanClass(getString(R.string.eighth_class), true, null));
    }

    private void setupRecyclerView(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new SpanningGridLayoutManager(this, 9));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setError(String errorMessage) {
        try {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToast(String planContent) {
        setToastText(planContent);
    }

    private void setToastText(String content) {
        toast.cancel();
        customToast();
        TextView toastText = toast.getView().findViewById(R.id.text);
        toastText.setText(content);
        toast.show();
    }

    private void customToast() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_layout, findViewById(R.id.custom_toast_container));
        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}