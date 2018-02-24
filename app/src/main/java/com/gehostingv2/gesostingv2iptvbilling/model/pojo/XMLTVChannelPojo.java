//package com.nst.iptvsmarters.model.pojo;
//
//import org.simpleframework.xml.Attribute;
//import org.simpleframework.xml.Element;
//import org.simpleframework.xml.Root;
//
//@Root(name="channel")
//public class XMLTVChannelPojo {
//
//    @Attribute(name = "id")
//    private String id;
//
//    @Element(name = "icon")
//    private XMLTVIconPojo icon;
//
//    @Element(name = "display-name")
//    private String display_name;
//
//    public String getId ()
//    {
//        return id;
//    }
//
//    public void setId (String id)
//    {
//        this.id = id;
//    }
//
//    public XMLTVIconPojo getXMLTVIconPojo ()
//    {
//        return icon;
//    }
//
//    public void setXMLTVIconPojo (XMLTVIconPojo icon)
//    {
//        this.icon = icon;
//    }
//
//    public String getDisplay_name () {
//    return display_name;
//}
//
//    public void setDisplay_name (String display_name) {
//    this.display_name = display_name;
//}
//
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [id = "+id+", icon = "+icon+", display-name = "+display_name+"]";
////        return "ClassPojo [id = "+id+", icon = "+icon+"]";
//    }
//}