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
import com.mathieu.starchars.api.models.People;
import com.mathieu.starchars.ui.fragments.PeopleDetailFragment;
import com.mathieu.starchars.ui.fragments.PeopleListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class MainActivity extends BaseActivity implements PeopleListFragment.Callbacks {

    @Bind(R.id.main_toolbar) protected Toolbar toolbar;

    public boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (findViewById(R.id.frame_container_detail) != null) {
            mTwoPane = true;
        }

        startAboutActivityIfNeeded();
        setPeopleListFragment();

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(null);
        if (!mTwoPane) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(null);
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

    private void startAboutActivityIfNeeded() {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            prefs.edit().putBoolean("isFirstLaunch", false).apply();
            startAboutActivity();
        }
    }

    private void startAboutActivity() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    @Override
    public void onItemSelected(int position, People people) {
        if (mTwoPane) {
            setPeopleDetailFragment(people);
        } else {
            startPeopleDetailActivity(people);
        }
    }

    private void setPeopleListFragment() {
        PeopleListFragment fragment;
        if (getFragmentManager().findFragmentByTag(PeopleListFragment.TAG) != null)
            fragment = (PeopleListFragment) getFragmentManager().findFragmentByTag(PeopleListFragment.TAG);
        else {
            fragment = PeopleListFragment.newInstance();
        }
        fragment.setActivateOnItemClick(mTwoPane);

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container_list, fragment, PeopleListFragment.TAG)
                .commit();
    }

    private void setPeopleDetailFragment(People people) {
        Bundle args = new Bundle();
        args.putSerializable("people", people);
        Fragment fragment = PeopleDetailFragment.newInstance(args);

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container_detail, fragment, PeopleDetailFragment.TAG)
                .commit();
    }

    private void startPeopleDetailActivity(People people) {
        Intent intent;
        intent = new Intent(this, PeopleDetailActivity.class);
        intent.putExtra("people", people);
        startActivity(intent);
    }
}
