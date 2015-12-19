package com.mathieu.starchars.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.models.People;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class DetailPeopleFragment extends Fragment {

    public final static String TAG = "DetailPeopleFragment";

    @Bind(R.id.people_description)
    protected TextView description;
    @Bind(R.id.people_full_name)
    protected TextView fullName;
    @Bind(R.id.people_birthday)
    protected TextView birthday;
    @Bind(R.id.people_homeworld)
    protected TextView homeworld;

    private People people;

    public static DetailPeopleFragment newInstance(Bundle args) {
        DetailPeopleFragment fragment = new DetailPeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        people = (People) getArguments().getSerializable("people");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_people, container, false);
        ButterKnife.bind(this, rootView);

        fullName.setText(people.name);
        birthday.setText(people.birthYear);
        description.setText(people.getDescription());

        return rootView;
    }
}
