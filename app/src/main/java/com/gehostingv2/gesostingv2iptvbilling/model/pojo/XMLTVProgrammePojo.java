package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

@Root(name="programme",strict=false)
public class XMLTVProgrammePojo implements Serializable {

    @Attribute(name = "start", required = false)
    private String start;
    @Attribute(name = "channel" , required = false)
    private String channel;
    @Attribute(name = "stop", required = false)
    private String stop;

//    @Element(name = "title" ,required = false)
//    private String title;

    @Path("title")
    @Text(required=false)
    private String title;

//    @Element(name = "desc",required = false)
//    private String desc;

    @Path("desc")
    @Text(required=false)
    private String desc;

    //@Element(name = "category",required = false)
    //private String category;

    @Path("category")
    @Text(required=false)
    private String category;


//    @Element(name = "episode-num",required = false)
//    private String episode_num;

    @Path("episode-num")
    @Text(required=false)
    private String episode_num;


//    @Element(name = "date",required = false)
//    private String date;

    @Path("date")
    @Text(required=false)
    private String date;

//    @Element(name = "sub-title",required = false)
//    private String sub_title;

    @Path("sub-title")
    @Text(required=false)
    private String sub_title;

//    @Element(name = "icon",required = false)
//    private String icon;

    @Path("icon")
    @Text(required=false)
    private String icon;

//    @Element(name = "country",required = false)
//    private String country;

    @Path("country")
    @Text(required=false)
    private String country;




    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }




    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }




    public String getStop () {
        return stop;
    }

    public void setStop (String stop) {
        this.stop = stop;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public String getDesc () {
        return desc;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public String getStart () {
        return start;
    }

    public void setStart (String start) {
        this.start = start;
    }

    public String getChannel () {
        return channel;
    }

    public void setChannel (String channel) {
        this.channel = channel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEpisodeNum() {
        return episode_num;
    }

    public void setEpisodeNum(String episodeNum) {
        this.episode_num = episodeNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "ClassPojo [stop = "+stop+",  title = "+title+", category = "+category+", episode-num = "+episode_num+", date = "+date+", country = "+country+", icon = "+icon+", sub-title = "+sub_title+",desc = "+desc+", start = "+start+", channel = "+channel+"]";
    }


}