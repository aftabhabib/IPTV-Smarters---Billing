package com.gehostingv2.gesostingv2iptvbilling.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.gehostingv2.gesostingv2iptvbilling.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoAddAdapter extends RecyclerView.Adapter<PromoAddAdapter.ViewHolder>{
    PromoAddAdapter.ViewHolder vh;
    Context context;
    ArrayList<Integer> promoImagesList;

    public PromoAddAdapter(ArrayList<Integer> myImageList, Context context) {
        this.promoImagesList = myImageList;
        this.context = context;
    }


    @Override
    public PromoAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_promo_add_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new PromoAddAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PromoAddAdapter.ViewHolder holder, final int position) {
        final Integer promoImages = promoImagesList.get(position);
        holder.ivDiscount.setImageResource(promoImagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return promoImagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return arrayList.get(position).type;
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_voip_discount)
        ImageView ivDiscount;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
