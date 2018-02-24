package com.gehostingv2.gesostingv2iptvbilling.view.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XtreamPanelAPICallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelAvailableChannelsPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelLivePojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelMoviePojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.XMLTVPresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.XtreamPanelAPIPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XtreamPanelAPIInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.LoadingGearSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportStreamsActivity extends AppCompatActivity implements XtreamPanelAPIInterface {

    Context context;
    @BindView(R.id.tv_setting_streams)
    TextView tvSettingStreams;
//    @Nullable
    @BindView(R.id.tv_importing_streams)
    TextView tvImportingStreams;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.rl_import_process)
    RelativeLayout rlImportProcess;
    LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.rl_import_layout)
    RelativeLayout rlImportLayout;
//    @Nullable
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    //    @BindView(R.id.pb_circle_loader)
//    ProgressBar pbCircleLoader;
    private SharedPreferences loginPreferencesAfterLogin;
    private XMLTVPresenter xmlTvPresenter;
    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_import_streams);
        ButterKnife.bind(this);
        changeStatusBarColor();
        context = this;
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        xtreamPanelAPIPresenter = new XtreamPanelAPIPresenter(this, context);
        initialize();
    }

    private void initialize() {
        if (context != null) {

            loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            getChannelsCategories(context, liveStreamDBHandler, username, password);
            addDatabaseStatusOnSetup();

//            int epgCounts = liveStreamDBHandler.getEPGCount();
//            Toast.makeText(this, "Total EPG: " + epgCounts, Toast.LENGTH_SHORT).show();
//            if (epgCounts == 0) {
//                xmlTvPresenter = new XMLTVPresenter(ImportStreamsActivity.this, context);
//                xmlTvPresenter.epgXMLTV(username, password);
//            }
        }
    }

    private void addDatabaseStatusOnSetup() {
        String currentDate = "";
        int count = -1;
        currentDate = currentDateValue();
        if(liveStreamDBHandler!=null) {
            count = liveStreamDBHandler.getDBStatusCount();
            if (count!=-1 && count ==0) {
                if (currentDate != null) {
                    addDBStatus(liveStreamDBHandler, currentDate);
                } else if (currentDate == null) {
                    Utils.showToast(context, "Invalid current date");
                }
            }
        }
    }

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler, String currentDate) {
//        String currentDate = "24/11/2017";
        DatabaseUpdatedStatusDBModel updatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        updatedStatusDBModel.setDbUpadatedStatusState(AppConst.DB_UPDATED_STATUS_FINISH);
        updatedStatusDBModel.setDbLastUpdatedDate(currentDate);
        updatedStatusDBModel.setDbCategory(AppConst.DB_CHANNELS);
        updatedStatusDBModel.setDbCategoryID(AppConst.DB_CHANNELS_ID);
        liveStreamDBHandler.addDBUpdatedStatus(updatedStatusDBModel);

//        updatedStatusDBModel.setDbUpadatedStatusState(AppConst.DB_UPDATED_STATUS_FINISH);
//        updatedStatusDBModel.setDbLastUpdatedDate(currentDate);
//        updatedStatusDBModel.setDbCategory(AppConst.DB_EPG);
//        updatedStatusDBModel.setDbCategoryID(AppConst.DB_EPG_ID);
//        liveStreamDBHandler.addDBUpdatedStatus(updatedStatusDBModel);
    }

    private String currentDateValue() {
        Date currentTime = Calendar.getInstance().getTime();
        String dateValue = currentTime.toString();
        String currentDate = Utils.parseDateToddMMyyyy(dateValue);
//        String currentDate = "28/11/2017";
        return currentDate;
    }


    private void getChannelsCategories(Context context,
                                       LiveStreamDBHandler liveStreamDBHandler,
                                       String username,
                                       String password) {
        if (username != null && password != null &&
                !username.isEmpty() && !password.isEmpty() &&
                !username.equals("") && !password.equals(""))
            xtreamPanelAPIPresenter.panelAPI(username, password);
    }


    private void changeStatusBarColor() {
        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }


    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {
        Utils.showToast(context, AppConst.NETWORK_ERROR_OCCURED);
    }

    @Override
    public void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String username) {


        if (xtreamPanelAPICallback != null && context != null) {
//            if (ivGearLoader != null) {
////                pbCircleLoader.setVisibility(View.GONE);
//                ivGearLoader.setVisibility(View.GONE);
//            }
//            if (rlImportLayout != null) {
//                rlImportLayout.setVisibility(View.VISIBLE);
//            }

            final ArrayList<PanelMoviePojo> movieList = xtreamPanelAPICallback.getCategories().getMovie();
            final ArrayList<PanelLivePojo> liveList = xtreamPanelAPICallback.getCategories().getLive();
            final Map<String, PanelAvailableChannelsPojo> availableChanelsList = (Map<String, PanelAvailableChannelsPojo>) xtreamPanelAPICallback.getAvailableChannels();
            int totalVod =  0;
            int totalLive = 0;
            int totalLiveAndVod = 0;

            if(movieList!=null) {
                totalVod = movieList.size();
            }
            if(liveList!=null) {
                totalLive = liveList.size();
            }
            if(availableChanelsList!=null) {
                totalLiveAndVod = availableChanelsList.size();
            }
//            final int totalLive = liveList.size();
//            final int totalLiveAndVod = availableChanelsList.size();
            final LiveStreamCategoryIdDBModel movieCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            final LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            final LiveStreamsDBModel availableChannel = new LiveStreamsDBModel();


//            Utils.showToast(context, "Records Fetched:" + xmltvCallback.programmePojos.size());

            final int finalTotalLiveAndVod = totalLiveAndVod;
            class AllChannelsAndVodAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;

                final int ITERATIONS = finalTotalLiveAndVod;

                AllChannelsAndVodAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);
                    int i = 0;

////                    PanelAvailableChannelsPojo availabaleChanelValue =  availableChanelsList.values();
//                    for (PanelAvailableChannelsPojo availabaleChanelValue : availableChanelsList.values()) {
//                        if (availabaleChanelValue.getNum() != null)
//                            availableChannel.setNum(String.valueOf(availabaleChanelValue.getNum()));
//                        else
//                            availableChannel.setNum("");
//
//                        if (availabaleChanelValue.getName() != null)
//                            availableChannel.setName(availabaleChanelValue.getName());
//                        else
//                            availableChannel.setName("");
//
//                        if (availabaleChanelValue.getStreamType() != null)
//                            availableChannel.setStreamType(availabaleChanelValue.getStreamType());
//                        else
//                            availableChannel.setStreamType("");
//
//                        if (availabaleChanelValue.getStreamId() != null)
//                            availableChannel.setStreamId(availabaleChanelValue.getStreamId());
//                        else
//                            availableChannel.setStreamId("");
//
//                        if (availabaleChanelValue.getStreamIcon() != null)
//                            availableChannel.setStreamIcon(availabaleChanelValue.getStreamIcon());
//                        else
//                            availableChannel.setStreamIcon("");
//
//                        if (availabaleChanelValue.getEpgChannelId() != null)
//                            availableChannel.setEpgChannelId(availabaleChanelValue.getEpgChannelId());
//                        else
//                            availableChannel.setEpgChannelId("");
//
//                        if (availabaleChanelValue.getAdded() != null)
//                            availableChannel.setAdded(availabaleChanelValue.getAdded());
//                        else
//                            availableChannel.setAdded("");
//
//                        if (availabaleChanelValue.getCategoryId() != null)
//                            availableChannel.setCategoryId(availabaleChanelValue.getCategoryId());
//                        else
//                            availableChannel.setCategoryId("");
//
//                        if (availabaleChanelValue.getCustomSid() != null)
//                            availableChannel.setCustomSid(availabaleChanelValue.getCustomSid());
//                        else
//                            availableChannel.setCustomSid("");
//
//                        if (availabaleChanelValue.getTvArchive() != null)
//                            availableChannel.setTvArchive(String.valueOf(availabaleChanelValue.getTvArchive()));
//                        else
//                            availableChannel.setTvArchive("");
//
//                        if (availabaleChanelValue.getDirectSource() != null)
//                            availableChannel.setDirectSource(availabaleChanelValue.getDirectSource());
//                        else
//                            availableChannel.setDirectSource("");
//
//                        if (availabaleChanelValue.getTvArchiveDuration() != null)
//                            availableChannel.setTvArchiveDuration(availabaleChanelValue.getTvArchiveDuration());
//                        else
//                            availableChannel.setTvArchiveDuration("");
//
//                        if (availabaleChanelValue.getTypeName() != null)
//                            availableChannel.setTypeName(String.valueOf(availabaleChanelValue.getTypeName()));
//                        else
//                            availableChannel.setTypeName("");
//
//                        if (availabaleChanelValue.getCategoryName() != null)
//                            availableChannel.setCategoryName(availabaleChanelValue.getCategoryName());
//                        else
//                            availableChannel.setCategoryName("");
//
//                        if (availabaleChanelValue.getSeriesNo() != null)
//                            availableChannel.setSeriesNo(String.valueOf(availabaleChanelValue.getSeriesNo()));
//                        else
//                            availableChannel.setSeriesNo("");
//
//                        if (availabaleChanelValue.getLive() != null)
//                            availableChannel.setLive(availabaleChanelValue.getLive());
//                        else
//                            availableChannel.setLive("");
//
//                        if (availabaleChanelValue.getContainerExtension() != null)
//                            availableChannel.setContaiinerExtension(String.valueOf(availabaleChanelValue.getContainerExtension()));
//                        else
//                            availableChannel.setContaiinerExtension("");
//
//                        publishProgress(i + 1);
//                        i++;
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addAllAvailableChannel(availableChannel);
//                        }
//                    }


                    if (liveStreamDBHandler != null) {
                        liveStreamDBHandler.addAllAvailableChannel(availableChanelsList);
                    }



                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
                    //if (context != null) {
                    //   float progressCount = progress[0];
                    //   float iterations = ITERATIONS;
                    //   float percentage = ((progressCount / iterations) * 100);
                    //   ((ImportStreamsActivity) context).tvImportingStreams.setText(getResources().getString(R.string.importign_all_channels));
                    //   ((ImportStreamsActivity) context).progressBar.setProgress((int) percentage);
                    //   ((ImportStreamsActivity) context).tvCountings.setText(progress[0] + "/" + ITERATIONS);
                    //   ((ImportStreamsActivity) context).tvPercentage.setText(String.format("%.2f", percentage) + "%");
                    //}
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
//                    Utils.showToast(context, "LiveStreams and VOD Imported Successfully!");
                    String currentDate = currentDateValue();
                    if (currentDate != null && liveStreamDBHandler != null) {
                        liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS,
                                AppConst.DB_CHANNELS_ID, AppConst.DB_UPDATED_STATUS_FINISH, currentDate);
                    }
                    if (context != null) {
                        Intent importDashboardIntent = new Intent(context, DashboardActivity.class);
                        startActivity(importDashboardIntent);
                        finish();
                    }
                }
            }


            final int finalTotalLive = totalLive;
            class LiveAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;

                final int ITERATIONS = finalTotalLive;

                LiveAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);
