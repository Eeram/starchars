package com.mathieu.starchars.ui;

import android.app.Fragment;
import android.os.Bundle;

import com.mathieu.starchars.R;
import com.mathieu.starchars.ui.fragments.PeopleDetailFragment;

import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initDetailPeopleFragment();
    }

    private void initDetailPeopleFragment() {
        Fragment fragment;
        if (getFragmentManager().findFragmentByTag(PeopleDetailFragment.TAG) != null)
            fragment = getFragmentManager().findFragmentByTag(PeopleDetailFragment.TAG);
        else {
            fragment = PeopleDetailFragment.newInstance(getIntent().getExtras());
        }


        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, PeopleDetailFragment.TAG)
                .commit();
    }
}
