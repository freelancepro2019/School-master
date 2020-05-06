package com.softray_solutions.newschoolproject.ui.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.ui.activities.base.BaseActivity;
import com.softray_solutions.newschoolproject.ui.fragments.main.MainFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    QBadgeView badgeView;
    private boolean backPressed = false;
    private int currentItemID = R.id.bottom_nav_home;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        bindView();
        initMainPresenter();
        initToolbar();

        mainPresenter.checkLogin();
        mainPresenter.getNotifications();
        openNewFragment(true, new MainFragment(), "main");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (currentItemID != item.getItemId() && !backPressed) {
                    mainPresenter.openFragment(item);
                } else if (backPressed) {
                    mainPresenter.setTitle(item);
                }
                backPressed = false;
                return true;
            }
        });
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_settings);
                closeSlidNav();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.main_activity_title);
        toolbar.setNavigationIcon(R.drawable.ic_menu_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingRootNav.isMenuOpened()) {
                    slidingRootNav.closeMenu();
                } else {
                    slidingRootNav.openMenu();
                }
            }
        });
    }

    private void initMainPresenter() {
        SharedPreferences userPref = getSharedPreferences("userData", MODE_PRIVATE);
        mainPresenter = new MainPresenter(this, this, new AppPrefsHelper(userPref));
        mainPresenter.getCurrentLanguage();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void hideBottomBar() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void showBottomBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNotificationBadge(int notificationsCount) {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        badgeView = new QBadgeView(this);
        badgeView.setBadgeBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        badgeView.bindTarget(v).setBadgeNumber(notificationsCount);
    }

    @Override
    public void openNewFragment(boolean firstFragment, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.container, fragment, tag);
        if (!firstFragment) {
            fragmentTransaction.addToBackStack(null);
        }
        if (tag.equals("notifications")) {
            if (badgeView != null) {
                badgeView.hide(false);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void setCurrentItemID(int id) {
        currentItemID = id;
    }

    @Override
    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(getString(title));
    }

    private String getCurrentFragmentName() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        return fragment.getTag();
    }

    @Override
    public void setCurrentFragment() {
        String fragmentName = getCurrentFragmentName();
        backPressed = true;
        switch (fragmentName) {
            case "main":
                currentItemID = R.id.bottom_nav_home;
                break;
           /* case "notifications":
                currentItemID = R.id.bottom_nav_notifications;
                break;*/
            case "tools":
                currentItemID = R.id.bottom_nav_tools;
                break;
            case "messages":
                currentItemID = R.id.bottom_nav_messages;
                break;
            case "settings":
                currentItemID = R.id.bottom_nav_settings;
                break;
            default:
                currentItemID = R.id.bottom_nav_home;
        }
        bottomNavigationView.setSelectedItemId(currentItemID);
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        for (Fragment fragment:fragmentList)
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragmentList = manager.getFragments();
        for (Fragment fragment:fragmentList)
        {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void backPressed() {
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        mainPresenter.backPressed();
    }
}