//                    for (int i = 0; i < finalTotalLive; i++) {
//                        liveStreamCategoryIdDBModel.setLiveStreamCategoryID(liveList.get(i).getCategoryId());
//                        liveStreamCategoryIdDBModel.setLiveStreamCategoryName(liveList.get(i).getCategoryName());
//                        liveStreamCategoryIdDBModel.setParentId(liveList.get(i).getParentId());
//                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addLiveCategories(liveStreamCategoryIdDBModel);
//                        }
//                    }
                    if (liveStreamDBHandler != null) {
                        liveStreamDBHandler.addLiveCategories(liveList);
                    }

                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        ((ImportStreamsActivity) context).tvImportingStreams.setText(getResources().getString(R.string.importing_livestreams_cat));
                    //    ((ImportStreamsActivity) context).progressBar.setProgress((int) percentage);
                    //    ((ImportStreamsActivity) context).tvCountings.setText(progress[0] + "/" + ITERATIONS);
                    //    ((ImportStreamsActivity) context).tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
//                    Utils.showToast(context, "LiveStreams Categories Imported Successfully!");
                    if (context != null) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        } else {
                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
                            ourAsyncTask.execute();

                        }

                    }
                }
            }


            final int finalTotalVod = totalVod;

            class VodAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;

                final int ITERATIONS = finalTotalVod;

                VodAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);
