package com.gehostingv2.gesostingv2iptvbilling.model.database;


public class EPGDBModel {
    private int idAuto;
    private String id;
    private String epgId;
    private String title;
    private String lang;
    private String start;
    private String end;
    private String description;
    private String channelId;
    private String startTimestamp;
    private String stopTimestamp;
    private String nowPlaying;
    private String hasArchive;

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

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

    public String getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(String nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    public String getHasArchive() {
        return hasArchive;
    }

    public void setHasArchive(String hasArchive) {
        this.hasArchive = hasArchive;
    }

    public EPGDBModel(){

    }
    public EPGDBModel(
            String id,
            String epgId,
            String title,
            String lang,
            String start,
            String end,
            String description,
            String channelId,
            String startTimestamp,
            String stopTimestamp,
            String nowPlaying,
            String hasArchive
    ){
        this.id= id;
        this.epgId =epgId;
        this.title = title;
        this.lang = lang;
        this.start = start;
        this.end = end;
        this.description =description;
        this.channelId =channelId;
        this.startTimestamp = startTimestamp;
        this.stopTimestamp =stopTimestamp;
        this.nowPlaying = nowPlaying;
        this.hasArchive = hasArchive;
    }
}
