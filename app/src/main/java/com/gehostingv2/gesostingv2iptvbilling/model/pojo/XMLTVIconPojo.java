package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="icon")

public class XMLTVIconPojo
{
    @Attribute(name = "src" ,required = false)
    private String src;

    public String getSrc ()
    {
        return src;
    }

    public void setSrc (String src)
    {
        this.src = src;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [src = "+src+"]";
    }
}

