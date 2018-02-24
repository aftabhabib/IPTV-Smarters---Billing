package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.gehostingv2.gesostingv2iptvbilling.view.activity.ServicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductPojo;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SubscriptionProductFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    private ProductPojo mDataset;
    private ArrayList<ProductDetailPojo> arrayList, filterList, completeList;
    private ServicesActivity dashboardActivity;
    private ViewHolder vh;
    private Context context;
    private String currencyPrefix = "";
    private String currencySuffix = "";


    public ServicesAdapter(ArrayList<ProductDetailPojo> arrayList1, Context context,
                           ServicesActivity dashboardActivity) {
        this.arrayList = arrayList1;
        this.completeList = arrayList1;
        this.context = context;
        this.dashboardActivity = dashboardActivity;


        SharedPreferences loginPreferencesEmail = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        this.currencyPrefix = loginPreferencesEmail.getString(AppConst.PREF_CURRENCIES_PREFIX_WHMCS, "");
        this.currencySuffix = loginPreferencesEmail.getString(AppConst.PREF_CURRENCIES_SUFFIX_WHMCS, "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_active_services_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProductDetailPojo productDetailPojo = arrayList.get(position);
        String nextDueDate = productDetailPojo.getNextDueDate().toString();
        String productName1 = "";
        String productNameTrunc="";
        if (productDetailPojo.getName() != null && !productDetailPojo.getName().isEmpty()) {
            productName1 = productDetailPojo.getName();
            productNameTrunc = productName1.substring(0, Math.min(productName1.length(), 17))+"...";
        }
        holder.tvServiceName.setText(productNameTrunc);
        holder.tvPricingValue.setText(currencyPrefix + productDetailPojo.getFirstPaymentAmount() + " " + productDetailPojo.getBillingCycle());
        if (nextDueDate.equals("0000-00-00")) {
            holder.tvNextDueDateValue.setText("NA");
        } else {
            holder.tvNextDueDateValue.setText(nextDueDate);
        }

        String domainName = productDetailPojo.getDomain();
        if (!domainName.isEmpty()) {
            holder.domainName.setText("( " + domainName + " )");
        }
        String serviceId = productDetailPojo.getServiceId();
        String productName = productDetailPojo.getName();
        String Status = productDetailPojo.getStatus();
        String registrationDate = productDetailPojo.getRegistarationDate();
        String recurringAmount = String.valueOf(productDetailPojo.getRecurringAmount());
        String billingCycle = productDetailPojo.getBillingCycle();
        String paymentMethod = productDetailPojo.getPaymentMethodName();
        String groupName = productDetailPojo.getGroupName();
        String firstTimePayment = productDetailPojo.getFirstPaymentAmount();
        String userName = productDetailPojo.getUserName();
        String password = productDetailPojo.getPassword();
        String domain = productDetailPojo.getDomain();
        String macAddress = "";
        final Bundle bundle = new Bundle();
        bundle.putString("product_name", productName);
        bundle.putString("status", Status);
        bundle.putString("reg_date", registrationDate);
        bundle.putString("next_due_date", nextDueDate);
        bundle.putString("recurring_amount", recurringAmount);
        bundle.putString("billing_cycle", billingCycle);
        bundle.putString("payment_method", paymentMethod);
        bundle.putString("first_time_payment", firstTimePayment);
        bundle.putString("user_name", userName);
        bundle.putString("domain", domain);
        bundle.putString("password", password);
        bundle.putString("mac_address", macAddress);
        bundle.putString("group_name", groupName);
        bundle.putString("service_id", serviceId);
        holder.rlServiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new SubscriptionProductFragment();
                newFragment.setArguments(bundle);
                ServicesActivity activity = dashboardActivity;
                FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fl_myservice, newFragment, AppConst.TAG_SUBSCRIPTION_PRODUCT_SERVICES);
                t.addToBackStack(AppConst.TAG_SUBSCRIPTION_PRODUCT_SERVICES
                );
                t.commit();
            }
        });

        holder.rlServiceItem.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        holder.ivServicesListItemForwardArrow.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image_icon)
        ImageView ivImageIcon;
        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.tv_pricing)
        TextView tvPricing;
        @BindView(R.id.tv_pricing_value)
        TextView tvPricingValue;
        @BindView(R.id.tv_margin)
        TextView tvMargin;
        @BindView(R.id.tv_next_due_date)
        TextView tvNextDueDate;
        @BindView(R.id.tv_next_due_date_value)
        TextView tvNextDueDateValue;
        @BindView(R.id.rl_services_list_item)
        RelativeLayout rlServicesListItem;
        @BindView(R.id.iv_services_list_item_forward_arrow)
        ImageView ivServicesListItemForwardArrow;
        @BindView(R.id.iv_line)
        ImageView ivLine;
        @BindView(R.id.tv_domain_name)
        TextView domainName;
        @BindView(R.id.rl_service)
        RelativeLayout rlServiceItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Do Search...
    public void filter(final String text, final TextView tvNoRecordFound) {
        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<ProductDetailPojo>();
                // Clear the filter list
                if (filterList != null)
                    filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (ProductDetailPojo item : completeList) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getStatus().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDomain().contains(text.toLowerCase())) {
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
                        } else if (!filterList.isEmpty() || filterList.isEmpty()) {
                            arrayList = filterList;
                            tvNoRecordFound.setVisibility(View.INVISIBLE);
                        }
                        if (filterList.size() == 0) {
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

