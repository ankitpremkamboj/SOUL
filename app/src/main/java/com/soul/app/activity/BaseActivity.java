package com.soul.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.soul.app.application.ApplicationController;
import com.soul.app.customui.MaterialProgressDialog;
import com.soul.app.interfaces.BaseListener;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.Apis;
import com.soul.app.utils.Lg;
import com.soul.app.utils.Utility;
import com.soul.app.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ashishkumar on 16/6/16.
 * Base Activity :- Common class for other classes
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseListener {

    private static Context mContext;
    protected Snackbar mSnackBar;
    protected Apis mApis;
    protected BaseActivity mThis;
    private ProgressBar nonBlockingProgressBar;
    private MaterialProgressDialog blockingProgressDialog;
    private NetworkChangeReceiver mNetworkReceiver = new NetworkChangeReceiver();
    private Toolbar toolbar;

    public static Context getContext() {
        return mContext;
    }

    //
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //BaseGpsActivity.getGoogleClient().disconnect();
    }
/*on Create method */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setLayout());
        mApis = new ApiClient().getApis();
        mThis = this;
        setContext(mThis);
        initUi();

        blockingProgressDialog = Utility.getProgressDialogInstance(this);
        nonBlockingProgressBar = Utility.getProgressBarInstance(this, R.id.circular_progress_bar);

        initToolbar();
    }

    public Apis getApis() {
        return mApis;
    }

    protected void initToolbar() {
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null)
                setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
        if (mSnackBar != null)
            mSnackBar.dismiss();
        ApplicationController.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApplicationController.activityResumed();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void showProgressDialog(boolean isShow) {
        try {
            if (blockingProgressDialog != null) {
                if (isShow)
                    blockingProgressDialog.show();
                else
                    blockingProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgressBar(boolean isShow) {
        try {
            if (nonBlockingProgressBar != null) {
                if (isShow)
                    nonBlockingProgressBar.setVisibility(View.VISIBLE);
                else
                    nonBlockingProgressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onArrowPress() {
        Utility.hideKeyboard(this);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDone() {
        Utility.hideKeyboard(this);
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Utility.getNetworkState(context)) {
                ApplicationController.getApplicationInstance().setIsNetworkConnected(true);
                Lg.i("Network Receiver", "connected");
            } else {
                ApplicationController.getApplicationInstance().setIsNetworkConnected(false);
                Lg.i("Network Receiver", "disconnected");
            }
        }
    }


}
