package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.misc;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPGData;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGChannel;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGEvent;


public class EPGDataImpl implements EPGData {

    private List<EPGChannel> channels = Lists.newArrayList();
    private Map<String, EPGChannel> channelsByName = new HashMap<>();
    //private List<List<EPGEvent>> events = Lists.newArrayList();

    public EPGDataImpl(Map<EPGChannel, List<EPGEvent>> data) {
        channels = Lists.newArrayList(data.keySet());
        //events = Lists.newArrayList(data.values());
        indexChannels();
    }

    public EPGChannel getChannel(int position) {
        return channels.get(position);
    }

    @Override
    public EPGChannel getOrCreateChannel(String channelName, String imageUrl, String streamID, String num, String epgChannelID) {
        EPGChannel channel = channelsByName.get(channelName);
        if (channel != null) {
            return channel;
        }
        return addNewChannel(channelName,imageUrl,streamID,num,epgChannelID);
    }




    public List<EPGEvent> getEvents(int channelPosition) {
        //return events.get(channelPosition);
        return channels.get(channelPosition).getEvents();
    }

    public EPGEvent getEvent(int channelPosition, int programPosition) {
        //return events.get(channelPosition).get(programPosition);
        return channels.get(channelPosition).getEvents().get(programPosition);
    }

    public int getChannelCount() {
        return channels.size();
    }

    @Override
    public boolean hasData() {
        return !channels.isEmpty();
    }

    public EPGChannel addNewChannel(String channelName, String imageUrl, String streamID, String num, String epgChannelID) {
        int newChannelID = channels.size();
        EPGChannel newChannel = new EPGChannel(imageUrl, channelName, newChannelID,streamID,num,epgChannelID);
        if(newChannelID>0) {
            EPGChannel previousChannel = channels.get(newChannelID-1);
            previousChannel.setNextChannel(newChannel);
            newChannel.setPreviousChannel(previousChannel);
        }
        channels.add(newChannel);
        channelsByName.put(newChannel.getName(), newChannel);
        return newChannel;
    }

    private void indexChannels() {
        channelsByName = new HashMap<>();
        for (int j = 0; j < channels.size(); j++) {
            EPGChannel channel = channels.get(j);
            channelsByName.put(channel.getName(), channel);
        }
    }
}
