package com.soul.app.utils;

import android.net.Uri;
import android.os.Environment;

/**
 * Created by techahead on 15/7/16.
 */
public class Constants {
    public static final String LIBERAL = "Liberal";
    public static final String MODERATE = "Moderate";
    public static final String CONSERVATIVES = "Conservative";
    public static final String HIDE_LIKE_AND_DISLIKE = "key_hide_and_dislike";
    public static final String CHAT_TYPE = "chat";
    public static final String NORMAL_TYPE = "normal_push_type";
    public static final int RESULT_OUTLOOK = 1;
    public static final int RESULT_SECT = 2;
    public static final int RESULT_INTEREST = 3;
    public static final int RESULT_ACADEMIC = 4;
    public static final int RESULT_HEIGHT = 5;


    public static final String EXTRA_OTHER_ID = "otherId";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_USER_PROFILE = "user_profile";
    public static final String EXTRA_SECT = "sect";
    public static final String EXTRA_OUTLOOK = "outlook";
    public static final String EXTRA_INTEREST = "interest";
    public static final String EXTRA_ACADEMIC = "academic";

    public static final String EXTRA_MIN_HEIGHT = "minHeight";
    public static final String EXTRA_MAX_HEIGHT = "maxHeight";


    public static final String EXTRA_INTEREST_LIST = "interest_list";
    public static final String EXTRA_USER_MATCH = "userMatch";
    public static final String EXTRA_PUSH_TYPE = "pushtype";
    public static final String EXTRA_FB_IMAGE = "fbImage";
    public static final int TAG_USERID = 1;
    public static final int TAG_USER_PIC = 2;
    public static final String EXTRA_USERID = "userid";
    public static final String CHAT_ACTIVITY_AVAILABLE = "inActive";
    public static final String EXTRA_OTHER_NAME = "otherName";
    public static final String EXTRA_OTHER_PIC = "otherPic";
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public final static int CAMERA_APP = 200;
    public static final String EXTRA_REPORT_REASON = "ReportReason";
    public static final String INAP_MSG = "Inapropriate Photos";
    public static final String OTHER = "Other";
    public static final String SPAM = "Spam";
    public static int LIKEDISLIKE = 555;
    public static Uri mCamRequestedUri;
    public static final int CHAT = 101;

    public static String fbCroppedImg = "CroppedImage";
    public static String Match = "Match";
    public static final String APPLICATION_AVAILABLE = "inActive";
}
