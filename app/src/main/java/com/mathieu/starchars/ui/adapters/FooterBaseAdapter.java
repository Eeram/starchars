package com.mathieu.starchars.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mathieu.starchars.api.SwapiService;

import java.util.ArrayList;
import java.util.List;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public abstract class FooterBaseAdapter<V> extends RecyclerView.Adapter {
    public final static int ITEM_VIEW_TYPE_NORMAL = 100;
    public final static int ITEM_VIEW_TYPE_FOOTER = 101;

    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final String TAG = "FooterBaseAdapter";

    protected ArrayList items = new ArrayList<>();
    protected View footer;

    private int selectionMode = CHOICE_MODE_SINGLE;
    private int selectedItem = 0;

    public FooterBaseAdapter(ArrayList items, View footer) {
        this.items = items;
        this.footer = footer;
    }

    public boolean isFooter(int position) {
        return position == items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.e(TAG, position + " bind : " + selectedItem);
        if (selectionMode == CHOICE_MODE_SINGLE) {
            Log.e(TAG, position + " selected : " + selectedItem);
            holder.itemView.setActivated(position == selectedItem);
        }
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

    public void setSelected(int pos) {
        if (selectionMode == CHOICE_MODE_SINGLE) {
            int oldSelected = selectedItem;
            selectedItem = pos;
            notifyItemChanged(oldSelected);
            notifyItemChanged(pos);
        }
    }

    private int getSelected() {
        if (selectionMode == CHOICE_MODE_SINGLE) {
            return selectedItem;
        } else {
            return -1;
        }
    }

    public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
        notifyItemChanged(selectedItem);
    }
}