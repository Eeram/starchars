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
import com.mathieu.starchars.api.models.PeoplesResponse;
import com.mathieu.starchars.ui.adapters.PeoplesAdapter;
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

public class PeoplesFragment extends Fragment implements Callback<PeoplesResponse> {

    public final static String TAG = "PeoplesFragment";

    @Bind(R.id.progress_bar)
    protected ProgressBar progressBar;
    @Bind(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private PeoplesAdapter adapter;

    private ArrayList<People> peoples;
    private int currentPage = 1;

    public static PeoplesFragment newInstance() {
        PeoplesFragment fragment = new PeoplesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_peoples, container, false);
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
        adapter = new PeoplesAdapter(getActivity(), peoples, footer);

        recyclerView.setAdapter(adapter);
//        removeBlink();

        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager, currentPage) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getPeople(page);
            }
        });
    }

    private void getPeople(int page) {
        Call<PeoplesResponse> articleCallback = new ApiManager().getSwapiService().getPeople(page);
        articleCallback.enqueue(this);
    }

    @Override
    public void onResponse(Response<PeoplesResponse> response, Retrofit retrofit) {
        try {
            Log.e(TAG, ", PeoplesResponse.success = " + response.body().results.size());
//            if (peoples.isEmpty()) {
//                recyclerView.scheduleLayoutAnimation();
//            }

            for (int i = 0; i < response.body().results.size(); ++i) {
                peoples.add((People) response.body().results.get(i));
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Error : " + t.getMessage());
    }

}
