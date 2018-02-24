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
//import com.lucassuto.lucassutoiptvbilling.model.database.PasswordStatusDBModel;
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
//public class AutoLoadService extends Service implements XtreamPanelAPIInterface {
//    private Context context;
//    private LiveStreamDBHandler liveStreamDBHandler;
//    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;
//    private boolean isRunning;
//    private Thread backgroundThread;
//    private boolean isAppRunning = false;
//    private Handler mHandler;
//
//
//    public AutoLoadService(Context context) {
//        this.context = context;
//    }
//
//    public AutoLoadService() {
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
//            if (context != null)
//                isBackgroundForeground(context);
////            inititalize();
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
//        if (flag) {
//            if(context!=null)
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
//            if(context!=null)
//                mHandler = new Handler(Looper.getMainLooper()) {
//                    @Override
//                    public void handleMessage(Message message) {
//                        // This is where you do your work in the UI thread.
//                        // Your worker tells you in the message what to do.
//                        Toast.makeText(context, "I'm in Background", Toast.LENGTH_SHORT).show();
//                    }
//                };
//            Log.e("Application :", "is in background");
//        }
//    }
//
//
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
//            }
//            getEpgs(username, password);
//            getChannelsCategories(context, liveStreamDBHandler, username, password);
//        }
//    }
//
//
//    private void getEpgs(String username, String password) {
//        if (username != null && password != null &&
//                !username.isEmpty() && !password.isEmpty() &&
//                !username.equals("") && !password.equals(""))
//            xtreamPanelAPIPresenter.epgXMLTV(username, password);
//    }
//
//    private void getChannelsCategories(Context context,
//                                       LiveStreamDBHandler liveStreamDBHandler,
//                                       String username,
//                                       String password) {
//        if (username != null && password != null &&
//                !username.isEmpty() && !password.isEmpty() &&
//                !username.equals("") && !password.equals(""))
//            xtreamPanelAPIPresenter.panelAPI(username, password);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //stopping the player when service is destroyed
//
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
//        if (context != null) {
//            Log.d("Epg background", "In panel api with categories results");
//            ArrayList<PanelMoviePojo> movieList = xtreamPanelAPICallback.getCategories().getMovie();
//            ArrayList<PanelLivePojo> liveList = xtreamPanelAPICallback.getCategories().getLive();
//            liveStreamDBHandler = new LiveStreamDBHandler(context);
//            String userNameIpTV = username;
//            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
//            PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
//            for (PanelLivePojo liveListItem : liveList) {
//                liveStreamCategoryIdDBModel.setLiveStreamCategoryID(liveListItem.getCategoryId());
//                liveStreamCategoryIdDBModel.setLiveStreamCategoryName(liveListItem.getCategoryName());
//                liveStreamDBHandler.addLiveCategories(liveStreamCategoryIdDBModel);
//                if (!liveListItem.getCategoryId().isEmpty())
//                    passwordStatusDBModel.setPasswordStatusCategoryId(liveListItem.getCategoryId());
//                if (!userNameIpTV.isEmpty() && !userNameIpTV.equals(""))
//                    passwordStatusDBModel.setPasswordStatusUserDetail(userNameIpTV);
//                passwordStatusDBModel.setPasswordStatus("false");
//                liveStreamDBHandler.addPasswordStatus(passwordStatusDBModel);
//            }
//            LiveStreamCategoryIdDBModel movieCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
//            for (PanelMoviePojo moveListItem : movieList) {
//                movieCategoryIdDBModel.setLiveStreamCategoryID(moveListItem.getCategoryId());
//                movieCategoryIdDBModel.setLiveStreamCategoryName(moveListItem.getCategoryName());
//                liveStreamDBHandler.addMovieCategories(movieCategoryIdDBModel);
//            }
//            Map<String, PanelAvailableChannelsPojo> availableChanelsList = (Map<String, PanelAvailableChannelsPojo>) xtreamPanelAPICallback.getAvailableChannels();
//            for (PanelAvailableChannelsPojo availabaleChanelValue : availableChanelsList.values()) {
//                LiveStreamsDBModel availableChannel = new LiveStreamsDBModel();
//                availableChannel.setNum(String.valueOf(availabaleChanelValue.getNum()));
//                availableChannel.setName(availabaleChanelValue.getName());
//                availableChannel.setStreamType(availabaleChanelValue.getStreamType());
//                availableChannel.setStreamId(availabaleChanelValue.getStreamId());
//                availableChannel.setStreamIcon(availabaleChanelValue.getStreamIcon());
//                availableChannel.setEpgChannelId(availabaleChanelValue.getEpgChannelId());
//                availableChannel.setAdded(availabaleChanelValue.getAdded());
//                availableChannel.setCategoryId(availabaleChanelValue.getCategoryId());
//                availableChannel.setCustomSid(availabaleChanelValue.getCustomSid());
//                availableChannel.setTvArchive(String.valueOf(availabaleChanelValue.getTvArchive()));
//                availableChannel.setDirectSource(availabaleChanelValue.getDirectSource());
//                availableChannel.setTvArchiveDuration(availabaleChanelValue.getTvArchiveDuration());
//                availableChannel.setTypeName(String.valueOf(availabaleChanelValue.getTypeName()));
//                availableChannel.setCategoryName(availabaleChanelValue.getCategoryName());
//                availableChannel.setSeriesNo(String.valueOf(availabaleChanelValue.getSeriesNo()));
//                availableChannel.setLive(availabaleChanelValue.getLive());
//                availableChannel.setContaiinerExtension(String.valueOf(availabaleChanelValue.getContainerExtension()));
//                liveStreamDBHandler.addAllAvailableChannel(availableChannel);
//            }
//
//            Log.d("Epg background", "Categories after setting results in the database");
//        }
//    }
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
//                    Log.d("Epg background", "epg call starts availabale");
//                    for (int i = 0; i < xmltvCallback.programmePojos.size(); i++) {
//                        String epgStart = xmltvCallback.programmePojos.get(i).getStart();
//                        String epgStop = xmltvCallback.programmePojos.get(i).getStop();
//                        String epgDesc = xmltvCallback.programmePojos.get(i).getDesc();
//                        String epgChannel = xmltvCallback.programmePojos.get(i).getChannel();
//                        String epgTitle = xmltvCallback.programmePojos.get(i).getTitle();
//                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addEPG(epgStart, epgStop, epgDesc, epgChannel, epgTitle);
//                        }
//                    }
//                    Log.d("Epg background", "epg call end after seting results in the database");
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
