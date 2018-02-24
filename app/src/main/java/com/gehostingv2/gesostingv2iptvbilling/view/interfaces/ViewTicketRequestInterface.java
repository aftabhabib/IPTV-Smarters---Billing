package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetViewRequestReplyCallback;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.TicketsAdapter;

public interface ViewTicketRequestInterface extends BaseInterface{
    void getTicket(GetViewRequestReplyCallback getViewRequestReplyCallback);
    void getTicketForDepptName(GetViewRequestReplyCallback getViewRequestReplyCallback,
                               TicketsAdapter.ViewHolder holder,
                               int position);
    void addTicketReply(CommonResponseCallback commonResponseCallback, String message);
    void updateTicketReply(CommonResponseCallback commonResponseCallback);
    void updateTicketClose(CommonResponseCallback commonResponseCallback);

    void onBackPressed();
}
