package com.softray_solutions.newschoolproject.ui.activities.revisions;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

public interface RevisionsActivityView {

    void startFragment(Fragment fragment, String tag);

    void changeLanguage(Configuration configuration);
}
