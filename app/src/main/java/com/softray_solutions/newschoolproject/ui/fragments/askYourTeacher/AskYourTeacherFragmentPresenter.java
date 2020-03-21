package com.softray_solutions.newschoolproject.ui.fragments.askYourTeacher;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.adapters.AskYourTeacherAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.AskYourTeacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskYourTeacherFragmentPresenter {
    private AskYourTeacherFragmentView askYourTeacherFragmentView;
    private String language;

    AskYourTeacherFragmentPresenter(AskYourTeacherFragmentView askYourTeacherFragmentView, String language) {
        this.askYourTeacherFragmentView = askYourTeacherFragmentView;
        this.language = language;
    }

    public void askYourTeacher(String teacherID, String subjectID, String schoolID, String rowLevelId) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.askYourTeacher(teacherID, subjectID, schoolID, rowLevelId).enqueue(new Callback<ArrayDataModel<List<AskYourTeacher>>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<List<AskYourTeacher>>> call, Response<ArrayDataModel<List<AskYourTeacher>>> response) {
                askYourTeacherFragmentView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().size() > 0) {
                            askYourTeacherFragmentView.setResponse(response.body().getData().get(0));
                        } else {
                            askYourTeacherFragmentView.setEmptyResponse();
                        }
                    } else {
                        askYourTeacherFragmentView.setError(response.body().getMessage());
                    }
                } else {
                    askYourTeacherFragmentView.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<List<AskYourTeacher>>> call, Throwable t) {
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
