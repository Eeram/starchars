package com.mathieu.starchars.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mathieu.starchars.api.SwapiService;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private final static String TAG = "RecylerViewEndlessScrollListener";

    private boolean loading = true;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int visibleThreshold = 5;
    private int previousTotal = 0;

    private LinearLayoutManager layoutManager;
    private int currentPage = 1;

    public abstract void onLoadMore(int page, int totalItemsCount);

    public EndlessScrollListener(LinearLayoutManager layoutManager, int currentPage) {
        this.layoutManager = layoutManager;
        this.currentPage = currentPage;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = (layoutManager).findFirstVisibleItemPosition();

        if (totalItemCount < previousTotal) {
            int startingPageIndex = 1;
            this.currentPage = startingPageIndex;
            this.previousTotal = totalItemCount;

            if (totalItemCount == 0)
                this.loading = true;
        }

        if (loading && (totalItemCount > previousTotal)) {
            loading = false;
            previousTotal = totalItemCount;
            currentPage++;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && totalItemCount >= SwapiService.ITEMS_PER_PAGE) {
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }

    }
}
