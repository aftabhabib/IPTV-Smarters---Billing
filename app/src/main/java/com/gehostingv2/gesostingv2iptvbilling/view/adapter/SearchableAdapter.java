package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// The standard text view adapter only seems to search from the beginning of whole words
// so we've had to write this whole class to make it possible to search
// for parts of the arbitrary string we want
public class SearchableAdapter extends BaseAdapter implements Filterable {

//    private List<String>originalData = null;
    public ArrayList<LiveStreamsDBModel> originalData;
    private ArrayList<LiveStreamsDBModel> filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private Context context;

    public SearchableAdapter(Context context, ArrayList<LiveStreamsDBModel> data) {
        this.filteredData = data;
        this.originalData = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }
    public ArrayList<LiveStreamsDBModel> getFilteredData(){
        return filteredData;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.channel_list, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.list_view);
            holder.image = (ImageView) convertView.findViewById(R.id.tv_logo);
            holder.ll_list_view = (LinearLayout) convertView.findViewById(R.id.ll_list_view);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // If weren't re-ordering this you could rely on what you set last time
        holder.text.setText(filteredData.get(position).getName());


        if (filteredData.get(position).getStreamIcon() != null && !filteredData.get(position).getStreamIcon().equals("")) {
            Picasso.with(context).load(filteredData.get(position).getStreamIcon()).placeholder(R.drawable.iptv_placeholder).into(holder.image); //dataSet.get(listPosition).getStreamIcon()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.image.setImageDrawable(this.context.getResources().getDrawable(R.drawable.iptv_placeholder, null));
            }
        }
//        holder.ll_list_view.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                holder.ll_list_view.setBackgroundResource(R.drawable.parental_control_icon);
//            }
//        });
//        holder.ll_list_view.setOnClickListener(new View.OnClickListener() {
////            @Override
//            public void onClick(View v) {
//                NSTPlayerActivity.onItemClick();
//                holder.ll_list_view.setFocusable(false);
//                Utils.showToast(context, "Testing1: ");
////                ArrayList<LiveStreamsDBModel> filteredData = adapter.getFilteredData();
//                if(filteredData!=null){
//                    int num = Integer.parseInt(filteredData.get(position).getNum());
//                    NSTPlayerActivity playerActivity = new NSTPlayerActivity();
//                    playerActivity.testing(num,filteredData,position);
//
//                }else{
////                    player.setCurrentWindowIndex(position + 1);
////                    int num = Integer.parseInt(allStreams.get(position).getNum());
////                    player.setCurrentWindowIndex(num);
////                    player.setTitle(allStreams.get(position).getName() + " -- " + allStreams.get(position).getStreamId() + " -- " + num);
////                    player.play(mFilePath, Integer.parseInt(allStreams.get(position).getStreamId()));
//
//                }
//                // TODO Auto-generated method stub
//
//            }
//        });
//        holder.image.

        return convertView;
    }

    static class ViewHolder {
        TextView text;
        ImageView image;
        LinearLayout ll_list_view;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<LiveStreamsDBModel> list = originalData;

            int count = list.size();
            final ArrayList<LiveStreamsDBModel> nlist = new ArrayList<>(count);

            LiveStreamsDBModel filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<LiveStreamsDBModel>) results.values;
            notifyDataSetChanged();
        }

    }
}