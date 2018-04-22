package com.soul.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.R;
import com.soul.app.activity.HomeFindingPeopleActivity;
import com.soul.app.activity.SwipeViewHomeActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.gcm.GCMUtils;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.Apis;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.flurry.android.FlurryAgent;
import com.soul.app.utils.Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashishkumar on 16/6/16.
 * Display animation at splash screen
 */
public class SplashActivity extends Activity {
    public Handler mHandler;
    Intent intent = null;
    private int SPLASH_TIME_OUT = 2000;
    private Apis mApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.setStatusBarGradiant(this);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mApis = new ApiClient().getApis();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                performNavigation();
            }
        }, SPLASH_TIME_OUT);

        GCMUtils.getRegId(this);

    }

    public void performNavigation() {
        boolean isLoggedIn = PrefUtils.getSharedPrefBoolean(this, AppConstant.IS_LOGGED_IN);

        if (!isLoggedIn) {
            intent = new Intent(SplashActivity.this, SwipeViewHomeActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, SwipingActivity.class);

        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

}
