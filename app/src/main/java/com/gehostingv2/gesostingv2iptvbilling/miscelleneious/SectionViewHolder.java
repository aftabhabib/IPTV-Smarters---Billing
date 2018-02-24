package com.gehostingv2.gesostingv2iptvbilling.miscelleneious;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;


public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.section);
    }
}