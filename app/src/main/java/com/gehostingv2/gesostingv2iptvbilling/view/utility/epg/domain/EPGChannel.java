package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain;

import com.google.common.collect.Lists;

import java.util.List;


public class EPGChannel {

    private final int channelID;
    private final String streamID;
    private final String name;
    private final String imageURL;
    private final String num;
    private final String epgChannelID;


    private List<EPGEvent> events = Lists.newArrayList();
    private EPGChannel previousChannel;
    private EPGChannel nextChannel;


    public EPGChannel(String imageURL, String name, int channelID, String streamID, String num , String epgChannelID) {
        this.imageURL = imageURL;
        this.name = name;
        this.channelID = channelID;
        this.streamID = streamID;
        this.num = num;
        this.epgChannelID = epgChannelID;

    }

    public int getChannelID() {
        return channelID;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getName() {
        return name;
    }

    public String getEpgChannelID() {
        return epgChannelID;
    }

    public String getNum() {
        return num;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<EPGEvent> getEvents() {
        return events;
    }

    public EPGChannel getPreviousChannel() {
        return previousChannel;
    }

    public void setPreviousChannel(EPGChannel previousChannel) {
        this.previousChannel = previousChannel;
    }

    public EPGChannel getNextChannel() {
        return nextChannel;
    }

    public void setNextChannel(EPGChannel nextChannel) {
        this.nextChannel = nextChannel;
    }

    public EPGEvent addEvent(EPGEvent event) {
        this.events.add(event);
        return event;
    }
}
