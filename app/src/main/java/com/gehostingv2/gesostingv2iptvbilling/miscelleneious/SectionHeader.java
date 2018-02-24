package com.gehostingv2.gesostingv2iptvbilling.miscelleneious;


import android.support.v7.widget.RecyclerView;

import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.SubCategoriesChildAdapter;
import com.intrusoft.sectionedrecyclerview.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionHeader implements Section<Child> {

    RecyclerView childList;
//    List<Child> childList;
    String sectionText;
    ArrayList<LiveStreamsDBModel> channelAvailable;
    private SubCategoriesChildAdapter subCategoriesChildAdapter;
    private List<Child> list;

    public SectionHeader(RecyclerView childList,
                         String sectionText,
                         ArrayList<LiveStreamsDBModel> channelAvailable,
                         SubCategoriesChildAdapter subCategoriesChildAdapter,
                         List<Child> list) {
        this.childList = childList;
        this.sectionText = sectionText;
        this.channelAvailable = channelAvailable;
        this.subCategoriesChildAdapter = subCategoriesChildAdapter;
        this.list = list;
    }

//    @Override
//    public RecyclerView getChildItems() {
//        return childList;
//    }

    public String getSectionText() {
        return sectionText;
    }

    @Override
    public List<Child> getChildItems() {
        return (List<Child>) list;
//        return null ;
    }

    public List<LiveStreamsDBModel> channelSelcted(){
        return channelAvailable;
    }
}
