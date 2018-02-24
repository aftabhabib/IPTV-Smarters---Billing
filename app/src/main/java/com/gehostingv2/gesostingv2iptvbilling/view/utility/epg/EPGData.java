package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg;

import java.util.List;

import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGChannel;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGEvent;


public interface EPGData {

    EPGChannel getChannel(int position);

    /**
     * Get or create a channel with the given name
     * @param channelName
     * @param streamID
     * @return
     */
    EPGChannel getOrCreateChannel(String channelName, String imageUrl, String streamID, String num,String epgChannelID);

    EPGChannel addNewChannel(String channelName, String imageUrl, String streamID , String num,String epgChannelID);

    List<EPGEvent> getEvents(int channelPosition);

    EPGEvent getEvent(int channelPosition, int programPosition);

    int getChannelCount();

    boolean hasData();

}
