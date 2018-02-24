//package com.lucassuto.lucassutoiptvbilling.miscelleneious.loadlivemovieepgdata;
//
//
//import android.app.ActivityManager;
//import android.app.Application;
//import android.app.IntentService;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.v4.content.WakefulBroadcastReceiver;
//import android.util.Log;
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
//public class NotificationIntentService extends IntentService implements XtreamPanelAPIInterface {
//
//    private static final int NOTIFICATION_ID = 1;
//    private static final String ACTION_START = "ACTION_START";
//    private static final String ACTION_DELETE = "ACTION_DELETE";
//    private Context context;
//    private LiveStreamDBHandler liveStreamDBHandler;
//    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;
//
//    private boolean isRunning;
//    Thread backgroundThread;
//    private boolean isAppRunning = false;
//
//    public NotificationIntentService() {
//        super(NotificationIntentService.class.getSimpleName());
//    }
//
//    public static Intent createIntentStartNotificationService(Context context) {
//        Intent intent = new Intent(context, NotificationIntentService.class);
//        intent.setAction(ACTION_START);
//        return intent;
//    }
//
//    public static Intent createIntentDeleteNotification(Context context) {
//        Intent intent = new Intent(context, NotificationIntentService.class);
//        intent.setAction(ACTION_DELETE);
//        return intent;
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
//        try {
//            String action = intent.getAction();
//            if (ACTION_START.equals(action)) {
//                processStartNotification();
//            }
//            if (ACTION_DELETE.equals(action)) {
//                processDeleteNotification(intent);
//            }
//        } finally {
//            WakefulBroadcastReceiver.completeWakefulIntent(intent);
//        }
//    }
//
//    private void processDeleteNotification(Intent intent) {
//        // Log something?
//    }
//
//    private void processStartNotification() {
//        // Do something. For example, fetch fresh data from backend to create a rich notification?
//
////        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
////        builder.setContentTitle("Scheduled Notification")
////                .setAutoCancel(true)
////                .setColor(getResources().getColor(R.color.colorAccent))
////                .setContentText("This notification has been triggered by Notification Service")
////                .setSmallIcon(R.drawable.notification_icon);
////
////        PendingIntent pendingIntent = PendingIntent.getActivity(this,
////                NOTIFICATION_ID,
////                new Intent(this, NotificationActivity.class),
////                PendingIntent.FLAG_UPDATE_CURRENT);
////        builder.setContentIntent(pendingIntent);
////        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));
////
////        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
////        manager.notify(NOTIFICATION_ID, builder.build());
//    }
//
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
////            MultiDexApplication multiDexApplication = new MultiDexApplication();
//            String applicationStaus = "";
//            boolean flag = false;
//            if (context != null && getPackageName() != null) {
//                isAppRunning = isAppOnForeground(context, getPackageName());
//                isAppRunnig(isAppRunning);
//            }
//
////            flag = Foreground.get().isForeground();
////            if (flag) {
////                applicationStaus = "foreground";
////                Log.e("Activity in background", applicationStaus);
////            } else {
////                applicationStaus = "background";
////                Log.e("Activity in background", applicationStaus);
//////                inititalize();
////            }
//        }
//    };
//
//
//    public void isAppRunnig(boolean flag) {
//        if (flag) {
//            Log.e("Application :", "is in foreground");
//        } else {
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
//    public void inititalize() {
//        LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
//        liveStreamDBHandler.makeEmptyAllTablesRecords();
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
//        if (context != null && xtreamPanelAPICallback != null &&
//                xtreamPanelAPICallback.getCategories() != null &&
//                xtreamPanelAPICallback.getCategories() != null &&
//                xtreamPanelAPICallback.getAvailableChannels() != null) {
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
