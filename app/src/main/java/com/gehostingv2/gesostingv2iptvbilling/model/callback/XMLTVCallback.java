package com.gehostingv2.gesostingv2iptvbilling.model.callback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.List;

@Root(name = "tv",strict=false)
public class XMLTVCallback {
    @ElementList(inline = true, required=false)  //,
    public
    List<XMLTVProgrammePojo> programmePojos;
    @Override
    public String toString(){
        return "ClassPojo [programmePojos= "+programmePojos+"]";
    }
}



