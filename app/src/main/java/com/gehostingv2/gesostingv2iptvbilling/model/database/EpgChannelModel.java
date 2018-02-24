package com.gehostingv2.gesostingv2iptvbilling.model.database;


public class EpgChannelModel {
    String nowPlaying="";

    public String getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(String nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    String next="";
}
