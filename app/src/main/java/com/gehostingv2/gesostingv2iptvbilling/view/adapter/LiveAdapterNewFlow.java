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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveTVListViewActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveTVListViewCatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LiveAdapterNewFlow extends RecyclerView.Adapter<LiveAdapterNewFlow.MyViewHolder> {

    private List<LiveStreamCategoryIdDBModel> moviesListl;
    private Context context;
    private List<LiveStreamCategoryIdDBModel> filterList;
    private List<LiveStreamCategoryIdDBModel> completeList;
    private int text_last_size;
    private int text_size;
    private int adapterPosition;
    private LiveStreamDBHandler liveStreamDBHandler;
    private DatabaseHandler dbHandeler;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.testing)
        RelativeLayout testing;

        @BindView(R.id.tv_sub_cat_count)
        TextView tvXubCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
             setIsRecyclable(false);
        }
    }

    public LiveAdapterNewFlow(List<LiveStreamCategoryIdDBModel> movieList, Context context) {
        this.filterList = new ArrayList<LiveStreamCategoryIdDBModel>();
        this.filterList.addAll(movieList);
        this.completeList = movieList;

        this.moviesListl= movieList;
        this.context = context;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context);
        this.dbHandeler = new DatabaseHandler(context);
    }

    @Override
    public LiveAdapterNewFlow.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_live_new_flow_list_item, parent, false);

        final LiveAdapterNewFlow.MyViewHolder myViewHolder = new LiveAdapterNewFlow.MyViewHolder(view);
//        myViewHolder.rlListOfCategories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//
//            }
//        });
//        view.getOnFocusChangeListener();
//        myViewHolder.rlListOfCategories.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
////                adapterPosition  = myViewHolder.getAdapterPosition();
////                myViewHolder.testing.set
//
////                myViewHolder.testing.getChildAt(adapterPosition).setPadding(10,10,10,10);
//
//
//                return false;
//            }
//        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final LiveAdapterNewFlow.MyViewHolder holder, final int listPosition) {
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
        int count = liveStreamDBHandler.getSubCatMovieCount(data.getLiveStreamCategoryID(), "live");
        if (count != 0 && count != -1)
            holder.tvXubCount.setText(String.valueOf(count));
        else
            holder.tvXubCount.setText("");

        if(listPosition==0 && data.getLiveStreamCategoryID().equals("0")){
            int countAll = liveStreamDBHandler.getStreamsCount("live");
            if (countAll != 0 && countAll != -1)
                holder.tvXubCount.setText(String.valueOf(countAll));
            else
                holder.tvXubCount.setText("");
        }
        if(listPosition==1 && data.getLiveStreamCategoryID().equals("-1")){
            int countAll = dbHandeler.getFavouriteCount("live");
            if (countAll != 0 && countAll != -1)
                holder.tvXubCount.setText(String.valueOf(countAll));
            else
                holder.tvXubCount.setText("0");
        }

        holder.rlOuter.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                LiveTVListViewActivity vodActivityNewFlow = new LiveTVListViewActivity();
                vodActivityNewFlow.progressBar(holder.pbPagingLoader);
                if (holder != null && holder.pbPagingLoader != null) {
                    holder.pbPagingLoader.getIndeterminateDrawable()
                            .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                    holder.pbPagingLoader.setVisibility(View.VISIBLE);

                }

                Intent intent = new Intent(context, LiveTVListViewCatActivity.class);
                intent.putExtra(AppConst.CATEGORY_ID, finalCategoryId);
                intent.putExtra(AppConst.CATEGORY_NAME, finalCategoryName);
                context.startActivity(intent);
            }
        });


        holder.rlListOfCategories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LiveTVListViewActivity vodActivityNewFlow = new LiveTVListViewActivity();
                vodActivityNewFlow.progressBar(holder.pbPagingLoader);
                if (holder != null && holder.pbPagingLoader != null) {
                    holder.pbPagingLoader.getIndeterminateDrawable()
                            .setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                    holder.pbPagingLoader.setVisibility(View.VISIBLE);

                }

                Intent intent = new Intent(context, LiveTVListViewCatActivity.class);
                intent.putExtra(AppConst.CATEGORY_ID, finalCategoryId);
                intent.putExtra(AppConst.CATEGORY_NAME, finalCategoryName);
                context.startActivity(intent);
            }
        });
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
