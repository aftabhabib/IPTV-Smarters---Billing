package com.gehostingv2.gesostingv2iptvbilling.model.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "iptv_smarters.db";

    // Products table name
    private static final String TABLE_IPTV_FAVOURITES = "iptv_favourites";

    Context context;
    SQLiteDatabase db;
    // Products Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_STREAM_ID = "streamID";
    private static final String KEY_CATEGORY_ID = "categoryID";

    String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_FAVOURITES + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TYPE + " TEXT,"
            + KEY_STREAM_ID + " TEXT,"
            + KEY_CATEGORY_ID + " TEXT" + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_FAVOURITES);

        onCreate(db);
    }


    // Adding new Favourite
    public void addToFavourite(FavouriteDBModel favourite, String type) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, type);
        values.put(KEY_STREAM_ID, favourite.getStreamID());
        values.put(KEY_CATEGORY_ID, favourite.getCategoryID());

        // Inserting Row
        db.insert(TABLE_IPTV_FAVOURITES, null, values);
        db.close(); // Closing database connection
    }


    // Deleting Favourite
    public void deleteFavourite(int streamID, String categoryID, String type) {
        db = this.getWritableDatabase();
//        db.delete(TABLE_IPTV_FAVOURITES, KEY_ID + " = ?",
//                new String[]{String.valueOf(favourite.getId())});
        db.delete(TABLE_IPTV_FAVOURITES,
                KEY_STREAM_ID + "='" + streamID + "' AND " + KEY_CATEGORY_ID + "='" + categoryID + "' AND " + KEY_TYPE + "='" + type + "'",
                null);
        db.close();

    }

    public void deleteAndRecreateAllTables() {
        db = this.getWritableDatabase();
        this.onUpgrade(db, 0, 0);
        db.close(); // Closing database connection
    }


    // Getting All Live Streams Favourites
    public ArrayList<FavouriteDBModel> getAllFavourites(String type) {
        ArrayList<FavouriteDBModel> favouriteList = new ArrayList<FavouriteDBModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IPTV_FAVOURITES + " WHERE " + KEY_TYPE + "='" + type + "'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavouriteDBModel favourite = new FavouriteDBModel();
                favourite.setId(Integer.parseInt(cursor.getString(0)));
                favourite.setType((cursor.getString(1)));
                favourite.setStreamID(Integer.parseInt(cursor.getString(2)));
                favourite.setCategoryID((cursor.getString(3)));
                // Adding favourite to list
                favouriteList.add(favourite);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return favouriteList;
    }

    // Getting All Live Streams Favourites
    public ArrayList<FavouriteDBModel> checkFavourite(int streamID, String categoryID, String type) {
        ArrayList<FavouriteDBModel> favouriteList = new ArrayList<FavouriteDBModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IPTV_FAVOURITES + " WHERE " + KEY_STREAM_ID + "='" + streamID + "' AND " + KEY_CATEGORY_ID + "='" + categoryID + "' AND " + KEY_TYPE + "='" + type + "'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavouriteDBModel favourite = new FavouriteDBModel();
                favourite.setId(Integer.parseInt(cursor.getString(0)));
                favourite.setType((cursor.getString(1)));
                favourite.setStreamID(Integer.parseInt(cursor.getString(2)));
                favourite.setCategoryID((cursor.getString(3)));
                // Adding favourite to list
                favouriteList.add(favourite);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return favourite list
        return favouriteList;
    }

}


/**
 * Database before update of new flow
 */
//
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 2;
//
//    // Database Name
//    private static final String DATABASE_NAME = "iptv_smarters.db";
//
//    // Products table name
//    private static final String TABLE_IPTV_FAVOURITES = "iptv_favourites";
//
//    Context context;
//    SQLiteDatabase db;
//    // Products Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_TYPE = "type";
//    private static final String KEY_STREAM_ID = "streamID";
//    private static final String KEY_CATEGORY_ID = "categoryID";
//
//    String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IPTV_FAVOURITES + "("
//            + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_TYPE + " TEXT,"
//            + KEY_STREAM_ID + " TEXT,"
//            + KEY_CATEGORY_ID + " TEXT" + ")";
//
//
//    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_PRODUCTS_TABLE);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IPTV_FAVOURITES);
//
//        onCreate(db);
//    }
//
//
//    // Adding new Favourite
//    public void addToFavourite(FavouriteDBModel favourite, String type) {
//        db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TYPE, type);
//        values.put(KEY_STREAM_ID, favourite.getStreamID());
//        values.put(KEY_CATEGORY_ID, favourite.getCategoryID());
//
//        // Inserting Row
//        db.insert(TABLE_IPTV_FAVOURITES, null, values);
//        db.close(); // Closing database connection
//    }
//
//
//    // Deleting Favourite
//    public void deleteFavourite(int streamID, String categoryID , String type) {
//        db = this.getWritableDatabase();
////        db.delete(TABLE_IPTV_FAVOURITES, KEY_ID + " = ?",
////                new String[]{String.valueOf(favourite.getId())});
//        db.delete(TABLE_IPTV_FAVOURITES,
//                KEY_STREAM_ID + "='"+streamID+"' AND " + KEY_CATEGORY_ID + "='"+categoryID+"' AND " + KEY_TYPE + "='"+type+"'",
//                null);
//        db.close();
//
//    }
//
//
//
//    // Getting All Live Streams Favourites
//    public ArrayList<FavouriteDBModel> getAllFavourites(String type) {
//        ArrayList<FavouriteDBModel> favouriteList = new ArrayList<FavouriteDBModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_IPTV_FAVOURITES + " WHERE " + KEY_TYPE + "='"+type+"'";
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                FavouriteDBModel favourite = new FavouriteDBModel();
//                favourite.setId(Integer.parseInt(cursor.getString(0)));
//                favourite.setType((cursor.getString(1)));
//                favourite.setStreamID(Integer.parseInt(cursor.getString(2)));
//                favourite.setCategoryID((cursor.getString(3)));
//                // Adding favourite to list
//                favouriteList.add(favourite);
//            } while (cursor.moveToNext());
//        }
//
//        // return favourite list
//        return favouriteList;
//    }
//
//    // Getting All Live Streams Favourites
//    public ArrayList<FavouriteDBModel> checkFavourite(int streamID, String categoryID, String type) {
//        ArrayList<FavouriteDBModel> favouriteList = new ArrayList<FavouriteDBModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_IPTV_FAVOURITES + " WHERE "+ KEY_STREAM_ID + "='" +streamID+ "' AND " + KEY_CATEGORY_ID + "='" +categoryID +"' AND " + KEY_TYPE + "='"+type+"'";
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                FavouriteDBModel favourite = new FavouriteDBModel();
//                favourite.setId(Integer.parseInt(cursor.getString(0)));
//                favourite.setType((cursor.getString(1)));
//                favourite.setStreamID(Integer.parseInt(cursor.getString(2)));
//                favourite.setCategoryID((cursor.getString(3)));
//                // Adding favourite to list
//                favouriteList.add(favourite);
//            } while (cursor.moveToNext());
//        }
//
//        // return favourite list
//        return favouriteList;
//    }
//
//}