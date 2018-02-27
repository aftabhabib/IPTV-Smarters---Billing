package com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common;

public class AppConst {

    public static final String PACKAGE_NMAE = "com.gehostingv2.gesostingv2iptvbilling";

//    public static final String BASE_URL_WHMCS = "http://dev2.whmcsmodules.in";
//    public static final String SUB_FOLDERS = ""; // /billing
//    public static final String CART_PAGE_URL = "cart.php?";
//    public static final String INVOICE_PAGE_URL = "viewinvoice.php";
//    public static final String API_USERNAME = "oiur4sfYCUni8xT";
//    public static final String API_PASSWORD = "wYmTTfC6PfsjNOA";
//    public static final String FULL_CART_PAGE_URL = "https://dev2.whmcsmodules.in/cart.php";


    //testing details
    public static final String BASE_URL_WHMCS = "http://ventura.whmcssmarters.com";
    public static final String SUB_FOLDERS = "/billing";
    public static final String CART_PAGE_URL = "billing/cart.php?";
    public static final String INVOICE_PAGE_URL = "viewinvoice.php";
    public static final String API_USERNAME = "8VFM5uPJfjf2Rgb";
    public static final String API_PASSWORD = "hU1OVm1zuU9qgz2";
    public static final String FULL_CART_PAGE_URL = "http://ventura.whmcssmarters.com/billing/cart.php";


//    public static final String FIREBASE_NOTIFICATION_WEB_API_KEY = "AAAAV7cGh24:APA91bEzW0MYH7k7PfNdq8Ngkv5sxGaH1RvGh9QNuXifsLR_Od3bDRduapPcYlB09f_Rtjv9bO7f_HxZeAfgisrV1E9u10kMWwBneWPNAnH708FJyplxrTDtrMKsnutBAcvHUx15Qfic";
    public static final String FIREBASE_NOTIFICATION_WEB_API_KEY = "AAAASif23RM:APA91bFcUC6PcMCFmxaRTrpq9A8otd2ykgkCFYoem3iWFYvK1eflNvVzJ82WdjIG06lMukOCklmFFRaonDlJH-kdcfCWrFj9caHsmaxZaFVXr3nXW62uO7D0HiDhNY91CmQydVK5EpRM";
    public static final String SKIP_BUTTON_PREF_IPTV = "skip";

    public static boolean CLOSE_TICKET = false;
    public static boolean TICKET_REPLY = false;
    public static String ACTIVITY_SERVICES = "service";
    public static String ACTIVITY_INVOICES = "invoice";
    public static String ACTIVITY_TICKETS = "ticket";
    public static String ACTIVITY_OPEN_TICKETS = "openticket";
    public static String ACTION_WHMCS_VALIDATE_CUSTOM_LOGIN = "validateCustomLogin";
    public static final String SHARED_PREFERENCE_WHMCS = "sharedPrefrence";
    public static final String LOGIN_SHARED_PREFERENCE_WHMCS = "loginPrefs";
    public static final String PREF_USERNAME_WHMCS = "usernameWHMCS";
    public static final String PREF_PASSWORD_WHMCS = "passwordWHMCS";
    public static final String PREF_EMAIL_WHMCS = "emailWHMCS";
    public static final String PREF_CLIENT_ID = "clientid";
    public static final String PREF_SAVE_LOGIN_WHMCS = "saveloginWHMCS";
    public static final String PREF_CURRENCIES_WHMCS = "currencyWHMCS";
    public static final String PREF_CURRENCIES_PREFIX_WHMCS = "currencyPrefix";
    public static final String PREF_CURRENCIES_SUFFIX_WHMCS = "currencySuffix";
    public static final String CLIENT_ID = "clientid";
    public static final String PASSWORD_HASH = "";
    public static final String RESPONSE_TYPE = "json";
    public static final boolean STATS = true;
    // tags used to attach the fragments
    public static final String TAG_HOME = "HOME";
    public static final String TAG_MY_SERVICES = "MY SERVICES";
    public static final String TAG_SUBSCRIPTION_PRODUCT_SERVICES = "SUBSCRIPTION PRODUCT SERVICES";
    public static final String TAG_PAYMENT_SUCCESS = "PAYMENT SUCCESS";
    public static final String TAG_MY_DOMAINS = "DOMAINS";
    public static final String TAG_MY_INVOICES = "MY INVOICES";
    public static final String TAG_ORDER_NEW_SERVICES = "ORDER NEW SERVICE";
    public static final String TAG_GET_INVOICE_DETIAIL = "INVOICE_DETAIL";
    public static final String TAG_VIEW_TICKET_REQUEST = "TICKETS REQUEST";
    public static final String TAG_OPEN_TICKET_GEN_ENQ = "OPEN TICKET GEN ENQ";
    public static final String TAG_MY_TICKETS = "TICKETS";
    public static final String TAG_OPEN_TICKETS = "OPEN TICKET";
    public static final String TAG_LIVE_TV = "LIVE TV";
    public static final String TAG_LIVE_TV_WITH_GUIDE = "LIVE TV WITH GUIDE";
    public static final String TAG_ACCOUNT_INFO = "ACCOUNT INFO";
    public static final String TAG_VIEO_ON_DEMAND = "VIDEO ON DEMAND";
    public static final String TAG_SETTINGS = "SETTINGS";
    public static final String TAG_LOGOUT = "LOGOUT";
    public static final String TAG_NO_SERVICE_PURCHASED = "NO SERVICE PURCHASED";
    public static final String TAG_ERROR_CODE_210_USERNAME_EMPTY = "ERROR CODE 210";
    public static final String TAG_ERROR_CODE_211_PASSWORD_EMPTY = "ERROR CODE 211";
    public static final String TAG_ERROR_CODE_215_SERVICES = "ERROR CODE 215";
    public static final String TICKET_ID = "ticketid";
    public static String CURRENT_TAG = TAG_HOME;

