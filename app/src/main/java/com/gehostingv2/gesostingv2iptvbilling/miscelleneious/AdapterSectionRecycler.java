package com.gehostingv2.gesostingv2iptvbilling.miscelleneious;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.SubCategoriesChildAdapter;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader,
        Child,
        SectionViewHolder,
        ChildViewHolder> {


    Context context;
    ArrayList<LiveStreamsDBModel> channelAvailable;
    List<SectionHeader> sectionItemList;
    public AdapterSectionRecycler(Context context,
                                  List<SectionHeader> sectionItemList,
                                  ArrayList<LiveStreamsDBModel> channelAvailable,
                                  RecyclerView childRecylerView) {
        super(context, sectionItemList);
        this.context = context;
        this.channelAvailable = channelAvailable;
        this.sectionItemList = sectionItemList;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_section_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_section_child, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader section) {
        sectionViewHolder.name.setText(section.sectionText);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            sectionViewHolder.name.setTextColor(context.getResources().getColor(R.color.white,null));
//        }
    }

//    @Override
//    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, int i1, RecyclerView recyclerView) {
//
//    }

//    @Override
//    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, int i1, LiveStreamsDBModel liveStreamsDBModel) {
//
//
//    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int sectionPosition, int childPosition, Child child) {

        channelAvailable = (ArrayList<LiveStreamsDBModel>) sectionItemList.get(sectionPosition).channelSelcted();


//        int noofCloumns = Utils.getNumberOfColumns(context);
//
//        LinearLayoutManager layoutManager = new GridLayoutManager(context, noofCloumns, GridLayoutManager.HORIZONTAL, false) ;
//                new GridLayoutManager(context, noofCloumns);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        childViewHolder.name.setLayoutManager(layoutManager);

//        LinearSnapHelper snapHelper = new LinearSnapHelper() {
//            @Override
//            public int findTargetSnapPosition(RecyclerView.LayoutManager lm, int velocityX, int velocityY) {
//                View centerView = findSnapView(lm);
//                if (centerView == null)
//                    return RecyclerView.NO_POSITION;
//
//                int position = lm.getPosition(centerView);
//                int targetPosition = -1;
//                if (lm.canScrollHorizontally()) {
//                    if (velocityX < 0) {
//                        targetPosition = position - 1;
//                    } else {
//                        targetPosition = position + 1;
//                    }
//                }
//
//                if (lm.canScrollVertically()) {
//                    if (velocityY < 0) {
//                        targetPosition = position - 1;
//                    } else {
//                        targetPosition = position + 1;
//                    }
//                }
//
//                final int firstItem = 0;
//                final int lastItem = lm.getItemCount() - 1;
//                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
//                return targetPosition;
//            }
//        };
//        snapHelper.attachToRecyclerView( childViewHolder.name);

//
//        int totalVisibleItems = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition();
//        int centeredItemPosition = totalVisibleItems / 2;
//        childViewHolder.name.smoothScrollToPosition(childPosition);
//        childViewHolder.name.setScrollY(centeredItemPosition );
        childViewHolder.name.setHorizontalScrollBarEnabled(true);


        SubCategoriesChildAdapter subCategoriesChildAdapter = new SubCategoriesChildAdapter(channelAvailable, context);
        childViewHolder.name.setAdapter(subCategoriesChildAdapter);
    }
}