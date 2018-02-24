package com.gehostingv2.gesostingv2iptvbilling.view.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XMLTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.presenter.XMLTVPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XMLTVInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.LoadingGearSpinner;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImportEPGActivity extends AppCompatActivity implements XMLTVInterface
{

    Context context;
    @BindView(R.id.tv_setting_streams)
    TextView tvSettingStreams;
    @BindView(R.id.tv_importing_epg)
    TextView tvImportingEpg;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.rl_import_process)
    RelativeLayout rlImportProcess;
    LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.rl_import_layout)
    RelativeLayout rlImportLayout;
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    @BindView(R.id.rl_skip)
    RelativeLayout rlSkip;

    //    @BindView(R.id.pb_circle_loader)
//    ProgressBar pbCircleLoader;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    private XMLTVPresenter xmlTvPresenter;

    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG =
            new DatabaseUpdatedStatusDBModel();



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_import_epg);
        ButterKnife.bind(this);
        changeStatusBarColor();
        context = this;
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        initialize();
    }

    private void initialize() {
        if (context != null) {
            loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            int epgCounts = liveStreamDBHandler.getEPGCount();
            databaseUpdatedStatusDBModelEPG = liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
            String status = "";
            if (databaseUpdatedStatusDBModelEPG != null) {
                status = databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
                addDatabaseStatusOnSetup(status);
            }
            if (epgCounts == 0) {
                xmlTvPresenter = new XMLTVPresenter(ImportEPGActivity.this, context);
                liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                        AppConst.DB_EPG_ID,
                        AppConst.DB_UPDATED_STATUS_PROCESSING);
                xmlTvPresenter.epgXMLTV(username, password);
            } else {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }



    private void addDatabaseStatusOnSetup(String status) {
        String currentDate = "";
        currentDate = currentDateValue();
        if(status == null || status.equals("")){
            if (currentDate != null) {
                addDBStatus(liveStreamDBHandler, currentDate);
            } else {
                Utils.showToast(context, "Invalid current date");
            }
        }
//        if (liveStreamDBHandler != null) {
//            count = liveStreamDBHandler.getDBStatusCount();
//            if (count != -1 && count == 0) {
//
//            }
//        }
    }

    private String currentDateValue() {
        Date currentTime = Calendar.getInstance().getTime();
        String dateValue = currentTime.toString();
        String currentDate = Utils.parseDateToddMMyyyy(dateValue);
//        String currentDate = "28/11/2017";
        return currentDate;
    }

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler, String currentDate) {
//        String currentDate = "24/11/2017";
        DatabaseUpdatedStatusDBModel updatedStatusDBModel = new DatabaseUpdatedStatusDBModel();

        updatedStatusDBModel.setDbUpadatedStatusState("");
        updatedStatusDBModel.setDbLastUpdatedDate(currentDate);
        updatedStatusDBModel.setDbCategory(AppConst.DB_EPG);
        updatedStatusDBModel.setDbCategoryID(AppConst.DB_EPG_ID);
        liveStreamDBHandler.addDBUpdatedStatus(updatedStatusDBModel);
    }



    private void changeStatusBarColor() {
        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }


    @Override
    public void epgXMLTV(final XMLTVCallback xmltvCallback) {
        if (xmltvCallback != null && context != null && xmltvCallback.programmePojos != null) {
//            if (ivGearLoader != null) {
//                ivGearLoader.setVisibility(View.GONE);
//            }
//            if (rlImportLayout != null) {
//                rlImportLayout.setVisibility(View.VISIBLE);
//            }
//            if (rlSkip != null) {
//                rlSkip.setVisibility(View.VISIBLE);
//            }



            // Execute the task
//            AsyncTask task = new NewAsyncTask().execute();


            class NewAsyncTask extends AsyncTask<String, Integer, Boolean> {



                private volatile boolean running = true;


                Context mcontext = null;
                final int ITERATIONS = xmltvCallback.programmePojos.size();

                NewAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {

                    publishProgress(0);
//                    for (int i = 0; i < xmltvCallback.programmePojos.size(); i++) {
//                        String epgStart = xmltvCallback.programmePojos.get(i).getStart();
//                        String epgStop = xmltvCallback.programmePojos.get(i).getStop();
//                        String epgDesc = xmltvCallback.programmePojos.get(i).getDesc();
//                        String epgChannel = xmltvCallback.programmePojos.get(i).getChannel();
//                        String epgTitle = xmltvCallback.programmePojos.get(i).getTitle();
//                        publishProgress(i + 1);
//                        if (liveStreamDBHandler != null) {
//                            liveStreamDBHandler.addEPG(epgStart, epgStop, epgDesc, epgChannel, epgTitle);
//                        }
//                    }
                    if (liveStreamDBHandler != null) {
                        liveStreamDBHandler.addEPG(xmltvCallback.programmePojos);
                    }
                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null) {

//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
                    //float percentage = ((progressCount / iterations) * 100);
                    //((ImportEPGActivity) context).progressBar.setProgress((int) percentage);
                    //((ImportEPGActivity) context).tvCountings.setText(progress[0] + "/" + ITERATIONS);
                    //((ImportEPGActivity) context).tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
                    int totalEPGFound = xmltvCallback.programmePojos.size();
                    loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                    String skipButton = loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF_IPTV, "");
                    Utils.showToast(context, getResources().getString(R.string.epg_imported) +" ("+ totalEPGFound+")");
                    if (liveStreamDBHandler != null) {
                        liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                                AppConst.DB_EPG_ID, AppConst.DB_UPDATED_STATUS_FINISH);
                    }
                    if (!skipButton.equals("pressed")) {
                        if (context != null) {
                            Intent tvGuideIntent = new Intent(context, LiveStreamEpgAcitivity.class);
                            startActivity(tvGuideIntent);
                            finish();
                        }
                    }
                }

                @Override
                protected void onCancelled() {
                    running = false;
                }
            }


            if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
                ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                Utils.showToast(context, getResources().getString(R.string.epg_imported));

            } else {
                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
                ourAsyncTask.execute();
//                Utils.showToast(context, getResources().getString(R.string.epg_imported));


            }

        } else {
            if (context != null) {
                if (liveStreamDBHandler != null) {
                    liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                            AppConst.DB_EPG_ID, AppConst.DB_UPDATED_STATUS_FINISH);
                }
                Intent tvGuideIntent = new Intent(context, LiveStreamEpgAcitivity.class);
                startActivity(tvGuideIntent);
                finish();
            }
        }

    }

    @Override
    public void epgXMLTVUpdateFailed(String failedUpdate) {
        if (failedUpdate.equals(AppConst.DB_UPDATED_STATUS_FAILED) &&
                liveStreamDBHandler != null) {
            liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                    AppConst.DB_EPG_ID,
                    AppConst.DB_UPDATED_STATUS_FAILED);
        }
    }

    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {
        Utils.showToast(context, AppConst.NETWORK_ERROR_OCCURED);
    }

    @OnClick(R.id.bt_skip)
    public void onViewClicked() {
        if(context!=null) {
            loginPrefsEditor = loginPreferencesAfterLogin.edit();
            if (loginPrefsEditor != null) {
                loginPrefsEditor.putString(AppConst.SKIP_BUTTON_PREF_IPTV, "pressed");
                loginPrefsEditor.commit();
            }

            Intent dashboardIntent = new Intent(context, DashboardActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        startActivity(dashboardIntent);
        finish();
    }
}
