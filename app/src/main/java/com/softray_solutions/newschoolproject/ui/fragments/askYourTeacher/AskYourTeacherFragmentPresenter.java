package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.adapters.AskYourTeacherAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.AskTeacherDataModel;
import com.softray_solutions.newschoolproject.model.AskYourTeacher;
import com.softray_solutions.newschoolproject.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskYourTeacherFragmentPresenter {
    private AskYourTeacherFragmentView askYourTeacherFragmentView;
    private String language;
    private SharedPreferences preferences;
    private AppPrefsHelper appPrefsHelper;

    private User user;

    AskYourTeacherFragmentPresenter(AskYourTeacherFragmentView askYourTeacherFragmentView, String language,SharedPreferences preferences) {
        this.askYourTeacherFragmentView = askYourTeacherFragmentView;
        this.language = language;
        this.preferences = preferences;
        appPrefsHelper = new AppPrefsHelper(preferences);
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        user = gson.fromJson(userString, User.class);
    }

    public void askYourTeacher(String teacherID, String subjectID, String schoolID, String rowLevelId) {

        Log.e("teacherID",teacherID);
        Log.e("subjectID",subjectID);
        Log.e("schoolID",schoolID);
        Log.e("rowLevelId",rowLevelId);
        Log.e("user_type",user.getType());
        String type = "";
        if (user.getType().equals("S"))
        {
            type = "S";
        }else if (user.getType().equals("E"))
        {
            type = "E";

        }
        MyInterface myInterface = Common.getMyInterface();
        myInterface.askYourTeacher(teacherID, subjectID, schoolID, rowLevelId,type).enqueue(new Callback<AskTeacherDataModel>() {
            @Override
            public void onResponse(Call<AskTeacherDataModel> call, Response<AskTeacherDataModel> response) {
                askYourTeacherFragmentView.hideProgressBar();

                if (response.isSuccessful())
                {
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            if (response.body().getUser_data().size() > 0) {
                                askYourTeacherFragmentView.setResponse(response.body().getUser_data());
                            } else {
                                askYourTeacherFragmentView.setEmptyResponse();
                            }
                        } else {
                            askYourTeacherFragmentView.setError(response.body().getMessage());
                        }
                    } else {
                        askYourTeacherFragmentView.setError("null response");
                    }
                }else
                    {
                        try {
                            Log.e("error",response.code()+"__"+response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

            }

            @Override
            public void onFailure(Call<AskTeacherDataModel> call, Throwable t) {
                Log.e("error2",t.getMessage()+"__");
                askYourTeacherFragmentView.hideProgressBar();
                askYourTeacherFragmentView.setError(t.getLocalizedMessage());
            }
        });
    }

    public void bindDataFromAdapter(AskYourTeacherAdapter.AskYourTeacherViewHolder holder, final List<AskYourTeacher> dataSet, final int position) {
        holder.titleTextView.setText(dataSet.get(position).getTitle());
        holder.answerTextView.setText(dataSet.get(position).getAnswer());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", dataSet.get(position).getTitle());
                bundle.putString("content", dataSet.get(position).getContent());
                bundle.putString("answer", dataSet.get(position).getAnswer());
                Fragment fragment = new SubAskYourTeacherFragment();
                fragment.setArguments(bundle);
                askYourTeacherFragmentView.startNextFragment(fragment, "SubAskYourTeacherFragment");
            }
        });

        if (language.equals("en")) {
            holder.arrow.setScaleX(-1);
        }

    }


}
