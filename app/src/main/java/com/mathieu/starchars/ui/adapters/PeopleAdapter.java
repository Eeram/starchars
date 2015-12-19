package com.mathieu.starchars.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mathieu.starchars.R;
import com.mathieu.starchars.api.models.People;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       19/12/2015
 */

public class PeopleAdapter extends FooterBaseAdapter<PeopleAdapter.ViewHolder>
        implements View.OnClickListener {

    private final static String TAG = "PeopleAdapter";

    private Context context;

    public PeopleAdapter(Context context, ArrayList<People> items, View footer) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        holder.container.setOnClickListener(this);
        holder.container.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooter(position)) {
            return;
        }

        People people = (People) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.personName.setText(people.name);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER ? ITEM_VIEW_TYPE_FOOTER : ITEM_VIEW_TYPE_NORMAL;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag();
        int position = holder.getPosition();

        if (v.getId() == holder.container.getId()) {
//            Intent intent;
//            intent = new Intent(context, ArticleActivity.class);
//            intent.putExtra("currentPosition", position);
//            intent.putExtra("articles", items);
//            context.startActivity(intent);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout container;
        public TextView personName;

        public ViewHolder(View itemView) {
            super(itemView);

            personName = (TextView) itemView.findViewById(R.id.person_name);
            container = (FrameLayout) itemView.findViewById(R.id.person_container);
        }
    }
}