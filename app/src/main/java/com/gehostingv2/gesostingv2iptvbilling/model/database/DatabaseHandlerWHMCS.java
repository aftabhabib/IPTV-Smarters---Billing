//package com.lucassuto.lucassutoiptvbilling.model.database;
//
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.whmcs.app.model.viewcartdbmodel.AddToCart;
//import com.whmcs.app.model.viewcartdbmodel.AddonDbModel;
//import com.whmcs.app.model.viewcartdbmodel.CheckBoxDBModel;
//import com.whmcs.app.model.viewcartdbmodel.ConfigOptionDbModel;
//import com.whmcs.app.model.viewcartdbmodel.ConfigOptionOptionDbModel;
//import com.whmcs.app.model.viewcartdbmodel.CustomFieldDbModel;
//import com.whmcs.app.model.viewcartdbmodel.QuantityCounterDBModel;
//
//import java.util.ArrayList;
//
//public class DatabaseHandlerWHMCS extends SQLiteOpenHelper {
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 2;
//
//    // Database Name
//    private static final String DATABASE_NAME = "productsmanager.db";
//
//    // Products table name
//    private static final String TABLE_PRODUCTS = "products";
//
//    // Products Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_PRODUCT_ID = "pid";
//    private static final String KEY_PRODUCT_PRICE = "product_price";
//    private static final String KEY_PRODUCT_NAME = "product_name";
//    private static final String KEY_DESCRIPTION = "product_description";
//
//    String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + "("
//            + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_PRODUCT_ID + " TEXT,"
//            + KEY_PRODUCT_PRICE + " TEXT,"
//            + KEY_PRODUCT_NAME + " TEXT,"
//            + KEY_DESCRIPTION + " TEXT" + ")";
//
//    // Quantity counter table name
//    private static final String TABLE_QUANTITY_COUNTER = "quantitycounter";
//
//    // Quantity counter Table Columns names
//    private static final String KEY_QUANTITY_COUNTER_ID = "id";
//    private static final String KEY_QUANTITY_COUNTER_PRODUCT_ID = "pid";
//    private static final String KEY_QUANTITY_COUNTER_VLAUE = "countervalue";
//    private static final String KEY_QUANTITY_CONFIG_OPTION_ID = "quantity_config_option_id";
//    private static final String KEY_QUANTUTY_COMMON_KEY_VALUE = "common_key_value";
//    private static final String KEY_QUANTITY_TYPE = "type";
//
//    String CREATE_QUANTITY_COUNTER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUANTITY_COUNTER + "("
//            + KEY_QUANTITY_COUNTER_ID + " INTEGER PRIMARY KEY,"
//            + KEY_QUANTITY_COUNTER_PRODUCT_ID + " TEXT,"
//            + KEY_QUANTITY_COUNTER_VLAUE + " TEXT,"
//            + KEY_QUANTITY_CONFIG_OPTION_ID + " TEXT,"
//            + KEY_QUANTUTY_COMMON_KEY_VALUE + " TEXT,"
//            + KEY_QUANTITY_TYPE + " TEXT" + ")";
//
//
//    // Checkbox table name
//    private static final String TABLE_CHECKBOX = "checkbox";
//
//    // Checkbox Table Columns names
//    private static final String KEY_CHECKBOX_ID = "id";
//    private static final String KEY_CHECKBOX_PRODUCT_ID = "pid";
//    private static final String KEY_CHECKBOX_VLAUE = "chekedornot";
//    private static final String KEY_CHECKBOX_CONFIG_OPTION_ID = "config_option_id";
//    private static final String KEY_CHECKBOX_COMMON_KEY_VALUE = "common_key_value";
//    private static final String KEY_CHECKBOX_TYPE = "type";
//
//
//    String CREATE_CHECKBOX_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CHECKBOX + "("
//            + KEY_CHECKBOX_ID + " INTEGER PRIMARY KEY,"
//            + KEY_CHECKBOX_PRODUCT_ID + " TEXT,"
//            + KEY_CHECKBOX_VLAUE + " TEXT,"
//            + KEY_CHECKBOX_CONFIG_OPTION_ID + " TEXT,"
//            + KEY_CHECKBOX_COMMON_KEY_VALUE + " TEXT,"
//            + KEY_CHECKBOX_TYPE + " TEXT" + ")";
//
//
//    // ProductAddon table name
//    private static final String TABLE_PRODUCT_ADDON = "product_addons";
//
//    // ProductAddon Table Columns names
//    private static final String KEY_ADDON_KEY_ID = "key_id";
//    private static final String KEY_ADDON_ID = "addon_id";
//    private static final String KEY_ADDON_PRODUCT_ID = "pid";
//    private static final String KEY_ADDON_PACKAGE = "packages";
//    private static final String KEY_ADDON_NAME = "name";
//    private static final String KEY_ADDON_DESCRIPTION = "description";
//    private static final String KEY_ADDON_BILLING_CYCLE = "billingcycle";
//    private static final String KEY_ADDON_TAX = "tax";
//    private static final String KEY_ADDON_SHOWORDER = "showorder";
//    private static final String KEY_ADDON_DOWNLOADS = "downloads";
//    private static final String KEY_ADDON_AUTOACTIVATE = "autoactivate";
//    private static final String KEY_ADDON_SUSPENDED_PRODUCT = "suspendproduct";
//    private static final String KEY_ADDON_WELCOME_EMIAL = "welcomeemail";
//    private static final String KEY_ADDON_WEIGHT = "weight";
//    private static final String KEY_ADDON_TYPE = "type";
//    private static final String KEY_ADDON_CURRENCY = "currency";
//    private static final String KEY_ADDON_RELID = "relid";
//    private static final String KEY_ADDON_M_SETUP_FEE = "msetupfee";
//    private static final String KEY_ADDON_Q_SETUP_FEE = "qsetupfee";
//    private static final String KEY_ADDON_S_SETUP_FEE = "ssetupfee";
//    private static final String KEY_ADDON_A_SETUP_FEE = "asetupfee";
//    private static final String KEY_ADDON_B_SETUP_FEE = "bsetupfee";
//    private static final String KEY_ADDON_T_SETUP_FEE = "tsetupfee";
//    private static final String KEY_ADDON_MONTHLY = "monthly";
//    private static final String KEY_ADDON_QUARTERLY = "quarterly";
//    private static final String KEY_ADDON_SEMIANNUALLY = "semiannually";
//    private static final String KEY_ADDON_ANNUALY = "annually";
//    private static final String KEY_ADDON_BIENNIALLY = "biennially";
//    private static final String KEY_ADDON_TRIENNIALLY = "triennially";
//    private static final String KEY_ADDON_COMMON_KEY_VALUE = "addon_common_key_value";
//
//    String CREATE_ADDON_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT_ADDON + "("
//            + KEY_ADDON_KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_ADDON_ID + " TEXT,"
//            + KEY_ADDON_PRODUCT_ID + " TEXT,"
//            + KEY_ADDON_PACKAGE + " TEXT,"
//            + KEY_ADDON_NAME + " TEXT,"
//            + KEY_ADDON_DESCRIPTION + " TEXT,"
//            + KEY_ADDON_BILLING_CYCLE + " TEXT,"
//            + KEY_ADDON_TAX + " TEXT,"
//            + KEY_ADDON_SHOWORDER + " TEXT,"
//            + KEY_ADDON_DOWNLOADS + " TEXT,"
//            + KEY_ADDON_AUTOACTIVATE + " TEXT,"
//            + KEY_ADDON_SUSPENDED_PRODUCT + " TEXT,"
//            + KEY_ADDON_WELCOME_EMIAL + " TEXT,"
//            + KEY_ADDON_WEIGHT + " TEXT,"
//            + KEY_ADDON_TYPE + " TEXT,"
//            + KEY_ADDON_CURRENCY + " TEXT,"
//            + KEY_ADDON_RELID + " TEXT,"
//            + KEY_ADDON_M_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_Q_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_S_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_A_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_B_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_T_SETUP_FEE + " TEXT,"
//            + KEY_ADDON_MONTHLY + " TEXT,"
//            + KEY_ADDON_QUARTERLY + " TEXT,"
//            + KEY_ADDON_SEMIANNUALLY + " TEXT,"
//            + KEY_ADDON_ANNUALY + " TEXT,"
//            + KEY_ADDON_BIENNIALLY + " TEXT,"
//            + KEY_ADDON_TRIENNIALLY + " TEXT,"
//            + KEY_ADDON_COMMON_KEY_VALUE + " TEXT" + ")";
//
//
//    // ConfigOption table name
//    private static final String TABLE_CONFIG_OPTIONS = "config_option";
//
//    // ConfigOption Table Columns names
//    private static final String KEY_CONFIG_OPTIONS_ID = "id";
//    private static final String KEY_CONFIG_OPTIONS_KEY_ID = "config_option_id";
//    private static final String KEY_CONFIG_OPTIONS_PRODUCT_ID = "pid";
//    private static final String KEY_CONFIG_OPTIONS_NAME = "config_option_name";
//    private static final String KEY_CONFIG_OPTIONS_TYPE = "config_option_type";
//    private static final String KEY_CONFIG_OPTIONS_COMMON_KEY_VALUE = "config_option_common_key_value";
//
//    String CREATE_CONFIG_OPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIG_OPTIONS + "("
//            + KEY_CONFIG_OPTIONS_ID + " INTEGER PRIMARY KEY,"
//            + KEY_CONFIG_OPTIONS_KEY_ID + " TEXT,"
//            + KEY_CONFIG_OPTIONS_PRODUCT_ID + " TEXT,"
//            + KEY_CONFIG_OPTIONS_NAME + " TEXT,"
//            + KEY_CONFIG_OPTIONS_TYPE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_COMMON_KEY_VALUE + " TEXT"+ ")";
//
//
//    // ConfigOptionOption table name
//    private static final String TABLE_CONFIG_OPTIONS_OPTION = "config_option_option";
//
//    // ConfigOptionOption Table Columns names
//    private static final String KEY_CONFIG_OPTIONS_OPTION_ID = "id";
//    private static final String KEY_CONFIG_OPTIONS_OPTION_KEY_ID = "config_option_optin_id";
//    private static final String KEY_CONFIG_OPTIONS_OPTION_PRODUCT_ID = "pid";
//    private static final String KEY_CONFIG_OPTIONS_OPTION_NAME = "config_option_option_name";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_RECURRING = "config_option_option_recurring";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_REQUIRED = "config_option_option_required";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_MSETUPFEEE = "config_option_option_msetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_QSETUPFEE = "config_option_option_qsetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_SSETUPFEE = "config_option_option_ssetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_ASETUPFEE = "config_option_option_asetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_BSETUPFEE = "config_option_option_bsetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_TSETUPFEE = "config_option_option_tsetupfee";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_MONTHLY = "config_option_option_monthly";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_QUARTARILY = "config_option_option_quatrily";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_SEMIANUALLY = "config_option_option_semianually";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_ANUALLY = "config_option_option_anually";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_BIEANUALLY = "config_option_option_biennially";
//    private static final String KEY_CONFIG_OPTIONS_OPTON_TRIENUALLY = "config_option_option_triannully";
//    private static final String KEY_CONFIG_OPTIONS_OPTION_COMMON_KEY_VALUE = "config_option_option_common_key_value";
//    private static final String KEY_CONFIG_OPTIONS_ID_VALUE = "config_option_id";
//
//
//    String CREATE_CONFIG_OPTION_OPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIG_OPTIONS_OPTION + "("
//            + KEY_CONFIG_OPTIONS_OPTION_ID + " INTEGER PRIMARY KEY,"
//            + KEY_CONFIG_OPTIONS_OPTION_KEY_ID + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTION_PRODUCT_ID + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTION_NAME + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_RECURRING + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_REQUIRED + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_MSETUPFEEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_QSETUPFEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_SSETUPFEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_ASETUPFEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_BSETUPFEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_TSETUPFEE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_MONTHLY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_QUARTARILY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_SEMIANUALLY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_ANUALLY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_BIEANUALLY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTON_TRIENUALLY + " TEXT,"
//            + KEY_CONFIG_OPTIONS_OPTION_COMMON_KEY_VALUE + " TEXT,"
//            + KEY_CONFIG_OPTIONS_ID_VALUE + " TEXT" +")";
//
//
//    // ConfigOption table name
//    private static final String TABLE_CUSTOM_FIELD = "custom_field";
//
//    // ConfigOption Table Columns names
//    private static final String KEY_CUSTOM_FIELD_ID = "id";
//    private static final String KEY_CUSTOM_FIELD_KEY_ID = "config_option_id";
//    private static final String KEY_CUSTOM_FIELD_PRODUCT_ID = "pid";
//    private static final String KEY_CUSTOM_FIELD_NAME = "name";
//    private static final String KEY_CUSTOM_FIELD_DESCRIPTION = "description";
//    private static final String KEY_CUSTOM_FIELD_REQUIRED = "required";
//    private static final String KEY_CUSTOM_FIELD_COMMON_KEY_VALUE = "custom_common_key_value";
//    private static final String KEY_CUSTOM_FIELD_FILLED_VALUE = "custom_field_value";
//
//    String CREATE_CUSTOM_FIELD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOM_FIELD + "("
//            + KEY_CUSTOM_FIELD_ID + " INTEGER PRIMARY KEY,"
//            + KEY_CUSTOM_FIELD_KEY_ID + " TEXT,"
//            + KEY_CUSTOM_FIELD_PRODUCT_ID + " TEXT,"
//            + KEY_CUSTOM_FIELD_NAME + " TEXT,"
//            + KEY_CUSTOM_FIELD_DESCRIPTION + " TEXT,"
//            + KEY_CUSTOM_FIELD_REQUIRED + " TEXT,"
//            + KEY_CUSTOM_FIELD_COMMON_KEY_VALUE + " TEXT,"
//            + KEY_CUSTOM_FIELD_FILLED_VALUE + " TEXT" + ")";
//
//    private Context context;
//    SQLiteDatabase db;
//
//    public DatabaseHandlerWHMCS(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_PRODUCTS_TABLE);
//        db.execSQL(CREATE_ADDON_TABLE);
//        db.execSQL(CREATE_CONFIG_OPTION_TABLE);
//        db.execSQL(CREATE_CONFIG_OPTION_OPTION_TABLE);
//        db.execSQL(CREATE_CUSTOM_FIELD_TABLE);
//        db.execSQL(CREATE_QUANTITY_COUNTER_TABLE);
//        db.execSQL(CREATE_CHECKBOX_TABLE);
//    }
//
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ADDON);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG_OPTIONS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG_OPTIONS_OPTION);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOM_FIELD);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUANTITY_COUNTER);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKBOX);
//        // Create tables again
//        onCreate(db);
//    }
//
//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
//
//    // Adding new counter
//    public void addQuantityCounter(QuantityCounterDBModel quantityCounterDBModel) {
//        db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_QUANTITY_COUNTER_PRODUCT_ID, quantityCounterDBModel.getPid());
//        values.put(KEY_QUANTITY_COUNTER_VLAUE, quantityCounterDBModel.getQuantityCounter());
//        values.put(KEY_QUANTITY_CONFIG_OPTION_ID, quantityCounterDBModel.getConfigOptionId());
//        values.put(KEY_QUANTUTY_COMMON_KEY_VALUE, quantityCounterDBModel.getCommonKeyValue());
//        values.put(KEY_QUANTITY_TYPE, quantityCounterDBModel.getType());
//
//
//        // Inserting Row
//        db.insert(TABLE_QUANTITY_COUNTER, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting All quantity counter field
//    public ArrayList<QuantityCounterDBModel> getAllQuatntityCounter() {
//        ArrayList<QuantityCounterDBModel> quantityCounterDBModelArrayList = new ArrayList<QuantityCounterDBModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_QUANTITY_COUNTER;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                QuantityCounterDBModel quantityCounterDBModel = new QuantityCounterDBModel();
//                quantityCounterDBModel.setId(Integer.parseInt(cursor.getString(0)));
//                quantityCounterDBModel.setPid(Integer.parseInt(cursor.getString(1)));
//                quantityCounterDBModel.setQuantityCounter(Integer.parseInt(cursor.getString(2)));
//                quantityCounterDBModel.setConfigOptionId(cursor.getString(3));
//                quantityCounterDBModel.setCommonKeyValue(Integer.parseInt((cursor.getString(4))));
//                quantityCounterDBModel.setType((cursor.getString(5)));
//                // Adding contact to list
//                quantityCounterDBModelArrayList.add(quantityCounterDBModel);
//            } while (cursor.moveToNext());
//        }
//        // return counter field list
//        return quantityCounterDBModelArrayList;
//    }
//
//
//    public int getConfigOPtionOptionRowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_CONFIG_OPTIONS_OPTION, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    public void updateConfigOptionOptionValue(int rowId, String configOptionOptionName,
//                                              String configOptionOptionMonthlyValue,
//                                              String configOptionOptionSetupValue,
//                                              String configOptionOptionId){
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_CONFIG_OPTIONS_OPTION_NAME, configOptionOptionName);
//        cv.put(KEY_CONFIG_OPTIONS_OPTON_MSETUPFEEE, configOptionOptionMonthlyValue);
//        cv.put(KEY_CONFIG_OPTIONS_OPTON_MONTHLY, configOptionOptionSetupValue);
//        cv.put(KEY_CONFIG_OPTIONS_OPTION_KEY_ID, configOptionOptionId);
//        db.update(TABLE_CONFIG_OPTIONS_OPTION, cv, KEY_CONFIG_OPTIONS_OPTION_ID+ "=" + rowId, null);
//    }
//
//    public int getConfigOPtionRowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_CONFIG_OPTIONS, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    public void updateConfigOptionValue(int newCounterValue, int rowId){
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_QUANTITY_COUNTER_VLAUE, newCounterValue);
//        db.update(TABLE_CONFIG_OPTIONS, cv, KEY_QUANTITY_COUNTER_ID+ "=" + rowId, null);
//    }
//
//
//
//    public int getQuantityCounterRowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_QUANTITY_COUNTER, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    public void updateCounterValue(int newCounterValue, int rowId){
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_QUANTITY_COUNTER_VLAUE, newCounterValue);
//        db.update(TABLE_QUANTITY_COUNTER, cv, KEY_QUANTITY_COUNTER_ID+ "=" + rowId, null);
//    }
//
//
//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
//
//    // Adding new product
//    public void setCheckboxDetail(CheckBoxDBModel checkBoxDBModel) {
//        db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_CHECKBOX_PRODUCT_ID, checkBoxDBModel.getPid());
//        values.put(KEY_CHECKBOX_VLAUE, checkBoxDBModel.getIsChecked());
//        values.put(KEY_CHECKBOX_CONFIG_OPTION_ID, checkBoxDBModel.getConfigOptionId());
//        values.put(KEY_CHECKBOX_COMMON_KEY_VALUE, checkBoxDBModel.getCommonValue());
//        values.put(KEY_CHECKBOX_TYPE, checkBoxDBModel.getType());
//
//        // Inserting Row
//        db.insert(TABLE_CHECKBOX, null, values);
//        db.close(); // Closing database connection
//    }
//
//    public int getCheckboxRowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_CHECKBOX, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    public void updateCheckBoxValue(int isChekcd, int rowId){
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_CHECKBOX_VLAUE, isChekcd);
//        db.update(TABLE_CHECKBOX, cv, KEY_CHECKBOX_ID+ "=" + rowId, null);
//    }
//
//
//
//
//
//    // Getting All Custom field
//    public ArrayList<CheckBoxDBModel> getCheckboxDetail() {
//        ArrayList<CheckBoxDBModel> checkBoxDBModels = new ArrayList<CheckBoxDBModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CHECKBOX;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                CheckBoxDBModel checkBoxDBModel = new CheckBoxDBModel();
//                checkBoxDBModel.setId(Integer.parseInt(cursor.getString(0)));
//                checkBoxDBModel.setPid(Integer.parseInt(cursor.getString(1)));
//                checkBoxDBModel.setIsChecked(Integer.parseInt((cursor.getString(2))));
//                checkBoxDBModel.setConfigOptionId((cursor.getString(3)));
//                checkBoxDBModel.setCommonValue(Integer.parseInt(cursor.getString(4)));
//                checkBoxDBModel.setType(cursor.getString(5));
//
//                // Adding contact to list
//                checkBoxDBModels.add(checkBoxDBModel);
//            } while (cursor.moveToNext());
//        }
//
//        // return custom field list
//        return checkBoxDBModels;
//    }
//
//
//
//
//
//
//
//
//
//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
//
//    // Adding new product
//    public void addProductToCart(AddToCart contact) {
//        db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_PRODUCT_ID, contact.getPid());
//        values.put(KEY_PRODUCT_PRICE, contact.getPrice());
//        values.put(KEY_PRODUCT_NAME, contact.getProductName());
//        values.put(KEY_DESCRIPTION, contact.getProductDescription());
//
//        // Inserting Row
//        db.insert(TABLE_PRODUCTS, null, values);
//        db.close(); // Closing database connection
//    }
//
//
//    public void updateProductPrice(int commonKeyValue, String productPrice){
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_PRODUCT_PRICE, productPrice);
//        db.update(TABLE_PRODUCTS, cv, KEY_ID+ "=" + commonKeyValue, null);
//    }
//
//
//    // Adding new Custom Field
//    public void addCustomFieldToCart(CustomFieldDbModel customFieldDbModel) {
//        db = this.getWritableDatabase();
//        db.execSQL(CREATE_CUSTOM_FIELD_TABLE);
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_CUSTOM_FIELD_KEY_ID, customFieldDbModel.getCustfieldId());
//        values.put(KEY_CUSTOM_FIELD_PRODUCT_ID, customFieldDbModel.getPid());
//        values.put(KEY_CUSTOM_FIELD_NAME, customFieldDbModel.getCustfieldName());
//        values.put(KEY_CUSTOM_FIELD_DESCRIPTION, customFieldDbModel.getCustfieldDescription());
//        values.put(KEY_CUSTOM_FIELD_REQUIRED, customFieldDbModel.getCustfieldRequired());
//        values.put(KEY_CUSTOM_FIELD_COMMON_KEY_VALUE, customFieldDbModel.getCommonKeyValue());
//        values.put(KEY_CUSTOM_FIELD_FILLED_VALUE, customFieldDbModel.getCutomFieldSelctedValue());
//
//        // Inserting Row
//        db.insert(TABLE_CUSTOM_FIELD, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting All Custom field
//    public ArrayList<CustomFieldDbModel> getAllCustomField() {
//        ArrayList<CustomFieldDbModel> customFieldDbModelArrayList = new ArrayList<CustomFieldDbModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOM_FIELD;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                CustomFieldDbModel customFieldDbModel = new CustomFieldDbModel();
//                customFieldDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                customFieldDbModel.setCustfieldId((cursor.getString(1)));
//                customFieldDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                customFieldDbModel.setCustfieldName((cursor.getString(3)));
//                customFieldDbModel.setCustfieldDescription(cursor.getString(4));
//                customFieldDbModel.setCustfieldRequired(cursor.getString(5));
//                customFieldDbModel.setCommonKeyValue(Integer.parseInt(cursor.getString(6)));
//                customFieldDbModel.setCutomFieldSelctedValue((cursor.getString(7)));
//                // Adding contact to list
//                customFieldDbModelArrayList.add(customFieldDbModel);
//            } while (cursor.moveToNext());
//        }
//
//        // return custom field list
//        return customFieldDbModelArrayList;
//    }
//
//    public int getCustomFieldRowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_CUSTOM_FIELD, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    public void updateCustomOptionValue(int rowId, String selectedValue, String id) {
//        db= this.getWritableDatabase();
//        db= this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_CUSTOM_FIELD_FILLED_VALUE, selectedValue);
//
////        db.update(TABLE_CUSTOM_FIELD, cv, KEY_CUSTOM_FIELD_COMMON_KEY_VALUE+ "=" + rowId, null);
//
//        db.update(TABLE_CUSTOM_FIELD, cv, KEY_CUSTOM_FIELD_COMMON_KEY_VALUE+"" +"=? AND " +
//                        KEY_CUSTOM_FIELD_KEY_ID+"=?",new String[] {String.valueOf(rowId),id });
////                "studentid=? AND testid=?", new String[] { "2","1" });
//    }
//
//
//
//
//    // Adding new config_options
//    public void addConfigOPtionsToCart(ConfigOptionDbModel configOptionDbModel) {
//        db = this.getWritableDatabase();
//        db.execSQL(CREATE_CONFIG_OPTION_TABLE);
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_CONFIG_OPTIONS_KEY_ID, configOptionDbModel.getConifgOptionId());
//        values.put(KEY_CONFIG_OPTIONS_PRODUCT_ID, configOptionDbModel.getPid());
//        values.put(KEY_CONFIG_OPTIONS_NAME, configOptionDbModel.getConfigOptionName());
//        values.put(KEY_CONFIG_OPTIONS_TYPE, configOptionDbModel.getConfigOptonType());
//        values.put(KEY_CONFIG_OPTIONS_COMMON_KEY_VALUE, configOptionDbModel.getCommonKeyValue());
//        // Inserting Row
//        db.insert(TABLE_CONFIG_OPTIONS, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting All config_options
//    public ArrayList<ConfigOptionDbModel> getAllConfigOption() {
//        ArrayList<ConfigOptionDbModel> configOptionDbModelArrayList = new ArrayList<ConfigOptionDbModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONFIG_OPTIONS;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ConfigOptionDbModel configOptionDbModel = new ConfigOptionDbModel();
//                configOptionDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                configOptionDbModel.setConifgOptionId((cursor.getString(1)));
//                configOptionDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                configOptionDbModel.setConfigOptionName((cursor.getString(3)));
//                configOptionDbModel.setConfigOptonType(cursor.getString(4));
//                configOptionDbModel.setCommonKeyValue(Integer.parseInt(cursor.getString(5)));
//                // Adding contact to list
//                configOptionDbModelArrayList.add(configOptionDbModel);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return configOptionDbModelArrayList;
//    }
//
//    // Adding new config_options_option
//    public void addConfigOPtionsOptionToCart(ConfigOptionOptionDbModel configOptionOptionDbModel) {
//        db = this.getWritableDatabase();
//        db.execSQL(CREATE_CONFIG_OPTION_OPTION_TABLE);
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_CONFIG_OPTIONS_OPTION_KEY_ID, configOptionOptionDbModel.getConfigOptionOptonID());
//        values.put(KEY_CONFIG_OPTIONS_OPTION_PRODUCT_ID, configOptionOptionDbModel.getPid());
//        values.put(KEY_CONFIG_OPTIONS_OPTION_NAME, configOptionOptionDbModel.getConfigOptionOptonName());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_RECURRING, configOptionOptionDbModel.getConfigOptionOptonRecurring());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_REQUIRED, configOptionOptionDbModel.getConfigOptionOptonRequired());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_MSETUPFEEE, configOptionOptionDbModel.getMsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_QSETUPFEE, configOptionOptionDbModel.getQsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_SSETUPFEE, configOptionOptionDbModel.getSsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_ASETUPFEE, configOptionOptionDbModel.getAsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_BSETUPFEE, configOptionOptionDbModel.getBsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_TSETUPFEE, configOptionOptionDbModel.getTsetupfee());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_MONTHLY, configOptionOptionDbModel.getMonthly());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_QUARTARILY, configOptionOptionDbModel.getQuatrily());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_SEMIANUALLY, configOptionOptionDbModel.getSemianually());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_ANUALLY, configOptionOptionDbModel.getAnually());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_BIEANUALLY, configOptionOptionDbModel.getBiennially());
//        values.put(KEY_CONFIG_OPTIONS_OPTON_TRIENUALLY, configOptionOptionDbModel.getTriannully());
//        values.put(KEY_CONFIG_OPTIONS_OPTION_COMMON_KEY_VALUE, configOptionOptionDbModel.getCommonKeyValue());
//        values.put(KEY_CONFIG_OPTIONS_ID_VALUE, configOptionOptionDbModel.getConfigOptionId());
//
//
//        // Inserting Row
//        db.insert(TABLE_CONFIG_OPTIONS_OPTION, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting All config_options_option
//    public ArrayList<ConfigOptionOptionDbModel> getAllConfigOptionOption() {
//        ArrayList<ConfigOptionOptionDbModel> configOptionDbModelArrayList =
//                new ArrayList<ConfigOptionOptionDbModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONFIG_OPTIONS_OPTION;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ConfigOptionOptionDbModel configOptionDbModel = new ConfigOptionOptionDbModel();
//                configOptionDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                configOptionDbModel.setConfigOptionOptonID((cursor.getString(1)));
//                configOptionDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                configOptionDbModel.setConfigOptionOptonName((cursor.getString(3)));
//                configOptionDbModel.setConfigOptionOptonRecurring(Integer.parseInt(cursor.getString(4)));
//                configOptionDbModel.setConfigOptionOptonRequired((cursor.getString(5)));
//                configOptionDbModel.setMsetupfee((cursor.getString(6)));
//                configOptionDbModel.setQsetupfee((cursor.getString(7)));
//                configOptionDbModel.setSsetupfee((cursor.getString(8)));
//                configOptionDbModel.setAsetupfee((cursor.getString(9)));
//                configOptionDbModel.setBsetupfee((cursor.getString(10)));
//                configOptionDbModel.setTsetupfee((cursor.getString(11)));
//                configOptionDbModel.setMonthly((cursor.getString(12)));
//                configOptionDbModel.setQuatrily((cursor.getString(13)));
//                configOptionDbModel.setSemianually((cursor.getString(14)));
//                configOptionDbModel.setAnually((cursor.getString(15)));
//                configOptionDbModel.setBiennially((cursor.getString(16)));
//                configOptionDbModel.setTriannully((cursor.getString(17)));
//                configOptionDbModel.setCommonKeyValue(Integer.parseInt((cursor.getString(18))));
//                configOptionDbModel.setConfigOptionId((cursor.getString(19)));
//                // Adding contact to list
//                configOptionDbModelArrayList.add(configOptionDbModel);
//            } while (cursor.moveToNext());
//        }
//        // return contact list
//        return configOptionDbModelArrayList;
//    }
//
//    public int rowId(){
//        db= this.getReadableDatabase();
//        db= this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_PRODUCTS, null);
//        int id=-1;
//        if(cursor.moveToLast()){
//            //name = cursor.getString(column_index);//to get other values
//            id = cursor.getInt(0);//to get id, 0 is the column index
//        }
//        return id;
//    }
//
//    // Adding new productAddon
//    public void addProductAddOnToCart(AddonDbModel addonDbModel) {
//        db = this.getWritableDatabase();
//        db.execSQL(CREATE_ADDON_TABLE);
//        ContentValues values = new ContentValues();
//        values.put(KEY_ADDON_ID, addonDbModel.getAddonID());
//        values.put(KEY_ADDON_PRODUCT_ID, addonDbModel.getPid());
//        values.put(KEY_ADDON_PACKAGE, addonDbModel.getPackages());
//        values.put(KEY_ADDON_NAME, addonDbModel.getName());
//        values.put(KEY_ADDON_DESCRIPTION, addonDbModel.getDescription());
//        values.put(KEY_ADDON_BILLING_CYCLE, addonDbModel.getBillingcycle());
//        values.put(KEY_ADDON_TAX, addonDbModel.getTax());
//        values.put(KEY_ADDON_SHOWORDER, addonDbModel.getShoworder());
//        values.put(KEY_ADDON_DOWNLOADS, addonDbModel.getDownloads());
//        values.put(KEY_ADDON_AUTOACTIVATE, addonDbModel.getAutoactivate());
//        values.put(KEY_ADDON_SUSPENDED_PRODUCT, addonDbModel.getSuspendproduct());
//        values.put(KEY_ADDON_WELCOME_EMIAL, addonDbModel.getWelcomeemail());
//        values.put(KEY_ADDON_WEIGHT, addonDbModel.getWeight());
//        values.put(KEY_ADDON_TYPE, addonDbModel.getType());
//        values.put(KEY_ADDON_CURRENCY, addonDbModel.getCurrency());
//        values.put(KEY_ADDON_RELID, addonDbModel.getRelid());
//        values.put(KEY_ADDON_M_SETUP_FEE, addonDbModel.getMsetupfee());
//        values.put(KEY_ADDON_Q_SETUP_FEE, addonDbModel.getQsetupfee());
//        values.put(KEY_ADDON_S_SETUP_FEE, addonDbModel.getSsetupfee());
//        values.put(KEY_ADDON_A_SETUP_FEE, addonDbModel.getAsetupfee());
//        values.put(KEY_ADDON_B_SETUP_FEE, addonDbModel.getBsetupfee());
//        values.put(KEY_ADDON_T_SETUP_FEE, addonDbModel.getTsetupfee());
//        values.put(KEY_ADDON_MONTHLY, addonDbModel.getMonthly());
//        values.put(KEY_ADDON_QUARTERLY, addonDbModel.getQuarterly());
//        values.put(KEY_ADDON_SEMIANNUALLY, addonDbModel.getSemiannually());
//        values.put(KEY_ADDON_ANNUALY, addonDbModel.getAnnually());
//        values.put(KEY_ADDON_BIENNIALLY, addonDbModel.getBiennially());
//        values.put(KEY_ADDON_TRIENNIALLY, addonDbModel.getTriennially());
//        values.put(KEY_ADDON_COMMON_KEY_VALUE, addonDbModel.getCommonKeyValue());
//
//
//
//        // Inserting Row
//        db.insert(TABLE_PRODUCT_ADDON, null, values);
//        db.close(); // Closing database connection
//    }
//
//    // Getting single contact
////    public AddToCart getContact(int id) {
////        SQLiteDatabase db = this.getReadableDatabase();
////
////        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] { KEY_ID,
////                        KEY_PRODUCT_NAME, KEY_PRODUCT_PRICE }, KEY_ID + "=?",
////                new String[] { String.valueOf(id) }, null, null, null, null);
////        if (cursor != null)
////            cursor.moveToFirst();
////
////        AddToCart contact = new AddToCart(Integer.parseInt(cursor.getString(0)),
////                cursor.getFloat(1), cursor.getString(2),cursor.getString(3));
////        // return contact
////        return contact;
////    }
//
//    // Getting All Products
//    public ArrayList<AddToCart> getAllCartProducts() {
//        ArrayList<AddToCart> productList = new ArrayList<AddToCart>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                AddToCart contact = new AddToCart();
//                contact.setId(Integer.parseInt(cursor.getString(0)));
//                contact.setPid(Integer.parseInt(cursor.getString(1)));
//                contact.setPrice((cursor.getString(2)));
//                contact.setProductName(cursor.getString(3));
//                contact.setProductDescription(cursor.getString(4));
//                // Adding contact to list
//                productList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return productList;
//    }
//
//    // Getting All Products
//    public ArrayList<AddonDbModel> getAllProductsAddOns() {
//        ArrayList<AddonDbModel> addonList = new ArrayList<AddonDbModel>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_ADDON;
//
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                AddonDbModel addonDbModel = new AddonDbModel();
//                addonDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                addonDbModel.setAddonID(Integer.parseInt(cursor.getString(1)));
//                addonDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                addonDbModel.setPackages((cursor.getString(3)));
//                addonDbModel.setName(cursor.getString(4));
//                addonDbModel.setDescription(cursor.getString(5));
//                addonDbModel.setBillingcycle((cursor.getString(6)));
//                addonDbModel.setTax(cursor.getString(7));
//                addonDbModel.setShoworder(cursor.getString(8));
//                addonDbModel.setDownloads((cursor.getString(9)));
//                addonDbModel.setAutoactivate(cursor.getString(10));
//                addonDbModel.setSuspendproduct(cursor.getString(11));
//                addonDbModel.setWelcomeemail(Integer.parseInt(cursor.getString(12)));
//                addonDbModel.setWeight(Integer.parseInt(cursor.getString(13)));
//                addonDbModel.setType(cursor.getString(14));
//                addonDbModel.setCurrency(Integer.parseInt(cursor.getString(15)));
//                addonDbModel.setRelid(Integer.parseInt(cursor.getString(16)));
//                addonDbModel.setMsetupfee((cursor.getString(17)));
//                addonDbModel.setQsetupfee(cursor.getString(18));
//                addonDbModel.setSsetupfee(cursor.getString(19));
//                addonDbModel.setAsetupfee((cursor.getString(20)));
//                addonDbModel.setBsetupfee(cursor.getString(21));
//                addonDbModel.setTsetupfee(cursor.getString(22));
//                addonDbModel.setMonthly((cursor.getString(23)));
//                addonDbModel.setQuarterly(cursor.getString(24));
//                addonDbModel.setSemiannually(cursor.getString(25));
//                addonDbModel.setAnnually((cursor.getString(26)));
//                addonDbModel.setBiennially(cursor.getString(27));
//                addonDbModel.setTriennially(cursor.getString(28));
//                addonDbModel.setCommonKeyValue(Integer.parseInt(cursor.getString(29)));
//                // Adding contact to list
//                addonList.add(addonDbModel);
//            } while (cursor.moveToNext());
//        }
//        // return contact list
//        return addonList;
//    }
//
//    public void dropTable() {
//        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ADDON_TABLE);
//    }
//
//    // Updating single contact
//    public int updateContact(AddToCart contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_ID, contact.getPid());
//        values.put(KEY_PRODUCT_PRICE, String.valueOf(contact.getPrice()));
//        values.put(KEY_PRODUCT_NAME, contact.getProductName());
//        values.put(KEY_DESCRIPTION, contact.getProductDescription());
//
//        // updating row
//        return db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(contact.getPid())});
//    }
//
//    // Deleting single contact
//    public void deleteProduct(AddToCart contact) {
//        db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
//                new String[]{String.valueOf(contact.getId())});
//        db.close();
//    }
//
//    // Deleting single contact
//    public void deleteAddonProduct(AddonDbModel contact) {
//        db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCT_ADDON, KEY_ADDON_KEY_ID + " = ?",
//                new String[]{String.valueOf(contact.getId())});
//        db.close();
//    }
//
//
//    // Deleting All  products
//    public void deleteAllProduct() {
//        db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCTS, null, null);
//        db.delete(TABLE_PRODUCT_ADDON, null, null);
//        db.delete(TABLE_CONFIG_OPTIONS, null, null);
//        db.delete(TABLE_CONFIG_OPTIONS_OPTION, null, null);
//        db.delete(TABLE_CUSTOM_FIELD, null, null);
//        db.delete(TABLE_CHECKBOX, null, null);
//        db.delete(TABLE_QUANTITY_COUNTER, null, null);
//        db.close();
//    }
//
//
//    // Deleting All  products
//    public void deleteAllConfigTable() {
//        db = this.getWritableDatabase();
//        db.delete(TABLE_PRODUCTS, null, null);
//        db.close();
//    }
//
//
//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//    public ArrayList<CustomFieldDbModel> getCustomFieldForPid(String customFieldId, int pid) {
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        ArrayList<CustomFieldDbModel> customFieldDbModelArrayList = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOM_FIELD + " WHERE " +KEY_CUSTOM_FIELD_KEY_ID+" = ? "+
//                "AND " + KEY_CUSTOM_FIELD_PRODUCT_ID+" = ?";
//        Cursor cursor = db.rawQuery(selectQuery, new String[] {customFieldId, String.valueOf(pid)});
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                CustomFieldDbModel customFieldDbModel = new CustomFieldDbModel();
//                customFieldDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                customFieldDbModel.setCustfieldId((cursor.getString(1)));
//                customFieldDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                customFieldDbModel.setCustfieldName((cursor.getString(3)));
//                customFieldDbModel.setCustfieldDescription(cursor.getString(4));
//                customFieldDbModel.setCustfieldRequired(cursor.getString(5));
//                // Adding contact to list
//                customFieldDbModelArrayList.add(customFieldDbModel);
//            } while (cursor.moveToNext());
//        }
//        return customFieldDbModelArrayList;
//    }
//
//
//
//    public ArrayList<CustomFieldDbModel> getcustomFieldForPid(int pid) {
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        ArrayList<CustomFieldDbModel> customFieldDbModelArrayList = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOM_FIELD + " WHERE " +KEY_CUSTOM_FIELD_COMMON_KEY_VALUE+" = ? ";
//        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(pid)});
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                CustomFieldDbModel customFieldDbModel = new CustomFieldDbModel();
//                customFieldDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                customFieldDbModel.setCustfieldId((cursor.getString(1)));
//                customFieldDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                customFieldDbModel.setCustfieldName((cursor.getString(3)));
//                customFieldDbModel.setCustfieldDescription(cursor.getString(4));
//                customFieldDbModel.setCustfieldRequired(cursor.getString(5));
//                customFieldDbModel.setCommonKeyValue(Integer.parseInt(cursor.getString(6)));
//                customFieldDbModel.setCutomFieldSelctedValue(cursor.getString(7));
//                // Adding contact to list
//                customFieldDbModelArrayList.add(customFieldDbModel);
//            } while (cursor.moveToNext());
//        }
//        return customFieldDbModelArrayList;
//    }
//
//    public ArrayList<AddonDbModel> getAddonForPid(String customFieldId, int pid) {
//        ArrayList<AddonDbModel> addonList = new ArrayList<AddonDbModel>();
//        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_ADDON + " WHERE " +KEY_ADDON_ID+" = ? "+
//                "AND " + KEY_ADDON_PRODUCT_ID+" = ?";
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[] {customFieldId, String.valueOf(pid)});
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                AddonDbModel addonDbModel = new AddonDbModel();
//                addonDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                addonDbModel.setAddonID(Integer.parseInt(cursor.getString(1)));
//                addonDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                addonDbModel.setPackages((cursor.getString(3)));
//                addonDbModel.setName(cursor.getString(4));
//                addonDbModel.setDescription(cursor.getString(5));
//                addonDbModel.setBillingcycle((cursor.getString(6)));
//                addonDbModel.setTax(cursor.getString(7));
//                addonDbModel.setShoworder(cursor.getString(8));
//                addonDbModel.setDownloads((cursor.getString(9)));
//                addonDbModel.setAutoactivate(cursor.getString(10));
//                addonDbModel.setSuspendproduct(cursor.getString(11));
//                addonDbModel.setWelcomeemail(Integer.parseInt(cursor.getString(12)));
//                addonDbModel.setWeight(Integer.parseInt(cursor.getString(13)));
//                addonDbModel.setType(cursor.getString(14));
//                addonDbModel.setCurrency(Integer.parseInt(cursor.getString(15)));
//                addonDbModel.setRelid(Integer.parseInt(cursor.getString(16)));
//                addonDbModel.setMsetupfee((cursor.getString(17)));
//                addonDbModel.setQsetupfee(cursor.getString(18));
//                addonDbModel.setSsetupfee(cursor.getString(19));
//                addonDbModel.setAsetupfee((cursor.getString(20)));
//                addonDbModel.setBsetupfee(cursor.getString(21));
//                addonDbModel.setTsetupfee(cursor.getString(22));
//                addonDbModel.setMonthly((cursor.getString(23)));
//                addonDbModel.setQuarterly(cursor.getString(24));
//                addonDbModel.setSemiannually(cursor.getString(25));
//                addonDbModel.setAnnually((cursor.getString(26)));
//                addonDbModel.setBiennially(cursor.getString(27));
//                addonDbModel.setTriennially(cursor.getString(28));
//                // Adding contact to list
//                addonList.add(addonDbModel);
//            } while (cursor.moveToNext());
//        }
//        // return contact list
//        return addonList;
//    }
//
//    // Getting All config_options_option
//    public ArrayList<ConfigOptionOptionDbModel> getAllConfigOptionOptionWithConfigID(String configOPtionId,
//                                                                                     int pid) {
//
//        ArrayList<ConfigOptionOptionDbModel> configOptionDbModelArrayList = new ArrayList<ConfigOptionOptionDbModel>();
//        String selectQuery = "SELECT  * FROM " + TABLE_CONFIG_OPTIONS_OPTION + " WHERE " +KEY_CONFIG_OPTIONS_OPTION_KEY_ID+" = ? "+
//                "AND " + KEY_CONFIG_OPTIONS_OPTION_PRODUCT_ID+" = ?";
//        db = this.getWritableDatabase();
//        db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, new String[] {configOPtionId, String.valueOf(pid)});
//
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ConfigOptionOptionDbModel configOptionDbModel = new ConfigOptionOptionDbModel();
//                configOptionDbModel.setId(Integer.parseInt(cursor.getString(0)));
//                configOptionDbModel.setConfigOptionOptonID((cursor.getString(1)));
//                configOptionDbModel.setPid(Integer.parseInt(cursor.getString(2)));
//                configOptionDbModel.setConfigOptionOptonName((cursor.getString(3)));
//                configOptionDbModel.setConfigOptionOptonRecurring(Integer.parseInt(cursor.getString(4)));
//                configOptionDbModel.setConfigOptionOptonRequired((cursor.getString(5)));
//                configOptionDbModel.setMsetupfee((cursor.getString(6)));
//                configOptionDbModel.setQsetupfee((cursor.getString(7)));
//                configOptionDbModel.setSsetupfee((cursor.getString(8)));
//                configOptionDbModel.setAsetupfee((cursor.getString(9)));
//                configOptionDbModel.setBsetupfee((cursor.getString(10)));
//                configOptionDbModel.setTsetupfee((cursor.getString(11)));
//                configOptionDbModel.setMonthly((cursor.getString(12)));
//                configOptionDbModel.setQuatrily((cursor.getString(13)));
//                configOptionDbModel.setSemianually((cursor.getString(14)));
//                configOptionDbModel.setAnually((cursor.getString(15)));
//                configOptionDbModel.setBiennially((cursor.getString(16)));
//                configOptionDbModel.setTriannully((cursor.getString(17)));
//                // Adding contact to list
//                configOptionDbModelArrayList.add(configOptionDbModel);
//            } while (cursor.moveToNext());
//        }
//
//        // return config option option array list
//        return configOptionDbModelArrayList;
//    }
//
//
//
//}
