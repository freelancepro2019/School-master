package com.softray_solutions.newschoolproject.ui.activities.onBoarding;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;

import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsHandler;
import com.softray_solutions.newschoolproject.data.sharedPref.PrefsInteractor;

import java.util.Locale;

public class OnBoardingPresenter implements Parcelable {

    public static final Creator<OnBoardingPresenter> CREATOR = new Creator<OnBoardingPresenter>() {
        @Override
        public OnBoardingPresenter createFromParcel(Parcel in) {
            return new OnBoardingPresenter(in);
        }

        @Override
        public OnBoardingPresenter[] newArray(int size) {
            return new OnBoardingPresenter[size];
        }
    };
    private OnBoardingView view;
    private PrefsInteractor interactor;

    public OnBoardingPresenter(OnBoardingView view, AppPrefsHelper appPrefsHelper) {
        this.view = view;
        this.interactor = new PrefsInteractor(appPrefsHelper);
    }

    private OnBoardingPresenter(Parcel in) {
    }

    void lastFragment(int fragmentIndex) {
        if (fragmentIndex == 3) {
            view.startCodeActivity();
        } else {
            view.setNextFragment();
        }
    }

    int getItem(int i, int currentItem) {
        return currentItem + i;
    }

    void checkLanguage(Context context) {
        Locale current = context.getResources().getConfiguration().locale;
        String language = current.getLanguage();

        if (language.equals("ar")) {
            view.rotateViewPager();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public void getCurrentLanguage() {
        interactor.getSelectedLanguage(new PrefsHandler<String>() {
            @Override
            public void dataSaved(String result) {
                String currentLanguage;
                if (result.equals("not selected")) {
                    currentLanguage = Locale.getDefault().getLanguage();
                } else {
                    currentLanguage = result;
                }
                Locale locale = new Locale(currentLanguage);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                view.changeLanguage(configuration);
            }
        });
    }
}
