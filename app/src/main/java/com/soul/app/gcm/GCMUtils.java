package com.soul.app.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.soul.app.R;
import com.soul.app.constants.AppConstant;
import com.soul.app.utils.Utility;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class GCMUtils {
    private static final String TAG = GCMUtils.class.getSimpleName();
    private static GoogleCloudMessaging gcm;
    private static String regId = "";

    public static void getRegId(Context activity) {

        try {

            if (checkPlayServices(activity)) {
                gcm = GoogleCloudMessaging.getInstance(activity);
                regId = getRegistrationId(activity);
                Log.e("Registration Id", regId);
                if (regId.equals("")) {
                    registerInBackground(activity);
                    Log.e("Registration Id", regId);
                } else {
                    Utility.putStringValueInSharedPreference(activity, AppConstant.PREF_DEVICE_TOKEN, regId);
                }
            } else {
                Log.i(TAG, "No valid Google Play Services APK found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void registerInBackground(final Context activity) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activity);
                    }

                    while (regId.equals("")) {
                        regId = gcm.register(activity.getResources().getString(R.string.project_id));
                    }

                    msg = "Device registered, registration ID=" + regId;

                    Log.e("msg", "is:" + msg);
                    // You should send the registration ID to your server over
                    // HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the
                    // device will send
                    // upstream messages to a server that echo back the message
                    // using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(activity, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            private void sendRegistrationIdToBackend() {
                // identify
            }

            /**
             * Stores the registration ID and the app versionCode in the
             * application's {@code SharedPreferences}.
             *
             * @param context
             *            application's context.
             * @param regId
             *            registration ID
             */
            private void storeRegistrationId(Context context, String regId) {
                int appVersion = getAppVersion(context);
                Log.i(TAG, "Saving regId on app version " + appVersion);
                Utility.putStringValueInSharedPreference(context, AppConstant.PREF_DEVICE_TOKEN, regId);
                Utility.putStringValueInSharedPreference(context, AppConstant.PREFS_PROPERTY_APP_VERSION, appVersion + "");
            }

            /**
             * @return Application's version code from the
             *         {@code PackageManager}.
             */
            private int getAppVersion(Context context) {
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    return packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    // should never happenNotificationFragmentActivity
                    throw new RuntimeException("Could not get package name: " + e);
                }
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!((Activity) activity).isTaskRoot()) {
                    //finish();
                    return;
                } else {
                    Utility.putStringValueInSharedPreference(activity, AppConstant.PREF_DEVICE_TOKEN, regId);

                }

                if (!regId.isEmpty())
                    Utility.putStringValueInSharedPreference(activity, AppConstant.PREF_DEVICE_TOKEN, regId);
            }

        }.execute(null, null, null);

    }

    public static String getRegistrationId(Context activity) {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String registrationId = prefs.getString(AppConstant.PREF_DEVICE_TOKEN, "");
        if (registrationId.length() == 0) {
            registerInBackground(activity);
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the
        // reconditiongistration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        /*int registeredVersion = Integer.parseInt(prefs.getString(AppConstants.PREFS_PROPERTY_APP_VERSION, Integer.MIN_VALUE + ""));
        int currentVersion = getAppVersion(activity);
		if (registeredVersion != currentVersion) {
		    Log.i(TAG, "App version changed.");
		    return "";
		}*/
        return registrationId;

    }

    private static int getAppVersion(Context activity) {

        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happenNotificationFragmentActivity
            throw new RuntimeException("Could not get package name: " + e);
        }

    }

    public static boolean checkPlayServices(Context activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) activity,
                        AppConstant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                //finish();
            }
            return false;
        }
        return true;
    }
}
