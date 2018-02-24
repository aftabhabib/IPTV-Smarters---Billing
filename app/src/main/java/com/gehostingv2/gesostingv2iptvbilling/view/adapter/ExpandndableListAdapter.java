package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.model.database.ExpandedMenuModel;

import java.util.HashMap;
import java.util.List;


public class ExpandndableListAdapter extends BaseExpandableListAdapter {


    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<ExpandedMenuModel, List<String>> mListDataChild;
    ExpandableListView expandList;

    public ExpandndableListAdapter(Context context,
                                   List<ExpandedMenuModel> listDataHeader,
                                   HashMap<ExpandedMenuModel, List<String>> listDataChild,
                                   ExpandableListView expandableList) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listDataChild;
        this.expandList=expandableList;
    }

    @Override
    public int getGroupCount() {
        int i= mListDataHeader.size();
        Log.d("GROUPCOUNT",String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount=0;
        if(this.mListDataChild.get(this.mListDataHeader.get(groupPosition))!=null)
        {
            childCount=this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                    .size();
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD",mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }



        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);
        ImageView headerIcon=    (ImageView)convertView.findViewById(R.id.iconimage);
        ImageView expandCollapseToggle = (ImageView)convertView.findViewById(R.id.exapnd_collapse_toggle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getIconName());
        headerIcon.setTag(groupPosition);

        headerIcon.setImageResource(headerTitle.getIconImg());

        int isChild = getChildrenCount(groupPosition);
        if(isChild>0){
            expandCollapseToggle.setVisibility(View.VISIBLE);
            expandCollapseToggle.setImageResource( isExpanded ? R.drawable.drawer_submenu_up_arrow : R.drawable.drawer_submenu_arrow );

        }else if(isChild==0){
            expandCollapseToggle.setVisibility(View.GONE);
        }

//        expandCollapseToggle.setTag(groupPosition);
//
//        if ( getChildrenCount( groupPosition ) == 0 ) {
//            expandCollapseToggle.setVisibility( View.INVISIBLE );
//        } else {
//            expandCollapseToggle.setVisibility( View.VISIBLE );
//            expandCollapseToggle.setImageResource( isExpanded ? R.drawable.up_arrow : R.drawable.drawer_submenu_arrow );
//        }



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,  boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        int childCount = getChildrenCount(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }
        convertView.setBackgroundResource(R.color.light_gray);
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);
        txtListChild.setText(childText);

        if(childCount==1) {
            ImageView lineAbove = (ImageView) convertView
                    .findViewById(R.id.iv_line_above);
            lineAbove.setVisibility(View.INVISIBLE);
            ImageView lineBelow = (ImageView) convertView
                    .findViewById(R.id.iv_line);
            lineBelow.setVisibility(View.INVISIBLE);
        }
        if(childCount>1&&childPosition==0){
            ImageView lineAbove = (ImageView) convertView
                    .findViewById(R.id.iv_line_above);
            lineAbove.setVisibility(View.INVISIBLE);
            ImageView lineBelow = (ImageView) convertView
                    .findViewById(R.id.iv_line);
            lineBelow.setVisibility(View.VISIBLE);
        }
        if(childCount>1&&isLastChild){
            ImageView lineAbove = (ImageView) convertView
                    .findViewById(R.id.iv_line_above);
            lineAbove.setVisibility(View.VISIBLE);
            ImageView lineBelow = (ImageView) convertView
                    .findViewById(R.id.iv_line);
            lineBelow.setVisibility(View.INVISIBLE);
        }




        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
