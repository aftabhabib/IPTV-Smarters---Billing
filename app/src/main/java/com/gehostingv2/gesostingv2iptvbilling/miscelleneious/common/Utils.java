package com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.model.database.ExpandedMenuModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.AccountInfoActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportEPGActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.InvoicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveStreamEpgAcitivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveTVListViewActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveTVTabViewActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWelcomeActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.MxPlayerArchiveActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.OpenTicketActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.OrderNewServicesActivtiy;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ServicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.SettingssActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TVArchiveActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TicketsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VLCPlayerArchiveActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VodTabViewActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VodListViewActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.nstplayer.NSTPlayerActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.nstplayer.NSTPlayerArchiveActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.nstplayer.NSTPlayerVodActivity;
import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.R;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.MxPlayerLiveStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.MxPlayerVodActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VLCPlayerLiveStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.VLCPlayerVodActivity;

import org.joda.time.LocalDateTime;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    private static SharedPreferences loginPreferences_layout;

    /***
     * This method is used to convert date received in
     * Mon Jun 18 00:00:00 IST 2012 format and change it to
     * dd/mm/yyyy format
     *
     * @param time is date in string format from server
     * @return expected date in string format
     */


//    public static String parseDateToddMMyyyy(String time) {
//        String dateStr = "Mon Jun 18 00:00:00 IST 2012";
//        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
//        Date date = null;
//        try {
//            date = (Date) formatter.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
//        System.out.println("formatedDate : " + formatedDate);
//        return formatedDate;
//    }
    public static String parseDateToddMMyyyy(String time) {
        String updatedDate = "";
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try {
            Date date1 = df.parse(time);
            DateFormat outputFormatter1 = new SimpleDateFormat("dd/MM/yyyy");
            updatedDate = outputFormatter1.format(date1); //
        } catch (ParseException e) {
            e.printStackTrace();
            return updatedDate;
        }
        return updatedDate;
    }


    /**
     * used to convert the date format YYYY-MM-DD to DD-MM-YYYY
     *
     * @param receivedDateFormat is parameter received in string yyyy-MM-dd HH:mm:ss
     * @return the date format into dd-MM-yyyy
     */

    public static String dateToPars(String receivedDateFormat) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDateFormat = null;
        try {
            convertedDateFormat = (Date) formatter.parse(receivedDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertedDateFormat);
        String formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
        return formatedDate;
    }

    public static ProgressDialog showProgressD(ProgressDialog progressDialog, Context context) {
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            return progressDialog;
        }
        return progressDialog;
    }

    public static ProgressDialog showProgressDWithSpinner(ProgressDialog progressDialog, Context context) {
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            return progressDialog;
        }
        return progressDialog;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static AlertDialog loadTVGuid(final Context context, String message) {
        if (context != null && !message.isEmpty()) {
            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
            alertDialog1.setTitle(context.getResources().getString(R.string.confirm_refresh));
            alertDialog1.setMessage(context.getResources().getString(R.string.proceed));
            alertDialog1.setIcon(R.drawable.questionmark);
            alertDialog1.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    loadTvGuid(context);
                }
            });

            alertDialog1.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog1.show();
        }
        return null;
    }

    private static void loadTvGuid(Context context) {
        if (context != null) {
            boolean isChannelEPGUpdating = false;
            isChannelEPGUpdating = getEPGUpdateStatus();
            if (isChannelEPGUpdating) {
                SharedPreferences loginPreferencesAfterLogin;
                SharedPreferences.Editor loginPrefsEditor;
                loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                loginPrefsEditor = loginPreferencesAfterLogin.edit();
                if (loginPrefsEditor != null) {
                    loginPrefsEditor.putString(AppConst.SKIP_BUTTON_PREF_IPTV, "autoLoad");
                    loginPrefsEditor.commit();
                    String skipButton = loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF_IPTV, "");
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
                    liveStreamDBHandler.makeEmptyEPG();
                    context.startActivity(new Intent(context, ImportEPGActivity.class));
                }
            } else {
                if (context != null)
                    Utils.showToast(context, context.getResources().getString(R.string.upadating_tv_guide));
            }
        }
    }

    private static boolean getEPGUpdateStatus() {
        return true;
    }


    public static JsonObject jsonDataToSend(JsonObject postParam) {
        postParam.addProperty("api_username", AppConst.API_USERNAME);
        postParam.addProperty("api_password", AppConst.API_PASSWORD);
        return postParam;
    }

    public static void showToast(Context context, String message) {
        if (context != null && message != "" && !message.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static Retrofit retrofitObjectWHMCS() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL_WHMCS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit retrofitObjectIPTV() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit retrofitObjectIPTVWithTimeRaise() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit retrofitObjectXML() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

//    public static Retrofit retrofitObjectIPTVWithTimeRaiseRx() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(AppConst.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        return retrofit;
//    }


    public static String generateHashSHA512(String stringToGenHash) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(stringToGenHash.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("Hex format : " + hexString.toString());
        return hexString.toString();
    }

    public static boolean getNetworkType(Context context) {
        boolean networkType = false;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkType = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    networkType = true;
                }
            } else {
                return networkType;// not connected to the internet
            }
            return networkType;
        }
        return networkType;
    }

    public static int getNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public static long epgTimeConverter(String str) {
        int i = 0;
        if (str == null) {
            return 0;
        }
        try {
            if (str.length() >= 18) {
                i = Integer.parseInt(str.substring(str.charAt(15) == '+' ? 16 : 15, 18)) * 60;
            }
            if (str.length() >= 19) {
                i += Integer.parseInt(str.substring(18));
            }
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateTimeFormat.parse(str.substring(0, 14)).getTime() - ((long) ((i * 60) * 1000));
        } catch (Throwable e) {
            Log.e("XMLTVReader", "Exception", e);
            return 0;
        }
    }

    public static void playWithPlayer(Context context,
                                      String selectedPlayer,
                                      int streamId,
                                      String streamType,
                                      String num,
                                      String name,
                                      String epgChannelId,
                                      String epgChannelLogo
    ) {
        if (context != null) {
            if (selectedPlayer.equals(context.getResources().getString(R.string.vlc_player))) {
                Intent VlcPlayerIntent = new Intent(context, VLCPlayerLiveStreamsActivity.class);
                VlcPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                VlcPlayerIntent.putExtra("STREAM_TYPE", streamType);
                context.startActivity(VlcPlayerIntent);

            } else if (selectedPlayer.equals(context.getResources().getString(R.string.mx_player))) {
                Intent MxPlayerIntent = new Intent(context, MxPlayerLiveStreamsActivity.class);
                MxPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                MxPlayerIntent.putExtra("STREAM_TYPE", streamType);
                context.startActivity(MxPlayerIntent);
            } else {

                Intent NSTPlayerIntent = new Intent(context, NSTPlayerActivity.class);
                NSTPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                NSTPlayerIntent.putExtra("STREAM_TYPE", streamType);
                int index = Integer.parseInt(num);
                NSTPlayerIntent.putExtra("VIDEO_NUM", index);
                NSTPlayerIntent.putExtra("VIDEO_TITLE", name);
                NSTPlayerIntent.putExtra("EPG_CHANNEL_ID", epgChannelId);
                NSTPlayerIntent.putExtra("EPG_CHANNEL_LOGO", epgChannelLogo);
                context.startActivity(NSTPlayerIntent);
            }
        }
    }

    public static void playWithPlayerVOD(Context context,
                                         String selectedPlayer,
                                         int streamId,
                                         String streamType,
                                         String containerExtension,
                                         String num,
                                         String name) {
        if (context != null) {
            if (selectedPlayer.equals(context.getResources().getString(R.string.vlc_player))) {

                Intent VlcPlayerIntent = new Intent(context, VLCPlayerVodActivity.class);
                VlcPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                VlcPlayerIntent.putExtra("STREAM_TYPE", streamType);
                VlcPlayerIntent.putExtra("CONTAINER_EXTENSION", containerExtension);
                context.startActivity(VlcPlayerIntent);


            } else if (selectedPlayer.equals(context.getResources().getString(R.string.mx_player))) {
                Intent MxPlayerIntent = new Intent(context, MxPlayerVodActivity.class);
                MxPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                MxPlayerIntent.putExtra("STREAM_TYPE", streamType);
                MxPlayerIntent.putExtra("CONTAINER_EXTENSION", containerExtension);
                context.startActivity(MxPlayerIntent);
            } else {
                Intent NSTPlayerIntent = new Intent(context, NSTPlayerVodActivity.class);

                NSTPlayerIntent.putExtra("VIDEO_ID", streamId);
                NSTPlayerIntent.putExtra("VIDEO_TITLE", name);
                NSTPlayerIntent.putExtra("EXTENSION_TYPE", containerExtension);
                int index = Integer.parseInt(num);
                NSTPlayerIntent.putExtra("VIDEO_NUM", index);
                context.startActivity(NSTPlayerIntent);


//                NSTPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
//                NSTPlayerIntent.putExtra("STREAM_TYPE", streamType);
//                int index = 0;
//                if (num != null)
//                    index = Integer.parseInt(num.trim());
//                NSTPlayerIntent.putExtra("VIDEO_NUM", index);
//                NSTPlayerIntent.putExtra("VIDEO_TITLE", name);

//                context.startActivity(NSTPlayerIntent);
            }
        }
    }

    public static Retrofit retrofitObject(Context context) {
        if (context != null) {
//                SharedPreferences loginPreferencesSharedPref;
//                loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, MODE_PRIVATE);
//                String serverUrl = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
////                boolean flag = URLUtil.isValidUrl(serverUrl);
//                if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
//                    serverUrl = "http://" + serverUrl;
//                }
//                if(serverUrl.endsWith("/c")){
//                    serverUrl = serverUrl.substring(0, serverUrl.length() - 2);
//                }
//                if(!serverUrl.endsWith("/")){
//                    serverUrl = serverUrl+"/";
//                }
//                boolean flag = Patterns.WEB_URL.matcher(serverUrl).matches();
//
//                if (flag) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }
//        }
        return null;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        str = str.trim();
        return TextUtils.isEmpty(str);
    }

    public static int getPositionOfEPG(String string) {
        int position = 10;
        switch (string) {
            case "-10":
                position = 0;
                break;
            case "-9":
                position = 1;
                break;
            case "-8":
                position = 2;
                break;
            case "-7":
                position = 3;
                break;
            case "-6":
                position = 4;
                break;
            case "-5":
                position = 5;
                break;
            case "-4":
                position = 6;
                break;
            case "-3":
                position = 7;
                break;
            case "-2":
                position = 8;
                break;
            case "-1":
                position = 9;
                break;
            case "0":
                position = 10;
                break;
            case "+1":
                position = 11;
                break;
            case "+2":
                position = 12;
                break;
            case "+3":
                position = 13;
                break;
            case "+4":
                position = 14;
                break;
            case "+5":
                position = 15;
                break;
            case "+6":
                position = 16;
                break;
            case "+7":
                position = 17;
                break;
            case "+8":
                position = 18;
                break;
            case "+9":
                position = 19;
                break;
            case "+10":
                position = 20;
                break;
        }
        return position;
    }

    public static int getMilliSeconds(String epgShift) {
        int milliseconds = 0;
        if (epgShift.contains("+")) {
            String hours[] = epgShift.split("\\+");
            milliseconds = Integer.parseInt(hours[1]) * 60 * 60 * 1000;
        } else if (epgShift.contains("-")) {
            String hours[] = epgShift.split("\\-");
            milliseconds = -Integer.parseInt(hours[1]) * 60 * 60 * 1000;
        }
        return milliseconds;
    }

    public static boolean isEventVisible(final long start, final long end) {
        long currentTime = LocalDateTime.now().toDateTime().getMillis();
        if (start <= currentTime && end >= currentTime) {
            return true;
        }
        return false;
    }


    public static void playWithPlayerArchive(Context context, String selectedPlayer, int streamId, String num, String name, String epgChannelId, String epgChannelLogo, String getStartFormatedTime, String streamChannelDuration, String getStopTime) {
        if (context != null) {
            if (selectedPlayer.equals(context.getResources().getString(R.string.vlc_player))) {

                Intent VlcPlayerIntent = new Intent(context, VLCPlayerArchiveActivity.class);
                VlcPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                VlcPlayerIntent.putExtra("STREAM_TYPE", "live");

                VlcPlayerIntent.putExtra("STREAM_START_TIME", getStartFormatedTime);
                VlcPlayerIntent.putExtra("STREAM_STOP_TIME", getStopTime);
                context.startActivity(VlcPlayerIntent);
            } else if (selectedPlayer.equals(context.getResources().getString(R.string.mx_player))) {
                Intent MxPlayerIntent = new Intent(context, MxPlayerArchiveActivity.class);
                MxPlayerIntent.putExtra("OPENED_STREAM_ID", streamId);
                MxPlayerIntent.putExtra("STREAM_TYPE", "live");

                MxPlayerIntent.putExtra("STREAM_START_TIME", getStartFormatedTime);
                MxPlayerIntent.putExtra("STREAM_STOP_TIME", getStopTime);
                context.startActivity(MxPlayerIntent);
            } else {
                Intent NSTPlayerArchiveIntent = new Intent(context, NSTPlayerArchiveActivity.class);
                NSTPlayerArchiveIntent.putExtra("OPENED_STREAM_ID", streamId);
                NSTPlayerArchiveIntent.putExtra("STREAM_TYPE", "live");
                int index = Integer.parseInt(num);
                NSTPlayerArchiveIntent.putExtra("VIDEO_NUM", index);
                NSTPlayerArchiveIntent.putExtra("VIDEO_TITLE", name);
                NSTPlayerArchiveIntent.putExtra("EPG_CHANNEL_ID", epgChannelId);
                NSTPlayerArchiveIntent.putExtra("EPG_CHANNEL_LOGO", epgChannelLogo);
                NSTPlayerArchiveIntent.putExtra("STREAM_START_TIME", getStartFormatedTime);
                NSTPlayerArchiveIntent.putExtra("STREAM_DURATION", streamChannelDuration);
                NSTPlayerArchiveIntent.putExtra("STREAM_STOP_TIME", getStopTime);
                context.startActivity(NSTPlayerArchiveIntent);
            }
        }
    }

    public static void set_layout_live(Context context) {
        loginPreferences_layout = context.getSharedPreferences(AppConst.LOGIN_PREF_LIVE_VOD_LAYOUT, MODE_PRIVATE);
        int layout_flag = loginPreferences_layout.getInt(AppConst.LOGIN_PREF_LIVE_VOD_LAYOUT, 0);
        //     AppConst.FLAG_LAYOUT_FLOW = layout_flag;


        if (layout_flag == 1) {
            Intent activity_old_flow = new Intent(context, LiveTVTabViewActivity.class);
            context.startActivity(activity_old_flow);
        } else {
            Intent activity_newflow = new Intent(context, LiveTVListViewActivity.class);
            context.startActivity(activity_newflow);
        }
    }

    public static void set_layout_vod(Context context) {
        loginPreferences_layout = context.getSharedPreferences(AppConst.LOGIN_PREF_LIVE_VOD_LAYOUT, MODE_PRIVATE);
        int layout_flag = loginPreferences_layout.getInt(AppConst.LOGIN_PREF_LIVE_VOD_LAYOUT, 0);
        //     AppConst.FLAG_LAYOUT_FLOW = layout_flag;
        if (layout_flag == 1) {
            Intent activity_old_flow = new Intent(context, VodTabViewActivity.class);
            context.startActivity(activity_old_flow);
        } else {
            Intent activity_newflow = new Intent(context, VodListViewActivity.class);
            context.startActivity(activity_newflow);
        }
    }


    public static List<ExpandedMenuModel> drawerMenuHeaderList(Context context) {
        List<ExpandedMenuModel> listDataHeader = new ArrayList<ExpandedMenuModel>();
        HashMap<ExpandedMenuModel, List<String>> listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        if (context != null) {
            ExpandedMenuModel homeHeader = new ExpandedMenuModel();
            homeHeader.setIconName(context.getString(R.string.drawer_home));
            homeHeader.setIconImg(R.drawable.drawer_home);
            listDataHeader.add(homeHeader);

            ExpandedMenuModel liveTv = new ExpandedMenuModel();
            liveTv.setIconName(context.getString(R.string.drawer_live_tv));
            liveTv.setIconImg(R.drawable.livetv_icon); //livetv_icon
            listDataHeader.add(liveTv);

            ExpandedMenuModel liveTvWihGuie = new ExpandedMenuModel();
            liveTvWihGuie.setIconName(context.getString(R.string.drawer_live_tv_guide));
            liveTvWihGuie.setIconImg(R.drawable.guide_icon);
            listDataHeader.add(liveTvWihGuie);

            ExpandedMenuModel accountInfo = new ExpandedMenuModel();
            accountInfo.setIconName(context.getString(R.string.drawer_account_info));
            accountInfo.setIconImg(R.drawable.account_info);
            listDataHeader.add(accountInfo);

            ExpandedMenuModel videoOnDemand = new ExpandedMenuModel();
            videoOnDemand.setIconName(context.getString(R.string.drawer_vod));
            videoOnDemand.setIconImg(R.drawable.vod_icon);
            listDataHeader.add(videoOnDemand);

            ExpandedMenuModel tvArchive = new ExpandedMenuModel();
            tvArchive.setIconName(context.getString(R.string.drawer_tv_archieve));
            tvArchive.setIconImg(R.drawable.tv_archive);
            listDataHeader.add(tvArchive);

            ExpandedMenuModel settings = new ExpandedMenuModel();
            settings.setIconName(context.getString(R.string.drawer_settings));
            settings.setIconImg(R.drawable.settings_icon);
            listDataHeader.add(settings);

            ExpandedMenuModel servicesHeader = new ExpandedMenuModel();
            servicesHeader.setIconName(context.getString(R.string.drawer_services));
            servicesHeader.setIconImg(R.drawable.drawer_services);
            listDataHeader.add(servicesHeader);

            ExpandedMenuModel billingHeader = new ExpandedMenuModel();
            billingHeader.setIconName(context.getString(R.string.drawer_billing));
            billingHeader.setIconImg(R.drawable.drawer_billing);
            listDataHeader.add(billingHeader);

            ExpandedMenuModel supportHeader = new ExpandedMenuModel();
            supportHeader.setIconName(context.getString(R.string.drawer_support));
            supportHeader.setIconImg(R.drawable.drawer_suppport);
            listDataHeader.add(supportHeader);

            ExpandedMenuModel log_out = new ExpandedMenuModel();
            log_out.setIconName(context.getString(R.string.drawer_logout));
            log_out.setIconImg(R.drawable.logout_icon);
            listDataHeader.add(log_out);


            List<String> servicesList = new ArrayList<String>();
            servicesList.add(context.getString(R.string.drawer_services_my_services));
            servicesList.add(context.getString(R.string.drawer_services_order_new_services));

            List<String> billingList = new ArrayList<String>();
            billingList.add(context.getString(R.string.drawer_billing_my_invoices));

            List<String> supportList = new ArrayList<String>();
            supportList.add(context.getString(R.string.drawer_support_ticket));
            supportList.add(context.getString(R.string.drawer_support_open_ticket));
            listDataChild.put(listDataHeader.get(7), servicesList);
            listDataChild.put(listDataHeader.get(8), billingList);
            listDataChild.put(listDataHeader.get(9), supportList);
            return listDataHeader;
        }
        return null;
    }


    public static HashMap<ExpandedMenuModel, List<String>> drawerMenuChildList(Context context) {
        List<ExpandedMenuModel> listDataHeader = new ArrayList<ExpandedMenuModel>();
        HashMap<ExpandedMenuModel, List<String>> listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        if (context != null) {
            ExpandedMenuModel homeHeader = new ExpandedMenuModel();
            homeHeader.setIconName(context.getString(R.string.drawer_home));
            homeHeader.setIconImg(R.drawable.drawer_home);
            listDataHeader.add(homeHeader);

            ExpandedMenuModel liveTv = new ExpandedMenuModel();
            liveTv.setIconName(context.getString(R.string.drawer_live_tv));
            liveTv.setIconImg(R.drawable.livetv_icon); //livetv_icon
            listDataHeader.add(liveTv);

            ExpandedMenuModel liveTvWihGuie = new ExpandedMenuModel();
            liveTvWihGuie.setIconName(context.getString(R.string.drawer_live_tv_guide));
            liveTvWihGuie.setIconImg(R.drawable.guide_icon);
            listDataHeader.add(liveTvWihGuie);

            ExpandedMenuModel accountInfo = new ExpandedMenuModel();
            accountInfo.setIconName(context.getString(R.string.drawer_account_info));
            accountInfo.setIconImg(R.drawable.account_info);
            listDataHeader.add(accountInfo);

            ExpandedMenuModel videoOnDemand = new ExpandedMenuModel();
            videoOnDemand.setIconName(context.getString(R.string.drawer_vod));
            videoOnDemand.setIconImg(R.drawable.vod_icon);
            listDataHeader.add(videoOnDemand);

            ExpandedMenuModel tvArchive = new ExpandedMenuModel();
            tvArchive.setIconName(context.getString(R.string.drawer_tv_archieve));
            tvArchive.setIconImg(R.drawable.tv_archive);
            listDataHeader.add(tvArchive);

            ExpandedMenuModel settings = new ExpandedMenuModel();
            settings.setIconName(context.getString(R.string.drawer_settings));
            settings.setIconImg(R.drawable.settings_icon);
            listDataHeader.add(settings);

            ExpandedMenuModel servicesHeader = new ExpandedMenuModel();
            servicesHeader.setIconName(context.getString(R.string.drawer_services));
            servicesHeader.setIconImg(R.drawable.drawer_services);
            listDataHeader.add(servicesHeader);

            ExpandedMenuModel billingHeader = new ExpandedMenuModel();
            billingHeader.setIconName(context.getString(R.string.drawer_billing));
            billingHeader.setIconImg(R.drawable.drawer_billing);
            listDataHeader.add(billingHeader);

            ExpandedMenuModel supportHeader = new ExpandedMenuModel();
            supportHeader.setIconName(context.getString(R.string.drawer_support));
            supportHeader.setIconImg(R.drawable.drawer_suppport);
            listDataHeader.add(supportHeader);

            ExpandedMenuModel log_out = new ExpandedMenuModel();
            log_out.setIconName(context.getString(R.string.drawer_logout));
            log_out.setIconImg(R.drawable.logout_icon);
            listDataHeader.add(log_out);


            List<String> servicesList = new ArrayList<String>();
            servicesList.add(context.getString(R.string.drawer_services_my_services));
            servicesList.add(context.getString(R.string.drawer_services_order_new_services));

            List<String> billingList = new ArrayList<String>();
            billingList.add(context.getString(R.string.drawer_billing_my_invoices));

            List<String> supportList = new ArrayList<String>();
            supportList.add(context.getString(R.string.drawer_support_ticket));
            supportList.add(context.getString(R.string.drawer_support_open_ticket));

            listDataChild.put(listDataHeader.get(7), servicesList);
            listDataChild.put(listDataHeader.get(8), billingList);
            listDataChild.put(listDataHeader.get(9), supportList);
            return listDataChild;
        }
        return null;
    }


    public static void startActivity(Context context, String selectedItem) {
        if (context != null && selectedItem != null && !selectedItem.isEmpty()) {
            int clientId = -1;
            if (selectedItem.equals(context.getResources().getString(R.string.drawer_home))) {
                context.startActivity(new Intent(context, DashboardActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_services_my_services))) {
                SharedPreferences prefServices = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefServices.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_SERVICES, AppConst.ACTIVITY_SERVICES));
                } else {
                    context.startActivity(new Intent(context, ServicesActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_billing_my_invoices))) {
                SharedPreferences prefInvoice = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefInvoice.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES));
                } else {
                    context.startActivity(new Intent(context, InvoicesActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_services_order_new_services))) {
                SharedPreferences prefOrderNewServices = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefOrderNewServices.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES));
                } else {
                    context.startActivity(new Intent(context, OrderNewServicesActivtiy.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_support_ticket))) {
                SharedPreferences prefTicket = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefTicket.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_TICKETS, AppConst.ACTIVITY_TICKETS));
                } else {
                    context.startActivity(new Intent(context, TicketsActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_settings))) {
                context.startActivity(new Intent(context, SettingssActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_support_open_ticket))) {
                SharedPreferences prefOpenTicket = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefOpenTicket.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_OPEN_TICKETS, AppConst.ACTIVITY_OPEN_TICKETS));
                } else {
                    context.startActivity(new Intent(context, OpenTicketActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_live_tv))) {
                if (context != null)
                    Utils.set_layout_live(context);
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_live_tv_guide))) {
                context.startActivity(new Intent(context, LiveStreamEpgAcitivity.class));
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_account_info))) {
                context.startActivity(new Intent(context, AccountInfoActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_vod))) {
//                context.startActivity(new Intent(context, VodTabViewActivity.class));
                if (context != null)
                    Utils.set_layout_vod(context);
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_tv_archieve))) {
                context.startActivity(new Intent(context, TVArchiveActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_logout))) {
                SharedPreferences loginPreferences;
                SharedPreferences.Editor loginPreferencesEditor;
                loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                loginPreferencesEditor = loginPreferences.edit();
                loginPreferencesEditor.clear();
                loginPreferencesEditor.commit();

                Toast.makeText(context, context.getResources().getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
                Intent intentLogout = new Intent(context, LoginWelcomeActivity.class);
                SharedPreferences loginPreferencesClientid;
                SharedPreferences.Editor loginPreferencesClientidEditor;
                loginPreferencesClientid = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                loginPreferencesClientidEditor = loginPreferencesClientid.edit();
                loginPreferencesClientidEditor.clear();
                loginPreferencesClientidEditor.commit();
                context.startActivity(intentLogout);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        }
    }




    public static void startActivityOtherDash(Context context, String selectedItem) {
        if (context != null && selectedItem != null && !selectedItem.isEmpty()) {
            int clientId = -1;
            if (selectedItem.equals(context.getResources().getString(R.string.drawer_home))) {
                context.startActivity(new Intent(context, DashboardActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_services_my_services))) {
                SharedPreferences prefServices = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefServices.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_SERVICES, AppConst.ACTIVITY_SERVICES));
                } else {
                    context.startActivity(new Intent(context, ServicesActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_billing_my_invoices))) {
                SharedPreferences prefInvoice = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefInvoice.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES));
                } else {
                    context.startActivity(new Intent(context, InvoicesActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_services_order_new_services))) {
                SharedPreferences prefOrderNewServices = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefOrderNewServices.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES));
                } else {
                    context.startActivity(new Intent(context, OrderNewServicesActivtiy.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_support_ticket))) {
                SharedPreferences prefTicket = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefTicket.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_TICKETS, AppConst.ACTIVITY_TICKETS));
                } else {
                    context.startActivity(new Intent(context, TicketsActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_settings))) {
                context.startActivity(new Intent(context, SettingssActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_support_open_ticket))) {
                SharedPreferences prefOpenTicket = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                clientId = prefOpenTicket.getInt(AppConst.CLIENT_ID, -1);
                if (clientId == -1 || clientId == 0) {
                    context.startActivity(new Intent(context, LoginWHMCSActivity.class).putExtra(AppConst.ACTIVITY_OPEN_TICKETS, AppConst.ACTIVITY_OPEN_TICKETS));
                } else {
                    context.startActivity(new Intent(context, OpenTicketActivity.class));
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_live_tv))) {
                if (context != null)
                    Utils.set_layout_live(context);
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_live_tv_guide))) {
                context.startActivity(new Intent(context, DashboardActivity.class).putExtra(AppConst.LAUNCH_TV_GUIDE, AppConst.LAUNCH_TV_GUIDE));
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_account_info))) {
                context.startActivity(new Intent(context, AccountInfoActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_vod))) {
                if (context != null)
                    Utils.set_layout_vod(context);
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_tv_archieve))) {
                context.startActivity(new Intent(context, TVArchiveActivity.class));
            } else if (selectedItem.equals(context.getResources().getString(R.string.drawer_logout))) {
                SharedPreferences loginPreferences;
                SharedPreferences.Editor loginPreferencesEditor;
                loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                loginPreferencesEditor = loginPreferences.edit();
                loginPreferencesEditor.clear();
                loginPreferencesEditor.commit();

                Toast.makeText(context, context.getResources().getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
                Intent intentLogout = new Intent(context, LoginWelcomeActivity.class);
                SharedPreferences loginPreferencesClientid;
                SharedPreferences.Editor loginPreferencesClientidEditor;
                loginPreferencesClientid = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                loginPreferencesClientidEditor = loginPreferencesClientid.edit();
                loginPreferencesClientidEditor.clear();
                loginPreferencesClientidEditor.commit();
                context.startActivity(intentLogout);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        }
    }
}


