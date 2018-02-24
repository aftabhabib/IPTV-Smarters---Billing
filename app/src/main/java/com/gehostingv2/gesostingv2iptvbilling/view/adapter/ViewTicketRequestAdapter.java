package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ReplyPojo;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewTicketRequestAdapter extends RecyclerView.Adapter<ViewTicketRequestAdapter.ViewHolder> {
    private ArrayList<ReplyPojo> repliesPojos;
    private ArrayList<ReplyPojo>  completeList= new ArrayList<>();
    private Context context;
    private Typeface fontOPenSansBold;
    private ArrayList<ReplyPojo> filterList;

    public ViewTicketRequestAdapter(Context context, ArrayList<ReplyPojo> repliesPojos, Typeface fontOPenSansBold) {
        this.context = context;
        this.repliesPojos = repliesPojos;
        this.completeList = repliesPojos;
        this.filterList = new ArrayList<ReplyPojo>();
        this.filterList.addAll(repliesPojos);
        this.fontOPenSansBold= fontOPenSansBold;
        Collections.reverse(this.repliesPojos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout. view_reply, parent, false);   //layout_ticket_reply(previous screen)  //layout_view_ticket_request_list_items
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ReplyPojo replyPojo = repliesPojos.get(position);
        String name = replyPojo.getName();
        String admin = replyPojo.getAdmin();
        int imgResource = R.drawable.chat_bubble;
        String msg = replyPojo.getMessage();

        if (!name.equals("") && !name.isEmpty()) {
            holder.ivClientPlaceholder.setVisibility(View.VISIBLE);
            holder.ivAdminPlaceholder.setVisibility(View.INVISIBLE);
            holder.tvName.setText(replyPojo.getName().toUpperCase());
            holder.tvName.setTypeface(fontOPenSansBold);
            holder.tvMessage.setText(replyPojo.getMessage());
            holder.tvDate.setText(replyPojo.getDate());
            holder.rlReplyTicket.setBackgroundColor(Color.parseColor("#eceef2"));
        } else if (!admin.isEmpty()&&!admin.equals("") ) {
            holder.ivAdminPlaceholder.setVisibility(View.VISIBLE);
            holder.ivClientPlaceholder.setVisibility(View.INVISIBLE);
            holder.tvName.setText(replyPojo.getAdmin().toUpperCase() + " (SUPPORT AGENT)");
            holder.tvName.setTextColor(Color.parseColor("#ffffff"));
            holder.tvName.setTypeface(fontOPenSansBold);
            holder.tvMessage.setText(replyPojo.getMessage());
            holder.tvMessage.setTextColor(Color.parseColor("#ffffff"));
            holder.tvDate.setText(replyPojo.getDate());
            holder.tvDate.setTextColor(Color.parseColor("#ffffff"));
            holder.rlReplyTicket.setBackgroundColor(Color.parseColor("#a5b0c2"));
        }




        holder.rlReplyTicket.setOnKeyListener(new View.OnKeyListener() {
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

    public static Drawable changeDrawableColor(int drawableRes, int colorRes, Context context) {
        //Convert drawable res to bitmap
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        final Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        final Paint p = new Paint();
        final Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);

        //Create new drawable based on bitmap
        final Drawable drawable = new BitmapDrawable(context.getResources(), resultBitmap);
        drawable.setColorFilter(new
                PorterDuffColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    @Override
    public int getItemCount() {
        return repliesPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_admin_placeholder)
        ImageView ivAdminPlaceholder;
        @BindView(R.id.iv_client_placeholder)
        ImageView ivClientPlaceholder;
        @BindView(R.id.rl_ticket_client_staff)
        RelativeLayout rlTicketClientStaff;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_reply_ticket)
        RelativeLayout rlReplyTicket;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void filter(final String text, final TextView tvNORecordFound) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList = new ArrayList<ReplyPojo>();
//                completeList.addAll(repliesPojos);
                // Clear the filter list
                if (filterList != null)
                    filterList.clear();
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(completeList);
                } else {
                    // Iterate in the original List and add it to filter list...
                    for (ReplyPojo item : repliesPojos) {
                        if (item.getMessage().toLowerCase().contains(text.toLowerCase()) ||
                                item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getAdmin().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDate().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(text)){
                            repliesPojos = completeList;
                        }
                        else if(!filterList.isEmpty() || filterList.isEmpty()){
                            repliesPojos =filterList;
                        }
                        if (filterList.size() == 0) {
                            tvNORecordFound.setVisibility(View.VISIBLE);
                        }
                        notifyDataSetChanged();



//                        if(filterList.size()>0){
//                            repliesPojos = filterList;
//                        }else if( TextUtils.isEmpty(text)){
//                            repliesPojos = completeList;
//                        }else if(filterList.size()==0){
//                            tvNORecordFound.setVisibility(View.VISIBLE);
//                        }
//                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}