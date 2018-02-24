package com.gehostingv2.gesostingv2iptvbilling.miscelleneious;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gehostingv2.gesostingv2iptvbilling.R;


public class ChildViewHolder extends RecyclerView.ViewHolder {

    RecyclerView name;
    public ChildViewHolder(View itemView) {
        super(itemView);
        name = (RecyclerView) itemView.findViewById(R.id.my_recycler_view);
    }
}
