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
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.presenter.VodPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ViewDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class VodAdapter extends RecyclerView.Adapter<VodAdapter.MyViewHolder>  {
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    private Context context;
    private List<LiveStreamsDBModel> dataSet;
    private SharedPreferences loginPreferencesSharedPref;
    public VodPresenter vodPresenter;
    private List<LiveStreamsDBModel> filterList;
    private List<LiveStreamsDBModel> completeList;
    DatabaseHandler database;
    private SharedPreferences settingsPrefs;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    int LIVE_FLAG_VOD ;

    public void setVisibiltygone(ProgressBar pbPagingLoader) {
        if (pbPagingLoader != null)
            pbPagingLoader.setVisibility(View.GONE);
    }


//    @Override
//    public void vodInfo(VodInfoCallback vodInfoCallback, MyViewHolder holder, FavouritesAdapter.MyViewHolderVOD favouriteHolder) {
//        if (vodInfoCallback != null  && vodInfoCallback.getInfo()!= null && holder!=null) {
//            String rating = vodInfoCallback.getInfo().getRating();
//            String director = vodInfoCallback.getInfo().getDirector();
//            String releaseDate = vodInfoCallback.getInfo().getReleasedate();
//            boolean ratingFlag = isNumeric(rating);
//
//            if(ratingFlag && rating != null && !rating.isEmpty() && !rating.equals("n/A")){
//                float newRating = (Float.parseFloat(rating) / 2);
//                holder.ratingBar.setRating(newRating);
//            }else{
//
//            }
//            if (director != null && !director.isEmpty()) {
//                holder.Director.setText(director);
//            } else {
//                holder.Director.setText("n/A");
//            }
//            if (releaseDate != null && !releaseDate.isEmpty()) {
//                holder.releaseDate.setText(releaseDate);
//            } else {
//                holder.releaseDate.setText("n/A");
//            }
//        }
//    }
//    public static boolean isNumeric(String str)
//    {
//        try
//        {
//            double d = Double.parseDouble(str);
//        }
//        catch(NumberFormatException nfe)
//        {
//            return false;
//        }
//        return true;
//    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_name)
        TextView MovieName;
        @BindView(R.id.tv_movie_name1)
        TextView movieNameTV;
        @BindView(R.id.rl_movie)
        RelativeLayout Movie;
        @BindView(R.id.iv_movie_image)
        ImageView MovieImage;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.rl_movie_bottom)
        RelativeLayout rlMovieBottom;
        @BindView(R.id.ll_menu)
        LinearLayout llMenu;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }

    public VodAdapter(List<LiveStreamsDBModel> vodCategories, Context context) {
        this.dataSet = vodCategories;
        this.context = context;
        this.filterList = new ArrayList<LiveStreamsDBModel>();
        this.filterList.addAll(vodCategories);
        this.completeList = vodCategories;
        this.database = new DatabaseHandler(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.vod_layout, parent, false);
        View view;

        pref = context.getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG_VOD = pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vod_linear_layout, parent, false);

            if (view != null) {
//                view.setFocusable(true);
//                view.setFocusableInTouchMode(true);
            }

        }
        else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vod_layout, parent, false);

            if (view != null) {
//                view.setFocusable(true);
//                view.setFocusableInTouchMode(true);
            }

        }


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        if(context!=null) {
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.SETTINGS_PREFERENCE, context.MODE_PRIVATE);
            final String selectedPlayer = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, "");






            final int streamId = Integer.parseInt(dataSet.get(listPosition).getStreamId());
            final String categoryId = dataSet.get(listPosition).getCategoryId();
            final String containerExtension = dataSet.get(listPosition).getContaiinerExtension();
            final String streamType = dataSet.get(listPosition).getStreamType();
            final String name = dataSet.get(listPosition).getName();
            final String num = dataSet.get(listPosition).getNum();
            holder.MovieName.setText(dataSet.get(listPosition).getName());
            holder.movieNameTV.setText(dataSet.get(listPosition).getName());
            String StreamIcon = dataSet.get(listPosition).getStreamIcon();
            final String movieName = dataSet.get(listPosition).getName();

            holder.MovieImage.setImageDrawable(null);
            if (StreamIcon != null && !StreamIcon.equals("")) {
                Picasso.with(context).load(dataSet.get(listPosition).getStreamIcon()).placeholder(R.drawable.noposter).into(holder.MovieImage);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.MovieImage.setImageDrawable(context.getResources().getDrawable(R.drawable.noposter, null));
                }
            }

//            holder.MovieImage.setColorFilter(Color.argb(100, 0, 0, 0));
            final ArrayList<FavouriteDBModel> checkFavourite = database.checkFavourite(streamId, categoryId, "vod");
            if (checkFavourite.size() > 0) {
                holder.ivFavourite.setVisibility(View.VISIBLE);
            } else {
                holder.ivFavourite.setVisibility(View.INVISIBLE);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startViewDeatilsActivity(streamId,
//                            movieName,
//                            selectedPlayer,
//                            streamType,
//                            containerExtension,
//                            categoryId);
                    Utils.playWithPlayerVOD(context, selectedPlayer, streamId, streamType, containerExtension,num,name);
                }
            });

            holder.MovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startViewDeatilsActivity(streamId,
                            movieName,
                            selectedPlayer,
                            streamType,
                            containerExtension,
                            categoryId,num,name);
