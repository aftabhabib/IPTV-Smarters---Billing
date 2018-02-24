package com.gehostingv2.gesostingv2iptvbilling.model.database;


public class VODDBModel {
    public int getIdAutoVOD() {
        return idAutoVOD;
    }

    public void setIdAutoVOD(int idAutoVOD) {
        this.idAutoVOD = idAutoVOD;
    }

    private int idAutoVOD;
    private String num;
    private String name;
    private String streamType;
    private String streamId;
    private String streamIcon;
    private String added;
    private String categoryId;
    private String seriesNo;
    private String containerExtension;
    private String customSid;
    private String directSource;

    public VODDBModel() {
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

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
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

    public VODDBModel(
            String num,
            String name,
            String streamType,
            String streamId,
            String streamIcon,
            String added,
            String categoryId,
            String seriesNo,
            String containerExtension,
            String customSid,
            String directSource
    ) {
        this.num = num;
        this.name = name;
        this.streamType = streamType;
        this.streamId = streamId;
        this.streamIcon = streamIcon;
        this.added = added;
        this.categoryId = categoryId;
        this.seriesNo = seriesNo;
        this.containerExtension = containerExtension;
        this.customSid = customSid;
        this.directSource = directSource;

    }
}
