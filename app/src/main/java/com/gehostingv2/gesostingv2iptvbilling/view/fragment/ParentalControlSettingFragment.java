package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.database.PasswordDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParentalControlSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParentalControlSettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_old_password)
    EditText tvOldPassword;
    @BindView(R.id.tv_new_password)
    EditText tvNewPassword;
    @BindView(R.id.tv_confirm_password)
    EditText tvConfirmPassword;
    @BindView(R.id.bt_save_password)
    Button btSavePassword;
//    @BindView(R.id.expandableLayout1)
//    ExpandableLinearLayout expandableLayout1;
    Unbinder unbinder;

    private String oldPassword = "";
    private String newPassword = "";
    private String confirmPassword = "";
    private Context context;
    private LiveStreamDBHandler liveStreamDBHandler;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ParentalControlSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParentalControlSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParentalControlSettingFragment newInstance(String param1, String param2) {
        ParentalControlSettingFragment fragment = new ParentalControlSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_parental_control_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        oldPassword = String.valueOf(tvOldPassword.getText());
        newPassword = String.valueOf(tvNewPassword.getText());
        confirmPassword = String.valueOf(tvConfirmPassword.getText());
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

    @OnClick(R.id.bt_save_password)
    public void onViewClicked() {
        if (context != null) {
            oldPassword = String.valueOf(tvOldPassword.getText());
            newPassword = String.valueOf(tvNewPassword.getText());
            confirmPassword = String.valueOf(tvConfirmPassword.getText());
            SharedPreferences preferencesIPTV = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, Context.MODE_PRIVATE);
            String username = preferencesIPTV.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            boolean oldPasswordValidationCheck = passwordValidationCheck(username, oldPassword);
            if (oldPasswordValidationCheck) {
                boolean fieldChcek = compNewConfirmPassword(newPassword, confirmPassword);
                if (fieldChcek) {
                    if (newPassword.equals(confirmPassword)) {
                        boolean updatePassword =
                                liveStreamDBHandler.upDatePassword(username, newPassword);
                        updateSuccessfull(updatePassword);
                    } else {
                        Toast.makeText(context, "New Password and Confirm Password do not match, " +
                                "Please try again", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                if (context != null)
                    Toast.makeText(context, "Invalid old password, Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateSuccessfull(boolean updatePassword) {
        if (updatePassword) {
            if (context != null)
                Toast.makeText(context, "Password Updated Successfully !", Toast.LENGTH_SHORT).show();
        } else {
            if (context != null)
                Toast.makeText(context, "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean compNewConfirmPassword(String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.equals("") || newPassword.isEmpty()) {
            if (context != null)
                Toast.makeText(context, "Pleas fill New Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("") &&
                confirmPassword == null && confirmPassword.isEmpty() || confirmPassword.equals("")) {
            Toast.makeText(context, "Pleas fill Confirm Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("") &&
                confirmPassword != null && !confirmPassword.isEmpty() || !confirmPassword.equals("")) {
            return true;
        }
        return false;
    }

    private boolean passwordValidationCheck(String username, String oldPassword) {
        LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
        ArrayList<PasswordDBModel> userPasswordList = liveStreamDBHandler.getAllPassword();
        boolean isUserExist = false;
        String userPasswordDB = "";
        if (userPasswordList != null) {
            for (PasswordDBModel listIem : userPasswordList) {
                if (listIem.getUserDetail().equals(username) && !listIem.getUserPassword().isEmpty()) {
                    isUserExist = true;
                    userPasswordDB = listIem.getUserPassword();
                }
            }
        }
        if (isUserExist && oldPassword != null && !oldPassword.isEmpty() &&
                !oldPassword.equals("") && !userPasswordDB.equals("") &&
                userPasswordDB.equals(oldPassword)) {
            return true;
        }
        return false;

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
