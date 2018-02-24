package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.NewEPGFragment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveStreamsEPGCategoriesAdapter extends FragmentPagerAdapter {
    private Context context;
    final int liveStreamTotalCategories;
    private String liveStreamCategoriesNames[];
    private String LiveStreamCategoriesIds[];
    private Map<Integer,String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public LiveStreamsEPGCategoriesAdapter(List<LiveStreamCategoryIdDBModel> liveStreamCategories, FragmentManager fm, Context context) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
        this.liveStreamTotalCategories = liveStreamCategories.size();
        this.liveStreamCategoriesNames = new String[liveStreamTotalCategories];
        this.LiveStreamCategoriesIds = new String[liveStreamTotalCategories];
        this.context = context;
        for(int i=0;i<this.liveStreamTotalCategories;i++){
            String categoryName = liveStreamCategories.get(i).getLiveStreamCategoryName();
            String categoryId = liveStreamCategories.get(i).getLiveStreamCategoryID();
            liveStreamCategoriesNames[i]=categoryName;
            LiveStreamCategoriesIds[i]=categoryId;
        }

    }


    @Override
    public int getCount() {
        return liveStreamTotalCategories;
    }

    @Override
    public Fragment getItem(int position) {

        return  NewEPGFragment.newInstance(LiveStreamCategoriesIds[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return liveStreamCategoriesNames[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        Object obj = super.instantiateItem(container,position);
        if(obj instanceof Fragment){
            //record the fragment tag here.
            Fragment f= (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position,tag);
        }
        return obj;
    }

    public NewEPGFragment getFragment(int position){
        String tag= mFragmentTags.get(position);
        if(tag == null)
            return null;
        return (NewEPGFragment) mFragmentManager.findFragmentByTag(tag);
    }
}
