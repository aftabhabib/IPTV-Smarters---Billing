package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

//import com.expresstvbox.expresstvboxiptv.presenter.LiveStreamsPresenter;

public class SubTVArchiveAdapter extends RecyclerView.Adapter<SubTVArchiveAdapter.MyViewHolder>  {

    private final int nowPlaying;
    private final String getActiveLiveStreamCategoryId;
    private final boolean nowPlayingFlag;
    private final int streamID;
    private final String streamNum;
    private final String streamName;
    private final String streamIcon;
    private final String streamChannelID;
    private final String streamChannelDuration;
    private Context context;
    private static SharedPreferences loginPreferencesSharedPref_time_format;

    private ArrayList<XMLTVProgrammePojo> dataSet;
//    private ArrayList dataSet = new ArrayList();
    private SharedPreferences loginPreferencesSharedPref;
//    public LiveStreamsPresenter liveStreamsPresenter;
//    private List<LiveStreamsCallback> filterList;
//    private List<LiveStreamsCallback> completeList;
//    private DatabaseHandler database;
    String currentFormatDateAfter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new java.util.Date());


//    public SubTVArchiveAdapter(ArrayList liveStreamsEpg,
//                               int nowPlayingPosition, boolean nowPlayingFlag, String getActiveLiveStreamCategoryId, int getActiveLiveStreamId, String getActiveLiveStreamNum, String getActiveLiveStreamName, String getActiveLiveStreamIcon, String getActiveLiveStreamChannelId, Context context) {
//
    public SubTVArchiveAdapter(ArrayList liveStreamsEpg,
                               int nowPlayingPosition, boolean nowPlayingFlag, String getActiveLiveStreamCategoryId, int getActiveLiveStreamId, String getActiveLiveStreamNum, String getActiveLiveStreamName, String getActiveLiveStreamIcon, String getActiveLiveStreamChannelId, String getActiveLiveStreamChannelDuration, Context context) {
    this.dataSet = liveStreamsEpg;
        this.context = context;
        this.nowPlaying = nowPlayingPosition;
        this.getActiveLiveStreamCategoryId = getActiveLiveStreamCategoryId;
        this.nowPlayingFlag = nowPlayingFlag;
        this.streamID = getActiveLiveStreamId;
        this.streamNum = getActiveLiveStreamNum;
        this.streamName = getActiveLiveStreamName;
        this.streamIcon = getActiveLiveStreamIcon;
        this.streamChannelID = getActiveLiveStreamChannelId;
        this.streamChannelDuration = getActiveLiveStreamChannelDuration;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_channel_name)
        TextView tvChannelName;
        @BindView(R.id.tv_now_playing)
        TextView tvNowPlaying;
        @BindView(R.id.rl_archive_layout)
        RelativeLayout rl_archive_layout;
        @BindView(R.id.ll_main_layout)
        LinearLayout ll_main_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_streams_epg_layout, parent, false);
        if (view != null) {
            view.setFocusable(true);
           // view.setFocusableInTouchMode(true);
        }
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        if(context!=null) {
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, MODE_PRIVATE);
            final String selectedPlayer = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, "");
//            String trimmed = dataSet.get(listPosition).getStreamId().trim();
//            final int streamId = Integer.parseInt(trimmed);
//            final String categoryId = dataSet.get(listPosition).getCategoryId();
//            final String streamType = dataSet.get(listPosition).getStreamType();
//            final String epgChannelID = dataSet.get(listPosition).getEpgChannelId();
//            dataSet.get(listPosition).getStart();



            final String getStart = dataSet.get(listPosition).getStart();
            final String getStop = dataSet.get(listPosition).getStop();

//        final int nowPlaying = dataSet.get(listPosition).getNowPlaying();
            Long epgDateToTimestamp = Utils.epgTimeConverter(getStart);
            Long epgDateToTimestampStop = Utils.epgTimeConverter(getStop);

            final String getStopTime = String.valueOf((epgDateToTimestampStop - epgDateToTimestamp) / 60000);
            loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
            String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
            String getTime = new SimpleDateFormat(timeFormat, Locale.US).format(epgDateToTimestamp);
            String getTimeStop = new SimpleDateFormat(timeFormat, Locale.US).format(epgDateToTimestampStop);
//            final String getStopTime = new SimpleDateFormat("HH-mm", Locale.US).format(epgDateToTimestampStop);
            final String getStartFormatedTime = new SimpleDateFormat("yyyy-MM-dd:hh-mm", Locale.US).format(epgDateToTimestamp);

//        final String getTime   = getStart.substring(11, 16);
            final String getTitle = dataSet.get(listPosition).getTitle();
//        byte[] decodeEpgTitle = Base64.decode(getTitleEncoded, Base64.DEFAULT);


            holder.tvDateTime.setText(getTime+" - "+getTimeStop);
            holder.tvChannelName.setText(getTitle);
            if (currentFormatDateAfter != null && currentFormatDateAfter.equals(getActiveLiveStreamCategoryId)) {
                if (listPosition == nowPlaying && nowPlayingFlag) {

                    holder.rl_archive_layout.setBackgroundColor(context.getResources().getColor(R.color.active_green));
                    //holder.tvNowPlaying.setText("(Now Playing)");
                } else {
                    holder.rl_archive_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
                    //holder.tvNowPlaying.setText("");
                }
            } else {
                //holder.tvNowPlaying.setText("");
                holder.rl_archive_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            holder.rl_archive_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.playWithPlayerArchive(context, selectedPlayer, streamID, streamNum, streamName, streamChannelID, streamIcon,getStartFormatedTime,streamChannelDuration,getStopTime);
                }
            });
            holder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.playWithPlayerArchive(context, selectedPlayer, streamID, streamNum, streamName, streamChannelID, streamIcon,getStartFormatedTime,streamChannelDuration,getStopTime);
                }
            });
//        if(nowPlaying == 1){
//            holder.tvNowPlaying.setText("(Now Playing)");
//        }else{
//            holder.tvNowPlaying.setText("");
//        }
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(final String text, final TextView tvNoRecordFound) {
//        // Searching could be complex..so we will dispatch it to a different thread...
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                filterList = new ArrayList<LiveStreamsCallback>();
//
//                // Clear the filter list
//                if (filterList != null)
//                    filterList.clear();
//                // If there is no search value, then add all original list items to filter list
//                if (TextUtils.isEmpty(text)) {
//                    filterList.addAll(completeList);
////                    dataSet = completeList;
//                } else {
//                    // Iterate in the original List and add it to filter list...
//                    for (LiveStreamsCallback item : dataSet) {
//                        if (item.getName().toLowerCase().contains(text.toLowerCase())) {
//                            // Adding Matched items
//                            filterList.add(item);
//                        }
//                    }
//                }
//
//                // Set on UI Thread
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Notify the List that the DataSet has changed...
//                        if (TextUtils.isEmpty(text)) {
//                            dataSet = completeList;
//                        } else if (!filterList.isEmpty() || filterList.isEmpty()) {
//                            dataSet = filterList;
//                        }
//                        if (dataSet.size() == 0) {
//                            tvNoRecordFound.setVisibility(View.VISIBLE);
//
////                            Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
//                        }
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        }).start();
    }

}
