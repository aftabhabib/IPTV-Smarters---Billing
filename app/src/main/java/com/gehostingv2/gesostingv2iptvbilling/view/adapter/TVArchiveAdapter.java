package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.SubTVArchiveActivity;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

//import com.nst.iptv.view.activity.SubTVArchiveActivity;

public class TVArchiveAdapter extends
        RecyclerView.Adapter<TVArchiveAdapter.MyViewHolder>
//        LiveStreamsInterface
{

    private Context context;
    private List<LiveStreamsDBModel> dataSet;
    private SharedPreferences loginPreferencesSharedPref;

    //    public LiveStreamsPresenter liveStreamsPresenter;
    private List<LiveStreamsDBModel> filterList;
    private List<LiveStreamsDBModel> completeList;
    private DatabaseHandler database;
    private LiveStreamDBHandler liveStreamDBHandler;
    MyViewHolder myViewHolder;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SimpleDateFormat programTimeFormat ;//= new SimpleDateFormat("HH:mm");
    private static SharedPreferences loginPreferencesSharedPref_time_format;

//    int streamId=-1;
//    String categoryId ="";
//    String streamType = "";
//    String epgChannelID = "";
//    String StreamIcon = "";


    public TVArchiveAdapter(List<LiveStreamsDBModel> liveStreamCategories,
                              Context context) {
        this.dataSet = liveStreamCategories;
        this.context = context;
        this.filterList = new ArrayList<LiveStreamsDBModel>();
        this.filterList.addAll(liveStreamCategories);
        this.completeList = liveStreamCategories;
        this.database = new DatabaseHandler(context);
        this.liveStreamDBHandler = new LiveStreamDBHandler(context);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_channel_logo)
        ImageView ivChannelLogo;
        @BindView(R.id.tv_movie_name)
        TextView tvChannelName;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.rl_streams_layout)
        RelativeLayout rlStreamsLayout;
        @BindView(R.id.rl_channel_bottom)
        RelativeLayout rlChannelBottom;
        @BindView(R.id.ll_menu)
        LinearLayout llMenu;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.tv_current_live)
        TextView tvCurrentLive;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_movie_image)
        RelativeLayout rlMovieImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view;
        pref = context.getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG = pref.getInt(AppConst.LIVE_STREAM, 0);
        if (AppConst.LIVE_FLAG == 1) {


            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.live_streams_layout, parent, false);

            if (view != null) {
//                view.setFocusable(true);
//                view.setFocusableInTouchMode(true);
            }
            myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.live_streams_linear_layout, parent, false);

            if (view != null) {
//                view.setFocusable(true);
//                view.setFocusableInTouchMode(true);
            }
            myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


//        myViewHolder = new MyViewHolder(view);
//        return null;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        if (context != null) {
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, MODE_PRIVATE);
            final String selectedPlayer = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, "");
//            final int streamId = Integer.parseInt(dataSet.get(listPosition).getStreamId());
            String trimmed = dataSet.get(listPosition).getStreamId().trim();
            final int streamId = Integer.parseInt(trimmed);
            final String categoryId = dataSet.get(listPosition).getCategoryId();
            final String streamType = dataSet.get(listPosition).getStreamType();
            final String epgChannelID = dataSet.get(listPosition).getEpgChannelId();


            holder.tvTime.setText("");
            holder.progressBar.setVisibility(View.GONE);
            holder.tvCurrentLive.setText("");


            if (epgChannelID != null && !epgChannelID.equals("") && liveStreamDBHandler != null) {
                ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos = liveStreamDBHandler.getEPG(epgChannelID);
//                long now = System.currentTimeMillis();
//                long testing = LocalDateTime.now().toDateTime().getMillis();

                String startDateTime;
                String stopDateTime;
                String Title = "";
                String NewTitle;
                String Desc;
                int epgPercentage = 0;


                if (xmltvProgrammePojos != null) {
                    for (int j = 0; j < xmltvProgrammePojos.size(); j++) {
                        startDateTime = xmltvProgrammePojos.get(j).getStart();
                        stopDateTime = xmltvProgrammePojos.get(j).getStop();
                        Title = xmltvProgrammePojos.get(j).getTitle();
                        Desc = xmltvProgrammePojos.get(j).getDesc();
                        Long epgStartDateToTimestamp = Utils.epgTimeConverter(startDateTime);
                        Long epgStopDateToTimestamp = Utils.epgTimeConverter(stopDateTime);
                        if (isEventVisible(epgStartDateToTimestamp, epgStopDateToTimestamp)) {
                            epgPercentage = getPercentageLeft(epgStartDateToTimestamp, epgStopDateToTimestamp);
                            if(epgPercentage!=0){
                                epgPercentage = 100-epgPercentage;
                                if(epgPercentage!=0 && Title!=null && !Title.equals("")) {
                                    if (AppConst.LIVE_FLAG == 0) {
                                        holder.tvTime.setVisibility(View.VISIBLE);
                                        loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
                                        String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");

                                        programTimeFormat = new SimpleDateFormat(timeFormat);
                                        holder.tvTime.setText(programTimeFormat.format(epgStartDateToTimestamp) + " - " + programTimeFormat.format(epgStopDateToTimestamp));
                                    }
                                    holder.progressBar.setVisibility(View.VISIBLE);
                                    holder.progressBar.setProgress(epgPercentage);

                                    holder.tvCurrentLive.setVisibility(View.VISIBLE);
                                    holder.tvCurrentLive.setText(Title);
                                }else{
                                    holder.tvTime.setVisibility(View.GONE);
                                    holder.progressBar.setVisibility(View.GONE);
                                    holder.tvCurrentLive.setVisibility(View.GONE);
                                }
                                break;
                            }

                        }
                    }
                }
            }

            final String num = dataSet.get(listPosition).getNum();
            final String name = dataSet.get(listPosition).getName();
            holder.tvChannelName.setText(dataSet.get(listPosition).getName());
            final String StreamIcon = dataSet.get(listPosition).getStreamIcon();
            final String epgChannelId = dataSet.get(listPosition).getEpgChannelId();
            final String tvArchiveDuration = dataSet.get(listPosition).getTvArchiveDuration();
            holder.ivChannelLogo.setImageDrawable(null);

