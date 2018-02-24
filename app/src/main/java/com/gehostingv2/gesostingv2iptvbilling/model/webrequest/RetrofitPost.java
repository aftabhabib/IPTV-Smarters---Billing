package com.gehostingv2.gesostingv2iptvbilling.model.webrequest;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ClientDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientCurrencyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientProductsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetDepartmentCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetIPTVCredentialsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetInvoicesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetOpenTicketCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetSITCountCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetTicketsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetViewRequestReplyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LoginWHMCSCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.MyDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidateCustomLoginCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodInfoCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XMLTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XtreamPanelAPICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitPost {


    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<ValidateCustomLoginCallback> validateCustomLogin(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<LoginWHMCSCallback> validateLogin(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<ClientDetailsCallback> getClientsDetails(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> sendmail(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> validatePasscode(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> updateClient(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> getCLientHomeCount(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetSITCountCallback> getSITCount(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> getNotification(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<MyDetailsCallback> getClientDetail(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetClientCurrencyCallback> getCurrency(@Body JsonObject data);


    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetTicketsCallback> getTickets(@Body JsonObject data);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetViewRequestReplyCallback> getTicket(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> addTicketReply(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> updateTicket(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetClientProductsCallback> getMyservices(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetInvoicesCallback> getInvioces(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<CommonResponseCallback> getInviocesTotalCount(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetDepartmentCallback> getSupportDepartment(@Body JsonObject jsonObject);

    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetOpenTicketCallback> openSupportDept(@Body JsonObject jsonObject);

    /**
     * iptv post rerquest
     */


    @POST(AppConst.SUB_FOLDERS + "/modules/addons/whmcsapimodule/response.php")
    Call<GetIPTVCredentialsCallback> getIPTVCredentials(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<ValidationIPTVCallback> validateIPTVLogin(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<List<LiveStreamCategoriesCallback>> liveStreamCategories(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String getlivecategories);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<List<LiveStreamsCallback>> liveStreams(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String getlivecategories,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<LiveStreamsEpgCallback> liveStreamsEpg(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String get_simple_data_table,
            @Field("stream_id") Integer stream_id);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<List<VodCategoriesCallback>> vodCategories(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String get_vod_categories);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<List<VodStreamsCallback>> vodStreams(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String get_vod_categories,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("/player_api.php")
    Call<VodInfoCallback> vodInfo(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("action") String get_vod_info,
            @Field("vod_id") int vod_id);


    @FormUrlEncoded
    @POST("/panel_api.php")
    Call<XtreamPanelAPICallback> panelAPI(
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("/xmltv.php")
    Call<XMLTVCallback> epgXMLTV(@Header("Content-Type") String contentType,
                                 @Field("username") String username,
                                 @Field("password") String password);


//
//    @Multipart
//    @FormUrlEncoded
//    @POST("/xmltv.php")
//    Call<XMLTVCallback> epgXMLTV(@Header("Content-Type") String contentType,
//                                 @Field("username") String username,
//                                 @Field("password") String password);




    //RxaJava code to use the api
//    @POST("/panel_api.php")
//    @FormUrlEncoded
//    Observable<XtreamPanelAPICallback> panelAPIRx(@Header("Content-Type") String contentType,
//                                                  @Field("username") String username,
//                                                  @Field("password") String password);

}
