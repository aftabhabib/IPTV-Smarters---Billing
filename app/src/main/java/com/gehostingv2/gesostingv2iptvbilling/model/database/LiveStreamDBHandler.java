package com.gehostingv2.gesostingv2iptvbilling.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelAvailableChannelsPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelLivePojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelMoviePojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiveStreamDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "iptv_live_streams.db";

    //Table Names
    private static final String TABLE_IPTV_LIVE_STREAMS_CATEGORY = "iptv_live_streams_category";
    private static final String TABLE_IPTV_MOVIE_CATEGORY = "iptv_movie_category";
    private static final String TABLE_IPTV_AVAILABLE_CHANNNELS = "iptv_live_streams";
    private static final String TABLE_IPTV_LIVE_CATEGORY = "iptv_live_category";
    private static final String TABLE_IPTV_PASSWORD_STATUS = "iptv_password_status_table";
    private static final String TABLE_IPTV_PASSWORD = "iptv_password_table";
    private static final String TABLE_IPTV_MAG_PORTAL = "iptv_mag_portal_table";
    // EPG table name
    private static final String TABLE_EPG = "epg";

    // Category table name
    private static final String TABLE_IPTV_VOD_STREAMS = "iptv_vod_streams";

    //Database update status table name
    private static final String TABLE_DATABASE_UPDATE_STATUS = "iptv_db_update_status";

    Context context;
    SQLiteDatabase db;


    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY_NAME = "categoryname";
    private static final String KEY_CATEGORY_ID = "categoryID";


    private static final String KEY_ID_MOVIE = "id_movie";
    private static final String KEY_CATEGORY_NAME_MOVIE = "categoryname_movie";
    private static final String KEY_CATEGORY_ID_MOVIE = "categoryID_movie";


    private static final String KEY_ID_PASWORD = "id_password";
    private static final String KEY_PASSWORD_USER_DETAIL = "user_detail";
    private static final String KEY_USER_PASSWORD = "password";


    private static final String KEY_ID_PASWORD_STATUS = "id_password_status";
    private static final String KEY_PASSWORD_STATUS_CAT_ID = "password_status_cat_id";
    private static final String KEY_USER_DETAIL = "password_user_detail";
    private static final String KEY_PASSWORD_STATUS = "password_status";


    private static final String KEY_ID_LIVE = "id_live";
    private static final String KEY_CATEGORY_NAME_LIVE = "categoryname_live";
    private static final String KEY_CATEGORY_ID_LIVE = "categoryID_live";
    private static final String KEY_ID_PARENT_ID = "paent_id";

    ////////////////////////////// Table TABLE_IPTV_AVAILABLE_CHANNNELS Columns names///////////////
    private static final String KEY_ID_LIVE_STREAMS = "id";
    private static final String KEY_NUM_LIVE_STREAMS = "num";
    private static final String KEY_NAME = "name";
    private static final String KEY_STRESM_TYPE = "stream_type";
    private static final String KEY_STREAM_ID = "stream_id";
    private static final String KEY_STREAM_ICON = "stream_icon";
    private static final String KEY_EPG_CHANNEL_ID = "epg_channel_id";
    private static final String KEY_ADDED = "added";
    private static final String KEY_CATEGORY_ID_LIVE_STREAMS = "categoryID";
    private static final String KEY_CUSTOMER_SID = "custom_sid";
    private static final String KEY_TV_ARCHIVE = "tv_archive";
    private static final String KEY_DIRECT_SOURCE = "direct_source";
    private static final String KEY_TV_ARCHIVE_DURATION = "tv_archive_duration";
    private static final String KEY_AVAIL_CHANNEL_TYPE_NAME = "type_name";
    private static final String KEY_AVAIL_CHANNEL_CATEGORY_NAME = "category_name";
    private static final String KEY_AVAIL_CHANNEL_SERIES_NO = "series_no";
    private static final String KEY_AVAIL_CHANNEL_LIVE = "live";
    private static final String KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION = "container_extension";
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////// EPG Table Columns names /////////////////////////////////////////
    private static final String KEY_ID_AUTO_EPG = "id_epg_aut0";
    private static final String KEY_TITLE = "title";
    private static final String KEY_START = "start";
    private static final String KEY_STOP = "stop";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CHANNEL_ID = "channel_id";
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////// Category Table Columns name //////////////////////////////////////
    private static final String KEY_ID_AUTO_VOD = "id_auto_vod";
    private static final String KEY_NUM_VOD = "num_";
    private static final String KEY_NAME_VOD = "name";
    private static final String KEY_STREAMTYPE_VOD = "streamType";
    private static final String KEY_STREAM_ID_VOD = "streamId";
    private static final String KEY_STREAM_ICON_VOD = "streamIcon";
    private static final String KEY_ADDED_VOD = "added";
    private static final String KEY_CATEGORY_ID_VOD = "categoryId";
    private static final String KEY_SERIAL_NO_VOD = "seriesNo";
    private static final String KEY_CONTAINER_EXT_VOD = "containerExtension";
    private static final String KEY_CUSTOM_SID_VOD = "customSid";
    private static final String KEY_DIRECT_SOURCE_VOD = "directSource";
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////CREATE_MAG_PORTAL_TABLE Columns//////////////////////////////////

    private static final String KEY_ID_MAG_PORTAL = "id_auto_mag";
    private static final String KEY_MAG_PORTAL = "mag_portal";

    //////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// Table TABLE_DATABASE_UPDATE_STATUS Columns names///////////////
    private static final String KEY_ID_DB_UPDATE_STATUS = "iptv_db_update_status_id";
    private static final String KEY_DB_STATUS_STATE = "iptv_db_updated_status_state";
    private static final String KEY_DB_LAST_UPDATED_DATE = "iptv_db_updated_status_last_updated_date";
    private static final String KEY_DB_CATEGORY = "iptv_db_updated_status_category";
    private static final String KEY_DB_CATEGORY_ID = "iptv_db_updated_status_category_id";
    ///////////////////////////////////////////////////////////////////////////////////////////////


    String CREATE_LIVE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_LIVE_CATEGORY + "("
            + KEY_ID_LIVE + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_ID_LIVE + " TEXT,"
            + KEY_CATEGORY_NAME_LIVE + " TEXT,"
            + KEY_ID_PARENT_ID + " TEXT"
            + ")";


    String CREATE_MOVIE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_MOVIE_CATEGORY + "("
            + KEY_ID_MOVIE + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_ID_MOVIE + " TEXT,"
            + KEY_CATEGORY_NAME_MOVIE + " TEXT,"
            + KEY_ID_PARENT_ID + " TEXT"

            + ")";


    String CREATE_LIVE_STREAM_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_LIVE_STREAMS_CATEGORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CATEGORY_ID + " TEXT,"
            + KEY_CATEGORY_NAME + " TEXT" + ")";


    String CREATE_PASSWORD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_PASSWORD + "("
            + KEY_ID_PASWORD + " INTEGER PRIMARY KEY,"
            + KEY_PASSWORD_USER_DETAIL + " TEXT,"
            + KEY_USER_PASSWORD + " TEXT" + ")";


    String CREATE_PASSWORD_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_PASSWORD_STATUS + "("
            + KEY_ID_PASWORD_STATUS + " INTEGER PRIMARY KEY,"
            + KEY_PASSWORD_STATUS_CAT_ID + " TEXT,"
            + KEY_USER_DETAIL + " TEXT,"
            + KEY_PASSWORD_STATUS + " TEXT" + ")";

    String CREATE_LIVE_AVAILABLE_CHANELS = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_AVAILABLE_CHANNNELS + "("
            + KEY_ID_LIVE_STREAMS + " INTEGER PRIMARY KEY,"
            + KEY_NUM_LIVE_STREAMS + " TEXT,"
            + KEY_NAME + " TEXT,"
            + KEY_STRESM_TYPE + " TEXT,"
            + KEY_STREAM_ID + " TEXT,"
            + KEY_STREAM_ICON + " TEXT,"
            + KEY_EPG_CHANNEL_ID + " TEXT,"
            + KEY_ADDED + " TEXT,"
            + KEY_CATEGORY_ID_LIVE_STREAMS + " TEXT,"
            + KEY_CUSTOMER_SID + " TEXT,"
            + KEY_TV_ARCHIVE + " TEXT,"
            + KEY_DIRECT_SOURCE + " TEXT,"
            + KEY_TV_ARCHIVE_DURATION + " TEXT,"
            + KEY_AVAIL_CHANNEL_TYPE_NAME + " TEXT,"
            + KEY_AVAIL_CHANNEL_CATEGORY_NAME + " TEXT,"
            + KEY_AVAIL_CHANNEL_SERIES_NO + " TEXT,"
            + KEY_AVAIL_CHANNEL_LIVE + " TEXT,"
            + KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION + " TEXT" + ")";


    String CREATE_EPG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EPG + "("
            + KEY_ID_AUTO_EPG + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_START + " TEXT,"
            + KEY_STOP + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_CHANNEL_ID + " TEXT" + ")";


    String CREATE_VOD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_VOD_STREAMS + "("
            + KEY_ID_AUTO_VOD + " INTEGER PRIMARY KEY,"
            + KEY_NUM_VOD + " TEXT,"
            + KEY_NAME_VOD + " TEXT,"
            + KEY_STREAMTYPE_VOD + " TEXT,"
            + KEY_STREAM_ID_VOD + " TEXT,"
            + KEY_STREAM_ICON_VOD + " TEXT,"
            + KEY_ADDED_VOD + " TEXT,"
            + KEY_CATEGORY_ID_VOD + " TEXT,"
            + KEY_SERIAL_NO_VOD + " TEXT,"
            + KEY_CONTAINER_EXT_VOD + " TEXT,"
            + KEY_CUSTOM_SID_VOD + " TEXT,"
            + KEY_DIRECT_SOURCE_VOD + " TEXT" + ")";

    String CREATE_DB_UPDATED_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DATABASE_UPDATE_STATUS + "("
            + KEY_ID_DB_UPDATE_STATUS + " INTEGER PRIMARY KEY,"
            + KEY_DB_STATUS_STATE + " TEXT,"
            + KEY_DB_LAST_UPDATED_DATE + " TEXT,"
            + KEY_DB_CATEGORY + " TEXT,"
            + KEY_DB_CATEGORY_ID + " TEXT" + ")";

    String CREATE_MAG_PORTAL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_MAG_PORTAL + "("
            + KEY_ID_MAG_PORTAL + " INTEGER PRIMARY KEY,"
            + KEY_MAG_PORTAL + " TEXT" + ")";


    public LiveStreamDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIVE_CATEGORY_TABLE);
        db.execSQL(CREATE_MOVIE_CATEGORY_TABLE);
        db.execSQL(CREATE_EPG_TABLE);
        db.execSQL(CREATE_PASSWORD_TABLE);
        db.execSQL(CREATE_PASSWORD_STATUS_TABLE);
        db.execSQL(CREATE_LIVE_STREAM_CATEGORY_TABLE);
        db.execSQL(CREATE_LIVE_AVAILABLE_CHANELS);
        db.execSQL(CREATE_VOD_TABLE);
        db.execSQL(CREATE_DB_UPDATED_STATUS_TABLE);
        db.execSQL(CREATE_MAG_PORTAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_LIVE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_MOVIE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EPG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_PASSWORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_PASSWORD_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_LIVE_STREAMS_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_AVAILABLE_CHANNNELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_VOD_STREAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATABASE_UPDATE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_MAG_PORTAL);
        onCreate(db);
    }

    public void deleteAndRecreateAllTables() {
        SQLiteDatabase deleteAndRecreateAllTablesDB;
        deleteAndRecreateAllTablesDB = this.getReadableDatabase();
        deleteAndRecreateAllTablesDB = this.getWritableDatabase();
        this.onUpgrade(deleteAndRecreateAllTablesDB, 0, 0);
        deleteAndRecreateAllTablesDB.close(); // Closing database connection
    }

    public void addMagPortal(String magPortalUrl) {
        SQLiteDatabase addMagportalDB;
        addMagportalDB = this.getReadableDatabase();
        addMagportalDB = this.getWritableDatabase();
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAG_PORTAL, magPortalUrl);
        // Inserting Row
        addMagportalDB.insert(TABLE_IPTV_MAG_PORTAL, null, values);
        addMagportalDB.close(); // Closing database connection
    }

    public int getMagportal(String magPortalUrl) {
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_MAG_PORTAL + " WHERE " + KEY_MAG_PORTAL + "='" + magPortalUrl + "'";
//        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_MAG_PORTAL;
//        db = this.getWritableDatabase();
        SQLiteDatabase getMagportalDB;
        try {
            getMagportalDB = this.getReadableDatabase();
            Cursor cursor = getMagportalDB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }
    }

    public void addMovieCategories(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel) {
        SQLiteDatabase addMovieCategoriesDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID_MOVIE, liveStreamCategoryIdDBModel.getLiveStreamCategoryID());
        values.put(KEY_CATEGORY_NAME_MOVIE, liveStreamCategoryIdDBModel.getLiveStreamCategoryName());
        values.put(KEY_ID_PARENT_ID, liveStreamCategoryIdDBModel.getParentId());

        // Inserting Row
        addMovieCategoriesDB.insert(TABLE_IPTV_MOVIE_CATEGORY, null, values);
        addMovieCategoriesDB.close(); // Closing database connection
    }


    public void addMovieCategories(ArrayList<PanelMoviePojo> liveStreamCategoryIdDBModel) {
        SQLiteDatabase addMoviesCategories;
        addMoviesCategories = this.getWritableDatabase();
        addMoviesCategories.beginTransaction();
        ContentValues values = new ContentValues();

        int totalMovie = liveStreamCategoryIdDBModel.size();

        if(totalMovie!=0) {
            for (int i = 0; i < totalMovie; i++) {
                values.put(KEY_CATEGORY_ID_MOVIE, liveStreamCategoryIdDBModel.get(i).getCategoryId());
                values.put(KEY_CATEGORY_NAME_MOVIE, liveStreamCategoryIdDBModel.get(i).getCategoryName());
                values.put(KEY_ID_PARENT_ID, liveStreamCategoryIdDBModel.get(i).getParentId());
                addMoviesCategories.insert(TABLE_IPTV_MOVIE_CATEGORY, null, values);
            }
        }

        addMoviesCategories.setTransactionSuccessful();
        addMoviesCategories.endTransaction();
        addMoviesCategories.close();
    }


    public void addAllAvailableChannel(LiveStreamsDBModel liveStreamsDBModel) {
        SQLiteDatabase addAllAvailableChannelDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NUM_LIVE_STREAMS, liveStreamsDBModel.getNum());
        values.put(KEY_NAME, liveStreamsDBModel.getName());
        values.put(KEY_STRESM_TYPE, liveStreamsDBModel.getStreamType());
        values.put(KEY_STREAM_ID, liveStreamsDBModel.getStreamId());
        values.put(KEY_STREAM_ICON, liveStreamsDBModel.getStreamIcon());
        values.put(KEY_EPG_CHANNEL_ID, liveStreamsDBModel.getEpgChannelId());
        values.put(KEY_ADDED, liveStreamsDBModel.getAdded());
        values.put(KEY_CATEGORY_ID_LIVE_STREAMS, liveStreamsDBModel.getCategoryId());
        values.put(KEY_CUSTOMER_SID, liveStreamsDBModel.getCustomSid());
        values.put(KEY_TV_ARCHIVE, liveStreamsDBModel.getTvArchive());
        values.put(KEY_DIRECT_SOURCE, liveStreamsDBModel.getDirectSource());
        values.put(KEY_TV_ARCHIVE_DURATION, liveStreamsDBModel.getTvArchiveDuration());

        values.put(KEY_AVAIL_CHANNEL_TYPE_NAME, liveStreamsDBModel.getTypeName());
        values.put(KEY_AVAIL_CHANNEL_CATEGORY_NAME, liveStreamsDBModel.getCategoryName());
        values.put(KEY_AVAIL_CHANNEL_SERIES_NO, liveStreamsDBModel.getSeriesNo());
        values.put(KEY_AVAIL_CHANNEL_LIVE, liveStreamsDBModel.getLive());
        values.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, liveStreamsDBModel.getContaiinerExtension());

        // Inserting Row
        addAllAvailableChannelDB.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, values);
        addAllAvailableChannelDB.close(); // Closing database connection
    }


    public void addAllAvailableChannel(Map<String, PanelAvailableChannelsPojo> availableChanelsList) {
        SQLiteDatabase addChannel;
        addChannel = this.getWritableDatabase();
        addChannel.beginTransaction();
        ContentValues values = new ContentValues();

        for (PanelAvailableChannelsPojo availabaleChanelValue : availableChanelsList.values()) {
            if (availabaleChanelValue.getNum() != null)
                values.put(KEY_NUM_LIVE_STREAMS, String.valueOf(availabaleChanelValue.getNum()));
            else
                values.put(KEY_NUM_LIVE_STREAMS, "");

            if (availabaleChanelValue.getName() != null)
                values.put(KEY_NAME, availabaleChanelValue.getName());
            else
                values.put(KEY_NAME, "");

            if (availabaleChanelValue.getStreamType() != null)
                values.put(KEY_STRESM_TYPE, availabaleChanelValue.getStreamType());
            else
                values.put(KEY_STRESM_TYPE, "");

            if (availabaleChanelValue.getStreamId() != null)
                values.put(KEY_STREAM_ID, availabaleChanelValue.getStreamId());
            else
                values.put(KEY_STREAM_ID, "");

            if (availabaleChanelValue.getStreamIcon() != null)
                values.put(KEY_STREAM_ICON, availabaleChanelValue.getStreamIcon());
            else
                values.put(KEY_STREAM_ICON, "");

            if (availabaleChanelValue.getEpgChannelId() != null)
                values.put(KEY_EPG_CHANNEL_ID, availabaleChanelValue.getEpgChannelId());
            else
                values.put(KEY_EPG_CHANNEL_ID, "");

            if (availabaleChanelValue.getAdded() != null)
                values.put(KEY_ADDED, availabaleChanelValue.getAdded());
            else
                values.put(KEY_ADDED, "");

            if (availabaleChanelValue.getCategoryId() != null)
                values.put(KEY_CATEGORY_ID_LIVE_STREAMS, availabaleChanelValue.getCategoryId());
            else
                values.put(KEY_CATEGORY_ID_LIVE_STREAMS, "");

            if (availabaleChanelValue.getCustomSid() != null)
                values.put(KEY_CUSTOMER_SID, availabaleChanelValue.getCustomSid());
            else
                values.put(KEY_CUSTOMER_SID, "");

            if (availabaleChanelValue.getTvArchive() != null)
                values.put(KEY_TV_ARCHIVE, availabaleChanelValue.getTvArchive());
            else
                values.put(KEY_TV_ARCHIVE, "");

            if (availabaleChanelValue.getDirectSource() != null)
                values.put(KEY_DIRECT_SOURCE, availabaleChanelValue.getDirectSource());
            else
                values.put(KEY_DIRECT_SOURCE, "");

            if (availabaleChanelValue.getTvArchiveDuration() != null)
                values.put(KEY_TV_ARCHIVE_DURATION, availabaleChanelValue.getTvArchiveDuration());
            else
                values.put(KEY_TV_ARCHIVE_DURATION, "");


            if (availabaleChanelValue.getTypeName() != null)
                values.put(KEY_AVAIL_CHANNEL_TYPE_NAME, String.valueOf(availabaleChanelValue.getTypeName()));
            else
                values.put(KEY_AVAIL_CHANNEL_TYPE_NAME, "");

            if (availabaleChanelValue.getCategoryName() != null)
                values.put(KEY_AVAIL_CHANNEL_CATEGORY_NAME, availabaleChanelValue.getCategoryName());
            else
                values.put(KEY_AVAIL_CHANNEL_CATEGORY_NAME, "");

            if (availabaleChanelValue.getSeriesNo() != null)
                values.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(availabaleChanelValue.getSeriesNo()));
            else
                values.put(KEY_AVAIL_CHANNEL_SERIES_NO, "");

            if (availabaleChanelValue.getLive() != null)
                values.put(KEY_AVAIL_CHANNEL_SERIES_NO, String.valueOf(availabaleChanelValue.getSeriesNo()));
            else
                values.put(KEY_AVAIL_CHANNEL_SERIES_NO,"");


            if (availabaleChanelValue.getLive() != null)
                values.put(KEY_AVAIL_CHANNEL_LIVE, availabaleChanelValue.getLive());
            else
                values.put(KEY_AVAIL_CHANNEL_LIVE, "");

            if (availabaleChanelValue.getContainerExtension() != null)
                values.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION, String.valueOf(availabaleChanelValue.getContainerExtension()));
            else
                values.put(KEY_AVAIL_CHANNEL_CONTAINER_EXTENSION,"");

            addChannel.insert(TABLE_IPTV_AVAILABLE_CHANNNELS, null, values);

        }
        addChannel.setTransactionSuccessful();
        addChannel.endTransaction();
        addChannel.close();

    }




    public ArrayList<LiveStreamCategoryIdDBModel> getAllliveCategories() {  // String categoryId
        ArrayList<LiveStreamCategoryIdDBModel> categoryListLive = new ArrayList<LiveStreamCategoryIdDBModel>();
        // Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_LIVE_CATEGORY + " INNER JOIN " + TABLE_IPTV_AVAILABLE_CHANNNELS + " ON " + TABLE_IPTV_LIVE_CATEGORY + "." + KEY_CATEGORY_ID_LIVE + " = " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " ORDER BY " + TABLE_IPTV_LIVE_CATEGORY + "." + KEY_ID_LIVE;
//        String selectQuery = "SELECT * FROM " + TABLE_IPTV_LIVE_CATEGORY ;
        SQLiteDatabase getAllliveCategoriesDB;
        try {
            getAllliveCategoriesDB = this.getReadableDatabase();
            Cursor cursor = getAllliveCategoriesDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel liveStreamObj = new LiveStreamCategoryIdDBModel();
                    liveStreamObj.setId(Integer.parseInt(cursor.getString(0)));
                    liveStreamObj.setLiveStreamCategoryID((cursor.getString(1)));
                    liveStreamObj.setLiveStreamCategoryName((cursor.getString(2)));
                    categoryListLive.add(liveStreamObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryListLive;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }

    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategories() {  // String categoryId
        ArrayList<LiveStreamCategoryIdDBModel> categoryListLive = new ArrayList<LiveStreamCategoryIdDBModel>();

//        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " INNER JOIN " +
//                TABLE_IPTV_AVAILABLE_CHANNNELS + " ON " + TABLE_IPTV_MOVIE_CATEGORY + "." +
//                KEY_CATEGORY_ID_MOVIE + " = " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." +
//                KEY_CATEGORY_ID_LIVE_STREAMS + " GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." +
//                KEY_CATEGORY_ID_LIVE_STREAMS + " ORDER BY " + TABLE_IPTV_MOVIE_CATEGORY + "." +
//                KEY_ID_MOVIE;

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY;

        try {
            SQLiteDatabase getAllMovieCategoriesDB;
            getAllMovieCategoriesDB = this.getReadableDatabase();
            Cursor cursor = getAllMovieCategoriesDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel movieStreamObj = new LiveStreamCategoryIdDBModel();
                    movieStreamObj.setId(Integer.parseInt(cursor.getString(0)));
                    movieStreamObj.setLiveStreamCategoryID((cursor.getString(1)));
                    movieStreamObj.setLiveStreamCategoryName((cursor.getString(2)));
                    movieStreamObj.setParentId(Integer.parseInt(cursor.getString(3)));
                    categoryListLive.add(movieStreamObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryListLive;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }

    // Getting All Live Streams
    public int getAvailableChannelsCount() {  // String categoryId
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS;
        SQLiteDatabase getAvailableChannelsCountDB;
        try {
            getAvailableChannelsCountDB = this.getReadableDatabase();
            Cursor cursor = getAvailableChannelsCountDB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }

    }

    public int getLiveStreamsCount(String catID) {  // String categoryId
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + "='" + catID + "'";
        SQLiteDatabase getLiveStreamsCountDB;
        try {
            getLiveStreamsCountDB = this.getReadableDatabase();
            Cursor cursor = getLiveStreamsCountDB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }

    }

    // Adding Epg
    public void addEPG(String epgStart, String epgStop, String epgDesc, String epgChannel, String epgTitle) {
        SQLiteDatabase addEPGDB;
        addEPGDB = this.getReadableDatabase();
        addEPGDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, epgTitle);
        values.put(KEY_START, epgStart);
        values.put(KEY_STOP, epgStop);
        values.put(KEY_DESCRIPTION, epgDesc);
        values.put(KEY_CHANNEL_ID, epgChannel);

        // Inserting Row
        addEPGDB.insert(TABLE_EPG, null, values);
        addEPGDB.close(); // Closing database connection
    }


    public void addEPG(List<XMLTVProgrammePojo> programmePojos) {
        SQLiteDatabase addEPGData;
        addEPGData = this.getWritableDatabase();
        addEPGData.beginTransaction();
        ContentValues values = new ContentValues();

        int totalPrograms = programmePojos.size();

        if(totalPrograms!=0) {
            for (int i = 0; i < totalPrograms; i++) {
                values.put(KEY_TITLE, programmePojos.get(i).getTitle());
                values.put(KEY_START, programmePojos.get(i).getStart());
                values.put(KEY_STOP, programmePojos.get(i).getStop());
                values.put(KEY_DESCRIPTION, programmePojos.get(i).getDesc());
                values.put(KEY_CHANNEL_ID, programmePojos.get(i).getChannel());
                addEPGData.insert(TABLE_EPG, null, values);
            }
        }

        addEPGData.setTransactionSuccessful();
        addEPGData.endTransaction();
        addEPGData.close();
    }


    public ArrayList<XMLTVProgrammePojo> getEPG(String channelID) {
        ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos = new ArrayList<XMLTVProgrammePojo>();
        String selectQuery = "SELECT  * FROM " + TABLE_EPG + " WHERE " + KEY_CHANNEL_ID + "='" + channelID + "'";
//        String selectQuery = "SELECT  * FROM " + TABLE_EPG ;
        SQLiteDatabase getEPGDB;
        try {
            getEPGDB = this.getReadableDatabase();
            Cursor cursor = getEPGDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    XMLTVProgrammePojo favourite = new XMLTVProgrammePojo();
                    favourite.setTitle(cursor.getString(1));
                    favourite.setStart((cursor.getString(2)));
                    favourite.setStop(cursor.getString(3));
                    favourite.setDesc((cursor.getString(4)));
                    favourite.setChannel((cursor.getString(5)));
                    // Adding epg to list
                    xmltvProgrammePojos.add(favourite);
                } while (cursor.moveToNext());
            }
            cursor.close();
            // return epg list
            return xmltvProgrammePojos;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }


    public int getEPGCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EPG;
        SQLiteDatabase getEPGCountDB;
        try {
            getEPGCountDB = this.getReadableDatabase();
            Cursor cursor = getEPGCountDB.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }

    }



    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategoriesHavingParentIdZero() {  // String categoryId
        ArrayList<LiveStreamCategoryIdDBModel> categoryListLive = new ArrayList<LiveStreamCategoryIdDBModel>();

//        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " INNER JOIN " + TABLE_IPTV_AVAILABLE_CHANNNELS + " ON " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_CATEGORY_ID_MOVIE + " = " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " ORDER BY " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_ID_MOVIE;

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " WHERE " + KEY_ID_PARENT_ID + "=0";

        try {
            SQLiteDatabase getAllMovieCategoriesDB;
            getAllMovieCategoriesDB = this.getReadableDatabase();
            Cursor cursor = getAllMovieCategoriesDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel movieStreamObj = new LiveStreamCategoryIdDBModel();
                    movieStreamObj.setId(Integer.parseInt(cursor.getString(0)));
                    movieStreamObj.setLiveStreamCategoryID((cursor.getString(1)));
                    movieStreamObj.setLiveStreamCategoryName((cursor.getString(2)));
                    movieStreamObj.setParentId(Integer.parseInt(cursor.getString(3)));
                    categoryListLive.add(movieStreamObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryListLive;

        } catch (SQLiteDatabaseLockedException e) {
            return null;

        }catch (SQLiteException e) {
            return null;
        }

    }


    public ArrayList<LiveStreamCategoryIdDBModel> getAllMovieCategoriesHavingParentIdNotZero(String getActiveLiveStreamCategoryId) {  // String categoryId
        ArrayList<LiveStreamCategoryIdDBModel> categoryListLive = new ArrayList<LiveStreamCategoryIdDBModel>();

//        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " INNER JOIN " + TABLE_IPTV_AVAILABLE_CHANNNELS + " ON " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_CATEGORY_ID_MOVIE + " = " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " ORDER BY " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_ID_MOVIE;

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " WHERE " + KEY_ID_PARENT_ID + "=" + getActiveLiveStreamCategoryId;

        try {
            SQLiteDatabase getAllMovieCategoriesDB;
            getAllMovieCategoriesDB = this.getReadableDatabase();
            Cursor cursor = getAllMovieCategoriesDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel movieStreamObj = new LiveStreamCategoryIdDBModel();
                    movieStreamObj.setId(Integer.parseInt(cursor.getString(0)));
                    movieStreamObj.setLiveStreamCategoryID((cursor.getString(1)));
                    movieStreamObj.setLiveStreamCategoryName((cursor.getString(2)));
                    movieStreamObj.setParentId(Integer.parseInt(cursor.getString(3)));
                    categoryListLive.add(movieStreamObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return categoryListLive;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }
        catch (SQLiteException e) {
            return null;
        }

    }



    public ArrayList<LiveStreamsDBModel> getAllLiveStreasWithCategoryId(String cateogryId, String type) {  // String categoryId
        SharedPreferences sort_prefrence = context.getSharedPreferences(AppConst.LOGIN_PREF_SORT,Context.MODE_PRIVATE);
        String sort = sort_prefrence.getString(AppConst.LOGIN_PREF_SORT,"");

        if (cateogryId.equals("0")) {
            ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
            // Select All Query
            String selectQuery;



            if(sort.equals("0")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'";
            }
            else if(sort.equals("1")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'" + " ORDER BY " + KEY_ADDED + " DESC";
            }
            else if(sort.equals("3")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'" + " ORDER BY " + KEY_NAME + " DESC";
            }
            else{
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'" + " ORDER BY " + KEY_NAME + " ASC";
            }

            // selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'" + " ORDER BY " + KEY_ADDED + " DESC";

            //selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'";



//            String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'";

            SQLiteDatabase getAllLiveStreasWithCategoryIdDB;

            try {
                getAllLiveStreasWithCategoryIdDB = this.getReadableDatabase();

                Cursor cursor = getAllLiveStreasWithCategoryIdDB.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                        liveStreamsDBModel.setNum((cursor.getString(1)));
                        liveStreamsDBModel.setName((cursor.getString(2)));
                        liveStreamsDBModel.setStreamType((cursor.getString(3)));
                        liveStreamsDBModel.setStreamId((cursor.getString(4)));
                        liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                        liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                        liveStreamsDBModel.setAdded((cursor.getString(7)));
                        liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                        liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                        liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                        liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                        liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));

                        liveStreamsDBModel.setTypeName((cursor.getString(13)));
                        liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                        liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                        liveStreamsDBModel.setLive((cursor.getString(16)));
                        liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                        liveStreamsList.add(liveStreamsDBModel);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                return liveStreamsList;
            } catch (SQLiteDatabaseLockedException e) {
                return null;
            }catch (SQLiteException e) {
                return null;
            }

        }

        else if(cateogryId.equals("null")) {
            ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
            // Select All Query


            String selectQuery;



            if(sort.equals("0")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" ;
            }
            else if(sort.equals("1")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";
            }
            else if(sort.equals("3")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_NAME + " DESC";
            }
            else{
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_NAME + " ASC";
            }

//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";

            // selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";




//            if (type == "movie") {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";
//            } else {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";
//            }


//            String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";  //KEY_CATEGORY_ID

            SQLiteDatabase getAllLiveStreasWithCategoryIdDB;

            try {
                getAllLiveStreasWithCategoryIdDB = this.getReadableDatabase();
                Cursor cursor = getAllLiveStreasWithCategoryIdDB.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                        liveStreamsDBModel.setNum((cursor.getString(1)));
                        liveStreamsDBModel.setName((cursor.getString(2)));
                        liveStreamsDBModel.setStreamType((cursor.getString(3)));
                        liveStreamsDBModel.setStreamId((cursor.getString(4)));
                        liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                        liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                        liveStreamsDBModel.setAdded((cursor.getString(7)));
                        liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                        liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                        liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                        liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                        liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));


                        liveStreamsDBModel.setTypeName((cursor.getString(13)));
                        liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                        liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                        liveStreamsDBModel.setLive((cursor.getString(16)));
                        liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                        liveStreamsList.add(liveStreamsDBModel);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return liveStreamsList;

            } catch (SQLiteDatabaseLockedException e) {
                return null;
            }catch (SQLiteException e) {
                return null;
            }


        }





        else {
            ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
            // Select All Query

            String selectQuery;


            if(sort.equals("0")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" ;

            }
            else if(sort.equals("1")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";

            }
            else if(sort.equals("3")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_NAME + " DESC";
            }
            else{
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_NAME + " ASC";

            }

//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";



            //  selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";






//            String selectQuery;
//            if (type == "movie") {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";
//            } else {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";
//            }


//            String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";  //KEY_CATEGORY_ID

            SQLiteDatabase getAllLiveStreasWithCategoryIdDB;

            try {
                getAllLiveStreasWithCategoryIdDB = this.getReadableDatabase();
                Cursor cursor = getAllLiveStreasWithCategoryIdDB.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                        liveStreamsDBModel.setNum((cursor.getString(1)));
                        liveStreamsDBModel.setName((cursor.getString(2)));
                        liveStreamsDBModel.setStreamType((cursor.getString(3)));
                        liveStreamsDBModel.setStreamId((cursor.getString(4)));
                        liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                        liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                        liveStreamsDBModel.setAdded((cursor.getString(7)));
                        liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                        liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                        liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                        liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                        liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));


                        liveStreamsDBModel.setTypeName((cursor.getString(13)));
                        liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                        liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                        liveStreamsDBModel.setLive((cursor.getString(16)));
                        liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                        liveStreamsList.add(liveStreamsDBModel);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return liveStreamsList;

            } catch (SQLiteDatabaseLockedException e) {
                return null;
            }catch (SQLiteException e) {
                return null;
            }


        }

    }

    public void addLiveCategories(LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel) {
        SQLiteDatabase addLiveCategoriesDB;
        addLiveCategoriesDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_ID_LIVE, liveStreamCategoryIdDBModel.getLiveStreamCategoryID());
        values.put(KEY_CATEGORY_NAME_LIVE, liveStreamCategoryIdDBModel.getLiveStreamCategoryName());
        values.put(KEY_ID_PARENT_ID, liveStreamCategoryIdDBModel.getParentId());

        // Inserting Row
        addLiveCategoriesDB.insert(TABLE_IPTV_LIVE_CATEGORY, null, values);
        addLiveCategoriesDB.close(); // Closing database connection
    }


    public void addLiveCategories(ArrayList<PanelLivePojo> liveStreamCategoryIdDBModel) {
        SQLiteDatabase addLiveCategories;
        addLiveCategories = this.getWritableDatabase();
        addLiveCategories.beginTransaction();
        ContentValues values = new ContentValues();

        int totalLive = liveStreamCategoryIdDBModel.size();

        if(totalLive!=0) {
            for (int i = 0; i < totalLive; i++) {
                values.put(KEY_CATEGORY_ID_LIVE, liveStreamCategoryIdDBModel.get(i).getCategoryId());
                values.put(KEY_CATEGORY_NAME_LIVE, liveStreamCategoryIdDBModel.get(i).getCategoryName());
                values.put(KEY_ID_PARENT_ID, liveStreamCategoryIdDBModel.get(i).getParentId());
                addLiveCategories.insert(TABLE_IPTV_LIVE_CATEGORY, null, values);

            }
        }

        addLiveCategories.setTransactionSuccessful();
        addLiveCategories.endTransaction();
        addLiveCategories.close();
    }


    // Getting  Single live Streams favourite row
    public LiveStreamsDBModel getLiveStreamFavouriteRow(String cateogryId, String streamId) {  // String categoryId
        ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + "='" + cateogryId + "'"
                + " AND " + KEY_STREAM_ID + "='" + streamId + "'";

        SQLiteDatabase getLiveStreamFavouriteRow;
        try {
            getLiveStreamFavouriteRow = this.getReadableDatabase();
            Cursor cursor = getLiveStreamFavouriteRow.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
            if (cursor.moveToFirst()) {
                do {
                    liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                    liveStreamsDBModel.setNum((cursor.getString(1)));
                    liveStreamsDBModel.setName((cursor.getString(2)));
                    liveStreamsDBModel.setStreamType((cursor.getString(3)));
                    liveStreamsDBModel.setStreamId((cursor.getString(4)));
                    liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                    liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                    liveStreamsDBModel.setAdded((cursor.getString(7)));
                    liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                    liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                    liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                    liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                    liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));

                    liveStreamsDBModel.setTypeName((cursor.getString(13)));
                    liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                    liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                    liveStreamsDBModel.setLive((cursor.getString(16)));
                    liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return liveStreamsDBModel;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }

    public int getDBStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATABASE_UPDATE_STATUS;
        SQLiteDatabase getDBStatusCount;
        try {
            getDBStatusCount = this.getReadableDatabase();
            Cursor cursor = getDBStatusCount.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }
    }

    public DatabaseUpdatedStatusDBModel getdateDBStatus(String category,
                                                        String categoryid) {
        String query = "SELECT * FROM " + TABLE_DATABASE_UPDATE_STATUS + " WHERE "
                + KEY_DB_CATEGORY + " = '" + category + "'" + " AND "
                + KEY_DB_CATEGORY_ID + " = '" + categoryid + "'";
        SQLiteDatabase getdateDBStatus;
        try {
            getdateDBStatus = this.getReadableDatabase();
            String rowId = "";
            Cursor res = getdateDBStatus.rawQuery(query, null);
            DatabaseUpdatedStatusDBModel updatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
            if (res.moveToFirst()) {
                do {
                    updatedStatusDBModel.setIdAuto(Integer.parseInt(res.getString(0)));
                    updatedStatusDBModel.setDbUpadatedStatusState(res.getString(1));
                    updatedStatusDBModel.setDbLastUpdatedDate((res.getString(2)));
                    updatedStatusDBModel.setDbCategory((res.getString(3)));
                    updatedStatusDBModel.setDbCategoryID((res.getString(4)));
//                listDBStatus.add(updatedStatusDBModel);
                } while (res.moveToNext());
            }
            res.close();
            return updatedStatusDBModel;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }

    public boolean updateDBStatus(String category,
                                  String categoryid,
                                  String updatedDBStatus) {
        String query = "SELECT rowid FROM " + TABLE_DATABASE_UPDATE_STATUS + " WHERE "
                + KEY_DB_CATEGORY + " = '" + category + "'" + " AND "
                + KEY_DB_CATEGORY_ID + " = '" + categoryid + "'";
        SQLiteDatabase updateDBStatus;
        updateDBStatus = this.getReadableDatabase();
        updateDBStatus = this.getWritableDatabase();
        String rowId = "";
        Cursor res = updateDBStatus.rawQuery(query, null);
        if (res != null) {
            if (res.moveToFirst()) {
                do {
                    rowId = String.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(KEY_ID_DB_UPDATE_STATUS)))); // if your column name is rowid then replace id with rowid
                } while (res.moveToNext());
            }
        } else {
            if (context != null)
                Toast.makeText(context, "cursor is null", Toast.LENGTH_LONG).show();
        }
        if (!rowId.equals("")) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_DB_STATUS_STATE, updatedDBStatus);
