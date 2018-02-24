package com.gehostingv2.gesostingv2iptvbilling.view.utility.epg;

import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGChannel;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGEvent;


public interface EPGClickListener {

    void onChannelClicked(int channelPosition, EPGChannel epgChannel);

    void onEventClicked(int channelPosition, int programPosition, EPGEvent epgEvent);

    void onResetButtonClicked();
}
