package com.mathieu.starchars.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mathieu.starchars.api.SwapiService;

import java.util.ArrayList;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public abstract class FooterBaseAdapter<V> extends RecyclerView.Adapter
        implements View.OnClickListener {

    public final static int ITEM_VIEW_TYPE_NORMAL = 100;
    public final static int ITEM_VIEW_TYPE_FOOTER = 101;

    protected ArrayList items = new ArrayList<>();
    protected View footer;

    public FooterBaseAdapter(ArrayList items, View footer) {
        this.items = items;
        this.footer = footer;
    }

    public boolean isFooter(int position) {
        return position == items.size();
    }

    /**
     * @return -1 when list is empty (so footer isn't displayed)
     * list size when there are no more items to load
     * list size + 1 (for footer) when there are items to load
     */
    @Override
    public int getItemCount() {
        return items.size() == 0 ? -1 :
                items.size() % SwapiService.ITEMS_PER_PAGE != 0 ? items.size() :
                        items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == items.size() ? ITEM_VIEW_TYPE_FOOTER : ITEM_VIEW_TYPE_NORMAL;
    }
}