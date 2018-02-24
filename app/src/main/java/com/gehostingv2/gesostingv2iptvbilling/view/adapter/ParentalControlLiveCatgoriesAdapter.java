package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.PasswordStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoicesPojo;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ParentalControlActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentalControlLiveCatgoriesAdapter extends RecyclerView.Adapter<ParentalControlLiveCatgoriesAdapter.ViewHolder> {

    Typeface fontOPenSansBold;
    private InvoicesPojo mDataset;
    private ArrayList<LiveStreamCategoryIdDBModel> arrayList, filterList, completeList;
    private ParentalControlActivity dashboardActivity;
    private ParentalControlLiveCatgoriesAdapter.ViewHolder vh;
    private Context context;
    private String username= "";
    private SharedPreferences preferencesIPTV;
    private LiveStreamDBHandler liveStreamDBHandler;
    private PasswordStatusDBModel passwordStatusDBModel;

    public ParentalControlLiveCatgoriesAdapter(ArrayList<LiveStreamCategoryIdDBModel> arrayList1, Context context,
                                               ParentalControlActivity dashboardActivity, Typeface fontOPenSansBold) {
        this.arrayList = arrayList1;
        this.context = context;
        this.dashboardActivity = dashboardActivity;
        this.fontOPenSansBold = fontOPenSansBold;
        this.completeList = arrayList1;
        if(context!=null) {
            preferencesIPTV = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, Context.MODE_PRIVATE);
            username = preferencesIPTV.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            passwordStatusDBModel = new PasswordStatusDBModel();
        }
    }

    @Override
    public ParentalControlLiveCatgoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_live_category_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ParentalControlLiveCatgoriesAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(final ParentalControlLiveCatgoriesAdapter.ViewHolder holder, final int position) {
        if (arrayList != null) {
            LiveStreamCategoryIdDBModel categoryItem = arrayList.get(position);
            final String categoryId = categoryItem.getLiveStreamCategoryID();
            final String categoryName = categoryItem.getLiveStreamCategoryName();
            setLockStatus(holder, categoryId);
            holder.categoryNameTV.setText(categoryItem.getLiveStreamCategoryName());
            holder.categoryRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    liveStreamDBHandler.getAllPasswordStatus();
                    passwordStatusDBModel =
                            liveStreamDBHandler.getPasswordStatus(username, categoryId);
                    if (passwordStatusDBModel != null && passwordStatusDBModel.getPasswordStatus() != null &&
                            passwordStatusDBModel.getPasswordStatus().equals(AppConst.PASSWORD_SET)) {
                        holder.lockIV.setImageResource(R.drawable.lock_open);
                        liveStreamDBHandler.updatePasswordStatus(username, categoryId, AppConst.PASSWORD_UNSET);
//                        liveStreamDBHandler.getAllPasswordStatus();

                        if (context != null)
                            Utils.showToast(context, context.getResources().getString(R.string.unlocked) + " " + categoryName);
                    } else if (passwordStatusDBModel != null &&
                            passwordStatusDBModel.getPasswordStatus() != null &&
                            passwordStatusDBModel.getPasswordStatus().equals(AppConst.PASSWORD_UNSET)) {
                        holder.lockIV.setImageResource(R.drawable.lock);
                        liveStreamDBHandler.updatePasswordStatus(username, categoryId, AppConst.PASSWORD_SET);
                        if (context != null)
                            Utils.showToast(context, context.getResources().getString(R.string.locked) + " " + categoryName);
                    } else {
//                        PasswordStatusDBModel passwordStatusDBModel = new PasswordStatusDBModel();
                        if (passwordStatusDBModel != null) {
                            passwordStatusDBModel.setPasswordStatusCategoryId(categoryId);
                            passwordStatusDBModel.setPasswordStatusUserDetail(username);
                            passwordStatusDBModel.setPasswordStatus(AppConst.PASSWORD_SET);
                            liveStreamDBHandler.addPasswordStatus(passwordStatusDBModel);
                            holder.lockIV.setImageResource(R.drawable.lock);
//                            liveStreamDBHandler.getAllPasswordStatus();
                            if (context != null)
                                Utils.showToast(context, context.getResources().getString(R.string.locked) + " " + categoryName);
                        }
                    }
                }
            });
        }

        holder.categoryRL1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        holder.categoryRL.performClick();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setLockStatus(ViewHolder holder, String categoryId) {
        liveStreamDBHandler.getAllPasswordStatus();
        passwordStatusDBModel =
                liveStreamDBHandler.getPasswordStatus(username, categoryId);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            holder.lockIV.setImageResource(R.drawable.lock_open);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.lockIV.setImageDrawable(context.getResources().getDrawable(R.drawable.lock_open, null));
        }
        if (passwordStatusDBModel != null && passwordStatusDBModel.getPasswordStatus() != null &&
                passwordStatusDBModel.getPasswordStatus().equals(AppConst.PASSWORD_SET)) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                holder.lockIV.setImageResource(R.drawable.lock);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.lockIV.setImageDrawable(context.getResources().getDrawable(R.drawable.lock, null));
            }
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return arrayList.get(position).type;
        return 0;
    }

//    @OnClick(R.id.bt_reply_toggle)
//    public void onViewClicked() {
//
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category_name)
        TextView categoryNameTV;
        @BindView(R.id.rl_category)
        RelativeLayout categoryRL;
        @BindView(R.id.rl_category1)
        RelativeLayout categoryRL1;
        @BindView(R.id.iv_lock_staus)
        ImageView lockIV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Do Search...
    public void filter(final String text, final TextView tvNoRecordFound, final ProgressDialog progressDialog) {
        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<LiveStreamCategoryIdDBModel>();

                // Clear the filter list
                if (filterList != null)
                    filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (LiveStreamCategoryIdDBModel item : completeList) {
                        if (item.getLiveStreamCategoryName().toLowerCase().contains(text.toLowerCase())) {
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
                        if (TextUtils.isEmpty(text)) {
                            arrayList = completeList;
                            tvNoRecordFound.setVisibility(View.INVISIBLE);
                        } else if (filterList.size() == 0) {
                            arrayList = filterList;
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                            if (context != null)
                                tvNoRecordFound.setText(context.getResources().getString(R.string.no_record_found));
                        } else if (!filterList.isEmpty() || filterList.isEmpty()) {
                            arrayList = filterList;
                            tvNoRecordFound.setVisibility(View.INVISIBLE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
