package com.softray_solutions.newschoolproject.ui.fragments.info;

import androidx.fragment.app.Fragment;

public interface InfoFragmentView {
    void getDeviceLanguage(String lang);

    void openNewFragment(Fragment fragment, String Tag);
}
