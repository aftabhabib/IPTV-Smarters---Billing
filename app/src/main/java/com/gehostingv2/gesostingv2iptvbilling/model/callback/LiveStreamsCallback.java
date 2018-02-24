package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveStreamsCallback implements Serializable {

    @SerializedName("num")
    @Expose
    private Integer num;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stream_type")
    @Expose
    private String streamType;
    @SerializedName("stream_id")
    @Expose
    private Integer streamId;
    @SerializedName("stream_icon")
    @Expose
    private String streamIcon;
    @SerializedName("epg_channel_id")
    @Expose
    private String epgChannelId;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("tv_archive")
    @Expose
    private Integer tvArchive;
    @SerializedName("direct_source")
    @Expose
    private String directSource;
    @SerializedName("tv_archive_duration")
    @Expose
    private Integer tvArchiveDuration;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public String getStreamIcon() {
        return streamIcon;
    }

    public void setStreamIcon(String streamIcon) {
        this.streamIcon = streamIcon;
    }

    public String getEpgChannelId() {
        return epgChannelId;
    }

    public void setEpgChannelId(String epgChannelId) {
        this.epgChannelId = epgChannelId;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCustomSid() {
        return customSid;
    }

    public void setCustomSid(String customSid) {
        this.customSid = customSid;
    }

    public Integer getTvArchive() {
        return tvArchive;
    }

    public void setTvArchive(Integer tvArchive) {
        this.tvArchive = tvArchive;
    }

    public String getDirectSource() {
        return directSource;
    }

    public void setDirectSource(String directSource) {
        this.directSource = directSource;
    }

    public Integer getTvArchiveDuration() {
        return tvArchiveDuration;
    }

    public void setTvArchiveDuration(Integer tvArchiveDuration) {
        this.tvArchiveDuration = tvArchiveDuration;
    }

    public String getActiveEpg() {
        return activeEpg;
    }

    public void setActiveEpg(String activeEpg) {
        this.activeEpg = activeEpg;
    }

    public String activeEpg = "";

    public String getOriginalStreamType(){
        return "live";
    }

}