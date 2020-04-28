package com.softray_solutions.newschoolproject.ui.activities.classSchedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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
import com.softray_solutions.newschoolproject.adapters.TeacherScheduleAdapter;
import com.softray_solutions.newschoolproject.adapters.TimeTableAdapter;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.StudentClass;
import com.softray_solutions.newschoolproject.model.StudentDay;
import com.softray_solutions.newschoolproject.model.TeacherClass;
import com.softray_solutions.newschoolproject.model.TeacherDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClassScheduleActivity extends AppCompatActivity implements ClassScheduleView {
    @BindView(R.id.recycler_view_classes_schedule)
    RecyclerView recyclerView;
    @BindView(R.id.classSchedule_progress_bar)
    ProgressBar progressBar;
    private Unbinder unbinder;
    private ArrayList<StudentClass> classes = new ArrayList<>();
    private ArrayList<TeacherClass> teacherData = new ArrayList<>();
    private ClassSchedulePresenter presenter;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_class_schedule);
        bindView();
        customToast();
        initPresenter();
        getID();
    }

    private void initPresenter() {
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        presenter = new ClassSchedulePresenter(this, preferences, new AppPrefsHelper(preferences));
        presenter.getCurrentLanguage();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void getID() {
        Intent intent = getIntent();
        presenter.getExtras(intent.getStringExtra("id"));
    }

    @Override
    public void showClassTeacher(String teacher) {
        setToastText(teacher);
    }

    @Override
    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    private void unbind() {
        unbinder.unbind();
    }

    private void setupRecyclerView(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new SpanningGridLayoutManager(this, 9));
        recyclerView.setAdapter(adapter);
    }

    private void setupTableHeader() {
        classes.add(new StudentClass(getString(R.string.classes) + "\n" + getString(R.string.days), null));
        classes.add(new StudentClass(getString(R.string.first_class), null));
        classes.add(new StudentClass(getString(R.string.second_class), null));
        classes.add(new StudentClass(getString(R.string.third_class), null));
        classes.add(new StudentClass(getString(R.string.forth_class), null));
        classes.add(new StudentClass(getString(R.string.fifth_class), null));
        classes.add(new StudentClass(getString(R.string.sixth_class), null));
        classes.add(new StudentClass(getString(R.string.seventh_class), null));
        classes.add(new StudentClass(getString(R.string.eighth_class), null));
    }

    @Override
    public void setClasses(List<StudentDay<StudentClass>> data) {

        Log.e("size",data.size()+"_");
        for (StudentClass aClass :data.get(0).getmDayData())
        {
            Log.e("data",data.get(0).getmDay()+"___"+aClass.getObject()+"_");
        }
        Log.e("ssss",data.get(0).getmDayData().size()+"");

        try {
            setupTableHeader();
            for (int i = 0; i < data.size(); i++) {

                String day = data.get(i).getmDay();

                if (day.toLowerCase().equals("sunday"))
                {
                    classes.add(new StudentClass(getString(R.string.sunday), null));

                }

                if (day.toLowerCase().equals("monday"))
                {
                    classes.add(new StudentClass(getString(R.string.monday), null));

                }

                if (day.toLowerCase().equals("tuesday"))
                {
                    classes.add(new StudentClass(getString(R.string.tuesday), null));

                }

                if (day.toLowerCase().equals("wednesday"))
                {
                    classes.add(new StudentClass(getString(R.string.wednesday), null));

                }



                if (day.toLowerCase().equals("thursday"))
                {
                    classes.add(new StudentClass(getString(R.string.thursday), null));

                }





                /*switch (i) {
                    case 0:
                        classes.add(new StudentClass(getString(R.string.sunday), null));
                        break;
                    case 1:
                        classes.add(new StudentClass(getString(R.string.monday), null));
                        break;
                    case 2:
                        classes.add(new StudentClass(getString(R.string.tuesday), null));
                        break;
                    case 3:
                        classes.add(new StudentClass(getString(R.string.wednesday), null));
                        break;
                    case 4:
                        classes.add(new StudentClass(getString(R.string.thursday), null));
                        break;
                }*/

                classes.addAll(data.get(i).getmDayData());

            }
            TimeTableAdapter adapter = new TimeTableAdapter(classes, presenter);

            setupRecyclerView(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTeacherClasses(List<TeacherDay> teacherClasses) {
        try {
            teacherData.add(new TeacherClass(getString(R.string.classes) + "\n" + getString(R.string.days), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.first_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.second_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.third_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.forth_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.fifth_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.sixth_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.seventh_class), null, null, null));
            teacherData.add(new TeacherClass(getString(R.string.eighth_class), null, null, null));

            for (int i = 0; i < teacherClasses.size(); i++) {
                switch (i) {
                    case 0:
                        teacherData.add(new TeacherClass(getString(R.string.sunday), null, null, null));
                        break;
                    case 1:
                        teacherData.add(new TeacherClass(getString(R.string.monday), null, null, null));
                        break;
                    case 2:
                        teacherData.add(new TeacherClass(getString(R.string.tuesday), null, null, null));
                        break;
                    case 3:
                        teacherData.add(new TeacherClass(getString(R.string.wednesday), null, null, null));
                        break;
                    case 4:
                        teacherData.add(new TeacherClass(getString(R.string.thursday), null, null, null));
                        break;
                }
                teacherData.addAll(teacherClasses.get(i).getDayData());
            }
            TeacherScheduleAdapter adapter = new TeacherScheduleAdapter(teacherData, presenter);
            setupRecyclerView(adapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setError(String errorMessage) {
        try {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customToast() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_layout, findViewById(R.id.custom_toast_container));
        toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
    }

    private void setToastText(String teacher) {
        toast.cancel();
        customToast();
        TextView toastText = toast.getView().findViewById(R.id.text);
        toastText.setText(teacher);
        toast.show();
    }

}
