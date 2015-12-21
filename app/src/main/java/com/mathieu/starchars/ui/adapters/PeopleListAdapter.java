package com.mathieu.starchars.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.models.People;

import java.util.ArrayList;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleListAdapter extends FooterBaseAdapter<PeopleListAdapter.ViewHolder> {
    private final static String TAG = "PeopleListAdapter";

    private Context context;

    public PeopleListAdapter(Context context, ArrayList<People> items, View footer) {
        super(items, footer);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return new ViewHolder(footer);
        } else {
            return createNormalViewHolder(viewGroup);
        }
    }

    @NonNull
    private ViewHolder createNormalViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_people, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

//        holder.container.setOnClickListener(this);
        holder.container.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooter(position)) {
            return;
        }
        super.onBindViewHolder(holder, position);

        People people = (People) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.personName.setText(people.name);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER ? ITEM_VIEW_TYPE_FOOTER : ITEM_VIEW_TYPE_NORMAL;
    }
//
//    @Override
//    public void onClick(View v) {
//        ViewHolder holder = (ViewHolder) v.getTag();
//        int position = holder.getPosition();
//
//        if (v.getId() == holder.container.getId()) {
//            Intent intent;
//            intent = new Intent(context, PeopleDetailActivity.class);
//            intent.putExtra("people", (People) items.get(position));
//            context.startActivity(intent);
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout container;
        public TextView personName;

        public ViewHolder(View itemView) {
            super(itemView);

            personName = (TextView) itemView.findViewById(R.id.people_name);
            container = (FrameLayout) itemView.findViewById(R.id.people_container);
        }
    }
}