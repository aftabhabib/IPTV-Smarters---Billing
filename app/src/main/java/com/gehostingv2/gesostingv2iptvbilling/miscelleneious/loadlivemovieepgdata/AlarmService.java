//package com.lucassuto.lucassutoiptvbilling.miscelleneious.loadlivemovieepgdata;
//
//import android.app.ActivityManager;
//import android.app.Application;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.lucassuto.lucassutoiptvbilling.miscelleneious.common.AppConst;
//import com.lucassuto.lucassutoiptvbilling.model.database.LiveStreamCategoryIdDBModel;
//import com.lucassuto.lucassutoiptvbilling.model.database.LiveStreamsDBModel;
//import com.lucassuto.lucassutoiptvbilling.model.callback.XMLTVCallback;
//import com.lucassuto.lucassutoiptvbilling.model.callback.XtreamPanelAPICallback;
//import com.lucassuto.lucassutoiptvbilling.model.database.LiveStreamDBHandler;
//import com.lucassuto.lucassutoiptvbilling.model.pojo.PanelAvailableChannelsPojo;
//import com.lucassuto.lucassutoiptvbilling.model.pojo.PanelLivePojo;
//import com.lucassuto.lucassutoiptvbilling.model.pojo.PanelMoviePojo;
//import com.lucassuto.lucassutoiptvbilling.presenter.XtreamPanelAPIPresenter;
//import com.lucassuto.lucassutoiptvbilling.view.interfaces.XtreamPanelAPIInterface;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class AlarmService extends Service implements XtreamPanelAPIInterface {
//    private Context context;
//    private LiveStreamDBHandler liveStreamDBHandler;
//    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;
//    private boolean isRunning;
//    private Thread backgroundThread;
//    private boolean isAppRunning = false;
//    private Handler mHandler;
//
//
//    public AlarmService(Context context) {
//        this.context = context;
//    }
//
//    public AlarmService() {
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public void onCreate() {
//        this.context = this;
//        this.isRunning = false;
//        this.backgroundThread = new Thread(myTask);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if (!this.isRunning) {
//            this.isRunning = true;
//            this.backgroundThread.start();
//        }
//
//        //we have some options for service
//        //start sticky means service will be explicity started and stopped
//        return START_STICKY;
//    }
//
//    private Runnable myTask = new Runnable() {
//        public void run() {
//            isRunning = false;
//            if (context != null)
//                isBackgroundForeground(context);
//            stopSelf();
//
//        }
//    };
//
//
//    private void isBackgroundForeground(Context context) {
//        boolean flag = false;
//        if (context != null && getPackageName() != null) {
//            isAppRunning = isAppOnForeground(context, getPackageName());
//            isAppRunnig(isAppRunning);
//        }
//    }
//
//    public void isAppRunnig(boolean flag) {
//
//        if (flag) {
//            if (context != null)
//                mHandler = new Handler(Looper.getMainLooper()) {
//                    @Override
//                    public void handleMessage(Message message) {
//                        // This is where you do your work in the UI thread.
//                        // Your worker tells you in the message what to do.
//                        Toast.makeText(context, "I'm in Foreground", Toast.LENGTH_SHORT).show();
//                    }
//                };
//            Log.e("Application :", "is in foreground");
//        } else {
//            if (context != null)
//                mHandler = new Handler(Looper.getMainLooper()) {
//                    @Override
//                    public void handleMessage(Message message) {
//                        // This is where you do your work in the UI thread.
//                        // Your worker tells you in the message what to do.
//                        Toast.makeText(context, "I'm in Background", Toast.LENGTH_SHORT).show();
//                    }
//                };
//            Log.e("Application :", "is in background");
//            inititalize();
//        }
////        try {
////            this.backgroundThread.sleep(8000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//    }
//    private boolean isAppOnForeground(Context context, String appPackageName) {
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
//        if (appProcesses == null) {
//            return false;
//        }
//        final String packageName = appPackageName;
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
//                //                Log.e("app",appPackageName);
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public void inititalize() {
//        Application app = new Application();
//        Log.d("Epg background", "Start up call initialize");
////        context = this;
//        xtreamPanelAPIPresenter = new XtreamPanelAPIPresenter(this, context);
//        liveStreamDBHandler = new LiveStreamDBHandler(context);
//        String username = "";
//        String password = "";
//        if (context != null) {
//            SharedPreferences preferencesIPTV = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, Context.MODE_PRIVATE);
////        preferencesIPTV = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            if (preferencesIPTV != null) {
//                username = preferencesIPTV.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
//                password = preferencesIPTV.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
//                liveStreamDBHandler.makeEmptyAllTablesRecords();
//                Log.e("Application Background", "Database make empty");
//                getEpgs(username, password);
//                getChannelsCategories(context, liveStreamDBHandler, username, password);
//            }
//        }
//    }
//
//
//    private void getEpgs(String username, String password) {
//        if (username != null && password != null &&
//                !username.isEmpty() && !password.isEmpty() &&
//                !username.equals("") && !password.equals("")) {
//            xtreamPanelAPIPresenter.epgXMLTV(username, password);
//            Log.e("Application Background", "Start up getEPG call ");
//        }
//    }
//
//    private void getChannelsCategories(Context context,
//                                       LiveStreamDBHandler liveStreamDBHandler,
//                                       String username,
//                                       String password) {
//        if (username != null && password != null &&
//                !username.isEmpty() && !password.isEmpty() &&
//                !username.equals("") && !password.equals("")) {
//            xtreamPanelAPIPresenter.panelAPI(username, password);
//            Log.e("Application Background", "Start up getChannels channel call ");
//        }
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //stopping the player when service is destroyed
//        if (isRunning) {
////            backgroundThread.interrupt();
////            backgroundThread.stop();
//        }
//    }
//
//    @Override
//    public void atStart() {
//
//    }
//
//    @Override
//    public void onFinish() {
//
//    }
//
//    @Override
//    public void onFailed(String errorMessage) {
//
//    }
//
//    @Override
//    public void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String username) {
//
//        if (xtreamPanelAPICallback != null && context != null && xtreamPanelAPICallback.getCategories() != null &&
//                xtreamPanelAPICallback.getAvailableChannels() != null) {
//            Log.e("Application Background", "Record Received fom api  ");
//            final ArrayList<PanelMoviePojo> movieList = xtreamPanelAPICallback.getCategories().getMovie();
//            final ArrayList<PanelLivePojo> liveList = xtreamPanelAPICallback.getCategories().getLive();
//            final Map<String, PanelAvailableChannelsPojo> availableChanelsList = (Map<String, PanelAvailableChannelsPojo>) xtreamPanelAPICallback.getAvailableChannels();
//            int totalVod = 0;
//            int totalLive = 0;
//            int totalLiveAndVod = 0;
//
//            if (movieList != null) {
//                totalVod = movieList.size();
//            }
//            if (liveList != null) {
//                totalLive = liveList.size();
//            }
//            if (availableChanelsList != null) {
//                totalLiveAndVod = availableChanelsList.size();
//            }
//            final LiveStreamCategoryIdDBModel movieCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
//            final LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
//            final LiveStreamsDBModel availableChannel = new LiveStreamsDBModel();
//            final int finalTotalLiveAndVod = totalLiveAndVod;
//            class AllChannelsAndVodAsyncTask extends AsyncTask<String, Integer, Boolean> {
//
//                Context mcontext = null;
//
//                final int ITERATIONS = finalTotalLiveAndVod;
//
//                AllChannelsAndVodAsyncTask(Context context) {
//                    mcontext = context;
//                }
//
//                @Override
//                protected Boolean doInBackground(String... params) {
////                    publishProgress(0);
//                    int i = 0;
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
////                        publishProgress(i + 1);
////                        i++;
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addAllAvailableChannel(availableChannel);
//                        }
//                        int channelCount = liveStreamDBHandler.getAvailableChannelsCount();
//                        int epgCounts = liveStreamDBHandler.getEPGCount();
//                    }
//                    Log.e("Application Background", "Channels updated to database");
////                    Toast.makeText(context, "Application Channels updated to databased", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//
//
//                @Override
//                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {
//
//                    }
//                }
//
//                @Override
//                protected void onPreExecute() {
//
//                }
//
//                @Override
//                protected void onPostExecute(Boolean result) {
//                    Log.e("Application Background", "Channels updated to database");
////                    Toast.makeText(context, "Application Channels updated to databased", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            final int finalTotalLive = totalLive;
//            class LiveAsyncTask extends AsyncTask<String, Integer, Boolean> {
//
//                Context mcontext = null;
//
//                final int ITERATIONS = finalTotalLive;
//
//                LiveAsyncTask(Context context) {
//                    mcontext = context;
//                }
//
//                @Override
//                protected Boolean doInBackground(String... params) {
////                    publishProgress(0);
//                    for (int i = 0; i < finalTotalLive; i++) {
//                        liveStreamCategoryIdDBModel.setLiveStreamCategoryID(liveList.get(i).getCategoryId());
//                        liveStreamCategoryIdDBModel.setLiveStreamCategoryName(liveList.get(i).getCategoryName());
////                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addLiveCategories(liveStreamCategoryIdDBModel);
//                        }
//                    }
//                    Log.e("Application Background", "live Categories updated to database");
////                    Toast.makeText(context, "Application Live Categories updated to databased", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//
//
//                @Override
//                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {
////                        float progressCount = progress[0];
////                        float iterations = ITERATIONS;
//                    }
//                }
//
//                @Override
//                protected void onPreExecute() {
//
//                }
//
//                @Override
//                protected void onPostExecute(Boolean result) {
//                    Log.e("Application Background", "LiveStreams Categories updated Successfully!");
////                    Utils.showToast(context, "LiveStreams Categories Imported Successfully!");
//                    if (context != null) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
//                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
//                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//                        } else {
//                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
//                            ourAsyncTask.execute();
//                        }
//                    }
//                }
//            }
//
//
//            final int finalTotalVod = totalVod;
//
//            class VodAsyncTask extends AsyncTask<String, Integer, Boolean> {
//
//                Context mcontext = null;
//
//                final int ITERATIONS = finalTotalVod;
//
//                VodAsyncTask(Context context) {
//                    mcontext = context;
//                }
//
//                @Override
//                protected Boolean doInBackground(String... params) {
////                    publishProgress(0);
//                    for (int i = 0; i < finalTotalVod; i++) {
//                        movieCategoryIdDBModel.setLiveStreamCategoryID(movieList.get(i).getCategoryId());
//                        movieCategoryIdDBModel.setLiveStreamCategoryName(movieList.get(i).getCategoryName());
////                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addMovieCategories(movieCategoryIdDBModel);
//                        }
//                    }
//                    return true;
//                }
//
//
//                @Override
//                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {
//                    }
//                }
//
//                @Override
//                protected void onPreExecute() {
//
//                }
//
//                @Override
//                protected void onPostExecute(Boolean result) {
//                    Log.e("Application Background", "VOD Categories updated Successfully! ");
////                    Utils.showToast(context, "VOD Categories Imported Successfully!");
//                    if (context != null) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
//                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
//                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                        } else {
//                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
//                            ourAsyncTask.execute();
//                        }
//                    }
//                }
//            }
//
//
//            if (finalTotalVod != 0) {
//
//                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
//                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
//                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//                } else {
//                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
//                    ourAsyncTask.execute();
//
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
//                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
//                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//                } else {
//                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
//                    ourAsyncTask.execute();
//
//                }
//            }
//        }
//    }
//
//    @Override
//    public void panelApiFailed(String updateDBFailed) {
//
//    }
//
//
//    @Override
//    public void epgXMLTV(final XMLTVCallback xmltvCallback) {
//        if (xmltvCallback != null && context != null) {
//            class NewAsyncTask extends AsyncTask<String, Integer, Boolean> {
//                Context mcontext = null;
//
//                NewAsyncTask(Context context) {
//                    mcontext = context;
//                }
//
//                @Override
//                protected Boolean doInBackground(String... params) {
//                    Log.e("Epg background", "epg call starts availabale");
//                    for (int i = 0; i < xmltvCallback.programmePojos.size(); i++) {
//                        String epgStart = xmltvCallback.programmePojos.get(i).getStart();
//                        String epgStop = xmltvCallback.programmePojos.get(i).getStop();
//                        String epgDesc = xmltvCallback.programmePojos.get(i).getDesc();
//                        String epgChannel = xmltvCallback.programmePojos.get(i).getChannel();
//                        String epgTitle = xmltvCallback.programmePojos.get(i).getTitle();
////                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addEPG(epgStart, epgStop, epgDesc, epgChannel, epgTitle);
//                        }
//                    }
//                    Log.e("Epg background", "epg call end after setting results in the database");
//                    return true;
//                }
//
//
//                @Override
//                protected void onProgressUpdate(Integer... progress) {
//
//                }
//
//                @Override
//                protected void onPreExecute() {
//
//                }
//
//                @Override
//                protected void onPostExecute(Boolean result) {
//                }
//            }
//            if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
//                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
//                ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            } else {
//                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
//                ourAsyncTask.execute();
//            }
//        }
//    }
//}
