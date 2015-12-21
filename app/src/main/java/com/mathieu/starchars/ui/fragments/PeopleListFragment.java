package com.mathieu.starchars.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.ApiManager;
import com.mathieu.starchars.api.models.People;
import com.mathieu.starchars.api.models.PeoplesResponse;
import com.mathieu.starchars.ui.MainActivity;
import com.mathieu.starchars.ui.adapters.FooterBaseAdapter;
import com.mathieu.starchars.ui.adapters.PeopleListAdapter;
import com.mathieu.starchars.ui.views.StarWarsRecyclerView;
import com.mathieu.starchars.utils.EndlessScrollListener;
import com.mathieu.starchars.utils.ItemTouchListenerAdapter;

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

public class PeopleListFragment extends Fragment implements Callback<PeoplesResponse>, ItemTouchListenerAdapter.RecyclerViewOnItemClickListener {

    public final static String TAG = "PeopleListFragment";
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String PEOPLE_LIST = "peopleList";
    private static final String CURRENT_PAGE = "currentPage";

    private Callbacks mCallbacks = itemSelectedCallbacks;
    private int mActivatedPosition = 0;
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
    @Bind(R.id.recycler_view) protected StarWarsRecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private PeopleListAdapter adapter;
    private ArrayList<People> peopleList;
    private int currentPage = 1;

    public static PeopleListFragment newInstance() {
        PeopleListFragment fragment = new PeopleListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people_list, container, false);
        ButterKnife.bind(this, rootView);

        initRecylerView();

        if (savedInstanceState != null && savedInstanceState.containsKey(PEOPLE_LIST)) {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE, 0);
            handleSuccess((ArrayList<People>) savedInstanceState.getSerializable(PEOPLE_LIST));
            Log.e(TAG, "peoples from saved = " + peopleList.size());
        } else {
            getPeople(currentPage);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = itemSelectedCallbacks;
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
        outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
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
                getPeople(page);
            }
        });

        recyclerView.addOnItemTouchListener(new ItemTouchListenerAdapter(recyclerView, this));
    }

    private void getPeople(int page) {
        Call<PeoplesResponse> articleCallback = new ApiManager().getSwapiService().getPeople(page);
        articleCallback.enqueue(this);
    }

    @Override
    public void onResponse(Response<PeoplesResponse> response, Retrofit retrofit) {
        try {
            handleSuccess(response.body().results);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSuccess(List<People> peopleList) {
        boolean activateFirstItem = ((MainActivity) getActivity()).mTwoPane && this.peopleList.isEmpty();
        for (int i = 0; i < peopleList.size(); ++i) {
            this.peopleList.add(peopleList.get(i));
        }
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        if (activateFirstItem)
            setActivatedPosition(0);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Error : " + t.getMessage());
    }


    public void setActivateOnItemClick(boolean activateOnItemClick) {
        selectionMode = activateOnItemClick ? CHOICE_MODE_SINGLE : CHOICE_MODE_NONE;
    }

    private void setActivatedPosition(int position) {
        adapter.setSelected(position);
        mActivatedPosition = position;
        mCallbacks.onItemSelected(position, peopleList.get(position));
    }
}
