package com.softray_solutions.newschoolproject.ui.fragments.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.ToolsAdapter;
import com.softray_solutions.newschoolproject.model.Tools;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.accountBalance.AccountBalanceActivity;
import com.softray_solutions.newschoolproject.ui.activities.askYourTeacher.AskYourTeacherActivity;
import com.softray_solutions.newschoolproject.ui.activities.classSchedule.ClassScheduleActivity;
import com.softray_solutions.newschoolproject.ui.activities.lessonPreparationTeacher.LessonPreparationsTeacher;
import com.softray_solutions.newschoolproject.ui.activities.lessons.LessonsActivity;
import com.softray_solutions.newschoolproject.ui.activities.paymentReset.PaymentResetActivity;
import com.softray_solutions.newschoolproject.ui.activities.revisions.RevisionsActivity;
import com.softray_solutions.newschoolproject.ui.activities.studentEvaluation.StudentEvaluationActivity;
import com.softray_solutions.newschoolproject.ui.activities.weeklyPlan.WeeklyPlanActivity;

import java.util.ArrayList;

public class ToolsPresenter {
    private ToolsView toolsView;
    private SharedPreferences sharedPreferences;
    private Context context;
    private String userType;
    private User user;

    ToolsPresenter(ToolsView view, Context context, SharedPreferences sharedPreferences) {
        this.toolsView = view;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        getUserType();
    }

    void setUpLists() {
        switch (userType) {
            case "S":
                getArray(getTitleArray(R.array.students_tools_titles), getIconsArray(R.array.student_tools_images));
                break;
            case "F":
                getArray(getTitleArray(R.array.parent_tools_titles), getIconsArray(R.array.parent_tools_images));
                break;
            case "E":
                getArray(getTitleArray(R.array.teacher_tools_titles), getIconsArray(R.array.teacher_tools_images));
                break;
            case "U":
                getArray(getTitleArray(R.array.parent_tools_titles), getIconsArray(R.array.parent_tools_images));
                break;
        }
    }

    public void bindViewAtPosition(ToolsAdapter.MyViewHolder holder, final int position, final ArrayList<Tools> tools) {
        holder.getImageView().setImageDrawable(tools.get(position).getIcon());
        holder.getTextView().setText(tools.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userType.equals("S")) {
                    switch (position) {
                        case 0:
                            toolsView.startActivity(ClassScheduleActivity.class);
                            break;
                        case 1:
                            toolsView.startActivity(RevisionsActivity.class);
                            break;
                        case 2:
                            toolsView.startActivity(AskYourTeacherActivity.class);
                            break;
                        case 3:
                            toolsView.startHomeworkActivity(user.getId(), user.getSchoolID());
                            break;
                        case 4:
                            toolsView.startActivity(LessonsActivity.class);
                            break;
                        case 5:
                            toolsView.startActivity(WeeklyPlanActivity.class);
                    }
                } else if (userType.equals("E")) {
                    switch (position) {
                        case 0:
                            toolsView.startActivity(ClassScheduleActivity.class);
                            break;
                        case 1:
                            toolsView.startActivity(RevisionsActivity.class);
                            break;
                        case 2:
                            toolsView.startActivity(AskYourTeacherActivity.class);
                            break;
                        case 3:
                            toolsView.startActivity(StudentEvaluationActivity.class);
                            break;
                        case 4:
                            toolsView.startHomeworkActivity(user.getId(), "");
                            break;
                        case 5:
                            toolsView.startActivity(LessonPreparationsTeacher.class);
                            break;
                        case 6:
                            toolsView.startActivity(WeeklyPlanActivity.class);
                    }
                } else {
                    switch (position) {
                        case 1:
                            toolsView.startActivity(AccountBalanceActivity.class);
                            break;
                        case 2:
                            toolsView.startActivity(PaymentResetActivity.class);
                            break;
                        default:
                            toolsView.startSonsActivity(position);
                            break;
                    }
                }
            }
        });

    }

    private void getUserType() {
        Gson gson = new Gson();
        String userString = sharedPreferences.getString("user", "");
        user = gson.fromJson(userString, User.class);
        userType = user.getType();
    }


    private void getArray(String[] titles, ArrayList<Drawable> icons) {
        ArrayList<Tools> tools = new ArrayList<>();
        for (int i = 0; i < icons.size(); i++) {
            tools.add(new Tools(titles[i], icons.get(i)));
        }
        toolsView.fillList(tools);
    }

    private String[] getTitleArray(int titlesArray) {
        return context.getResources().getStringArray(titlesArray);
    }

    private ArrayList<Drawable> getIconsArray(int imagesArray) {
        TypedArray ta = context.getResources().obtainTypedArray(imagesArray);
        ArrayList<Drawable> images = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                images.add(ContextCompat.getDrawable(context, id));
            }
        }
        ta.recycle();
        return images;
    }
}
