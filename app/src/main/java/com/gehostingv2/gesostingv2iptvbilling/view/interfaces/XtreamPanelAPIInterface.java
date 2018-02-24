package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.XtreamPanelAPICallback;

public interface XtreamPanelAPIInterface extends BaseInterface{
    void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String username);
    void panelApiFailed(String updateDBFailed);

//    void epgXMLTV(XMLTVCallback xmltvCallback);
}
