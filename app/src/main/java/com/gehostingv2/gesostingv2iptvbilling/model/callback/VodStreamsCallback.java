package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VodStreamsCallback {

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
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("series_no")
    @Expose
    private Object seriesNo;
    @SerializedName("container_extension")
    @Expose
    private String containerExtension;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("direct_source")
    @Expose
    private String directSource;

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

    public Object getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(Object seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getContainerExtension() {
        return containerExtension;
    }

    public void setContainerExtension(String containerExtension) {
        this.containerExtension = containerExtension;
    }

    public String getCustomSid() {
        return customSid;
    }

    public void setCustomSid(String customSid) {
        this.customSid = customSid;
    }

    public String getDirectSource() {
        return directSource;
    }

    public void setDirectSource(String directSource) {
        this.directSource = directSource;
    }

    public String getOriginalStreamType(){
        return "movie";
    }
}