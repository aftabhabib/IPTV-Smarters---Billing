package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.EpgListingPojo;
//import com.happyboxtv.happyboxtviptvbilling.presenter.LiveStreamsPresenter;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ViewDetailsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LiveStreamsInterface;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class LiveStreamsAdapter extends RecyclerView.Adapter<LiveStreamsAdapter.MyViewHolder> implements LiveStreamsInterface {



    int LIVE_FLAG ;


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
    private SimpleDateFormat programTimeFormat = new SimpleDateFormat("HH:mm");
    private static SharedPreferences loginPreferencesSharedPref_time_format;


    @Override
    public void liveStreamCategories(List<LiveStreamCategoriesCallback> liveStreamCategoriesCallback) {

    }

    @Override
    public void liveStreams(List<LiveStreamsCallback> liveStreamsCallback,
                            ArrayList<FavouriteDBModel> allFavourites) {

    }

    @Override
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback,
                               TextView tvActiveChannel,
                               TextView tvNextChannel) {
        if (liveStreamsEpgCallback != null && liveStreamsEpgCallback.getEpgListingPojos().size() != 0) {
            String encodedChannelTitle = getNowPlayingEpg(liveStreamsEpgCallback);
            String encodedNEextChannelTitle = getExtPlayingEpg(liveStreamsEpgCallback, encodedChannelTitle);
            if (encodedChannelTitle != null) {
                byte[] decodeChannelTitle = Base64.decode(encodedChannelTitle, Base64.DEFAULT);
                tvActiveChannel.setText("Now : " + new String(decodeChannelTitle));
            } else {
                tvActiveChannel.setText("Now : " + context.getResources().getString(R.string.no_program_found));
            }
            if (encodedNEextChannelTitle != null) {
                byte[] decodeChannelTitle = Base64.decode(encodedNEextChannelTitle, Base64.DEFAULT);
                tvNextChannel.setText("Next : " + new String(decodeChannelTitle));
            } else {
                tvNextChannel.setText("Next : " + context.getResources().getString(R.string.no_program_found));
            }
        } else {
            tvActiveChannel.setText("Now : " + context.getResources().getString(R.string.no_program_found));
            tvNextChannel.setText("Next : " + context.getResources().getString(R.string.no_program_found));
        }

    }

    private String getExtPlayingEpg(LiveStreamsEpgCallback liveStreamsEpgCallback,
                                    String encodedChannelTitle) {

        List<EpgListingPojo> list = liveStreamsEpgCallback.getEpgListingPojos();
        boolean flag = false;
        for (EpgListingPojo listItem : list) {
            if (flag) {
                return listItem.getTitle();
            }
            if (listItem.getTitle().equals(encodedChannelTitle)) {
                flag = true;
            }

        }
        return null;
    }

    public String getNowPlayingEpg(LiveStreamsEpgCallback liveStreamsEpgCallback) {
        int totalEpg = liveStreamsEpgCallback.getEpgListingPojos().size();
        for (int i = 0; i < totalEpg; i++) {
            int getNowPlaying = liveStreamsEpgCallback.getEpgListingPojos().get(i).getNowPlaying();
            if (getNowPlaying == 1) {
                return liveStreamsEpgCallback.getEpgListingPojos().get(i).getTitle();
            }
        }
        return null;
    }

    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {

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

    public LiveStreamsAdapter(List<LiveStreamsDBModel> liveStreamCategories, Context context) {
        this.dataSet = liveStreamCategories;
        this.context = context;
        this.filterList = new ArrayList<LiveStreamsDBModel>();
        this.filterList.addAll(liveStreamCategories);
        this.completeList = liveStreamCategories;
        this.database = new DatabaseHandler(context);

        this.liveStreamDBHandler = new LiveStreamDBHandler(context);
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

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.live_streams_linear_layout, parent, false);

            if (view != null) {
//                view.setFocusable(true);
//                view.setFocusableInTouchMode(true);
            }
        }


//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.live_streams_layout, parent, false);
//        if (view != null) {
//            view.setFocusable(true);
//            view.setFocusableInTouchMode(true);
//        }
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        if (context != null) {
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.SETTINGS_PREFERENCE, MODE_PRIVATE);
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
                            if (epgPercentage != 0) {
                                epgPercentage = 100 - epgPercentage;
                                if (epgPercentage != 0 && !Title.equals("")) {
                                    if (AppConst.LIVE_FLAG == 0) {
                                        holder.tvTime.setVisibility(View.VISIBLE);
//                                        holder.tvTime.setText(programTimeFormat.format(epgStartDateToTimestamp) + " - " + programTimeFormat.format(epgStopDateToTimestamp));

                                        loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
                                        String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                                        programTimeFormat = new SimpleDateFormat(timeFormat);
                                        holder.tvTime.setText(programTimeFormat.format(epgStartDateToTimestamp) + " - " + programTimeFormat.format(epgStopDateToTimestamp));
                                    }
                                    holder.progressBar.setVisibility(View.VISIBLE);
                                    holder.progressBar.setProgress(epgPercentage);

                                    holder.tvCurrentLive.setVisibility(View.VISIBLE);
                                    holder.tvCurrentLive.setText(Title);
                                } else {
                                    holder.tvTime.setVisibility(View.GONE);
                                    holder.progressBar.setVisibility(View.GONE);
                                    holder.tvCurrentLive.setVisibility(View.GONE);
                                }
                                break;
                            }

                        }
