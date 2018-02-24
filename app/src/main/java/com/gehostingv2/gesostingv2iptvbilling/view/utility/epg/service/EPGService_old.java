//package com.expresstvbox.expresstvboxiptv.view.utility.epg.service;
//
//import android.content.Context;
//
////import com.android.volley.AuthFailureError;
////import com.android.volley.Request;
////import com.android.volley.RequestQueue;
////import com.android.volley.Response;
////import com.android.volley.VolleyError;
////import com.android.volley.toolbox.JsonObjectRequest;
////import com.android.volley.toolbox.StringRequest;
////import com.android.volley.toolbox.Volley;
//import com.google.common.collect.Maps;
////
////import org.apache.http.client.ClientProtocolException;
////import org.apache.http.impl.client.DefaultHttpClient;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.expresstvbox.expresstvboxiptv.R;
//import com.expresstvbox.expresstvboxiptv.miscelleneious.common.AppConst;
//import com.expresstvbox.expresstvboxiptv.miscelleneious.common.Utils;
//import com.expresstvbox.expresstvboxiptv.model.LiveStreamsDBModel;
//import com.expresstvbox.expresstvboxiptv.model.database.LiveStreamDBHandler;
//import com.expresstvbox.expresstvboxiptv.model.database.PasswordStatusDBModel;
//import com.expresstvbox.expresstvboxiptv.model.pojo.XMLTVProgrammePojo;
////import com.nst.iptv.view.activity.SubTVArchiveActivity;
////import com.nst.iptv.view.adapter.SubTVArchiveCategoriesAdapter;
//import com.expresstvbox.expresstvboxiptv.view.utility.epg.EPGData;
//import com.expresstvbox.expresstvboxiptv.view.utility.epg.domain.EPGChannel;
//import com.expresstvbox.expresstvboxiptv.view.utility.epg.domain.EPGEvent;
//import com.expresstvbox.expresstvboxiptv.view.utility.epg.misc.EPGDataImpl;
//import com.expresstvbox.expresstvboxiptv.view.utility.epg.misc.EPGDataListener;
//
//
//public class EPGService {
//
//    private Context context;
//    LiveStreamDBHandler liveStreamDBHandler;
//    private ArrayList<String> listPassword = new ArrayList<>();
//    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
//    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
//    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
//    private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
//    private ArrayList<LiveStreamsDBModel> liveListDetail;
//
//
//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//    private static SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//    public EPGService(Context context) {
//        this.context = context;
//    }
//
//    public EPGData getData(final EPGDataListener listener, final int dayOffset, String catID) {
//        try {
//            return parseData(catID);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //TODO
//        }
//        return null;
//    }
//
//    private EPGData parseData(String catID) {
//        liveStreamDBHandler = new LiveStreamDBHandler(context);
//        EPGChannel firstChannel = null;
//        EPGChannel prevChannel = null;
//        EPGChannel currentChannel = null;
//        EPGEvent prevEvent = null;
//        try {
//            Map<EPGChannel, List<EPGEvent>> map = Maps.newLinkedHashMap();
//            LiveStreamDBHandler dbObj = new LiveStreamDBHandler(context);
//            ArrayList<LiveStreamsDBModel> allChannels = dbObj.getAllLiveStreasWithCategoryId(catID, "live");
//            categoryWithPasword = new ArrayList<PasswordStatusDBModel>();
//            liveListDetailUnlcked = new ArrayList<LiveStreamsDBModel>();
//            liveListDetailUnlckedDetail = new ArrayList<LiveStreamsDBModel>();
//            liveListDetailAvailable = new ArrayList<LiveStreamsDBModel>();
//            liveListDetail = new ArrayList<LiveStreamsDBModel>();
//            int parentalStatusCount = liveStreamDBHandler.getParentalStatusCount();
//            if (parentalStatusCount > 0 && allChannels!=null) {
//                listPassword = getPasswordSetCategories();
//                if(listPassword!=null) {
//                    liveListDetailUnlckedDetail = getUnlockedCategories(allChannels,
//                            listPassword);
//                }
//                liveListDetailAvailable = liveListDetailUnlckedDetail;
//            } else {
//                liveListDetailAvailable = allChannels;
//            }
//
//            if(liveListDetailAvailable!=null) {
//                for (int i = 0; i < liveListDetailAvailable.size(); i++) {
//                    String channelName = liveListDetailAvailable.get(i).getName();
//                    String channelID = liveListDetailAvailable.get(i).getEpgChannelId();
//                    String streamIcon = liveListDetailAvailable.get(i).getStreamIcon();
//                    String streamID = liveListDetailAvailable.get(i).getStreamId();
//                    String num = liveListDetailAvailable.get(i).getNum();
//                    String epgChannelId = liveListDetailAvailable.get(i).getEpgChannelId();
//
//                    currentChannel = new EPGChannel(streamIcon, channelName, i, streamID,num,epgChannelId);
//                    if (firstChannel == null) {
//                        firstChannel = currentChannel;
//                    }
//                    if (prevChannel != null) {
//                        currentChannel.setPreviousChannel(prevChannel);
//                        prevChannel.setNextChannel(currentChannel);
//                    }
//                    prevChannel = currentChannel;
//                    List<EPGEvent> epgEvents = new ArrayList<>();
//                    map.put(currentChannel, epgEvents);
//
//                    if (!channelID.equals("")) {
//                        ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos = liveStreamDBHandler.getEPG(channelID);
//                        String startDateTime;
//                        String stopDateTime;
//                        String Title;
//                        String Desc;
//                        Long epgTempStop = null;
//
//                        if (xmltvProgrammePojos != null && xmltvProgrammePojos.size()!=0) {
//                            for (int j = 0; j < xmltvProgrammePojos.size(); j++) {
//                                startDateTime = xmltvProgrammePojos.get(j).getStart();
//                                stopDateTime = xmltvProgrammePojos.get(j).getStop();
//                                Title = xmltvProgrammePojos.get(j).getTitle();
//                                Desc = xmltvProgrammePojos.get(j).getDesc();
//                                Long epgStartDateToTimestamp = Utils.epgTimeConverter(startDateTime);
//                                Long epgStopDateToTimestamp = Utils.epgTimeConverter(stopDateTime);
//
//                                if(epgTempStop!=null && epgStartDateToTimestamp.equals(epgTempStop)){
//                                    EPGEvent epgEvent = new EPGEvent(currentChannel, epgStartDateToTimestamp, epgStopDateToTimestamp, Title, streamIcon, Desc);
//                                    if (prevEvent != null) {
//                                        epgEvent.setPreviousEvent(prevEvent);
//                                        prevEvent.setNextEvent(epgEvent);
//                                    }
//                                    prevEvent = epgEvent;
//                                    currentChannel.addEvent(epgEvent);
//                                    epgEvents.add(epgEvent);
//                                }else{
//                                    if(epgTempStop!=null) {
//                                        EPGEvent epgEvent = new EPGEvent(currentChannel, epgTempStop, epgStartDateToTimestamp, context.getResources().getString(R.string.no_information), streamIcon, "");
//                                        if (prevEvent != null) {
//                                            epgEvent.setPreviousEvent(prevEvent);
//                                            prevEvent.setNextEvent(epgEvent);
//                                        }
//                                        prevEvent = epgEvent;
//                                        currentChannel.addEvent(epgEvent);
//                                        epgEvents.add(epgEvent);
//
//
//                                        EPGEvent epgEvent1 = new EPGEvent(currentChannel, epgStartDateToTimestamp, epgStopDateToTimestamp, Title, streamIcon, Desc);
//                                        if (prevEvent != null) {
//                                            epgEvent1.setPreviousEvent(prevEvent);
//                                            prevEvent.setNextEvent(epgEvent1);
//                                        }
//                                        prevEvent = epgEvent1;
//                                        currentChannel.addEvent(epgEvent1);
//                                        epgEvents.add(epgEvent1);
//
//                                    }else{
//                                        EPGEvent epgEvent1 = new EPGEvent(currentChannel, epgStartDateToTimestamp, epgStopDateToTimestamp, Title, streamIcon, Desc);
//                                        if (prevEvent != null) {
//                                            epgEvent1.setPreviousEvent(prevEvent);
//                                            prevEvent.setNextEvent(epgEvent1);
//                                        }
//                                        prevEvent = epgEvent1;
//                                        currentChannel.addEvent(epgEvent1);
//                                        epgEvents.add(epgEvent1);
//                                    }
//                                }
//                                epgTempStop = epgStopDateToTimestamp;
//
//
//                            }
//                        }else{
//                            long nowTime = System.currentTimeMillis();
//                            long starttesting1 = nowTime - Long.parseLong("86400000");
//                            long endtesting1 = starttesting1 + Long.parseLong("7200000");
//                            for(int k=0;k<50;k++){
//                                EPGEvent epgEvent = new EPGEvent(currentChannel, starttesting1, endtesting1, context.getResources().getString(R.string.no_information), streamIcon, "");
//                                if (prevEvent != null) {
//                                    epgEvent.setPreviousEvent(prevEvent);
//                                    prevEvent.setNextEvent(epgEvent);
//                                }
//                                prevEvent = epgEvent;
//                                currentChannel.addEvent(epgEvent);
//                                epgEvents.add(epgEvent);
//                                starttesting1 = endtesting1;
//                                endtesting1 = starttesting1 + Long.parseLong("7200000");
//                            }
//                        }
//                    }else{
//                        long nowTime = System.currentTimeMillis();
//                        long starttesting = nowTime - Long.parseLong("86400000");
//                        long endtesting = starttesting + Long.parseLong("7200000");
//                        for(int k=0;k<50;k++){
//                            EPGEvent epgEvent = new EPGEvent(currentChannel, starttesting, endtesting, context.getResources().getString(R.string.no_information), streamIcon, "");
//                            if (prevEvent != null) {
//                                epgEvent.setPreviousEvent(prevEvent);
//                                prevEvent.setNextEvent(epgEvent);
//                            }
//                            prevEvent = epgEvent;
//                            currentChannel.addEvent(epgEvent);
//                            epgEvents.add(epgEvent);
//                            starttesting = endtesting;
//                            endtesting = starttesting + Long.parseLong("7200000");
//                        }
//
//
//                    }
//                }
//            }
//            if (currentChannel != null) {
//                currentChannel.setNextChannel(firstChannel);
//            }
//            if (firstChannel != null) {
//                firstChannel.setPreviousChannel(currentChannel);
//            }
//            EPGData data = new EPGDataImpl(map);
//            return data;
//
//        } catch (Throwable ex) {
//            throw new RuntimeException(ex.getMessage(), ex);
//        }
//    }
//
//
//    private ArrayList<LiveStreamsDBModel> getUnlockedCategories(ArrayList<LiveStreamsDBModel> liveListDetail, ArrayList<String> listPassword) {
//        for (LiveStreamsDBModel user1 : liveListDetail) {
//            boolean flag = false;
//            for (String user2 : listPassword) {
//                if (user1.getCategoryId().equals(user2)) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag == false) {
//                liveListDetailUnlcked.add(user1);
//            }
//        }
//        return liveListDetailUnlcked;
//    }
//
//    private ArrayList<String> getPasswordSetCategories() {
//        categoryWithPasword =
//                liveStreamDBHandler.getAllPasswordStatus();
//        if(categoryWithPasword!=null) {
//            for (PasswordStatusDBModel listItemLocked : categoryWithPasword) {
//                if (listItemLocked.getPasswordStatus().equals(AppConst.PASSWORD_SET)) {
//                    listPassword.add(listItemLocked.getPasswordStatusCategoryId());
//                }
//            }
//        }
//        return listPassword;
//    }
//}