//                    for (int i = 0; i < finalTotalVod; i++) {
//                        movieCategoryIdDBModel.setLiveStreamCategoryID(movieList.get(i).getCategoryId());
//                        movieCategoryIdDBModel.setLiveStreamCategoryName(movieList.get(i).getCategoryName());
//                        movieCategoryIdDBModel.setParentId(movieList.get(i).getParentId());
//                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addMovieCategories(movieCategoryIdDBModel);
//                        }
//                    }
                    if (liveStreamDBHandler != null) {
                        liveStreamDBHandler.addMovieCategories(movieList);
                    }
                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        ((ImportStreamsActivity) context).tvImportingStreams.setText(getResources().getString(R.string.importing_vod_cat));
                    // ((ImportStreamsActivity) context).progressBar.setProgress((int) percentage);
                    // ((ImportStreamsActivity) context).tvCountings.setText(progress[0] + "/" + ITERATIONS);
                    // ((ImportStreamsActivity) context).tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
//                    Utils.showToast(context, "VOD Categories Imported Successfully!");
                    if (context != null) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                            ourAsyncTask.execute();
                        }
                    }
                }
            }


            if(finalTotalVod!=0) {

                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {
                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
                    ourAsyncTask.execute();

                }
            }else{
                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {
                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                    ourAsyncTask.execute();

                }
            }



        }

    }

    @Override
    public void panelApiFailed(String updateDBFailed) {
        if (updateDBFailed.equals(AppConst.DB_UPDATED_STATUS_FAILED) &&
                liveStreamDBHandler != null) {
            liveStreamDBHandler.updateDBStatus(AppConst.DB_CHANNELS,
                    AppConst.DB_CHANNELS_ID,
                    AppConst.DB_UPDATED_STATUS_FAILED);
        }
    }


}
