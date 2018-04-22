package com.soul.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.soul.app.constants.AppConstant;
import com.soul.app.models.res.SocialMediaInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhanu on 8/5/15.
 */
public class PrefUtils {

    public static final String SHARED_PREF_NAME = "salamSwipe";
    public static final String IS_LOGGEDIN = "is_loggedin";
    public static final String JOB_STATUS = "job_status";
    public static final String USER_ID = "userId";
    public static final String OTHER_ID = "otherId";
    public static final String USER_PIC = "userImage";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String REMEMBER_ME = "remember";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USERMODE = "UserMode";
    public static final String IS_ACTIVE = "isActive";
    public static final String IS_INSURANCE_PLACED = "insurance";
    public static final String IS_TRACKED = "track_order";
    public static final String IS_LOGIN_CHECKED = "loginchecked";
    public static final String FB_TOKEN = "fbtoken";
    public static final String FB_SESSION_EXPIRE = "fbsessionexpire";
    public static final String PROFILE_PIC_LIST = "profilePicList";
    public static final String PREF_INTEREST = "prefInterest";
    public static final String PREF_PROFILE_PIC = "fbprofilepic";
    public static final String PREF_LOCATION = "updatedLoc";
    public static final String PREF_STATUS = "status";
    public static final String PREF_DENOMINATION = "denomination";
    public static final String PREF_CATSELECTED = "catselected";
    public static final String PREF_USER_ALBUM = "user album";
    public static final String PREF_FB_IMAGE = "userFbImage";


    public static final String USER_PIC1 = "userImage1";
    public static final String USER_PIC2 = "userImage2";
    public static final String USER_PIC3 = "userImage3";
    public static final String USER_PIC4 = "userImage4";
    public static final String USER_PIC5 = "userImage5";
    public static final String PREF_FB_SOURCE_IMAGE = "fbsourceimage";
    public static final String PREF_FBURL_IMAGE = "fburl";

    private static List<String> mValueList = new ArrayList<String>();


    public static void loginUser(Context context, String userId, int mode, String isActive) {
        setSharedPrefBooleanData(context, PrefUtils.IS_LOGGEDIN, true);
        setSharedPrefStringData(context, PrefUtils.USER_ID, userId);
        setSharedPrefIntData(context, PrefUtils.USERMODE, mode);
        setSharedPrefStringData(context, PrefUtils.IS_ACTIVE, isActive);

    }

//    public static void makeUserActive(Context context,String isActive) {
//        try {
//            setSharedPrefStringData(context, PrefUtils.IS_ACTIVE, isActive);
//
//            User user1 = DbHelper.getInstance(context).getModel(User.class, WebConstants.USER_ID, PrefUtils.getSharedPrefString(context, PrefUtils.USER_ID));
//            user1.setIsActive(isActive);
//            DbHelper.getInstance(context).saveModel(user1);
//        }
//        catch (Exception e)
//        {
//            DialogUtils.showToast(context,"App Failed Error");
//        }
//    }

    public static void logoutUser(Context context) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putBoolean(AppConstant.IS_LOGGED_IN, false);
        appInstallInfoEditor.putString(USER_PIC1, "");
        appInstallInfoEditor.putString(USER_PIC2, "");
        appInstallInfoEditor.putString(USER_PIC3, "");
        appInstallInfoEditor.putString(USER_PIC4, "");
        appInstallInfoEditor.putString(USER_PIC5, "");
        appInstallInfoEditor.putBoolean(PREF_PROFILE_PIC, false);
        appInstallInfoEditor.putString(PREF_LOCATION, "");
        appInstallInfoEditor.putString(PREF_DENOMINATION, "");
        appInstallInfoEditor.putString(PREF_STATUS, "");
        appInstallInfoEditor.putString(PREF_USER_ALBUM, "");
        appInstallInfoEditor.putString(PREF_FB_IMAGE, "");
        appInstallInfoEditor.putString(PrefUtils.USER_ID, "");
        appInstallInfoEditor.putString(PrefUtils.PREF_CATSELECTED, "");
        //appInstallInfoEditor.clear();
        appInstallInfoEditor.commit();
    }


    public static void setSharedPrefStringData(Context context, String key, String value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putString(key, value);
        appInstallInfoEditor.commit();
    }


    public static ArrayList getSharedPrefListData(Context context, String key) {
        SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        String localKey;
        ArrayList<String> list = new ArrayList<String>();
        if (mValueList != null)
            for (int i = 0; i < mValueList.size(); i++) {
                localKey = key + (i + 1);
                list.add(userAcountPreference.getString(localKey, ""));
            }
        return list;

    }


    public static boolean getSharedPrefBoolean(Context context, String key) {
        SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        return userAcountPreference.getBoolean(key, false);
    }


    public static String getSharedPrefString(Context context, String key) {
        SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return userAcountPreference.getString(key, "");
    }


    public static float getSharedPrefFloat(Context context, String key) {
        SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        return userAcountPreference.getFloat(key, 0);
    }


    public static int getSharedPrefInt(Context context, String key) {
        SharedPreferences userAcountPreference = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        return userAcountPreference.getInt(key, 0);
    }

    public static void setSharedPrefBooleanData(Context context, String key, boolean value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putBoolean(key, value);
        appInstallInfoEditor.commit();
    }

    public static void setSharedPrefFloatData(Context context, String key, float value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putFloat(key, value);
        appInstallInfoEditor.commit();
    }

    public static void setSharedPrefIntData(Context context, String key, int value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putInt(key, value);
        appInstallInfoEditor.commit();
    }

    public static void setSharedPreferencesList(Context context, String key, List<SocialMediaInfoBean.AlbumsEntity.DataEntity> list) {
        SharedPreferences mPrefs = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static List getSharedPreferencesLogList(Context context, String key) {
        List<SocialMediaInfoBean.AlbumsEntity.DataEntity> list = new ArrayList<SocialMediaInfoBean.AlbumsEntity.DataEntity>();
        SharedPreferences mPrefs = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        if (json.isEmpty()) {
            list = new ArrayList();
        } else {
            Type type = new TypeToken<List<SocialMediaInfoBean.AlbumsEntity.DataEntity>>() {
            }.getType();
            list = gson.fromJson(json, type);
        }
        return list;
    }


}
