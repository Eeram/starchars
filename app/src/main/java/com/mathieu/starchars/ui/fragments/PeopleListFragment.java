package com.mathieu.starchars.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.ApiManager;
import com.mathieu.starchars.api.models.People;
import com.mathieu.starchars.api.models.PeopleListResponse;
import com.mathieu.starchars.ui.adapters.FooterBaseAdapter;
import com.mathieu.starchars.ui.adapters.PeopleListAdapter;
import com.mathieu.starchars.utils.EndlessScrollListener;
import com.mathieu.starchars.utils.ItemTouchListenerAdapter;
import com.mathieu.starchars.utils.NetworkErrorManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.mathieu.starchars.ui.adapters.FooterBaseAdapter.CHOICE_MODE_NONE;
import static com.mathieu.starchars.ui.adapters.FooterBaseAdapter.CHOICE_MODE_SINGLE;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleListFragment extends Fragment implements
        Callback<PeopleListResponse>,
        ItemTouchListenerAdapter.RecyclerViewOnItemClickListener,
        NetworkErrorManager.OnNetworkErrorClickListener {

    public final static String TAG = "PeopleListFragment";
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String PEOPLE_LIST = "peopleList";
    private static final String CURRENT_PAGE = "currentPage";

    private Callbacks callbacks = itemSelectedCallbacks;
    private int activatedPosition = 0;
    private int selectionMode = FooterBaseAdapter.CHOICE_MODE_NONE;

    public interface Callbacks {
        void onItemSelected(int position, People people);
    }

    private static Callbacks itemSelectedCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int position, People people) {
        }
    };

    @Bind(R.id.progress_bar) protected ProgressBar progressBar;
    @Bind(R.id.recycler_view) protected RecyclerView recyclerView;
    @Bind(R.id.container) protected FrameLayout container;

    private NetworkErrorManager networkErrorManager;
    private LinearLayoutManager layoutManager;
    private PeopleListAdapter adapter;
    private ArrayList<People> peopleList;
    private int currentPage = 1;
    private boolean isTwoPane = false;

    public static PeopleListFragment newInstance(boolean isTwoPane) {
        PeopleListFragment fragment = new PeopleListFragment();
        Bundle args = new Bundle();
        args.putBoolean("isTwoPane", isTwoPane);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people_list, container, false);
        ButterKnife.bind(this, rootView);

        networkErrorManager = new NetworkErrorManager();
        isTwoPane = getArguments().getBoolean("isTwoPane", false);

        initRecylerView();

        if (savedInstanceState != null && savedInstanceState.containsKey(PEOPLE_LIST)) {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE, 0);
            populateList((ArrayList<People>) savedInstanceState.getSerializable(PEOPLE_LIST));
        } else {
            getPeopleList(currentPage);
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            activatedPosition = savedInstanceState.getInt(STATE_ACTIVATED_POSITION);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        callbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = itemSelectedCallbacks;
    }

    @Override
    public void onItemClick(RecyclerView parent, View clickedView, int position) {
        Log.d("", "onItemClick()");
        setActivatedPosition(position);
    }

    @Override
    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
        outState.putSerializable(PEOPLE_LIST, peopleList);
        outState.putInt(CURRENT_PAGE, currentPage);
        Log.e(TAG, "peoples out = " + peopleList.size());
    }

    private void initRecylerView() {
        peopleList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_loading_footer, recyclerView, false);
        adapter = new PeopleListAdapter(getActivity(), peopleList, footer);
        adapter.setSelectionMode(selectionMode);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager, currentPage) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getPeopleList(page);
            }
        });

        recyclerView.addOnItemTouchListener(new ItemTouchListenerAdapter(recyclerView, this));
        setActivateOnItemClick(isTwoPane);
    }

    private void getPeopleList(int page) {
        if (peopleList.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        Call<PeopleListResponse> articleCallback = new ApiManager().getSwapiService().getPeople(page);
        articleCallback.enqueue(this);
    }

    @Override
    public void onResponse(Response<PeopleListResponse> response, Retrofit retrofit) {
        if (response.body() != null) {
            populateList(response.body().results);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (peopleList.isEmpty()) {
            networkErrorManager.displayNetworkError(container, NetworkErrorManager.MODE_FULL, PeopleListFragment.this);
        } else {
            networkErrorManager.displayNetworkError(container, NetworkErrorManager.MODE_SNACK, PeopleListFragment.this);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void populateList(List<People> peopleList) {
        boolean activateFirstItem = isTwoPane && this.peopleList.isEmpty();

        for (int i = 0; i < peopleList.size(); ++i) {
            this.peopleList.add(peopleList.get(i));
        }
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

        if (isTwoPane && activateFirstItem)
            setActivatedPosition(activatedPosition);
    }

    @Override
    public void onNetworkErrorClickListener() {
        networkErrorManager.hideNetworkError(container);
        getPeopleList(currentPage);
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        selectionMode = activateOnItemClick ? CHOICE_MODE_SINGLE : CHOICE_MODE_NONE;
    }

    private void setActivatedPosition(int position) {
        activatedPosition = position;
        adapter.setSelected(position);
        callbacks.onItemSelected(position, peopleList.get(position));
    }
}
