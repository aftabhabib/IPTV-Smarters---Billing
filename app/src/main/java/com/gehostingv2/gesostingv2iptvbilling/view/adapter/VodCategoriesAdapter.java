package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.VodFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VodCategoriesAdapter extends FragmentPagerAdapter {
    private Context context;
    final int liveStreamTotalCategories;
    private String vodCategoriesNames[];
    private String vodCategoriesIds[];
    private Map<Integer,String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;

    public VodCategoriesAdapter(List<LiveStreamCategoryIdDBModel> vodCategories,
                                FragmentManager fm,
                                Context context
//                                ArrayList<LiveStreamCategoryIdDBModel> subCategoryList
    ) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
        this.liveStreamTotalCategories = vodCategories.size();
        this.vodCategoriesNames = new String[liveStreamTotalCategories];
        this.vodCategoriesIds = new String[liveStreamTotalCategories];
        this.context = context;
        this.subCategoryList = subCategoryList;
        for(int i=0;i<this.liveStreamTotalCategories;i++){
            String categoryName = vodCategories.get(i).getLiveStreamCategoryName();
            String categoryId = vodCategories.get(i).getLiveStreamCategoryID();
            vodCategoriesNames[i]=categoryName;
            vodCategoriesIds[i]=categoryId;
        }
    }


    @Override
    public int getCount() {
        return liveStreamTotalCategories;
    }


    @Override
    public Fragment getItem(int position) {
        return  VodFragment.newInstance(vodCategoriesIds[position], vodCategoriesNames[position]);  //, subCategoryList
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return vodCategoriesNames[position];
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

    public VodFragment getFragment(int position){
        String tag= mFragmentTags.get(position);
        if(tag == null)
            return null;
        return (VodFragment) mFragmentManager.findFragmentByTag(tag);
    }

}
