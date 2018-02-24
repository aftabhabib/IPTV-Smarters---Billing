package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;
import android.support.v7.widget.RecyclerView;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetTicketsCallback;

public interface TicketInterface extends BaseInterface {
    void onBackPressed();

    void getTickets(GetTicketsCallback getTicketsCallback, RecyclerView myRecyclerView);
}
