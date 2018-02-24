package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static android.content.Context.MODE_PRIVATE;


public class EPGUtil {
    private static final String TAG = "EPGUtil";
    private static  DateTimeFormatter dtfShortTime = null ;
    private static Picasso picasso = null;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static Context context1;

    public static String getShortTime(Context context ,long timeMillis) {
        context1=context;
        loginPreferencesSharedPref_time_format = context1.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
        String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");

        dtfShortTime = DateTimeFormat.forPattern(timeFormat);
        return dtfShortTime.print(timeMillis);
    }

    public static String getWeekdayName(long dateMillis) {
        LocalDate date = new LocalDate(dateMillis);
        return date.dayOfWeek().getAsText();
    }

    public static String getEPGdayName(long dateMillis) {
        LocalDate date = new LocalDate(dateMillis);
        return date.dayOfWeek().getAsShortText() + " " + date.getDayOfMonth() + "/" + date.getMonthOfYear();
    }

    public static void loadImageInto(Context context, String url, int width, int height, Target target) {
        initPicasso(context);

        if(url==null || url.equals("")){

//            Drawable d = context.getResources().getDrawable(R.drawable.iptv_placeholder);
//            ImageView image = (ImageView)findViewById(R.id.image);
//            image.setImageDrawable(d);

//            context.getResources().getDrawable(R.drawable.iptv_placeholder, null);
//            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.icon);
//            Drawable myIcon = context.getResources().getDrawable( R.drawable.iptv_placeholder);
//            url = String.valueOf(myIcon);
            picasso.load(R.drawable.iptv_placeholder).into(target);

        }else {
            picasso.load(url)
                    .resize(width, height)
                    .centerInside()
                    .into(target);
        }
    }

    private static void initPicasso(Context context) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(new OkHttpClient()))
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Log.e(TAG, exception.getMessage());
                        }
                    })
                    .build();
        }
    }

}
