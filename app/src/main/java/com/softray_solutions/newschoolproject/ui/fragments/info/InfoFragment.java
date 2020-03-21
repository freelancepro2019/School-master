package com.softray_solutions.newschoolproject.ui.fragments.info;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.activities.info.InfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InfoFragment extends Fragment implements InfoFragmentView {
    public static int detailPosition;
    @BindView(R.id.info_curve_iv)
    ImageView curveImageView;
    InfoFragmentPresenter presenter;
    InfoActivity context;
    Unbinder unbinder;

    public InfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initPresenter();

    }

    private void initPresenter() {
        presenter = new InfoFragmentPresenter(this);
        context = (InfoActivity) getActivity();
        presenter.checkLanguage(context);
    }


    private void bindView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private void unbind() {
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind();
    }

    @Override
    public void getDeviceLanguage(String lang) {
        if (lang.equals("ar")) {
            curveImageView.setRotationY(180);
        }
    }

    @Override
    public void openNewFragment(Fragment fragment, String Tag) {
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right
                , R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.info_container, fragment, Tag).addToBackStack(null).commit();
        if (detailPosition == 0) {
            context.getSupportActionBar().setTitle(context.getResources().getString(R.string.info_team));
        } else {
            context.getSupportActionBar().setTitle(context.getResources().getString(R.string.info_about_app));
        }
    }

    @OnClick(R.id.team_info)
    public void showTeamDetails() {
        detailPosition = 0;
        presenter.openDetailsFragment();
    }

    @OnClick(R.id.app_info)
    public void showAppDetails() {
        detailPosition = 1;
        presenter.openDetailsFragment();
    }
}
