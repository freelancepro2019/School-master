package com.softray_solutions.newschoolproject.ui.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.news.AllFragment;
import com.softray_solutions.newschoolproject.ui.fragments.news.EventsFragment;
import com.softray_solutions.newschoolproject.ui.fragments.news.NewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements MainView {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private Unbinder unbinder;
    private MainPresenter mainPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        viewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        mainPresenter = new MainPresenter(this);

        mainPresenter.checkLanguage(getContext());
    }

    @Override
    public void rotateViewPager() {
        viewPager.setRotationY(180);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return AllFragment.newInstance();
            } else if (position == 1) {
                return NewsFragment.newInstance();
            } else {
                return EventsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.all);
            } else if (position == 1) {
                return getString(R.string.news);
            } else {
                return getString(R.string.events);
            }
        }
    }


}