//                    Utils.playWithPlayerVOD(context, selectedPlayer, streamId, streamType, containerExtension);
                }
            });

            holder.Movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startViewDeatilsActivity(streamId,
                            movieName,
                            selectedPlayer,
                            streamType,
                            containerExtension,
                            categoryId,num,name);
                }
            });


//            holder.Movie.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
////                            holder.rlMovieBottom.performClick();
//                            holder.llMenu.performClick();
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });

            holder.Movie.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popmenu(holder,
                            streamId,
                            categoryId,
                            movieName,
                            selectedPlayer,
                            streamType,
                            containerExtension,
                            num,
                            name);
                    return true;
                }
            });


            holder.MovieImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popmenu(holder,
                            streamId,
                            categoryId,
                            movieName,
                            selectedPlayer,
                            streamType,
                            containerExtension,
                            num,
                            name);
                    return true;
                }
            });


            holder.llMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    popmenu(holder,
                            streamId,
                            categoryId,
                            movieName,
                            selectedPlayer,
                            streamType,
                            containerExtension,
                            num,
                            name);
                }
            });


        }
    }

    private void startViewDeatilsActivity(int streamId,
                                          String movieName,
                                          String selectedPlayer,
                                          String streamType,
                                          String containerExtension,
                                          String categoryId,
                                          String num,
                                          String name) {
        if (context != null) {
            Intent viewDetailsActivityIntent = new Intent(context, ViewDetailsActivity.class);
            viewDetailsActivityIntent.putExtra(AppConst.STREAM_ID, String.valueOf(streamId));
            viewDetailsActivityIntent.putExtra("movie", movieName);
            viewDetailsActivityIntent.putExtra("selectedPlayer", selectedPlayer);
            viewDetailsActivityIntent.putExtra("streamType", streamType);
            viewDetailsActivityIntent.putExtra("containerExtension", containerExtension);
            viewDetailsActivityIntent.putExtra("categoryID", categoryId);
            viewDetailsActivityIntent.putExtra("num", num);
            viewDetailsActivityIntent.putExtra("name", name);
            context.startActivity(viewDetailsActivityIntent);
        }
    }

    private void popmenu(final VodAdapter.MyViewHolder holder,
                         final int streamId,
                         final String categoryId,
                         final String movieName,
                         final String selectedPlayer,
                         final String streamType,
                         final String containerExtension,
                         final String num,
                         final String name) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.tvStreamOptions);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_card_vod);
        ArrayList<FavouriteDBModel> checkFavourite = database.checkFavourite(streamId, categoryId, "vod");
        if (checkFavourite.size() > 0) {
            popup.getMenu().getItem(2).setVisible(true);
//                    holder.llRating.setVisibility(View.VISIBLE);
        } else {
            popup.getMenu().getItem(1).setVisible(true);
//                    holder.llRating.setVisibility(View.INVISIBLE);
        }
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_play:
                        playMovie();
                        break;
                    case R.id.nav_add_to_fav:
                        addToFavourite();
                        break;
                    case R.id.nav_remove_from_fav:
                        removeFromFavourite();
                        break;
                    case R.id.menu_view_details:
                        startViewDeatilsActivity(streamId,
                                movieName,
                                selectedPlayer,
                                streamType,
                                containerExtension,
                                categoryId,
                                num,
                                name);
                        break;

                }
                return false;
            }

            private void startViewDeatilsActivity(int streamId,
                                                  String movieName,
                                                  String selectedPlayer,
                                                  String streamType,
                                                  String containerExtension,
                                                  String categoryId,
                                                  String num,
                                                  String name) {
                if (context != null) {
                    Intent viewDetailsActivityIntent = new Intent(context, ViewDetailsActivity.class);
                    viewDetailsActivityIntent.putExtra(AppConst.STREAM_ID, String.valueOf(streamId));
                    viewDetailsActivityIntent.putExtra("movie", movieName);
                    viewDetailsActivityIntent.putExtra("selectedPlayer", selectedPlayer);
                    viewDetailsActivityIntent.putExtra("streamType", streamType);
                    viewDetailsActivityIntent.putExtra("containerExtension", containerExtension);
                    viewDetailsActivityIntent.putExtra("categoryID", categoryId);
                    viewDetailsActivityIntent.putExtra("num", num);
                    viewDetailsActivityIntent.putExtra("name", name);
                    context.startActivity(viewDetailsActivityIntent);
                }
            }


            private void playMovie() {
                holder.cardView.performClick();
            }

            private void addToFavourite() {
                FavouriteDBModel LiveStreamsFavourite = new FavouriteDBModel();
                LiveStreamsFavourite.setCategoryID(categoryId);
                LiveStreamsFavourite.setStreamID(streamId);
                database.addToFavourite(LiveStreamsFavourite, "vod");
                holder.ivFavourite.setVisibility(View.VISIBLE);
            }

            private void removeFromFavourite() {
                database.deleteFavourite(streamId, categoryId, "vod");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<LiveStreamsDBModel>();
                if (filterList != null)
                    filterList.clear();
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);
                } else {
                    for (LiveStreamsDBModel item : dataSet) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                            filterList.add(item);
                        }
                    }
                }

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
}
