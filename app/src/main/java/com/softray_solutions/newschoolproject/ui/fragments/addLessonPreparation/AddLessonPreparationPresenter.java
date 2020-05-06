package com.softray_solutions.newschoolproject.ui.fragments.addLessonPreparation;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.model.AddLessonPreparationSubject;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.ObjectDataModel;
import com.softray_solutions.newschoolproject.model.Skill;
import com.softray_solutions.newschoolproject.model.User;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AddLessonPreparationPresenter {
    private AddLessonPreparationView view;
    private List<AddLessonPreparationSubject> subjects;
    private List<Skill> skills;
    private AddLessonPreparationSubject selectedSubject;
    private Skill selectedSkill;
    private String selectedSubjectID, selectedSkillID, filePath, teacherID;
    private SharedPreferences preferences;

    public AddLessonPreparationPresenter(AddLessonPreparationView view, SharedPreferences preferences) {
        this.view = view;
        this.preferences = preferences;
    }

    void getSubjects(String language) {
        getUserData();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getSubjects(teacherID, language).enqueue(new Callback<ArrayDataModel<AddLessonPreparationSubject>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<AddLessonPreparationSubject>> call, Response<ArrayDataModel<AddLessonPreparationSubject>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        subjects = response.body().getData();
                        view.setSubjects(subjects);
                    } else {
                        view.showToast(response.body().getMessage());
                    }
                } else {
                    view.showToast("server error!");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<AddLessonPreparationSubject>> call, Throwable t) {
                view.showToast(t.getLocalizedMessage());
            }
        });
    }

    void getSkills(int position) {
        selectedSubject = subjects.get(position);
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getSkills(selectedSubject.getSubjectID(), selectedSubject.getRowLevelID()).enqueue(new Callback<ArrayDataModel<Skill>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Skill>> call, Response<ArrayDataModel<Skill>> response) {
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        skills = response.body().getData();
                        view.setSkills(skills);
                    } else {
                        view.showToast(response.body().getMessage());
                    }
                } else {
                    view.showToast("server error!");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Skill>> call, Throwable t) {
                view.showToast(t.getLocalizedMessage());
            }
        });
    }

    void getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
        Calendar c = Calendar.getInstance();
        String formattedDate = df.format(c.getTime());
        view.setDate(formattedDate);
    }

    void pickDate(int year, int month, int day) {
        String formattedDay, formattedMonth;
        formattedMonth = "" + (month + 1);
        formattedDay = "" + day;
        if (month + 1 < 10) {
            formattedMonth = "0" + (month + 1);
        }
        if (day < 10) {
            formattedDay = "0" + day;
        }
        String formattedDate = year + "-" + formattedMonth + "-" + formattedDay;
        view.setDate(formattedDate);
    }

    void uploadFile(Context context, final List<File> fileList, final String lessonTitle, final String dayDate, final String lessonStrategy, final String lessonGoals,
                    final String lessonMeans, final String lessonPreface, final String lessonEvaluation, final String lessonHomeworks,final String Scripts) {
        view.showDialog();
        RequestBody requestBody;
        MultipartBody.Part part;
        MyInterface myInterface = Common.getMyInterface();
        List<MultipartBody.Part> files = new ArrayList<>();
        for (File file :
                fileList) {
            requestBody = RequestBody.create(getMediaType(file.toURI(), context), file);
            part = MultipartBody.Part.createFormData("filesupload[]", file.getName(), requestBody);
            files.add(part);
        }
        myInterface.uploadAttachment(files).enqueue(new Callback<ObjectDataModel<String>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<String>> call, Response<ObjectDataModel<String>> response) {
                if (response.body() != null && response.body().getSuccess() == 1 && !response.body().getData().isEmpty()) {
                    filePath = response.body().getData();

                    uploadLesson(lessonTitle, dayDate, lessonStrategy, lessonGoals, lessonMeans
                            , lessonPreface, lessonEvaluation, lessonHomeworks,Scripts);
                } else {
                    view.showToast("Upload failed, try again later.");
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<String>> call, Throwable t) {
                view.dismissDialog();
                view.showToast(t.getLocalizedMessage());
            }
        });
    }

    void getSelectedSkill(int skillPosition) {
        selectedSkill = skills.get(skillPosition);
        selectedSubjectID = selectedSubject.getSubjectID() + "|" + selectedSubject.getRowLevelID();
        selectedSkillID = selectedSkill.getSkillId();
    }

    String extractStringFromEditText(EditText target) {
        return target.getText().toString().trim();
    }

    void uploadLesson(String lessonTitle, String dayDate, String lessonStrategy, String lessonGoals,
                      String lessonMeans, String lessonPreface, String lessonEvaluation, String lessonHomeworks,String Scripts) {
        view.showDialog();
        MyInterface myInterface = Common.getMyInterface();
        myInterface.uploadLesson(teacherID, selectedSubjectID, selectedSkillID, lessonTitle, dayDate, lessonStrategy, lessonGoals
                , lessonMeans, lessonPreface, lessonEvaluation, lessonHomeworks, filePath,Scripts).enqueue(new Callback<ObjectDataModel<String>>() {
            @Override
            public void onResponse(Call<ObjectDataModel<String>> call, Response<ObjectDataModel<String>> response) {
                view.dismissDialog();
                if (response.body() != null) {
                    view.showToast(response.body().getMessage());
                    view.clearFields();
                }
            }

            @Override
            public void onFailure(Call<ObjectDataModel<String>> call, Throwable t) {
                view.dismissDialog();
                view.showToast(t.getLocalizedMessage());

            }
        });
    }

    private void getUserData() {
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        User user = gson.fromJson(userString, User.class);
        teacherID = user.getId();
    }

    void checkPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission();
        }
    }

    private okhttp3.MediaType getMediaType(URI uri1, Context context) {
        Uri uri = Uri.parse(uri1.toString());
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return okhttp3.MediaType.parse(mimeType);
    }
}