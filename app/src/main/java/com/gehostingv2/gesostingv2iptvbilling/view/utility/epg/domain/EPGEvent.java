package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain;


public class EPGEvent {

    private final long start;
    private final long end;
    private final String title;
    private final String desc;
    private final EPGChannel channel;
    private final String programUrl;

    private EPGEvent previousEvent;
    private EPGEvent nextEvent;

    //is this the current selected event?
    public boolean selected;

    public EPGEvent(EPGChannel epgChannel, long start, long end, String title, String programUrl , String desc) {
        this.channel = epgChannel;
        this.start = start;
        this.end = end;
        this.title = title;
        this.programUrl = programUrl;
        this.desc = desc;
    }

    public EPGChannel getChannel() {
        return channel;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getProgramUrl() {
        return programUrl;
    }

    public boolean isCurrent(int timeShift) {
        long now = System.currentTimeMillis();
        now = now + timeShift;
        return now >= start && now <= end;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setNextEvent(EPGEvent nextEvent) {
        this.nextEvent = nextEvent;
    }

    public EPGEvent getNextEvent() {
        return nextEvent;
    }

    public void setPreviousEvent(EPGEvent previousEvent) {
        this.previousEvent = previousEvent;
    }

    public EPGEvent getPreviousEvent() {
        return previousEvent;
    }
}
