package com.gehostingv2.gesostingv2iptvbilling.model.database;


public class LiveStreamsDBModel {
    private int idAuto;
    private String num;
    private String name;
    private String streamType;
    private String streamId;
    private String streamIcon;
    private String epgChannelId;
    private String added;
    private String categoryId;
    private String customSid;
    private String tvArchive;
    private String directSource;
    private String tvArchiveDuration;

    private String typeName;
    private String categoryName;
    private String seriesNo;
    private String live;
    private String contaiinerExtension;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getContaiinerExtension() {
        return contaiinerExtension;
    }

    public void setContaiinerExtension(String contaiinerExtension) {
        this.contaiinerExtension = contaiinerExtension;
    }


    public LiveStreamsDBModel() {
    }

    public LiveStreamsDBModel(String num,
                              String name,
                              String streamType,
                              String streamId,
                              String streamIcon,
                              String epgChannelId,
                              String added,
                              String categoryId,
                              String customSid,
                              String tvArchive,
                              String directSource,
                              String tvArchiveDuration,

                              String typeName,
                              String categoryName,
                              String seriesNo,
                              String live,
                              String contaiinerExtension) {
        this.num = num;
        this.name = name;
        this.streamType = streamType;
        this.streamId = streamId;
        this.streamIcon = streamIcon;
        this.epgChannelId = epgChannelId;
        this.added = added;
        this.categoryId = categoryId;
        this.customSid = customSid;
        this.tvArchive = tvArchive;
        this.directSource = directSource;
        this.tvArchiveDuration = tvArchiveDuration;

        this.typeName = typeName;
        this.categoryName = categoryName;
        this.seriesNo = seriesNo;
        this.live = live;
        this.contaiinerExtension = contaiinerExtension;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
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

    public String getTvArchive() {
        return tvArchive;
    }

    public void setTvArchive(String tvArchive) {
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
