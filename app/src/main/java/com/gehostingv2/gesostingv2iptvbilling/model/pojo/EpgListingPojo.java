package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EpgListingPojo implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("epg_id")
    @Expose
    private String epgId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName("start_timestamp")
    @Expose
    private String startTimestamp;
    @SerializedName("stop_timestamp")
    @Expose
    private String stopTimestamp;
    @SerializedName("now_playing")
    @Expose
    private Integer nowPlaying;
    @SerializedName("has_archive")
    @Expose
    private Integer hasArchive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpgId() {
        return epgId;
    }

    public void setEpgId(String epgId) {
        this.epgId = epgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getStopTimestamp() {
        return stopTimestamp;
    }

    public void setStopTimestamp(String stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    public Integer getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(Integer nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    public Integer getHasArchive() {
        return hasArchive;
    }

    public void setHasArchive(Integer hasArchive) {
        this.hasArchive = hasArchive;
    }

}