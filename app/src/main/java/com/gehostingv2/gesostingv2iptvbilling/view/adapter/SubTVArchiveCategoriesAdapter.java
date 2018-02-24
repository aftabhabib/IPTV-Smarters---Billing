package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SubTVArchiveFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SubTVArchiveCategoriesAdapter extends FragmentPagerAdapter {
//    private final LiveStreamsEpgCallback liveStreamEpgCallback;
    private final ArrayList<XMLTVProgrammePojo> liveStreamEpgCallback;
    private final int opened_stream_id;
    private final String opened_num;
    private final String opened_channel_name;
    private final String opened_channel_icon;
    private final String opened_channel_id;
    private final String opened_channel_duration;
    private Context context;
    final int liveStreamTotalCategories;
    private List<String> liveStreamCategoriesNames;
    private String LiveStreamCategoriesIds[];
    private Map<Integer,String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public SubTVArchiveCategoriesAdapter(List<String> liveStreamCategories, ArrayList<XMLTVProgrammePojo> liveStreamsEpgCallback, int opened_stream_id, String opened_num, String opened_channel_name, String opened_channel_icon, String opened_channel_id, String opened_channel_duration, FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
        this.liveStreamTotalCategories = liveStreamCategories.size();
        this.liveStreamCategoriesNames = liveStreamCategories;
        this.liveStreamEpgCallback = liveStreamsEpgCallback;
        this.opened_stream_id = opened_stream_id;
        this.opened_num = opened_num;
        this.opened_channel_name = opened_channel_name;
        this.opened_channel_icon = opened_channel_icon;
        this.opened_channel_id = opened_channel_id;
        this.opened_channel_duration = opened_channel_duration;
//        this.liveStreamCategoriesNames = new String[liveStreamTotalCategories];
//        this.LiveStreamCategoriesIds = new String[liveStreamTotalCategories];
        this.context = context;
//        for(int i=0;i<this.liveStreamTotalCategories;i++){
//            String categoryName = liveStreamCategories.get(i).getCategoryName();
//            String categoryId = liveStreamCategories.get(i).getCategoryId();
//            liveStreamCategoriesNames[i]=categoryName;
//            LiveStreamCategoriesIds[i]=categoryId;
//        }

    }


    @Override
    public int getCount() {
        return liveStreamTotalCategories;
    }

    @Override
    public Fragment getItem(int position) {
//return null;
        return  SubTVArchiveFragment.newInstance(liveStreamCategoriesNames.get(position),liveStreamEpgCallback,opened_stream_id,opened_num,opened_channel_name,opened_channel_icon,opened_channel_id,opened_channel_duration);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return liveStreamCategoriesNames.get(position);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position){
//        Object obj = super.instantiateItem(container,position);
//        if(obj instanceof Fragment){
//            //record the fragment tag here.
//            Fragment f= (Fragment) obj;
//            String tag = f.getTag();
//            mFragmentTags.put(position,tag);
//        }
//        return obj;
//
//    }


//    public SubTVArchiveFragment getFragment(int position){
//        String tag= mFragmentTags.get(position);
//        if(tag == null)
//            return null;
//        return (SubTVArchiveFragment) mFragmentManager.findFragmentByTag(tag);
//    }
}