    public static String ACTION_GET_CLIENTS_DETAIL = "GetClientsDetails";
    public static String ACTION_GET_CURRENCY_WHMCS = "GetCurrencies";
    public static String ACTION_SEND_MAIL = "SendEmail";
    public static String ACTION_UPADTE_CLIENT = "UpdateClient";
    public static String ACTION_VALIDATE_LOGIN = "ValidateLogin";
    public static String ACTION_GET_CLIENT_PRODUCTS_COUNT = "GetClientsProducts";
    public static String ACTION_GET_SIT_COUNT = "sitcount";
    public static String ACTION_GET_CLIENT_DOMAIN_COUNT = "GetClientsDomains";
    public static String ACTION_GET_TICKETS = "GetTickets";
    public static String ACTION_GET_TICKET = "GetTicket";
    public static String ACTION_ADD_TICKET_REPLY = "AddTicketReply";
    public static String ACTION_UPDATE_TICKET = "UpdateTicket";
    public static String ACTION_GET_SERVICES = "GetClientsProducts";
    public static String ACTION_GET_SUPPORT_DEPARTMEENT = "GetSupportDepartments";
    public static String ACTION_GET_OPEN_TICKET = "OpenTicket";
    public static String ACTION_GET_CLIENT_INVOICES_COUNT = "GetInvoices";
    public static String ACTION_GET_CLIENT_TICKET_COUNT = "GetTickets";

    public static String VALIDATION_EMAIL_SENT = "Validation Email Sent";
    public static String PASSCODE_VALIDATION = "Passcode not verified";

    public static String UNAUTHORISED_USER = "No client account was found with the email address you entered";
    public static String ACTION_GET_NOTIFICATION = "addusr";
    public static final String SETTINGS_PREFERENCE = "settingsPrefs";
    public static final String SOMETHING_WRONG = "Oops! Something went wrong, please contact your administrator.";

    /**
     * custom action to get results
     */
    public static String ACTION_VALIDATE_PASSCODE = "ValidatePasscode";

    /**
     * iptv constants
     */


    public static final String BASE_URL = "http://iptvtugapt.site:8080";

