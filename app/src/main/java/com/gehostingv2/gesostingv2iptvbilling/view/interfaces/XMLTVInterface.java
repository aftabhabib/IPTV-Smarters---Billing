package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.XMLTVCallback;

public interface XMLTVInterface extends BaseInterface{
    void epgXMLTV(XMLTVCallback xmltvCallback);
    void epgXMLTVUpdateFailed(String failedUpdate);
}