//            dataSet.get
            if (StreamIcon != null && !StreamIcon.equals("")) {
                Picasso.with(context).load(StreamIcon).placeholder(R.drawable.iptv_placeholder).into(holder.ivChannelLogo); //dataSet.get(listPosition).getStreamIcon()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ivChannelLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.iptv_placeholder, null));
                }else{
                    holder.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.iptv_placeholder));
                }
            }


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent TVArchiveIntent = new Intent(context, SubTVArchiveActivity.class);
                    TVArchiveIntent.putExtra("OPENED_CHANNEL_ID", epgChannelId);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ID", streamId);
                    TVArchiveIntent.putExtra("OPENED_NUM", num);
                    TVArchiveIntent.putExtra("OPENED_NAME", name);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ICON", StreamIcon);
                    TVArchiveIntent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);


                    context.startActivity(TVArchiveIntent);



//                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelId,StreamIcon);
                }
            });

            holder.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent TVArchiveIntent = new Intent(context, SubTVArchiveActivity.class);
                    TVArchiveIntent.putExtra("OPENED_CHANNEL_ID", epgChannelId);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ID", streamId);
                    TVArchiveIntent.putExtra("OPENED_NUM", num);
                    TVArchiveIntent.putExtra("OPENED_NAME", name);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ICON", StreamIcon);
                    TVArchiveIntent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);


                    context.startActivity(TVArchiveIntent);


//                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelId,StreamIcon);
                }
            });


            holder.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent TVArchiveIntent = new Intent(context, SubTVArchiveActivity.class);
                    TVArchiveIntent.putExtra("OPENED_CHANNEL_ID", epgChannelId);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ID", streamId);
                    TVArchiveIntent.putExtra("OPENED_NUM", num);
                    TVArchiveIntent.putExtra("OPENED_NAME", name);
                    TVArchiveIntent.putExtra("OPENED_STREAM_ICON", StreamIcon);
                    TVArchiveIntent.putExtra("OPENED_ARCHIVE_DURATION", tvArchiveDuration);


                    context.startActivity(TVArchiveIntent);


                    //                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelId,StreamIcon);
                }
            });


            final ArrayList<FavouriteDBModel> checkFavourite = database.checkFavourite(streamId, categoryId, "live");
            if (checkFavourite != null && checkFavourite.size() > 0) {
                holder.ivFavourite.setVisibility(View.VISIBLE);
            } else {
                holder.ivFavourite.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(final String text, final TextView tvNoRecordFound) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<LiveStreamsDBModel>();

                // Clear the filter list
                if (filterList != null)
                    filterList.clear();
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);
//                    dataSet = completeList;
                } else {
                    // Iterate in the original List and add it to filter list...
                    for (LiveStreamsDBModel item : dataSet) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        if (TextUtils.isEmpty(text)) {
                            dataSet = completeList;
                        } else if (!filterList.isEmpty() || filterList.isEmpty()) {
                            dataSet = filterList;
                        }
                        if (dataSet.size() == 0) {
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private boolean isEventVisible(final long start, final long end) {
        long currentTime = LocalDateTime.now().toDateTime().getMillis();
        if (start <= currentTime && end >= currentTime) {
            return true;
        }
        return false;
//        return (start >= mTimeLowerBoundary && start <= mTimeUpperBoundary)
//                || (end >= mTimeLowerBoundary && end <= mTimeUpperBoundary)
//                || (start <= mTimeLowerBoundary && end >= mTimeUpperBoundary);
    }
    private static int getPercentageLeft(final long start, final long end) {
        long now = LocalDateTime.now().toDateTime().getMillis();
        if (start >= end || now >= end) {
            return 0;
        }
        if (now <= start) {
            return 100;
        }
        return (int) ((end - now) * 100 / (end - start));
    }
}
