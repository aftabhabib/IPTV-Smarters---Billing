package com.gehostingv2.gesostingv2iptvbilling.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ActiveServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.CancelledServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.PendingServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SuspendedServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TerminatedServicesFragment;

import java.util.ArrayList;

public class ServicesPagerAdapter extends FragmentStatePagerAdapter {

    String tabTitles[] = new String[5];
    int mNumOfTabs = 5;
    Context context;
    ArrayList<Integer> tabServicesTotalCount = new ArrayList<>();

    public ServicesPagerAdapter(FragmentManager fm, int NumOfTabs, Context myContext, ArrayList<Integer> tabServicesTotalCount) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = myContext;
        this.tabServicesTotalCount = tabServicesTotalCount;
        this.tabTitles[0] = context.getString(R.string.my_service_active);
        this.tabTitles[1] = context.getString(R.string.my_service_cancelled);
        this.tabTitles[2] = context.getString(R.string.my_service_pending);
        this.tabTitles[3] = context.getString(R.string.my_service_suspended);
        this.tabTitles[4] = context.getString(R.string.my_service_terminated);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ActiveServicesFragment tab2 = new ActiveServicesFragment();
                return tab2;

            case 1:
                CancelledServicesFragment tab1 = new CancelledServicesFragment();
                return tab1;
            case 2:
                PendingServicesFragment tab3 = new PendingServicesFragment();
                return tab3;
            case 3:
                SuspendedServicesFragment tab4 = new SuspendedServicesFragment();
                return tab4;
            case 4:
                TerminatedServicesFragment tab5 = new TerminatedServicesFragment();
                return tab5;
            default:
                return null;
        }
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tablayout_text_icon, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_tab_service_name);
        tv.setText(tabTitles[position]);
        return v;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public void setCancelledTabView(View view, Typeface fontOPenSansBold) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansBold);
            serviceName.setTextColor(Color.parseColor("#0c99e2"));
        }
    }

    public void setActiveTabView(View view, Typeface fontOPenSansBold) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansBold);
            serviceName.setTextColor(Color.parseColor("#0cdc78"));
        }
    }

    public void setPendingView(View view, Typeface fontOPenSansBold) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansBold);
            serviceName.setTextColor(Color.parseColor("#e4cc00"));
        }
    }

    public void setSuspendedTabView(View view, Typeface fontOPenSansBold) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansBold);
            serviceName.setTextColor(Color.parseColor("#ff6f43"));
        }
    }

    public void setTerminatedTabView(View view, Typeface fontOPenSansBold) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansBold);
            serviceName.setTextColor(Color.parseColor("#ffc600"));
        }
    }

    public void setCancelledTabUnselectedView(View view, Typeface fontOPenSansRegular) {
        if (view != null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansRegular);
            serviceName.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setActiveTabUnselectedView(View view, Typeface fontOPenSansRegular) {
        if(view!=null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansRegular);
            serviceName.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setPendingUnselectedView(View view, Typeface fontOPenSansRegular) {
        if(view!=null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansRegular);
            serviceName.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setSuspendedTabUnselectedView(View view, Typeface fontOPenSansRegular) {
        if(view!=null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansRegular);
            serviceName.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setTerminatedTabUnselectedView(View view, Typeface fontOPenSansRegular) {
        if(view!=null) {
            TextView serviceName = (TextView) view.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(fontOPenSansRegular);
            serviceName.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void setDefaultOpningViewTab(View viewDefaultOPeningTab, Typeface font) {
        if(viewDefaultOPeningTab!=null) {
            TextView serviceName = (TextView) viewDefaultOPeningTab.findViewById(R.id.tv_tab_service_name);
            serviceName.setTypeface(font);
            serviceName.setTextColor(Color.parseColor("#0cdc78"));
        }
    }
}
