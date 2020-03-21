package com.softray_solutions.newschoolproject.ui.fragments.revisions;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.LibraryAdapter;
import com.softray_solutions.newschoolproject.adapters.TeacherLibraryAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.RevisionLibrary;
import com.softray_solutions.newschoolproject.model.TeacherLibraryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryListPresenter {
    private LibraryListView libraryListView;
    private String language;

    LibraryListPresenter(LibraryListView view, String language) {
        this.libraryListView = view;
        this.language = language;
    }

    void getLibraryList(String studentID, String subjectID, String schoolID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getLibrary(studentID, subjectID, schoolID).enqueue(new Callback<ArrayDataModel<RevisionLibrary>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayDataModel<RevisionLibrary>> call, @NonNull Response<ArrayDataModel<RevisionLibrary>> response) {
                libraryListView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().size() > 0) {
                            libraryListView.setData(response.body().getData());
                        } else {
                            libraryListView.onEmptyList();
                        }
                    } else {
                        libraryListView.setError("Error");
                    }
                } else {
                    libraryListView.setError("null response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayDataModel<RevisionLibrary>> call, @NonNull Throwable t) {
                libraryListView.hideProgressBar();
                libraryListView.setError(t.getLocalizedMessage());
            }
        });
    }

    public void setTitles(LibraryAdapter.MainViewHolder holder, final int position, final List<RevisionLibrary> libraries) {
        if (libraries.get(position).getFileTitle().isEmpty()) {
            holder.textView.setText(R.string.revision_without_title);
        } else {
            holder.textView.setText(libraries.get(position).getFileTitle());
        }
        if (language.equals("en")) {
            holder.imageView.setScaleX(-1);
        }
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("teacherName", libraries.get(position).getContactName());
            bundle.putString("revisionName", libraries.get(position).getFileTitle());
            bundle.putString("fileUrl", libraries.get(position).getFileUrl());
            bundle.putString("youtubeLink", libraries.get(position).getLinkYoutube());
            bundle.putString("userType", "S");
            libraryListView.startRevisionLibraryInfoFragment(bundle);
        });
    }

    void getTeacherLibraryList(String rowlevelID, String subjectID, String userID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getTeacherLibrary(rowlevelID, subjectID, userID, "1").enqueue(new Callback<ArrayDataModel<TeacherLibraryModel>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<TeacherLibraryModel>> call, Response<ArrayDataModel<TeacherLibraryModel>> response) {
                libraryListView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        if (response.body().getData().isEmpty()) {
                            libraryListView.onEmptyList();
                        } else {
                            libraryListView.setTeacherData(response.body().getData());
                        }
                    } else {
                        libraryListView.onEmptyList();
                        libraryListView.setError(response.body().getMessage());
                    }
                } else {
                    libraryListView.setError("Null Response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<TeacherLibraryModel>> call, Throwable t) {
                libraryListView.hideProgressBar();
                libraryListView.setError(t.getLocalizedMessage());
            }
        });
    }

    public void setTeacherLibraries(TeacherLibraryAdapter.MainViewHolder holder, TeacherLibraryModel teacherLibraryModel) {
        if (teacherLibraryModel.getTitle().isEmpty()) {
            holder.textView.setText(R.string.revision_without_title);
        } else {
            holder.textView.setText(teacherLibraryModel.getTitle());
        }

        if (language.equals("en")) {
            holder.imageView.setScaleX(-1);
        }
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("revisionName", teacherLibraryModel.getTitle());
            bundle.putString("fileUrl", teacherLibraryModel.getFileUrl());
            bundle.putString("subjectName", teacherLibraryModel.getSubName());
            bundle.putString("youtubeLink", teacherLibraryModel.getLinkOfYoutube());
            bundle.putString("userType", "E");
            libraryListView.startRevisionLibraryInfoFragment(bundle);
        });
    }
}