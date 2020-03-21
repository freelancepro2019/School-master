package com.softray_solutions.newschoolproject.ui.activities.onBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.code.CodeActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OnBoardingActivity extends AppCompatActivity implements OnBoardingView {
    @BindView(R.id.on_boarding_view_pager)
    ViewPager viewPager;
    @BindView(R.id.on_boarding_tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;
    SharedPreferences.Editor editor;
    private OnBoardingPresenter onBoardingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        editor = getSharedPreferences("OnBoarding", MODE_PRIVATE).edit();
        editor.putBoolean("FirstRun", false).apply();
        unbinder = ButterKnife.bind(this);
        initPresenter();
        onBoardingPresenter.checkLanguage(this);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), onBoardingPresenter));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        onBoardingPresenter = new OnBoardingPresenter(this, new AppPrefsHelper(userPref));
        onBoardingPresenter.getCurrentLanguage();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    public void skip(View view) {
        startActivity(new Intent(this, CodeActivity.class));
        finish();
    }


    @Override
    public void startCodeActivity() {
        startActivity(new Intent(this, CodeActivity.class));
        finish();
    }

    @Override
    public void setNextFragment() {
        viewPager.setCurrentItem(onBoardingPresenter.getItem(+1, viewPager.getCurrentItem()));
    }

    @Override
    public void rotateViewPager() {
        Log.i("rotate", "viewPager");
        viewPager.setRotationY(180);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        @BindView(R.id.on_boarding_image)
        ImageView backgroundImage;
        @BindView(R.id.on_boarding_icon)
        ImageView iconImage;
        @BindView(R.id.on_boarding_text)
        TextView onBoardingText;
        @BindView(R.id.next_image)
        ImageView nextImage;
        @BindView(R.id.curve_image)
        ImageView curveImage;
        private OnBoardingPresenter onBoardingPresenter;
        private Unbinder unbinder;
        private View rootView;

        public PlaceholderFragment() {

        }

        public static PlaceholderFragment newInstance(int sectionNumber, OnBoardingPresenter onBoardingPresenter) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putParcelable("presenter", onBoardingPresenter);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            assert getArguments() != null;

            final int fragmentIndex = getArguments().getInt(ARG_SECTION_NUMBER);
            rootView = inflater.inflate(R.layout.item_on_boarding, container, false);

            unbinder = ButterKnife.bind(this, rootView);

            onBoardingPresenter = getArguments().getParcelable("presenter");

            checkLanguage();

            nextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBoardingPresenter.lastFragment(fragmentIndex);
                }
            });


            if (fragmentIndex == 1) {
                backgroundImage.setImageResource(R.drawable.on_boarding_photo_1);
                iconImage.setImageResource(R.drawable.on_boarding_icon_1);
                onBoardingText.setText(getString(R.string.if_teacher));
                nextImage.setImageResource(R.drawable.play);
            } else if (fragmentIndex == 2) {
                backgroundImage.setImageResource(R.drawable.on_boarding_photo_2);
                iconImage.setImageResource(R.drawable.on_boarding_icon_2);
                onBoardingText.setText(getString(R.string.if_student));
                nextImage.setImageResource(R.drawable.play);
            } else {
                backgroundImage.setImageResource(R.drawable.on_boarding_photo_3);
                iconImage.setImageResource(R.drawable.on_boarding_icon_3);
                onBoardingText.setText(getString(R.string.if_parent));
                nextImage.setImageResource(R.drawable.stop);
            }
            return rootView;
        }

        private void checkLanguage() {
            Locale current = getResources().getConfiguration().locale;
            String language = current.getLanguage();

            if (language.equals("ar")) {
                rootView.setRotationY(180);
                curveImage.setScaleX(1);
                nextImage.setScaleX(1);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            unbinder.unbind();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private OnBoardingPresenter onBoardingPresenter;

        SectionsPagerAdapter(FragmentManager fm, OnBoardingPresenter onBoardingPresenter) {
            super(fm);
            this.onBoardingPresenter = onBoardingPresenter;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1, onBoardingPresenter);
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
