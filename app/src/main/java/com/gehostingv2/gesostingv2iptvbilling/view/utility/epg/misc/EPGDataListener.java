package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.misc;

import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPG;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPGData;


public class EPGDataListener {

    private EPG epg;

    public EPGDataListener(EPG epg){

        this.epg = epg;
    }

    public void processData(EPGData data) {
        epg.setEPGData(data);
//        epg.recalculateAndRedraw(false);

        epg.recalculateAndRedraw(null, false, null, null);
    }

}
