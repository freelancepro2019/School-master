package com.softray_solutions.newschoolproject.ui.activities.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.DrawerAdapter;
import com.softray_solutions.newschoolproject.adapters.DrawerItem;
import com.softray_solutions.newschoolproject.adapters.SimpleItem;
import com.softray_solutions.newschoolproject.adapters.SpaceItem;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity implements BaseView, DrawerAdapter.OnItemSelectedListener {
    private static final int POS_LOGIN = 0;
    private static final int POS_LOGOUT = 1;
    private static final int POS_FEEDBACK = 2;
    private static final int POS_INFO = 3;
    public static CircleImageView circleImageView;
    public static TextView textView;
    public Button editButton;
    public SlidingRootNav slidingRootNav;
    public DrawerAdapter adapter;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SharedPreferences sharedPreferences;
    private SlidingRootNavBuilder slidingRootNavBuilder;
    private RelativeLayout relativeLayout;
    private BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initSharePreferences();
        initPresenter();
        setUpMyNavigationDrawer(savedInstanceState);
        presenter.getLanguage();
        presenter.checkLogin();
    }

    @Override
    public void changeLanguage(Configuration configuration) {
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    private void initPresenter() {
        AppPrefsHelper appPrefsHelper = new AppPrefsHelper(sharedPreferences);
        presenter = new BasePresenter(this, sharedPreferences, appPrefsHelper);
    }

    private void initSharePreferences() {
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
    }

    private void setUpMyNavigationDrawer(Bundle savedInstanceState) {
        slidingRootNavBuilder = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.slider_drawer);
    }

    @Override
    public void logout() {
        sharedPreferences.edit().remove("user").apply();
        sharedPreferences.edit().remove("isLogged").apply();
        Intent signOutIntent = new Intent(this, MainActivity.class);
        signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(signOutIntent);
        finish();
    }

    @Override
    public void setUserData(User userData) {
        relativeLayout = findViewById(R.id.user_info_RL);
        textView = findViewById(R.id.name_tv);
        circleImageView = findViewById(R.id.circle_image);
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        relativeLayout.setVisibility(View.VISIBLE);
        textView.setText(userData.getName());
        Glide.with(this).load(userData.getImg()).apply(new RequestOptions().placeholder(R.drawable.default_avatar)).into(circleImageView);
        setUpAdapter(POS_LOGOUT);
    }

    @Override
    public void removeUserDetails() {
        relativeLayout = findViewById(R.id.user_info_RL);
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        relativeLayout.setVisibility(View.INVISIBLE);
        setUpAdapter(POS_LOGIN);
    }

    private void setUpAdapter(int position) {
        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(position),
                createItemFor(POS_FEEDBACK),
                createItemFor(POS_INFO),
                new SpaceItem(48)));
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    @Override
    public void closeSlidNav() {
        slidingRootNav.closeMenu();
    }

    @Override
    public void startDrawerActivity(Class activity) {
        startActivity(new Intent(this, activity));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position], this)
                .withIconTint(color(R.color.dark_blue))
                .withTextTint(color(R.color.dark_blue))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void setSlider(SlideGravity slideGravity) {
        slidingRootNav = slidingRootNavBuilder.withGravity(slideGravity).inject();
    }

    @Override
    public void onItemSelected(int position) {
        presenter.openNavDrawerFragment(position, sharedPreferences);
    }

    @Override
    public void onBackPressed() {
        if (slidingRootNav.isMenuOpened()) {
            presenter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.unSelectAll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

}
