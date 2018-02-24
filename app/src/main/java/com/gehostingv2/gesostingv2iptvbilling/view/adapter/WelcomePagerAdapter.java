package com.gehostingv2.gesostingv2iptvbilling.view.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.LoginIPTVFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.LoginWHMCSFragment;

public class WelcomePagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[]{"PAID", "UNPAID"};
    int mNumOfTabs = 2;
    private Context context;
    private int postion= 0;


    public WelcomePagerAdapter(FragmentManager fm, int NumOfTabs,
                               Context myContext) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = myContext;
        this.postion= 0;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginIPTVFragment tab1 = new LoginIPTVFragment();
                postion = 0;
                return tab1;
            case 1:
                LoginWHMCSFragment tab2 = new LoginWHMCSFragment();
                postion = 1;
                return tab2;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public int getCurrentPostion(){
        return postion;
    }
}
