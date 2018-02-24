//package com.lucassuto.lucassutoiptvbilling.miscelleneious.loadlivemovieepgdata;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.content.WakefulBroadcastReceiver;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.lucassuto.lucassutoiptvbilling.model.database.LiveStreamDBHandler;
//
//import java.util.Calendar;
//import java.util.Date;
//
//public class NotificationEventReceiver extends WakefulBroadcastReceiver {
//
//    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
//    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
//    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 2;
//    private static Context context1;
//
//    public static void setupAlarm(Context context) {
//        context1 = context;
//        long currentTime = System.currentTimeMillis();
//        long oneMinute = 60 * 1000;
//        long time = currentTime + oneMinute;
//        Toast.makeText(context, " Success ", Toast.LENGTH_SHORT).show();
//        Log.d("Notification", "The Receiver Successful");
//
////        NOTIFICATIONS_INTERVAL_IN_HOURS * AlarmManager.INTERVAL_HOUR
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent alarmIntent = getStartPendingIntent(context);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//                getTriggerAt(new Date()), oneMinute
//                ,
//                alarmIntent);
//    }
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
//        liveStreamDBHandler.makeEmptyAllTablesRecords();
//        AutoLoadService autoLoadService = new AutoLoadService(context1);
//        autoLoadService.inititalize();
//        String action = intent.getAction();
//        Intent serviceIntent = null;
//        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
//            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
//            serviceIntent = NotificationIntentService.createIntentStartNotificationService(context);
//        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
//            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
//            serviceIntent = NotificationIntentService.createIntentDeleteNotification(context);
//        }
//
//        if (serviceIntent != null) {
//            startWakefulService(context, serviceIntent);
//        }
//    }
//
//    private static long getTriggerAt(Date now) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
//        return calendar.getTimeInMillis();
//    }
//
//    private static PendingIntent getStartPendingIntent(Context context) {
//        Intent intent = new Intent(context, NotificationEventReceiver.class);
//        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
//        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    public static PendingIntent getDeleteIntent(Context context) {
//        Intent intent = new Intent(context, NotificationEventReceiver.class);
//        intent.setAction(ACTION_DELETE_NOTIFICATION);
//        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//}