//            cv.put(KEY_DB_LAST_UPDATED_DATE, upadtedDate);
            updateDBStatus.update(TABLE_DATABASE_UPDATE_STATUS, cv, KEY_ID_DB_UPDATE_STATUS + "= ?", new String[]{rowId});
            if (res != null) {
                res.close();
            }
            return true;
        }
        if (res != null) {
            res.close();
        }
        return false;
    }

    public void makeEmptyChanelsRecord() {
        SQLiteDatabase makeEmptyChanelsRecordDB;
        makeEmptyChanelsRecordDB = this.getWritableDatabase();
        makeEmptyChanelsRecordDB.execSQL("delete from " + TABLE_IPTV_AVAILABLE_CHANNNELS);
        makeEmptyChanelsRecordDB.close();
    }

    public boolean updateDBStatusAndDate(String category,
                                         String categoryid,
                                         String updatedDBStatus,
                                         String upadtedDate) {
        String query = "SELECT rowid FROM " + TABLE_DATABASE_UPDATE_STATUS + " WHERE "
                + KEY_DB_CATEGORY + " = '" + category + "'" + " AND "
                + KEY_DB_CATEGORY_ID + " = '" + categoryid + "'";
        SQLiteDatabase updateDBStatusAndDateDB;
        updateDBStatusAndDateDB = this.getReadableDatabase();
        updateDBStatusAndDateDB = this.getWritableDatabase();
        String rowId = "";
        Cursor res = updateDBStatusAndDateDB.rawQuery(query, null);
        if (res != null) {
            if (res.moveToFirst()) {
                do {
                    rowId = String.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(KEY_ID_DB_UPDATE_STATUS)))); // if your column name is rowid then replace id with rowid
                } while (res.moveToNext());
            }
        } else {
            if (context != null)
                Toast.makeText(context, "cursor is null", Toast.LENGTH_LONG).show();
        }
        if (!rowId.equals("")) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_DB_STATUS_STATE, updatedDBStatus);
            cv.put(KEY_DB_LAST_UPDATED_DATE, upadtedDate);
            updateDBStatusAndDateDB.update(TABLE_DATABASE_UPDATE_STATUS, cv, KEY_ID_DB_UPDATE_STATUS + "= ?", new String[]{rowId});
            if (res != null) {
                res.close();
            }
            return true;
        }
        if (res != null) {
            res.close();
        }
        return false;
    }

    public void makeEmptyLiveCategory() {
        SQLiteDatabase makeEmptyLiveCategoryDB;
        makeEmptyLiveCategoryDB = this.getReadableDatabase();
        makeEmptyLiveCategoryDB = this.getWritableDatabase();
        makeEmptyLiveCategoryDB.execSQL("delete from " + TABLE_IPTV_LIVE_CATEGORY);
        makeEmptyLiveCategoryDB.close();
    }

    public void makeEmptyMovieCategory() {
        SQLiteDatabase makeEmptyMovieCategoryDB;
        makeEmptyMovieCategoryDB = this.getReadableDatabase();
        makeEmptyMovieCategoryDB = this.getWritableDatabase();
        makeEmptyMovieCategoryDB.execSQL("delete from " + TABLE_IPTV_MOVIE_CATEGORY);
        makeEmptyMovieCategoryDB.close();
    }

    public void makeEmptyEPG() {
        SQLiteDatabase makeEmptyEPGDB;
        makeEmptyEPGDB = this.getReadableDatabase();
        makeEmptyEPGDB = this.getWritableDatabase();
        makeEmptyEPGDB.execSQL("delete from " + TABLE_EPG);
        makeEmptyEPGDB.close();
    }

    public void addDBUpdatedStatus(DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel) {
        SQLiteDatabase addDBUpdatedStatusDB;
        addDBUpdatedStatusDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DB_STATUS_STATE, databaseUpdatedStatusDBModel.getDbUpadatedStatusState());
        values.put(KEY_DB_LAST_UPDATED_DATE, databaseUpdatedStatusDBModel.getDbLastUpdatedDate());
        values.put(KEY_DB_CATEGORY, databaseUpdatedStatusDBModel.getDbCategory());
        values.put(KEY_DB_CATEGORY_ID, databaseUpdatedStatusDBModel.getGetDbCategoryID());
        // Inserting Row
        addDBUpdatedStatusDB.insert(TABLE_DATABASE_UPDATE_STATUS, null, values);
        addDBUpdatedStatusDB.close(); // Closing database connection
    }

    public void makeEmptyAllTablesRecords() {
        SQLiteDatabase makeEmptyAllTablesRecordsDB;
        makeEmptyAllTablesRecordsDB = this.getReadableDatabase();
        makeEmptyAllTablesRecordsDB = this.getWritableDatabase();
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_LIVE_CATEGORY);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_MOVIE_CATEGORY);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_EPG);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_AVAILABLE_CHANNNELS);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_DATABASE_UPDATE_STATUS);
        makeEmptyAllTablesRecordsDB.close();
    }


    public void makeEmptyAllChannelsVODTablesRecords() {
        SQLiteDatabase makeEmptyAllTablesRecordsDB;
        makeEmptyAllTablesRecordsDB = this.getReadableDatabase();
        makeEmptyAllTablesRecordsDB = this.getWritableDatabase();
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_LIVE_CATEGORY);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_MOVIE_CATEGORY);
        makeEmptyAllTablesRecordsDB.execSQL("delete from " + TABLE_IPTV_AVAILABLE_CHANNNELS);
        makeEmptyAllTablesRecordsDB.close();
    }


    // Getting All Live Streams Favourites
    public ArrayList<PasswordStatusDBModel> getAllPasswordStatus() {  // String categoryId
        ArrayList<PasswordStatusDBModel> passwordStatusDBModelArrayList = new ArrayList<PasswordStatusDBModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_IPTV_PASSWORD_STATUS;
        SQLiteDatabase getAllPasswordStatusDB;
        try {
            getAllPasswordStatusDB = this.getReadableDatabase();
            Cursor cursor = getAllPasswordStatusDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
                    passwordStatusDBModel.setIdPaswordStaus(Integer.parseInt(cursor.getString(0)));
                    passwordStatusDBModel.setPasswordStatusCategoryId(cursor.getString(1));
                    passwordStatusDBModel.setPasswordStatusUserDetail((cursor.getString(2)));
                    passwordStatusDBModel.setPasswordStatus((cursor.getString(3)));
                    // Adding favourite to list
                    passwordStatusDBModelArrayList.add(passwordStatusDBModel);
                } while (cursor.moveToNext());
            }

            cursor.close();

            return passwordStatusDBModelArrayList;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }


    // Adding new Favourite
    public void addPassword(PasswordDBModel passwordDBModel) {
        SQLiteDatabase addPasswordDB;
        addPasswordDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD_USER_DETAIL, passwordDBModel.getUserDetail());
        values.put(KEY_USER_PASSWORD, passwordDBModel.getUserPassword());
        // Inserting Row
        addPasswordDB.insert(TABLE_IPTV_PASSWORD, null, values);
        addPasswordDB.close(); // Closing database connection
    }


    // Adding new Favourite
    public void addPasswordStatus(PasswordStatusDBModel passwordStatusDBModel) {
        SQLiteDatabase addPasswordStatusDB;
        addPasswordStatusDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD_STATUS_CAT_ID, passwordStatusDBModel.getPasswordStatusCategoryId());
        values.put(KEY_USER_DETAIL, passwordStatusDBModel.getPasswordStatusUserDetail());
        values.put(KEY_PASSWORD_STATUS, passwordStatusDBModel.getPasswordStatus());
        // Inserting Row
        addPasswordStatusDB.insert(TABLE_IPTV_PASSWORD_STATUS, null, values);
        addPasswordStatusDB.close(); // Closing database connection
    }


    public PasswordStatusDBModel getPasswordStatus(String userDetail,
                                                   String categoryID) {
        String query = "SELECT * FROM " + TABLE_IPTV_PASSWORD_STATUS + " WHERE "
                + KEY_USER_DETAIL + " = '" + userDetail + "'" + " AND "
                + KEY_PASSWORD_STATUS_CAT_ID + " = '" + categoryID + "'";
        SQLiteDatabase getPasswordStatusDB;
        try {
            getPasswordStatusDB = this.getReadableDatabase();
            String rowId = "";
            Cursor res = getPasswordStatusDB.rawQuery(query, null);
            PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
            if (res.moveToFirst()) {
                do {
                    passwordStatusDBModel.setIdPaswordStaus(Integer.parseInt(res.getString(0)));
                    passwordStatusDBModel.setPasswordStatusCategoryId(res.getString(1));
                    passwordStatusDBModel.setPasswordStatusUserDetail((res.getString(2)));
                    passwordStatusDBModel.setPasswordStatus((res.getString(3)));
//                listDBStatus.add(updatedStatusDBModel);
                } while (res.moveToNext());
            }

            res.close();

            return passwordStatusDBModel;

        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }


    public boolean updatePasswordStatus(String username,
                                        String categoryid,
                                        String updatePasswordStatus) {
        String query = "SELECT rowid FROM " + TABLE_IPTV_PASSWORD_STATUS + " WHERE "
                + KEY_USER_DETAIL + " = '" + username + "'" + " AND "
                + KEY_PASSWORD_STATUS_CAT_ID + " = '" + categoryid + "'";
        SQLiteDatabase updatePasswordStatusDB;
        updatePasswordStatusDB = this.getReadableDatabase();
        updatePasswordStatusDB = this.getWritableDatabase();
        String rowId = "";
        Cursor res = updatePasswordStatusDB.rawQuery(query, null);
        if (res != null) {
            if (res.moveToFirst()) {
                do {
                    rowId = String.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(KEY_ID_PASWORD_STATUS)))); // if your column name is rowid then replace id with rowid
                } while (res.moveToNext());
            }
        } else {
            if (context != null)
                Toast.makeText(context, "cursor is null", Toast.LENGTH_LONG).show();
        }
        if (!rowId.equals("")) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_PASSWORD_STATUS, updatePasswordStatus);