//                        EPGEvent epgEvent = new EPGEvent(currentChannel, epgStartDateToTimestamp, epgStopDateToTimestamp, Title, streamIcon, Desc);
//                        if (prevEvent != null) {
//                            epgEvent.setPreviousEvent(prevEvent);
//                            prevEvent.setNextEvent(epgEvent);
//                        }
//                        prevEvent = epgEvent;
//                        currentChannel.addEvent(epgEvent);
//                        epgEvents.add(epgEvent);
                    }


                }
            }
//            getEPG(String channelID)

            final String num = dataSet.get(listPosition).getNum();
            final String name = dataSet.get(listPosition).getName();
            holder.tvChannelName.setText(dataSet.get(listPosition).getName());
            final String StreamIcon = dataSet.get(listPosition).getStreamIcon();

            holder.ivChannelLogo.setImageDrawable(null);

//            dataSet.get
            if (StreamIcon != null && !StreamIcon.equals("")) {
                Picasso.with(context).load(StreamIcon).placeholder(R.drawable.iptv_placeholder).into(holder.ivChannelLogo); //dataSet.get(listPosition).getStreamIcon()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.ivChannelLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.iptv_placeholder, null));
                }
            }


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelID,StreamIcon);
                }
            });

            holder.rlMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelID,StreamIcon);
                }
            });

            holder.rlStreamsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.playWithPlayer(context, selectedPlayer, streamId, streamType, num, name,epgChannelID,StreamIcon);
                }
            });

            holder.rlStreamsLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popmenu(holder,
                            streamId,
                            categoryId);
                    return true;
                }
            });


//            holder.rlStreamsLayout.setOnKeyListener(new View.OnKeyListener() {
//                //            holder.rlStreamsLayout.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
//                            holder.llMenu.performClick();
////                            holder.rlChannelBottom.performClick();
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });

            final ArrayList<FavouriteDBModel> checkFavourite = database.checkFavourite(streamId, categoryId, "live");
            if (checkFavourite != null && checkFavourite.size() > 0) {
                holder.ivFavourite.setVisibility(View.VISIBLE);
            } else {
                holder.ivFavourite.setVisibility(View.INVISIBLE);
            }

            holder.rlMovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popmenu(holder,
                            streamId,
                            categoryId);
                    return true;
                }
            });

            holder.llMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    popmenu(holder,
                            streamId,
                            categoryId);


                }
            });
        }
    }


    private void popmenu(final LiveStreamsAdapter.MyViewHolder holder,
                         final int streamId,
                         final String categoryId) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.tvStreamOptions);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_card);


//                    SubMenu subMenu = popup.getMenu().getItem(0).getSubMenu();


        ArrayList<FavouriteDBModel> checkFavourite = database.checkFavourite(streamId, categoryId, "live");


        if (checkFavourite.size() > 0) {
//                        subMenu.getItem(2).setVisible(true);

//                        popup.getMenu().getItem(0).getSubMenu().getItem(2).setVisible(true);
// code to run without icon
            popup.getMenu().getItem(2).setVisible(true);

        } else {
//                        subMenu.getItem(1).setVisible(true);

//                        popup.getMenu().getItem(0).getSubMenu().getItem(1).setVisible(true);
// code to run without icon
            popup.getMenu().getItem(1).setVisible(true);
        }
        //adding click listener


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_play:
                        playChannel();
                        break;
                    case R.id.nav_add_to_fav:
                        addToFavourite();
                        break;
                    case R.id.nav_remove_from_fav:
                        removeFromFavourite();
                        break;
//                                case R.id.menu_view_details:
//                                    startViewDeatilsActivity();
//                                    break;

                }
                return false;
            }

            private void startViewDeatilsActivity() {
                if (context != null) {
                    Intent viewDetailsActivityIntent = new Intent(context, ViewDetailsActivity.class);
                    context.startActivity(viewDetailsActivityIntent);
                }
            }

            private void playChannel() {
                holder.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel LiveStreamsFavourite = new FavouriteDBModel();
                LiveStreamsFavourite.setCategoryID(categoryId);
                LiveStreamsFavourite.setStreamID(streamId);
                database.addToFavourite(LiveStreamsFavourite, "live");
                holder.ivFavourite.setVisibility(View.VISIBLE);
            }

            private void removeFromFavourite() {
                database.deleteFavourite(streamId, categoryId, "live");
                holder.ivFavourite.setVisibility(View.INVISIBLE);
            }
        });
        //displaying the popup
        popup.show();

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
                            tvNoRecordFound.setVisibility(View.INVISIBLE);
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
