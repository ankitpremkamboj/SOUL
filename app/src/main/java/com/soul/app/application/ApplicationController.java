package com.soul.app.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.soul.app.R;
import com.soul.app.activity.BaseActivity;
import com.soul.app.constants.AppConstant;
import com.soul.app.utils.Utility;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ashishkumar on 17/5/16.
 */
public class ApplicationController extends Application implements Application.ActivityLifecycleCallbacks {


    private static ApplicationController mApplicationInstance;
    private static boolean activityVisible;
    private BaseActivity activity;
    private boolean mIsNetworkConnected;

    public static ApplicationController getApplicationInstance() {
        if (mApplicationInstance == null)
            mApplicationInstance = new ApplicationController();
        return mApplicationInstance;
    }

    ///////////////
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    /**
     * Method to tell the state of internet connectivity
     *
     * @return State of internet
     */
    public boolean isNetworkConnected() {
        return mIsNetworkConnected;
    }

    public void setIsNetworkConnected(boolean mIsNetworkConnected) {
        this.mIsNetworkConnected = mIsNetworkConnected;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //set log enabled
        FlurryAgent.setLogEnabled(true);
        //set log events
        FlurryAgent.setLogEvents(true);
        // initialize Flurry
        FlurryAgent.init(this, AppConstant.FLURRY_API_KEY);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirLTStd-Roman.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        mIsNetworkConnected = Utility.getNetworkState(this);
        registerActivityLifecycleCallbacks(this);
        mApplicationInstance = this;


    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof BaseActivity)
            this.activity = (BaseActivity) activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
