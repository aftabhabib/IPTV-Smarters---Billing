package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetViewRequestReplyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ReplyPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ViewTicketRequestPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.TicketsAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.ViewTicketRequestAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ViewTicketRequestInterface;

import java.util.ArrayList;

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
 * Use the {@link ViewTicketsRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTicketsRequestFragment extends Fragment implements ViewTicketRequestInterface, ExpandableListView.OnChildClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_my_support_tickets)
    TextView tvMySupportTickets;
    @BindView(R.id.view_line_my_support_ticket)
    View viewLineMySupportTicket;
    @BindView(R.id.rl_view_request)
    RelativeLayout rlViewRequest;
    @BindView(R.id.bt_reply_toggle)
    Button btReplyToggle;
    @BindView(R.id.tv_name_content)
    TextView tvNameContent;
    @BindView(R.id.tv_email_address)
    TextView tvEmailAddress;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_cancel_reply)
    Button btCancelReply;
    @BindView(R.id.ll_expandable)
    RelativeLayout llExpandable;
    @BindView(R.id.expandableLayout1)
    ExpandableLinearLayout expandableLayout1;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.rl_reply_ticket)
    RelativeLayout rlReplyTicket;
    @BindView(R.id.bt_close_ticket)
    Button closeTicketBT;
    @BindView(R.id.tv_no_record_found)
    TextView tvNORecordFound;

    private ArrayList<ReplyPojo> repliesPojos;
    private int ticketId;
    private ViewTicketRequestAdapter viewTicketRequestAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private ImageView logoHomeIV;
    private Menu menu;


    GetViewRequestReplyCallback getViewRequestReplyCallback;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ViewTicketRequestPresenter viewTicketRequestPresenter;
    private Context context;
    private SearchView searchView;
    private Typeface fontOPenSansBold;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    private FragmentActivity myContext;


    public ViewTicketsRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewTicketsRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTicketsRequestFragment newInstance(String param1, String param2) {
        ViewTicketsRequestFragment fragment = new ViewTicketsRequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_tickets_request, container, false);
        unbinder = ButterKnife.bind(this, view);

        ActivityCompat.invalidateOptionsMenu(getActivity());
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        searchView= (SearchView) toolbar.findViewById(R.id.action_search);
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        intialize();
        return view;
    }

    private void setToolbarLogoImagewithSearchView() {
//        setHasOptionsMenu(true);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);
//        searchView= (SearchView) toolbar.findViewById(R.id.action_search);
    }

    private void intialize() {
        context = getContext();
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");

        tvMySupportTickets.setTypeface(fontOPenSansBold);
        viewTicketRequestPresenter = new ViewTicketRequestPresenter(this);
        mLayoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(mLayoutManager);
        progressDialog = Utils.showProgressD(progressDialog, context);

        int defaultValue = -1;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int ticketId = bundle.getInt(AppConst.TICKET_ID, defaultValue);
            if (ticketId != 0 && ticketId != -1) {
                viewTicketRequestPresenter.getTickets(ticketId);
            }
        }
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
        myContext = (FragmentActivity) getActivity();
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

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        logoHomeIV.setImageResource(R.drawable.logo_home_new);

        mListener = null;
    }

    @Override
    public void getTicket(GetViewRequestReplyCallback getViewRequestReplyCallback) {
        this.getViewRequestReplyCallback = getViewRequestReplyCallback;
        if (tvNameContent != null && tvEmailAddress != null) {
            tvNameContent.setText(getViewRequestReplyCallback.getName());
            tvEmailAddress.setText(getViewRequestReplyCallback.getEmail());
        }
        repliesPojos = getViewRequestReplyCallback.getReplies().getReply();
        ticketId = Integer.parseInt(getViewRequestReplyCallback.getTicketid());
        viewTicketRequestAdapter = new ViewTicketRequestAdapter(context, repliesPojos, fontOPenSansBold);
        if (myRecyclerView != null && viewTicketRequestAdapter != null)
            myRecyclerView.setAdapter(viewTicketRequestAdapter);
    }

    @Override
    public void getTicketForDepptName(GetViewRequestReplyCallback getViewRequestReplyCallback, TicketsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public void addTicketReply(CommonResponseCallback commonResponseCallback, String message) {
        if (etMessage != null)
            etMessage.getText().clear();
        if (progressDialog != null)
            progressDialog.dismiss();
        if (commonResponseCallback.getResult().equals("success")) {
            AppConst.TICKET_REPLY = true;
            Utils.showToast(context, context.getResources().getString(R.string.ticket_added_succuessfully));
            viewTicketRequestPresenter.getTickets(ticketId);

            if (expandableLayout1.isExpanded()) {
                expandableLayout1.collapse();
                btReplyToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.reply_off, 0);
                if (context != null)
                    tvMySupportTickets.setText(context.getResources().getString(R.string.view_request));
            }

            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

//            getActivity().getWindow().setSoftInputMode(
//                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//            viewTicketRequestAdapter.notifyDataSetChanged();
        }
        if (commonResponseCallback.getResult().equals("error")) {
            Utils.showToast(context, commonResponseCallback.getMessage());
        }

    }

    @Override
    public void updateTicketReply(CommonResponseCallback commonResponseCallback) {
        if (commonResponseCallback.getResult().equals("success")) {
            if (expandableLayout1.isExpanded()) {
                expandableLayout1.collapse();
                if (context != null)
                    tvMySupportTickets.setText(context.getResources().getString(R.string.view_request));
            }
            if (expandableLayout1.isExpanded()) {
                btReplyToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.reply_off, 0);
                tvMySupportTickets.setText(context.getResources().getString(R.string.view_ticket));
            }
            getViewRequestReplyCallback.setStatus("Closed");
        }
    }

    @Override
    public void updateTicketClose(CommonResponseCallback commonResponseCallback) {
        if (commonResponseCallback.getResult().equals("success")) {

            AppConst.CLOSE_TICKET = true;

            Fragment ticketFragment = new TicketsFragment();
            this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
                    .replace(R.id.fl_view_ticket, ticketFragment, AppConst.TAG_MY_TICKETS)
                    .addToBackStack(AppConst.TAG_MY_TICKETS)
                    .commit();

//            ViewTicketsRequestFragment fragment = new ViewTicketsRequestFragment();
//            FragmentManager manager = getActivity().getSupportFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove(fragment);
//            trans.commit();
//            manager.popBackStack();


//            if (getActivity().getSupportFragmentManager().findFragmentByTag(AppConst.TAG_VIEW_TICKET_REQUEST) != null) {
//                getActivity().getSupportFragmentManager().popBackStackImmediate(AppConst.TAG_VIEW_TICKET_REQUEST,
//                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }
        }

    }

    @Override
    public void atStart() {
        if (pbLoader != null)
            pbLoader.setVisibility(View.VISIBLE);
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onFinish() {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (pbLoader != null)
            pbLoader.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_submit, R.id.bt_cancel_reply, R.id.iv_back_press, R.id.bt_close_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                if (progressDialog != null)
                    progressDialog.show();
                int clientId = 0;
                if (context != null) {
                    SharedPreferences preferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                    clientId = preferences.getInt("clientid", -1);
                }
                String ticketReply = etMessage.getText().toString();
                if (!ticketReply.isEmpty()) {
                    viewTicketRequestPresenter.addTicketReply(ticketId, ticketReply, clientId);
                } else {
                    progressDialog.dismiss();
                    Utils.showToast(context, context.getResources().getString(R.string.please_write_your_message));
                }
                break;
            case R.id.bt_cancel_reply:
                SharedPreferences preferences1 = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                int clientId1 = preferences1.getInt("clientid", -1);
                viewTicketRequestPresenter.updateTicket(ticketId, clientId1);
                break;
            case R.id.iv_back_press:
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


//                ViewTicketsRequestFragment fragment = new ViewTicketsRequestFragment();
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction trans = manager.beginTransaction();
//                trans.remove(fragment);
//                trans.commit();
//                manager.popBackStack();


                Fragment ticketFragment = new TicketsFragment();
                this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out)
                        .replace(R.id.fl_view_ticket, ticketFragment, AppConst.TAG_MY_TICKETS)
                        .addToBackStack(AppConst.TAG_MY_TICKETS)
                        .commit();
//                if (getActivity().getSupportFragmentManager().findFragmentByTag(AppConst.TAG_VIEW_TICKET_REQUEST) != null) {
//                    getActivity().getSupportFragmentManager().popBackStackImmediate(AppConst.TAG_VIEW_TICKET_REQUEST,
//                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                }

//                myContext.onBackPressed();


                break;
            case R.id.bt_close_ticket:
                if (context != null) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage(context.getResources().getString(R.string.is_close_ticket));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            context.getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences preferences1 = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                                    int clientId1 = preferences1.getInt("clientid", -1);
                                    viewTicketRequestPresenter.updateTicketClosed(ticketId, clientId1, "Closed");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            context.getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
//        if (getActivity().getSupportFragmentManager().findFragmentByTag(AppConst.TAG_VIEW_TICKET_REQUEST) != null) {
//                    getActivity().getSupportFragmentManager().popBackStackImmediate(AppConst.TAG_VIEW_TICKET_REQUEST,
//                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                }

        Fragment ticketFragment = new TicketsFragment();
        this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out)
                .replace(R.id.fl_view_ticket, ticketFragment, AppConst.TAG_MY_TICKETS)
                .addToBackStack(AppConst.TAG_MY_TICKETS)
                .commit();


    }


    @OnClick(R.id.bt_reply_toggle)
    public void onViewClicked() {
        expandableLayout1.toggle();
        Drawable stata = btReplyToggle.getCompoundDrawables()[2];
        if (expandableLayout1.isExpanded()) {
            btReplyToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.reply_off, 0);
            if (context != null)
                tvMySupportTickets.setText(context.getResources().getString(R.string.view_request));
        } else {
            btReplyToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.reply_on, 0);
            if (context != null)
                tvMySupportTickets.setText(context.getResources().getString(R.string.view_ticket));
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        viewTicketRequestAdapter.notifyDataSetChanged();
        return true;
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();


/**
 * commented code used here to can be used if have use default menu
 * inflater
 */
//        inflater.inflate(R.menu.menu_search_view_ticket, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//


/**
 * This code used here to put menu item in center vertical using instanceof Action menu
 * view inflating menu view using toolbar
 */
        toolbar.inflateMenu(R.menu.menu_search_view_ticket);
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            // make toggle drawable center-vertical, you can make each view alignment whatever you want
            if (toolbar.getChildAt(i) instanceof ActionMenuView) {
                Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(i).getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_ticket_reply:
                searchView = (SearchView) (SearchView) MenuItemCompat.getActionView(item);
                if (context != null)
                    searchView.setQueryHint(context.getResources().getString(R.string.search_view_ticket_reply));
                SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponetName()));
                searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if(tvNORecordFound!=null) {
                            tvNORecordFound.setVisibility(View.GONE);
                            //filters list items from adapter
                            if (viewTicketRequestAdapter != null)
                                viewTicketRequestAdapter.filter(newText, tvNORecordFound);
                            return false;
                        }
                        return false;
                    }
                });

                return true;

            default:
                break;
        }
        return false;
    }
}
