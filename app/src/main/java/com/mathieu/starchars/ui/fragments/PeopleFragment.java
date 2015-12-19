package com.mathieu.starchars.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.ApiManager;
import com.mathieu.starchars.api.models.People;
import com.mathieu.starchars.api.models.PeopleResponse;
import com.mathieu.starchars.ui.adapters.PeopleAdapter;
import com.mathieu.starchars.utils.EndlessScrollListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleFragment extends Fragment implements Callback<PeopleResponse> {

    public final static String TAG = "PeopleFragment";

    @Bind(R.id.progress_bar)
    protected ProgressBar progressBar;
    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private PeopleAdapter adapter;

    private ArrayList<People> peoples;
    private int currentPage = 1;

    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this, rootView);

        initRecylerView();
        getPeople(currentPage);
        return rootView;
    }


    private void initRecylerView() {
        peoples = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_loading_footer, recyclerView, false);
        adapter = new PeopleAdapter(getActivity(), peoples, footer);

        recyclerView.setAdapter(adapter);
//        removeBlink();

        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager, currentPage) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Toast.makeText(getActivity(), "Loading more : " + page, Toast.LENGTH_SHORT).show();
                getPeople(page);
            }
        });
    }

    private void getPeople(int page) {
        Call<PeopleResponse> articleCallback = new ApiManager().getSwapiService().getPeople(page);
        articleCallback.enqueue(this);
    }

    @Override
    public void onResponse(Response<PeopleResponse> response, Retrofit retrofit) {
        try {
            Log.e(TAG, ", PeopleResponse.success = " + response.body().results.size());

            if (peoples.isEmpty()) {
                recyclerView.scheduleLayoutAnimation();
            }

            for (People people : response.body().results) {
                peoples.add(people);
            }
            adapter.notifyDataSetChanged();

            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Error : " + t.getMessage());
    }

}
