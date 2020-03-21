package com.softray_solutions.newschoolproject.ui.activities.sons;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;

import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.adapters.SonsAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.network.service.MyInterface;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;
import com.softray_solutions.newschoolproject.model.ArrayDataModel;
import com.softray_solutions.newschoolproject.model.Son;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SonsPresenter {
    private SonsView sonsView;
    private SharedPreferences preferences;
    private String language;
    private int iconPosition;
    private PrefsInteractor interactor;

    SonsPresenter(SonsView sonsView, SharedPreferences sharedPreferences, int iconPosition, AppPrefsHelper appPrefsHelper) {
        this.sonsView = sonsView;
        this.preferences = sharedPreferences;
        this.iconPosition = iconPosition;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    private void getSons(String fatherID) {
        MyInterface myInterface = Common.getMyInterface();
        myInterface.getMySons(fatherID).enqueue(new Callback<ArrayDataModel<Son>>() {
            @Override
            public void onResponse(Call<ArrayDataModel<Son>> call, Response<ArrayDataModel<Son>> response) {
                sonsView.hideProgressBar();
                if (response.body() != null) {
                    if (response.body().getSuccess() == 1) {
                        sonsView.setSons(response.body().getData());
                    } else {
                        sonsView.setError(response.body().getMessage());
                    }
                } else {
                    sonsView.setError("null response");
                }
            }

            @Override
            public void onFailure(Call<ArrayDataModel<Son>> call, Throwable t) {
                sonsView.hideProgressBar();
                sonsView.setError(t.getLocalizedMessage());
            }
        });
    }

    void getExtras() {
        if (getSavedID() != null) {
            getSons(getSavedID());
        }
    }

    private String getSavedID() {
        return Customization.obtainUser(preferences).getId();
    }

    public void setupHolder(final SonsAdapter.MainViewHolder holder, final int position, final List<Son> sonsList) {
        holder.textView.setText(sonsList.get(position).getName());
        if (language.equals("en")) {
            holder.imageView.setScaleX(-1);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (iconPosition) {
                    case 0:
                        sonsView.StartClassSchedule(sonsList.get(position).getID());
                        break;
//                    case 1:
//                        sonsView.startStudentEvaluationActivity();
//                        break;
                    case 3:
                        sonsView.startHomeworkActivity(sonsList.get(position).getID(), sonsList.get(position).getSchoolId());
                        break;
                    case 4:
                        sonsView.startWeeklyPlanActivity(sonsList.get(position).getID());
                }
            }
        });
    }

    public void getCurrentLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                if (result.equals("not selected")) {
                    language = Locale.getDefault().getLanguage();
                } else {
                    language = result;
                }
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                sonsView.changeLanguage(configuration);
            }
        });
    }
}
