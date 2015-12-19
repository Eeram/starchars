package com.mathieu.starchars.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mathieu.starchars.R;
import com.mathieu.starchars.ui.fragments.DetailPeopleFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class DetailPeopleActivity extends BaseActivity {

    @Bind(R.id.detail_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initToolbar();
        initDetailPeopleFragment();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_close);
    }

    private void initDetailPeopleFragment() {
        Fragment fragment;
        if (getFragmentManager().findFragmentByTag(DetailPeopleFragment.TAG) != null)
            fragment = getFragmentManager().findFragmentByTag(DetailPeopleFragment.TAG);
        else {
            fragment = DetailPeopleFragment.newInstance(getIntent().getExtras());
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, DetailPeopleFragment.TAG)
                .commit();
    }
}
