package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.LoginIPTVFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.LoginWHMCSFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.LoginWelcomeFragment;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.CURRENT_TAG;

public class LoginWelcomeActivity extends AppCompatActivity implements
        LoginWelcomeFragment.OnFragmentInteractionListener,
        LoginWHMCSFragment.OnFragmentInteractionListener,
        LoginIPTVFragment.OnFragmentInteractionListener{

    private String forgetPass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        Intent intentForgetPassword = getIntent();
        Bundle bundle = new Bundle();
//        Foreground foreground = new Foreground();
//        foreground.init((Application) getApplicationContext());


        if(intentForgetPassword!=null){
            forgetPass = intentForgetPassword.getStringExtra(AppConst.FORGETPASS);
        }
        if(forgetPass!=null && !forgetPass.isEmpty() && forgetPass.equals(AppConst.FORGETPASS)){
            bundle.putString(AppConst.FORGETPASS, AppConst.FORGETPASS);
        }
        Fragment fragment = new LoginWelcomeFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            moveTaskToBack(true);
            finish();
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
