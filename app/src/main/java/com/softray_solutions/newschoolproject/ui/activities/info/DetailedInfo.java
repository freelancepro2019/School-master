package com.softray_solutions.newschoolproject.ui.activities.info;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.info.InfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DetailedInfo extends Fragment {

    @BindView(R.id.detailed_info_imageView)
    ImageView imageView;
    @BindView(R.id.detailed_info_textView)
    TextView textView;
    Unbinder unbinder;
    private View rootView;

    public DetailedInfo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detailed_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InfoFragment.detailPosition == 1) {
            imageView.setImageResource(R.drawable.ic_info_app);
            textView.setText(getString(R.string.about_app));
        } else {
            imageView.setImageResource(R.drawable.ic_info_team);
            textView.setText(getString(R.string.about_team));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
