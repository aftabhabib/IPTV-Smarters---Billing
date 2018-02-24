package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.TicketsActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetViewRequestReplyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.TicketDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ViewTicketRequestPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ViewTicketsRequestFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ViewTicketRequestInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> implements ViewTicketRequestInterface {

    private ArrayList<TicketDetailPojo> ticketPojoArrayList, filterList, completeList;
    private Context context;
    public FragmentManager f_manager;
    TicketsActivity dashboardActivity;
    FragmentActivity fragmentActivity;
    ViewTicketRequestPresenter viewTicketRequestPresenter;

    TicketDetailPojo ticketDetialPjo;

    public TicketsAdapter(Context context, ArrayList<TicketDetailPojo> ticketDetailPojos,
                          TicketsActivity dashboardActivity, FragmentActivity activity) {
        this.context = context;
        this.ticketPojoArrayList = ticketDetailPojos;
        this.filterList = new ArrayList<TicketDetailPojo>();
        this.filterList.addAll(ticketPojoArrayList);
        this.dashboardActivity = dashboardActivity;
        this.fragmentActivity = activity;
        this.completeList = ticketDetailPojos;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_get_tickets_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        viewTicketRequestPresenter = new ViewTicketRequestPresenter(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(ticketPojoArrayList!=null && ticketPojoArrayList.size()>0 ) {
            ticketDetialPjo = ticketPojoArrayList.get(position);
            final String id = ticketDetialPjo.getId();

//        viewTicketRequestPresenter.getTicketsForDepttName(Integer.parseInt(ticketDetialPjo.getId()),holder,position);
            String ticketId = ticketDetialPjo.getTid();
            String status = ticketDetialPjo.getStatus();
            if (!status.isEmpty() && status.equalsIgnoreCase("open")) {
                holder.tvStatusValue.setText(status);
                holder.tvStatusValue.setTextColor(Color.parseColor("#779500"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("customer-reply")) {
                holder.tvStatusValue.setText(status);
                holder.tvStatusValue.setTextColor(Color.parseColor("#ff6600"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("closed")) {
                holder.tvStatusValue.setText(status);
                holder.tvStatusValue.setTextColor(Color.parseColor("#888888"));
            } else if (!status.isEmpty() && status.equalsIgnoreCase("answered")) {
                holder.tvStatusValue.setText(status);
                holder.tvStatusValue.setTextColor(Color.parseColor("#000000"));
            }
//        holder.tvDepartmentName.setText(ticketDetialPjo.getDeptName());
            if (ticketDetialPjo.getTid() != null && ticketDetialPjo.getSubject() != null)
                holder.tvDepartmentName.setText("#" + ticketDetialPjo.getTid() + " - " + ticketDetialPjo.getSubject());

            holder.tvLastUpdatedValue.setText(ticketDetialPjo.getLastreply());
            holder.rlSuppotTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment newFragment = new ViewTicketsRequestFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConst.TICKET_ID, Integer.parseInt(id));
                    newFragment.setArguments(bundle);
                    TicketsActivity activity = dashboardActivity;
                    FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.fl_tickets_fragment, newFragment, AppConst.TAG_VIEW_TICKET_REQUEST);
                    t.addToBackStack(AppConst.TAG_VIEW_TICKET_REQUEST);
                    t.commit();
                }
            });


            holder.rlSuppotTicket.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                            holder.ivServicesListItemForwardArrow.requestFocus();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        return ticketPojoArrayList.size();

        return (null != filterList ? filterList.size() : 0);
    }

    @Override
    public void getTicket(GetViewRequestReplyCallback getViewRequestReplyCallback) {
    }

    @Override
    public void getTicketForDepptName(GetViewRequestReplyCallback getViewRequestReplyCallback, ViewHolder holder, int position) {
        if (getViewRequestReplyCallback != null) {
            holder.tvDepartmentName.setText(getViewRequestReplyCallback.getDeptname());
            ticketDetialPjo.setDeptName(getViewRequestReplyCallback.getDeptname());
        }
    }

    @Override
    public void addTicketReply(CommonResponseCallback commonResponseCallback, String message) {

    }

    @Override
    public void updateTicketReply(CommonResponseCallback commonResponseCallback) {

    }

    @Override
    public void updateTicketClose(CommonResponseCallback commonResponseCallback) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image_icon)
        ImageView ivImageIcon;
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_status_value)
        TextView tvStatusValue;
        @BindView(R.id.tv_margin)
        TextView tvMargin;
        @BindView(R.id.tv_last_updated)
        TextView tvLastUpdated;
        @BindView(R.id.tv_last_updated_value)
        TextView tvLastUpdatedValue;
        @BindView(R.id.rl_support_status)
        RelativeLayout rlSupportStatus;
        @BindView(R.id.rl_support)
        RelativeLayout rlSupport;
        @BindView(R.id.iv_support_ticket_forward_arrrow)
        ImageView ivSupportTicketForwardArrrow;
        @BindView(R.id.iv_line)
        ImageView ivLine;
        @BindView(R.id.rl_my_support_ticket)
        RelativeLayout rlSuppotTicket;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    // Do Search...
    public void filter(final String text, final TextView tvNoSupportTicketFound) {
        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<TicketDetailPojo>();

                // Clear the filter list
                if (filterList != null)
                    filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);
                } else {
                    // Iterate in the original List and add it to filter list...
                    for (TicketDetailPojo item : ticketPojoArrayList) {
                        if (item.getLastreply().toLowerCase().contains(text.toLowerCase()) ||
                                item.getStatus().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDeptName().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
//                        getRecycledViewPool().clear();
//                        if(TextUtils.isEmpty(text)){
//                            ticketPojoArrayList = completeList;
//                        }
//                        else if(!filterList.isEmpty() || filterList.isEmpty()){
//                            ticketPojoArrayList =filterList;
//                        }
//                        if (filterList.size() == 0) {
//                            tvNoSupportTicketFound.setVisibility(View.VISIBLE);
//                        }
//                        notifyDataSetChanged();

                        if(TextUtils.isEmpty(text)){
                            ticketPojoArrayList = completeList;
                            tvNoSupportTicketFound.setVisibility(View.INVISIBLE);
                        }
                        else if(!filterList.isEmpty() || filterList.isEmpty()){
                            ticketPojoArrayList =filterList;
                            tvNoSupportTicketFound.setVisibility(View.INVISIBLE);
                        }
                        if (filterList.size() == 0) {
                            tvNoSupportTicketFound.setVisibility(View.VISIBLE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
