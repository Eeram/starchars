package com.mathieu.starchars.ui;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mathieu.starchars.R;
import com.mathieu.starchars.ui.fragments.PeoplesFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startAboutActivityIfNeeded();
        initToolbar();
        initPeoplesFragment();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
    }

    private void initPeoplesFragment() {
        Fragment fragment;
        if (getFragmentManager().findFragmentByTag(PeoplesFragment.TAG) != null)
            fragment = getFragmentManager().findFragmentByTag(PeoplesFragment.TAG);
        else {
            fragment = PeoplesFragment.newInstance();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, PeoplesFragment.TAG)
                .commit();
    }

    private void startAboutActivityIfNeeded() {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            prefs.edit().putBoolean("isFirstLaunch", false).apply();
            startAboutActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                startAboutActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startAboutActivity() {
        startActivity(new Intent(this, AboutActivity.class));

    }
}
