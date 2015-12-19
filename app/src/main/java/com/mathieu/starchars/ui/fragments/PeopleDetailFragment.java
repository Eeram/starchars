package com.mathieu.starchars.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.PictureProvider;
import com.mathieu.starchars.api.models.People;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleDetailFragment extends Fragment {

    public final static String TAG = "PeopleDetailFragment";

    @Bind(R.id.header_image)
    protected ImageView headerImage;
    @Bind(R.id.header_progress_bar)
    protected ProgressBar progressBar;
    @Bind(R.id.header_error_view)
    protected View headerErrorView;

    @Bind(R.id.people_description)
    protected TextView description;
    @Bind(R.id.people_full_name)
    protected TextView fullName;
    @Bind(R.id.people_birthday)
    protected TextView birthday;
    @Bind(R.id.people_homeworld)
    protected TextView homeworld;
    @Bind(R.id.people_gender)
    protected TextView gender;

    @Bind(R.id.detail_toolbar)
    protected Toolbar toolbar;
    private People people;

    public static PeopleDetailFragment newInstance(Bundle args) {
        PeopleDetailFragment fragment = new PeopleDetailFragment();
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

        retrieveCharacterPicture();

        initToolbar();

        populateViews();

        return rootView;
    }

    private void initToolbar() {
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_close);
        actionBar.setElevation(0);
        actionBar.setTitle(people.name);
    }

    private void populateViews() {
        fullName.setText(people.name);
        birthday.setText(people.birthYear);
        description.setText(people.getDescription());
        gender.setText(people.getGender());
    }

    private void retrieveCharacterPicture() {

        PictureProvider pictureProvider = new PictureProvider();
        pictureProvider.addOnPictureRetrievedListener(new PictureProvider.OnPictureRetrievedListener() {

            @Override
            public void onPictureRetrieved(String pictureUrl) {
                Picasso.with(getActivity()).load(pictureUrl).into(headerImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                        headerErrorView.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                headerErrorView.setVisibility(View.VISIBLE);
            }
        });
        pictureProvider.retrieveCharacterPicture(people.getSlug());
    }
}
