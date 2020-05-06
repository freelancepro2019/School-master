package com.softray_solutions.newschoolproject.ui.fragments.lessonDetails;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.lessonDetails.LessonDetailsAdapter;
import com.softray_solutions.newschoolproject.adapters.lessonDetails.LessonDetailsHolder;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.LessonContent;
import com.softray_solutions.newschoolproject.model.LessonDetails;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonDetailsPresenter {

    private LessonDetailsView view;
    private List<LessonContent> lessonList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    LessonDetailsPresenter(LessonDetailsView view, SharedPreferences sharedPreferences) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
    }

    void getLessonDetails(String lessonToken) {
        MyInterface myInterface = Common.getMyInterface();
        Log.e("tokeb",lessonToken);
        myInterface.getLesson(lessonToken).enqueue(new Callback<ObjectDataModel<LessonDetails>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<LessonDetails>> call, Response<ObjectDataModel<LessonDetails>> response) {
                view.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        LessonDetails lesson = response.body().getData();
                        addLessonItems(lesson);
                    } else {
                        view.setError(response.body().getMessage());
                    }
                } else {
                    view.setError("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<LessonDetails>> call, Throwable t) {
                view.hideProgressBar();
                view.setError(t.getLocalizedMessage());
            }
        });
    }

    private void addLessonItems(LessonDetails lesson) {
        if (isStudent()) {
            if (!lesson.getTrainhome().isEmpty())
                lessonList.add(new LessonContent(R.string.train_home, lesson.getTrainhome()));

            if (!lesson.getScripts().isEmpty())
                lessonList.add(new LessonContent(R.string.scripts, lesson.getScripts()));
            if (!lesson.getAttachs().isEmpty()) {
                StringBuilder attachmentString = new StringBuilder();
                for (String attachment :
                        lesson.getFileAttachmentString()) {

                    lessonList.add(new LessonContent(R.string.attachs,attachment));

                    /*if (attachmentString.length() == 0) {
                        attachmentString = new StringBuilder(attachment);

                    } else {
                        attachmentString.append("\n\n\n").append(attachment);
                    }*/
                }
                //lessonList.add(new LessonContent(R.string.attachs, attachmentString.toString()));

            }
        } else {
            if (!lesson.getStratigy().isEmpty())
                lessonList.add(new LessonContent(R.string.stratigy, lesson.getStratigy()));

            if (!lesson.getAims().isEmpty())
                lessonList.add(new LessonContent(R.string.aims, lesson.getAims()));

            if (!lesson.getAids().isEmpty())
                lessonList.add(new LessonContent(R.string.aids, lesson.getAids()));

            if (!lesson.getIntro().isEmpty())
                lessonList.add(new LessonContent(R.string.intro, lesson.getIntro()));

            if (!lesson.getContent().isEmpty())
                lessonList.add(new LessonContent(R.string.content, lesson.getContent()));

            if (!lesson.getReviews().isEmpty())
                lessonList.add(new LessonContent(R.string.reviews, lesson.getReviews()));

            if (!lesson.getTrainhome().isEmpty())
                lessonList.add(new LessonContent(R.string.train_home, lesson.getTrainhome()));

            if (!lesson.getAttachs().isEmpty()) {
                StringBuilder attachmentString = new StringBuilder();
                for (String attachment :
                        lesson.getFileAttachmentString()) {

                    lessonList.add(new LessonContent(R.string.attachs,attachment));

                    /*if (attachmentString.length() == 0) {
                        attachmentString = new StringBuilder(attachment);

                    } else {
                        attachmentString.append("\n\n\n").append(attachment);
                    }*/
                }
                //lessonList.add(new LessonContent(R.string.attachs, attachmentString.toString()));

            }

            if (!lesson.getScripts().isEmpty())
                lessonList.add(new LessonContent(R.string.scripts, lesson.getScripts()));
        }

        setAdapter();
    }

    public void onBindItem(LessonDetailsHolder holder, int position) {
        LessonContent lessonContent = lessonList.get(position);
        holder.setTitle(lessonContent.getTitle());
        /*if (holder.getTitleText().getText().toString().equals("المرفقات") || holder.getTitleText().getText().toString().equals("Attachment")) {
           // holder.setContentAsAutolink();


        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse(lessonContent.getContent());
                holder.startIntent(uri);
            }
        });
        File file = new File(lessonContent.getContent());
        holder.setContent(file.getName());

        //holder.setContent(lessonContent.getContent());
    }

    public int getItemsCount() {
        return lessonList.size();
    }

    private void setAdapter() {
        view.setAdapter(new LessonDetailsAdapter(this));
    }

    private boolean isStudent() {
        User user = Customization.obtainUser(sharedPreferences);
        return user.getType().equals("S");
    }
}
