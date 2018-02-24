package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PanelAvailableChannelsPojo {
    @SerializedName("num")
    @Expose
    private Integer num;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stream_type")
    @Expose
    private String streamType;
    @SerializedName("type_name")
    @Expose
    private Object typeName;
    @SerializedName("stream_id")
    @Expose
    private String streamId;
    @SerializedName("stream_icon")
    @Expose
    private String streamIcon;
    @SerializedName("epg_channel_id")
    @Expose
    private String epgChannelId;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("series_no")
    @Expose
    private Object seriesNo;
    @SerializedName("live")
    @Expose
    private String live;
    @SerializedName("container_extension")
    @Expose
    private Object containerExtension;
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
    private String tvArchiveDuration;

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

    public Object getTypeName() {
        return typeName;
    }

    public void setTypeName(Object typeName) {
        this.typeName = typeName;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Object getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(Object seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public Object getContainerExtension() {
        return containerExtension;
    }

    public void setContainerExtension(Object containerExtension) {
        this.containerExtension = containerExtension;
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

    public String getTvArchiveDuration() {
        return tvArchiveDuration;
    }

    public void setTvArchiveDuration(String tvArchiveDuration) {
        this.tvArchiveDuration = tvArchiveDuration;
    }
}
