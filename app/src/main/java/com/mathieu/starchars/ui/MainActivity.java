package com.mathieu.starchars.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mathieu.starchars.R;
import com.mathieu.starchars.ui.fragments.PeopleFragment;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startWelcomeActivityIfNeeded();
        initPeopleFragment();
    }

    private void startWelcomeActivityIfNeeded() {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            prefs.edit().putBoolean("isFirstLaunch", false).apply();
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }
    private void initPeopleFragment() {
        Fragment fragment;
        if (getFragmentManager().findFragmentByTag(PeopleFragment.TAG) != null)
            fragment = getFragmentManager().findFragmentByTag(PeopleFragment.TAG);
        else {
            fragment = PeopleFragment.newInstance();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, PeopleFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