    public static final String SHARED_PREFERENCE_IPTV = "sharedPreferenceIPTV";
    public static final String LOGIN_SHARED_PREFERENCE_IPTV = "loginPrefIPTV";
    public static final String LOGIN_PREF_SELECTED_PLAYER_IPTV = "selectedPlayer";
    public static final String PREF_ACTIVATION_CODE_IPTV = "activationCodeIPTV";
    public static final String PREF_USERNAME_IPTV = "usernameIPTV";
    public static final String PREF_PASSWORD_IPTV = "passwordIPTV";
    public static final String PREF_SAVE_LOGIN_IPTV = "saveloginIPTV";
    public static final String LOGIN_PREF_USERNAME_IPTV = "usernameIPTV";
    public static final String LOGIN_PREF_PASSWORD_IPTV = "passwordIPTV";
    public static final String LOGIN_PREF_CLIENT_STATUS = "clientStatus";
    public static final String LOGIN_PREF_ALLOWED_FORMAT = "allowedFormat";
    public static final String LOGIN_PREF_SERVER_PORT = "serverPort";
    public static final String LOGIN_PREF_SERVER_URL = "serverUrl";
    public static final String LOGIN_PREF_EXP_DATE = "expDate";
    public static final String LOGIN_PREF_IS_TRIAL = "isTrial";
    public static final String LOGIN_PREF_ACTIVE_CONS = "activeCons";
    public static final String LOGIN_PREF_CREATE_AT = "createdAt";
    public static final String LOGIN_PREF_MAX_CONNECTIONS = "maxConnections";
    public static final String PREF_LOGIN_WITH = "loginWith";
    public static final String LOGIN_WITH_ACTIVATION_CODE = "loginWithActivationCode";
    public static final String LOGIN_WITH_DETAILS = "loginWithDetails";
    public static final String ACTIVATION_CODE = "activatihttp://servomaniak.top:25461	onCode";
    public static final String VALIDATE_LOGIN = "getIPTVDetails";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String ACTION_GET_LIVE_STREAMS_EPG = "get_simple_data_table";
    public static final String ACTION_GET_VOD_CATEGORIES = "get_vod_categories";
    public static final String ACTION_GET_VOD_STREAMS = "get_vod_streams";
    public static final String ACTION_GET_VOD_INFO = "get_vod_info";

    public static final String ACTION_IPTV_CREDENTIALS = "getIPTVActiveService";
    public static final String NETWORK_ERROR_OCCURED = "Network error occured! Please try again";

    public static final String FORGETPASS = "forgetpassword";
    public static final String USERNAME_PASSWORD_IPTV_ISSUE = "Your IPTV credentials are insufficient";
    public static final String IPTV_CREDENTIAL_NOT_AVAILABLE = "Your don't have IPTV Credentials, Please contact to your administrator.";
    public static final String WHMCS_URL_ERROR = "Unable to resolve host";
    public static final String WHMCS_URL_ERROR_MESSAGE = "Error code 213 occurs. Please contact to " +
            "your administrator";
    public static final String IPTV_SERVICE_STATUS = "IPTV Service Status";
    public static final String STREAM_TYPE_LIVE = "live";
    public static final String STREAM_TYPE_MOVIE = "movie";

    public static final String DB_UPDATED_STATUS_PROCESSING = "Processing";
    public static final String DB_UPDATED_STATUS_FINISH = "Finished";
    public static final String DB_UPDATED_STATUS_FAILED = "Failed";
    public static final String DB_CHANNELS = "Channels";
    public static final String DB_EPG = "EPG";
    public static final String DB_CHANNELS_ID = "1";
    public static final String DB_EPG_ID = "2";


    public static final String PASSWORD_SET = "1";
    public static final String PASSWORD_UNSET = "0";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";


    //public static int LIVE_FLAG = 0;
    //public static int VOD_FLAG = 0;

    public static String ALLOWED_FORMAT = "ts";
    public static final String LAUNCH_TV_GUIDE = "launchtvguide";
    public static final String LIST_GRID_VIEW = "listgridview";
    public static final String LIVE_STREAM = "livestream";
    public static final String VOD = "vod";
    public static int LIVE_FLAG = 0;
    public static int LIVE_FLAG_VOD = 0;
    public static String STREAM_ID = "Stream Id";
    public static final String LOGIN_PREF_SELECTED_EPG_SHIFT = "selectedEPGShift";
    public static final String LOGIN_PREF_TIME_FORMAT = "timeFormat";
    public static final String SKIP_BUTTON_PREF = "skip";
    public static final String LOGIN_PREF_EPG_CHANNEL_UPDATE = "epgchannelupdate";
    public static final String LOGIN_PREF_SORT = "sort";
    public static final String LOGIN_PREF_LIVE_VOD_LAYOUT = "livevodlayout";



    public class
    Config {
        public static final String SHARED_PREF = "ah_firebase";
        // global topic to receive app wide push notifications
        public static final String TOPIC_GLOBAL = "global";
        // broadcast receiver intent filters
        public static final String REGISTRATION_COMPLETE = "registrationComplete";
        public static final String PUSH_NOTIFICATION = "pushNotification";
        // id to handle the notification in the notification tray
        public static final int NOTIFICATION_ID = 100;
        public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    }
}