//            cv.put(KEY_DB_LAST_UPDATED_DATE, upadtedDate);
            updatePasswordStatusDB.update(TABLE_IPTV_PASSWORD_STATUS, cv, KEY_ID_PASWORD_STATUS + "= ?", new String[]{rowId});
            if (res != null) {
                res.close();
            }
            return true;
        }
        if (res != null) {
            res.close();
        }
        return false;
    }


    // Getting All Live Streams Favourites
    public ArrayList<PasswordDBModel> getAllPassword() {  // String categoryId
        ArrayList<PasswordDBModel> passwordList = new ArrayList<PasswordDBModel>();
        // Select All Query

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_PASSWORD;

        SQLiteDatabase getAllPasswordDB;
        try {
            getAllPasswordDB = this.getReadableDatabase();
            Cursor cursor = getAllPasswordDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    PasswordDBModel passwordDBModel = new PasswordDBModel();
                    passwordDBModel.setId(Integer.parseInt(cursor.getString(0)));
                    passwordDBModel.setUserDetail((cursor.getString(1)));
                    passwordDBModel.setUserPassword((cursor.getString(2)));
                    // Adding favourite to list
                    passwordList.add(passwordDBModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return passwordList;
        } catch (SQLiteDatabaseLockedException e) {
            return null;
        }

    }


    public boolean upDatePassword(String userName, String newPassword) {
        String query = "SELECT rowid FROM " + TABLE_IPTV_PASSWORD + " WHERE "
                + KEY_PASSWORD_USER_DETAIL + " = '" + userName + "'";
        SQLiteDatabase upDatePasswordDB;
        upDatePasswordDB = this.getReadableDatabase();
        upDatePasswordDB = this.getWritableDatabase();
        String rowId = "";
        Cursor res = upDatePasswordDB.rawQuery(query, null);
        if (res != null) {
            if (res.moveToFirst()) {
                do {
                    rowId = String.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(KEY_ID_PASWORD)))); // if your column name is rowid then replace id with rowid
                } while (res.moveToNext());
            }
        } else {
            if (context != null)
                Toast.makeText(context, "cursor is null", Toast.LENGTH_LONG).show();
        }
        if (!rowId.equals("")) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_USER_PASSWORD, newPassword);
            upDatePasswordDB.update(TABLE_IPTV_PASSWORD, cv, KEY_ID_PASWORD + "= ?", new String[]{rowId});
            if (res != null) {
                res.close();
            }
            return true;
        }
        if (res != null) {
            res.close();
        }
        return false;
    }


    public void makeEmptyStatus() {
        SQLiteDatabase makeEmptyStatusDB;
        makeEmptyStatusDB = this.getReadableDatabase();
        makeEmptyStatusDB = this.getWritableDatabase();
        makeEmptyStatusDB.execSQL("delete from " + TABLE_DATABASE_UPDATE_STATUS);
        makeEmptyStatusDB.close();
    }

    public int getParentalStatusCount() {
        SQLiteDatabase getParentalStatusCountDB;
        String countQuery = "SELECT  * FROM " + TABLE_IPTV_PASSWORD_STATUS;
        try {
            getParentalStatusCountDB = this.getReadableDatabase();
            Cursor cursor = getParentalStatusCountDB.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }
    }

    public int getStreamsCount(String type) {  // String categoryId
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'";
        SQLiteDatabase getAvailableChannelsCountDB;
        try {
            getAvailableChannelsCountDB = this.getReadableDatabase();
            Cursor cursor = getAvailableChannelsCountDB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }catch (SQLiteException e) {
            return 0;
        }

    }


    public int getMovieCategoryCount() {
        SQLiteDatabase getParentalStatusCountDB;
        String countQuery = "SELECT  * FROM " + TABLE_IPTV_MOVIE_CATEGORY;
        try {
            getParentalStatusCountDB = this.getReadableDatabase();
            Cursor cursor = getParentalStatusCountDB.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            cursor.close();
            return cnt;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }catch (SQLiteException e) {
            return 0;
        }
    }

    public ArrayList<LiveStreamCategoryIdDBModel> getMovieCategoriesinRange(int startLimit,
                                                                            int endLimit) {  // String categoryId
        ArrayList<LiveStreamCategoryIdDBModel> categoryListLive = new ArrayList<LiveStreamCategoryIdDBModel>();

//        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " INNER JOIN " + TABLE_IPTV_AVAILABLE_CHANNNELS + " ON " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_CATEGORY_ID_MOVIE + " = " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " GROUP BY " + TABLE_IPTV_AVAILABLE_CHANNNELS + "." + KEY_CATEGORY_ID_LIVE_STREAMS + " ORDER BY " + TABLE_IPTV_MOVIE_CATEGORY + "." + KEY_ID_MOVIE;


        SQLiteDatabase getAllMovieCategoriesDB;
        getAllMovieCategoriesDB = this.getReadableDatabase();
        getAllMovieCategoriesDB.beginTransaction();

        String selectQuery = "SELECT * FROM " + TABLE_IPTV_MOVIE_CATEGORY + " WHERE " + KEY_ID_MOVIE + ">=" + startLimit +" AND "
                + KEY_ID_MOVIE + "<=" + endLimit ;

        try {

            Cursor cursor = getAllMovieCategoriesDB.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    LiveStreamCategoryIdDBModel movieStreamObj = new LiveStreamCategoryIdDBModel();
                    movieStreamObj.setId(Integer.parseInt(cursor.getString(0)));
                    movieStreamObj.setLiveStreamCategoryID((cursor.getString(1)));
                    movieStreamObj.setLiveStreamCategoryName((cursor.getString(2)));
                    movieStreamObj.setParentId(Integer.parseInt(cursor.getString(3)));
                    categoryListLive.add(movieStreamObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            getAllMovieCategoriesDB.setTransactionSuccessful();
            getAllMovieCategoriesDB.endTransaction();
            return categoryListLive;

        } catch (SQLiteDatabaseLockedException e) {
            return null;

        }catch (SQLiteException e) {
            return null;
        }

    }



    public ArrayList<LiveStreamsDBModel> getAllLiveStreamsArchive(String cateogryId) {  // String categoryId
        SharedPreferences sort_prefrence = context.getSharedPreferences(AppConst.LOGIN_PREF_SORT,Context.MODE_PRIVATE);
        String sort = sort_prefrence.getString(AppConst.LOGIN_PREF_SORT,"");
        if (cateogryId.equals("0")) {
            ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
            // Select All Query
            String selectQuery;


            if(sort.equals("0")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' " ;
            }
            else if(sort.equals("1")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' ORDER BY " + KEY_ADDED + " DESC";
            }
            else if(sort.equals("3")){
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' ORDER BY " + KEY_NAME + " DESC";
            }
            else{
                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' ORDER BY " + KEY_NAME + " ASC";
            }



            //  selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' ORDER BY " + KEY_ADDED + " DESC";



//            String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'";

            SQLiteDatabase getAllLiveStreasWithCategoryIdDB;

            try {
                getAllLiveStreasWithCategoryIdDB = this.getReadableDatabase();

                Cursor cursor = getAllLiveStreasWithCategoryIdDB.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                        liveStreamsDBModel.setNum((cursor.getString(1)));
                        liveStreamsDBModel.setName((cursor.getString(2)));
                        liveStreamsDBModel.setStreamType((cursor.getString(3)));
                        liveStreamsDBModel.setStreamId((cursor.getString(4)));
                        liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                        liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                        liveStreamsDBModel.setAdded((cursor.getString(7)));
                        liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                        liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                        liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                        liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                        liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));

                        liveStreamsDBModel.setTypeName((cursor.getString(13)));
                        liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                        liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                        liveStreamsDBModel.setLive((cursor.getString(16)));
                        liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                        liveStreamsList.add(liveStreamsDBModel);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                return liveStreamsList;
            } catch (SQLiteDatabaseLockedException e) {
                return null;
            }catch (SQLiteException e) {
                return null;
            }

        } else {
            ArrayList<LiveStreamsDBModel> liveStreamsList = new ArrayList<LiveStreamsDBModel>();
            // Select All Query


            String selectQuery;
//            if (type == "movie") {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'" + " ORDER BY " + KEY_ADDED + " DESC";
//            } else {
//                selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";
//            }
            selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='live'" + " AND " + KEY_TV_ARCHIVE + "='1' AND "+KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "' AND "+KEY_EPG_CHANNEL_ID+" IS NOT NULL AND "+KEY_EPG_CHANNEL_ID+" !='' ORDER BY " + KEY_ADDED + " DESC";



//            String selectQuery = "SELECT  * FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_CATEGORY_ID_LIVE_STREAMS + " ='" + cateogryId + "'";  //KEY_CATEGORY_ID

            SQLiteDatabase getAllLiveStreasWithCategoryIdDB;

            try {
                getAllLiveStreasWithCategoryIdDB = this.getReadableDatabase();
                Cursor cursor = getAllLiveStreasWithCategoryIdDB.rawQuery(selectQuery, null);

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        LiveStreamsDBModel liveStreamsDBModel = new LiveStreamsDBModel();
                        liveStreamsDBModel.setIdAuto(Integer.parseInt(cursor.getString(0)));
                        liveStreamsDBModel.setNum((cursor.getString(1)));
                        liveStreamsDBModel.setName((cursor.getString(2)));
                        liveStreamsDBModel.setStreamType((cursor.getString(3)));
                        liveStreamsDBModel.setStreamId((cursor.getString(4)));
                        liveStreamsDBModel.setStreamIcon((cursor.getString(5)));
                        liveStreamsDBModel.setEpgChannelId((cursor.getString(6)));
                        liveStreamsDBModel.setAdded((cursor.getString(7)));
                        liveStreamsDBModel.setCategoryId((cursor.getString(8)));
                        liveStreamsDBModel.setCustomSid((cursor.getString(9)));
                        liveStreamsDBModel.setTvArchive((cursor.getString(10)));
                        liveStreamsDBModel.setDirectSource((cursor.getString(11)));
                        liveStreamsDBModel.setTvArchiveDuration((cursor.getString(12)));


                        liveStreamsDBModel.setTypeName((cursor.getString(13)));
                        liveStreamsDBModel.setCategoryName((cursor.getString(14)));
                        liveStreamsDBModel.setSeriesNo((cursor.getString(15)));
                        liveStreamsDBModel.setLive((cursor.getString(16)));
                        liveStreamsDBModel.setContaiinerExtension((cursor.getString(17)));
                        liveStreamsList.add(liveStreamsDBModel);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return liveStreamsList;

            } catch (SQLiteDatabaseLockedException e) {
                return null;
            }catch (SQLiteException e) {
                return null;
            }


        }

    }


    public int getSubCatMovieCount(String categoryID, String type ) {  // String categoryId
        String selectQuery = "SELECT  COUNT(*) FROM " + TABLE_IPTV_AVAILABLE_CHANNNELS + " WHERE " + KEY_STRESM_TYPE + "='" + type + "'" + " AND " + KEY_CATEGORY_ID_LIVE_STREAMS  + "='" + categoryID + "'";
        SQLiteDatabase getAvailableChannelsCountDB;
        try {
            getAvailableChannelsCountDB = this.getReadableDatabase();
            Cursor cursor = getAvailableChannelsCountDB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (SQLiteDatabaseLockedException e) {
            return 0;
        }catch (SQLiteException e) {
            return 0;
        }

    }

}

