package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.WelcomePagerAdapter;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginWelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginWelcomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.tab_layout_invoices)
    TabLayout tabLayoutInvoices;
    @BindView(R.id.line_below_tabs)
    View lineBelowTabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.rl_my_invoices)
    RelativeLayout rlMyInvoices;

    Unbinder unbinder;
    @BindView(R.id.ll_dots)
    LinearLayout llDots;

    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;


    @BindView(R.id.ll_billing)
    LinearLayout llBilling;

    @BindView(R.id.ll_iptv)
    LinearLayout llIPTV;

    @BindView(R.id.tv_mybilling)
    TextView tvMyBilling;

    private TextView[] dots;
    int pagePasotion = 0;

    //    private List<ImageView> dots;
    private final static int NUM_PAGES = 2;


    private FragmentActivity myContext;
    private Context context;
    WelcomePagerAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String forgetPass;

    public LoginWelcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginWelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginWelcomeFragment newInstance(String param1, String param2) {
        LoginWelcomeFragment fragment = new LoginWelcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fabric.with(getContext(), new Crashlytics());
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        tabLayoutInvoices.addTab(tabLayoutInvoices.newTab().setText("PAID"));
        tabLayoutInvoices.addTab(tabLayoutInvoices.newTab().setText("UNPAID"));
        tabLayoutInvoices.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new WelcomePagerAdapter(getChildFragmentManager(),
                tabLayoutInvoices.getTabCount(), context
        );
        pager.setAdapter(adapter);
        tabLayoutInvoices.setupWithViewPager(pager, true);
        Bundle bundle = getArguments();
        if(bundle!=null){
            forgetPass = bundle.getString(AppConst.FORGETPASS);
        }
        if(forgetPass!=null && !forgetPass.isEmpty() && !forgetPass.equals("") && forgetPass.equals(AppConst.FORGETPASS)){
            pager.setCurrentItem(1);
            pagePasotion = 1;
            llBilling.setVisibility(View.INVISIBLE);
            llIPTV.setVisibility(View.VISIBLE);
        }else{
            pager.setCurrentItem(0);
            pagePasotion = 0;
            llIPTV.setVisibility(View.INVISIBLE);
        }
        addBottomDots(pager.getCurrentItem(), context);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (context != null) {
                    addBottomDots(position, context);
                    pagePasotion = position;
                }
                if(position==0){
                    llIPTV.setVisibility(View.INVISIBLE);
                    llBilling.setVisibility(View.VISIBLE);
                }else if(position==1){
                    llBilling.setVisibility(View.INVISIBLE);
                    llIPTV.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void addBottomDots(int currentPage, Context context) {
        dots = new TextView[2];
        llDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this.context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            llDots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void getCurrentItem(int position){
        pager.setCurrentItem(position);
    }

    @OnClick({R.id.ll_iptv,R.id.ll_billing})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_iptv:
                pagePasotion = adapter.getCurrentPostion();
                getCurrentItem(pagePasotion-1);
                llIPTV.setVisibility(View.GONE);
                llBilling.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_billing:
                pagePasotion = adapter.getCurrentPostion();
                getCurrentItem(pagePasotion+1);
                llIPTV.setVisibility(View.VISIBLE);
                llBilling.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
