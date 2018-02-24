package com.gehostingv2.gesostingv2iptvbilling.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VoDListViewCatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.nst.iptvsmarters.view.activity.VodActivityNewFlow;

public class VodAdapterNewFlow extends RecyclerView.Adapter<VodAdapterNewFlow.MyViewHolder> {

    private List<LiveStreamCategoryIdDBModel> moviesListl;
    private Context context;
    private LiveStreamDBHandler liveStreamDBHandler;
    private List<LiveStreamCategoryIdDBModel> filterList;
    private List<LiveStreamCategoryIdDBModel> completeList;
    public int text_last_size;
    public int text_size;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_tv_icon)
        ImageView ivTvIcon;
        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.iv_foraward_arrow)
        ImageView ivForawardArrow;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;

        @BindView(R.id.tv_sub_cat_count)
        TextView tvXubCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }

    public VodAdapterNewFlow(List<LiveStreamCategoryIdDBModel> movieList, Context context) {
        this.filterList = new ArrayList<LiveStreamCategoryIdDBModel>();
        this.filterList.addAll(movieList);
        this.completeList = movieList;
        this.moviesListl= movieList;
        this.context = context;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context);
    }

    public VodAdapterNewFlow(){

    }

    @Override
    public VodAdapterNewFlow.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_vod_new_flow_list_item, parent, false);
        VodAdapterNewFlow.MyViewHolder myViewHolder = new VodAdapterNewFlow.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final VodAdapterNewFlow.MyViewHolder holder, final int listPosition) {
        String categoryName = "";
        String categoryId = "";
        LiveStreamCategoryIdDBModel data = moviesListl.get(listPosition);
        categoryName = data.getLiveStreamCategoryName();
        categoryId = data.getLiveStreamCategoryID();
        final Bundle bundle = new Bundle();
        bundle.putString(AppConst.CATEGORY_ID, categoryId);
        bundle.putString(AppConst.CATEGORY_NAME, categoryName);
        if(categoryName!=null && !categoryName.equals("") && !categoryName.isEmpty())
        holder.tvMovieCategoryName.setText(categoryName);


        final String finalCategoryId = categoryId;
        final String finalCategoryName = categoryName;
        holder.rlOuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                VodActivityNewFlow vodActivityNewFlow = new VodActivityNewFlow();
//                vodActivityNewFlow.progressBar(holder.pbPagingLoader);
                VoDListViewCatActivity voDListViewCatActivity = new VoDListViewCatActivity();
                voDListViewCatActivity.progressBar(holder.pbPagingLoader);
                if (holder != null && holder.pbPagingLoader != null) {
                    holder.pbPagingLoader.getIndeterminateDrawable()
                            .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                    holder.pbPagingLoader.setVisibility(View.VISIBLE);

                }

                Intent intent = new Intent(context, VoDListViewCatActivity.class);
                intent.putExtra(AppConst.CATEGORY_ID, finalCategoryId);
                intent.putExtra(AppConst.CATEGORY_NAME, finalCategoryName);
                context.startActivity(intent);
            }
        });

        int count = liveStreamDBHandler.getSubCatMovieCount(data.getLiveStreamCategoryID(), "movie");
        if (count != 0 && count != -1)
            holder.tvXubCount.setText(String.valueOf(count));
        else
            holder.tvXubCount.setText("");


        holder.rlListOfCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                VodActivityNewFlow vodActivityNewFlow = new VodActivityNewFlow();
//                vodActivityNewFlow.progressBar(holder.pbPagingLoader);
                VoDListViewCatActivity voDListViewCatActivity = new VoDListViewCatActivity();
                voDListViewCatActivity.progressBar(holder.pbPagingLoader);
                if (holder != null && holder.pbPagingLoader != null) {
                    holder.pbPagingLoader.getIndeterminateDrawable()
                            .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                    holder.pbPagingLoader.setVisibility(View.VISIBLE);

                }
                Intent intent = new Intent(context, VoDListViewCatActivity.class);
                intent.putExtra(AppConst.CATEGORY_ID, finalCategoryId);
                intent.putExtra(AppConst.CATEGORY_NAME, finalCategoryName);
                context.startActivity(intent);
            }
        });


        if (moviesListl.size() != 0) {
            holder.rlOuter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return moviesListl.size();
    }

    public void setVisibiltygone(ProgressBar pbPagingLoader) {
        if (pbPagingLoader != null)
            pbPagingLoader.setVisibility(View.GONE);

    }
    public void filter(final String text, final TextView tvNoRecordFound) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<LiveStreamCategoryIdDBModel>();
                text_size = text.length();
                // Clear the filter list
                if (filterList != null)
                    filterList.clear();
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);
//                    dataSet = completeList;
                } else {

                    //when we are using backpress in alphabets . . . .
                    if (moviesListl!=null && moviesListl.size() == 0 || text_last_size > text_size) {
                        moviesListl = completeList;
                    }

                    // Iterate in the original List and add it to filter list...
                    if(moviesListl!=null) {
                        for (LiveStreamCategoryIdDBModel item : moviesListl) {
                            if (item.getLiveStreamCategoryName().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                filterList.add(item);
                            }
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        if (TextUtils.isEmpty(text)) {
                            moviesListl = completeList;
                        } else if (!filterList.isEmpty() || filterList.isEmpty()) {
                            moviesListl = filterList;
                        }
                        if (moviesListl!=null && moviesListl.size() == 0) {
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                        }
                        text_last_size = text_size;
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
