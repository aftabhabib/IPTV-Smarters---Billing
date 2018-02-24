package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.InvoicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoicesDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoicesPojo;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoiceViewFragment;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InvoicesAdaper extends RecyclerView.Adapter<InvoicesAdaper.ViewHolder> {

    Typeface fontOPenSansBold;
    private InvoicesPojo mDataset;
    private ArrayList<InvoicesDetailPojo> arrayList, filterList, completeList;
    private InvoicesActivity dashboardActivity;

    ViewHolder vh;
    Context context;

    public InvoicesAdaper(ArrayList<InvoicesDetailPojo> arrayList1, Context context,
                          InvoicesActivity dashboardActivity, Typeface fontOPenSansBold) {
        this.arrayList = arrayList1;
        this.context = context;
        this.dashboardActivity = dashboardActivity;
        this.fontOPenSansBold = fontOPenSansBold;
        this.completeList = arrayList1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_invoices_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        InvoicesDetailPojo invoicesDetailPojo = arrayList.get(position);
        holder.rlInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.expandableLayout1.isExpanded()) {
                    holder.expandableLayout1.collapse();
                    holder.downArrow.setImageResource(R.drawable.down_arrow);
                } else {
                    holder.expandableLayout1.expand();
                    holder.downArrow.setImageResource(R.drawable.up_arrow);
                }
            }
        });
        holder.tvInvoicesNo.setText("Invoice # " + invoicesDetailPojo.getId());
        holder.tvInvoicesNo.setTextColor(Color.parseColor("#3e6372"));
        holder.tvInvoicesNo.setTypeface(fontOPenSansBold);

        holder.tvInvoiceDateValue.setText(invoicesDetailPojo.getDate());
        holder.tvInvoiceDueDateValue.setText(invoicesDetailPojo.getDuedate());
        holder.tvInvoiceTotalValue.setText(invoicesDetailPojo.getCurrencyprefix() + "" + invoicesDetailPojo.getTotal()
                + " " + invoicesDetailPojo.getCurrencysuffix());
        String status = invoicesDetailPojo.getStatus();
        if (status.equalsIgnoreCase("paid")) {
            holder.tvInvoicePayment.setText(context.getResources().getString(R.string.view));
        } else if (status.equalsIgnoreCase("unpaid")) {
            holder.tvInvoicePayment.setText(context.getResources().getString(R.string.pay_now));
        }
        String invoiceId = invoicesDetailPojo.getId();
        String companyName = invoicesDetailPojo.getCompanyname();
        String statusInvoice = invoicesDetailPojo.getStatus();
//        String invoiceDate=invoicesDetailPojo.getDate();
        String invoiceDate = invoicesDetailPojo.getDatepaid();
        String firstName = invoicesDetailPojo.getFirstname();
        String lastName = invoicesDetailPojo.getLastname();
        String paymentMethod = invoicesDetailPojo.getPaymentmethod();
        String subTotal = invoicesDetailPojo.getSubtotal();
        String credit = invoicesDetailPojo.getCredit();
        String total = invoicesDetailPojo.getTotal();
        String currencyPrefix = invoicesDetailPojo.getCurrencyprefix();
        String cirrencySuffix = invoicesDetailPojo.getCurrencysuffix();


        final Bundle bundle = new Bundle();
        bundle.putString("invoice_id", invoiceId);
        bundle.putString("company_name", companyName);
        bundle.putString("status_invoice", statusInvoice);
        bundle.putString("invoice_date", invoiceDate);
        bundle.putString("first_name", firstName);
        bundle.putString("last_name", lastName);
        bundle.putString("payment_method", paymentMethod);
        bundle.putString("sub_total", subTotal);
        bundle.putString("credit", credit);
        bundle.putString("total", total);
        bundle.putString("currency_prefix", currencyPrefix);
        bundle.putString("currency_suffix", cirrencySuffix);

        holder.tvInvoicePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tvInvoicePayment.getText().equals("VIEW") ||
                        holder.tvInvoicePayment.getText().equals("PAY NOW")) {
                    Fragment newFragment = new InvoiceViewFragment();
                    newFragment.setArguments(bundle);
                    InvoicesActivity activity = dashboardActivity;
                    FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.fl_invoices, newFragment, AppConst.TAG_GET_INVOICE_DETIAIL);
                    t.addToBackStack(AppConst.TAG_GET_INVOICE_DETIAIL
                    );
                    t.commit();
                }

            }
        });

        holder.rlInvoices.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        holder.downArrow.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });

        holder.downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.expandableLayout1.isExpanded()) {
                    holder.expandableLayout1.collapse();
                    holder.downArrow.setImageResource(R.drawable.down_arrow);
                    holder.tvInvoicePayment.requestFocus();
                } else {
                    holder.expandableLayout1.expand();
                    holder.downArrow.setImageResource(R.drawable.up_arrow);
                    holder.tvInvoicePayment.requestFocus();
                }
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

    @OnClick(R.id.bt_reply_toggle)
    public void onViewClicked() {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bt_reply_toggle)
        Button btReplyToggle;
        @BindView(R.id.tv_invoices_no)
        TextView tvInvoicesNo;
        @BindView(R.id.rl_reply_ticket)
        RelativeLayout rlReplyTicket;
        @BindView(R.id.tv_invoice_date)
        TextView tvInvoiceDate;
        @BindView(R.id.tv_invoice_date_value)
        TextView tvInvoiceDateValue;
        @BindView(R.id.tv_invoice_due_date)
        TextView tvInvoiceDueDate;
        @BindView(R.id.tv_invoice_due_date_value)
        TextView tvInvoiceDueDateValue;
        @BindView(R.id.tv_invoice_total)
        TextView tvInvoiceTotal;
        @BindView(R.id.tv_invoice_total_value)
        TextView tvInvoiceTotalValue;
        @BindView(R.id.tv_invoice_payment)
        Button tvInvoicePayment;
        @BindView(R.id.ll_expandable)
        LinearLayout llExpandable;
        @BindView(R.id.expandableLayout1)
        ExpandableLinearLayout expandableLayout1;
        @BindView(R.id.iv_line)
        ImageView ivLine;

        @BindView(R.id.bt_view_manage)
        LinearLayout llViewPaidInvoices;
        @BindView(R.id.rl_bt_manage)
        RelativeLayout rlPayViewInvoicesBT;


        @BindView(R.id.rl_invoices)
        RelativeLayout rlInvoices;
        @BindView(R.id.iv_arrow_downward)
        ImageView downArrow;

        @BindView(R.id.rl_invoices_)
        RelativeLayout rlInvoicesO;


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
                filterList = new ArrayList<InvoicesDetailPojo>();

                // Clear the filter list
                if (filterList != null)
                    filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (InvoicesDetailPojo item : completeList) {
                        if (item.getCompanyname().toLowerCase().contains(text.toLowerCase()) ||
                                item.getInvoicenum().toLowerCase().contains(text.toLowerCase()) ||
                                item.getId().toLowerCase().contains(text.toLowerCase())) {
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
                        }
                        else if (filterList.size() == 0 ) {
                            arrayList = filterList;
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                            tvNoRecordFound.setText(((Activity) context).getResources().getString(R.string.no_record_found));
                        }
                        else if (!filterList.isEmpty() || filterList.isEmpty()) {
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
