package com.mathieu.starchars.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.PictureProvider;
import com.mathieu.starchars.api.models.People;
import com.mathieu.starchars.utils.DeviceUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleDetailFragment extends Fragment {

    public final static String TAG = "PeopleDetailFragment";
    public final static String GOOGLE_BASE_URL = "http://www.google.com/";

    @Bind(R.id.detail_toolbar) protected Toolbar toolbar;

    @Bind(R.id.header_image) protected ImageView headerImage;
    @Bind(R.id.header_progress_bar) protected ProgressBar progressBar;
    @Bind(R.id.header_error_view) protected View headerErrorView;

    @Bind(R.id.people_description) protected TextView description;
    @Bind(R.id.people_full_name) protected TextView fullName;
    @Bind(R.id.people_birthday) protected TextView birthday;
    @Bind(R.id.people_homeworld) protected TextView homeworld;
    @Bind(R.id.people_gender) protected TextView gender;
    @Bind(R.id.people_mass) protected TextView mass;
    @Bind(R.id.people_height) protected TextView height;
    @Bind(R.id.button_more_on_google) protected Button buttonMoreGoogle;

    @Bind(R.id.people_episode_1) protected TextView episode1;
    @Bind(R.id.people_episode_2) protected TextView episode2;
    @Bind(R.id.people_episode_3) protected TextView episode3;
    @Bind(R.id.people_episode_4) protected TextView episode4;
    @Bind(R.id.people_episode_5) protected TextView episode5;
    @Bind(R.id.people_episode_6) protected TextView episode6;
    @Bind(R.id.people_episode_7) protected TextView episode7;

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
        View rootView = inflater.inflate(R.layout.fragment_people_detail, container, false);
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
        actionBar.setElevation(0);
        actionBar.setTitle(people.name);
        if (!DeviceUtils.isTablet(getActivity()) || !DeviceUtils.isLandscape(getActivity())) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_close);
        }
    }

    private void populateViews() {
        fullName.setText(people.name);
        birthday.setText(people.birthYear);
        description.setText(people.getDescription());
        gender.setText(people.getGender());
        mass.setText(people.getMass());
        height.setText(people.getHeight());
        buttonMoreGoogle.setText("More info on " + people.name);

        episode1.setBackgroundResource(people.isInFilm(1) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode2.setBackgroundResource(people.isInFilm(2) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode3.setBackgroundResource(people.isInFilm(3) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode4.setBackgroundResource(people.isInFilm(4) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode5.setBackgroundResource(people.isInFilm(5) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode6.setBackgroundResource(people.isInFilm(6) ? R.drawable.circle_red : R.drawable.circle_gray);
        episode7.setBackgroundResource(people.isInFilm(7) ? R.drawable.circle_red : R.drawable.circle_gray);
    }

    private void retrieveCharacterPicture() {

        PictureProvider pictureProvider = new PictureProvider();
        pictureProvider.addOnPictureRetrievedListener(new PictureProvider.OnPictureRetrievedListener() {

            @Override
            public void onPictureRetrieved(String pictureUrl) {
                if (getActivity() == null)
                    return;

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

    @OnClick(R.id.button_more_on_google)
    public void onButtonMoreClick() {
        Uri uri = Uri.parse(GOOGLE_BASE_URL + "search?q=" + "star+wars+" + people.name.replace(" ", "+"));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}
