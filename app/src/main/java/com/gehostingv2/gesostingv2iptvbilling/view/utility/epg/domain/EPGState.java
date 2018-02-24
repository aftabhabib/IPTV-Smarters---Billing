package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain;

import android.os.Parcelable;
import android.view.View;



public class EPGState extends View.BaseSavedState {

    private EPGEvent currentEvent=null;

    public EPGState(Parcelable superState) {
        super(superState);
    }

    public EPGEvent getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(EPGEvent currentEvent) {
        this.currentEvent = currentEvent;
    }
}
