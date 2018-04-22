package com.soul.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.OutLookActivity;
import com.soul.app.adapter.HomeSlidingImageAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.CirclePageIndicator;
import com.soul.app.gcm.GCMUtils;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.req.LoginReq;
import com.soul.app.models.res.FriendsListIdResp;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.LoginRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.PhotosEntityResp;
import com.soul.app.models.res.SocialMediaInfoBean;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.ApiConstants;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Constants;
import com.soul.app.utils.DialogUtils;
import com.soul.app.utils.Lg;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.mrengineer13.snackbar.SnackBar;
import com.glidepool.Util;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
*  Application Home Screen
* */

public class SwipeViewHomeActivity extends com.soul.app.activity.BaseGpsActivity {
    private static final Integer[] IMAGES = {R.drawable.tutorial_1, R.drawable.tutorial_2, R.drawable.tutorial_3, R.drawable.tutorial_4};
    private static final Integer[] PAGE_TITLE = {R.string.first_find_the_best, R.string.second_swipe_picture_left_to_right, R.string.third_let_us_notify, R.string.four_get_to_know};
    // private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    // ImageView fbLogin, infoIcon;
    String device_token;
    Double lat = 0.0;
    Double lng = 0.0;
    //  GPSTracker gpsTracker;
    // private TextView privacyPolicy2, termAndUse4;
    private String TAG = "FACEBOOK LOG:";
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<Integer> PageTitleArray = new ArrayList<Integer>();
    private CallbackManager mFbcallbackManager;
    private String albumId;
    private SocialMediaInfoBean socialMediaInfoBean;
    private ArrayList<SocialMediaInfoBean.AlbumsEntity.DataEntity> mListOfAlbums = new ArrayList<>();
    private List<String> READ_PERMISSION = Arrays.asList("email", "public_profile", "user_photos", "user_likes", "user_friends", "user_birthday", "user_work_history", "user_education_history", "user_location", "user_about_me", "user_hometown"); //"manage_friendlists",
    private ArrayList<String> mListOfPhotos = new ArrayList<String>();
    private FriendsListIdResp friendsEntity;
    private String profilePic;

    private LinearLayout fb_login_ly;
    private TextView fb_login_tv, terms_of_services_tv, privacy_policy_tv;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_login;
    }

    @Override
    public void initUi() {

        // gpsTracker = new GPSTracker(SwipeViewHomeActivity.this);

        for (int i = 0; i < IMAGES.length; i++) {
            ImagesArray.add(IMAGES[i]);
            PageTitleArray.add(PAGE_TITLE[i]);
        }
        //   GCMUtils.getRegId(this);

        // mPager = (ViewPager) findViewById(R.id.viewpager);
        //mPager.setAdapter(new HomeSlidingImageAdapter(this, ImagesArray, PageTitleArray));
        // CirclePageIndicator indicator = (CirclePageIndicator)        findViewById(R.id.indicator);
        //indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
//        indicator.setRadius(5 * density);
        // Pager listener over indicator
       /* indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });*/

        // device_token = Utility.getStringSharedPreference(SwipeViewHomeActivity.this, AppConstant.PREF_DEVICE_TOKEN);

        /*facebook login*/
        //  fbLogin = (ImageView) findViewById(R.id.fb_login_iv);
        fb_login_tv = (TextView) findViewById(R.id.fb_login_tv);
        fb_login_tv.setTextColor(Color.WHITE);
        privacy_policy_tv = (TextView) findViewById(R.id.privacy_policy_tv);
        privacy_policy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SwipeViewHomeActivity.this, ApiConstants.URL_PRIVACY, 3);

            }
        });
        terms_of_services_tv = (TextView) findViewById(R.id.terms_of_services_tv);
        terms_of_services_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SwipeViewHomeActivity.this, ApiConstants.URL_TERMS, 3);
            }
        });

        fb_login_ly = (LinearLayout) findViewById(R.id.fb_login_ly);
        fb_login_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {

                    if (Utility.isGpsOn(SwipeViewHomeActivity.this)) {
                        if (mLocation == null) {

                            new SnackBar.Builder(SwipeViewHomeActivity.this)
                                    .withMessage(getResources().getString(R.string.waiting_for_location)).show();
                            requestLocationUpdate(1000 * 10 * 6 * 1);

                        } else {
                            lat = mLocation.getLatitude();
                            lng = mLocation.getLongitude();
                            loginWithFacebook();
                        }
                    } else {
                        new SnackBar.Builder(SwipeViewHomeActivity.this)
                                .withMessage(getResources().getString(R.string.turn_on_location)).show();
                        Utility.showSettingsAlert(SwipeViewHomeActivity.this);
                    }

                } else {
                    //DialogUtils.showToast(SwipeViewHomeActivity.this, getResources().getString(R.string.err_network));
                    new SnackBar.Builder(SwipeViewHomeActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });
/*
        infoIcon = (ImageView) findViewById(R.id.info_icon_iv);
        infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SwipeViewHomeActivity.this, ApiConstants.URL_CONTACT, 1);
            }
        });
        privacyPolicy2 = (TextView) findViewById(R.id.privacy_policy_2_tv);
        privacyPolicy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SwipeViewHomeActivity.this, ApiConstants.URL_PRIVACY, 2);
            }
        });
        termAndUse4 = (TextView) findViewById(R.id.term_and_use_4_tv);
        termAndUse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SwipeViewHomeActivity.this, ApiConstants.URL_TERMS, 3);
            }
        });*/

    }


    @Override
    public String getName() {
        return SwipeViewHomeActivity.class.getName();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFbcallbackManager != null)
            mFbcallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook() {

        mFbcallbackManager = CallbackManager.Factory.create();
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(mFbcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, loginResult.toString());
                getFbDetails(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG, exception.toString());
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null)
            loginManager.logInWithReadPermissions(this, READ_PERMISSION);
        else
            getFbDetails(accessToken);
    }


    private void getFbDetails(AccessToken accessToken) {


        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.e(TAG, object.toString());

                            socialMediaInfoBean = Utility.fromJson(object.toString(), SocialMediaInfoBean.class);
                            profilePic = "https://graph.facebook.com/v2.4/" + socialMediaInfoBean.getId() + "/picture?type" + "=large";
                            getProfilePicturesUrl();
                            List albumList = socialMediaInfoBean.getAlbums().getData();
                            PrefUtils.setSharedPreferencesList(SwipeViewHomeActivity.this, PrefUtils.PREF_USER_ALBUM, albumList);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,birthday,friends,email,hometown,education,first_name,last_name,location,relationship_status,work,about,posts.limit(3){id,picture},photos.limit(6){id,picture},picture,albums,likes.limit(100)");
        // parameters.putString("fields", "id,birthday,name,gender,link,friends,email,picture,first_name,last_name,photos.limit(6){id,picture},albums,likes.limit(100)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void getProfilePicturesUrl() {

        if (socialMediaInfoBean != null) {
            if (socialMediaInfoBean.getAlbums() != null) {
                mListOfAlbums.addAll(socialMediaInfoBean.getAlbums().getData());
                for (int i = 0; i < mListOfAlbums.size(); i++) {
                    if (mListOfAlbums.get(i).getName().equals("Profile Pictures")) {
                        albumId = mListOfAlbums.get(i).getId();
                    }
                }
            }

            String str = "https://graph.facebook.com/v2.4/" + socialMediaInfoBean.getId() + "/picture?type" + "=large";
            PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC, str);
            PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC1, str);


            Bundle paramtersphoto = new Bundle();
            paramtersphoto.putString("fields", "images.limit(5)");
            GraphRequest picGr = new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/v2.4/" + albumId + "/photos",
                    paramtersphoto,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                Log.e("responseProfilePictures", response.toString());
                                PhotosEntityResp photosEntity = Utility.fromJson(response.getJSONObject().toString(), PhotosEntityResp.class);
                                getData(photosEntity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
            );
            picGr.setParameters(paramtersphoto);
            picGr.executeAsync();
        }


        GraphRequest grq = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        Log.e("responseFriends", response.toString());
                        friendsEntity = Utility.fromJson(response.getJSONObject().toString(), FriendsListIdResp.class);
                        if (socialMediaInfoBean != null) {
                            try {
                                if (!TextUtils.isEmpty(profilePic)) {
                                    callSignUpApi(socialMediaInfoBean, profilePic);
                                } else {
                                    DialogUtils.showOkDialog(SwipeViewHomeActivity.this, "Information", getResources().getString(R.string.err_blank_profile_pic));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");
//        parameters.putString("edges", "members");
//
//        // parameters.putString("fields", "id,birthday,name,gender,link,friends,email,picture,first_name,last_name,photos.limit(6){id,picture},albums,likes.limit(100)");
        grq.setParameters(parameters);
        grq.executeAsync();

//
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/me/taggable_friends",
//                null,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//                        FriendsListIdResp friendsEntity = Utility.fromJson(response.getJSONObject().toString(), FriendsListIdResp.class);
//                        Log.e("responseFriends", response.toString());
//            /* handle the result */
//
//                    }
//                }
//        ).executeAsync();


    }


    private void getData(PhotosEntityResp photosEntity) {

        if (photosEntity != null && photosEntity.getData() != null) {
            for (int i = 0; i < photosEntity.getData().size(); i++) {
                String sourceUrl = photosEntity.getData().get(i).getImages().get(0).getSource();
                mListOfPhotos.add(sourceUrl);

                Log.e("sourceUrl", sourceUrl);
                if (i > 3)
                    break;

            }

            if (mListOfPhotos != null) {
                if (mListOfPhotos.size() > 0) {
                    PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC1, profilePic);
                    PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC2, mListOfPhotos.get(1));
                    PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC3, mListOfPhotos.get(2));
                    PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC4, mListOfPhotos.get(3));
                    PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_PIC5, mListOfPhotos.get(4));

                }
            }
        }
    }

    public LoginReq getLoginReq(SocialMediaInfoBean sb, String profilePic) throws Exception {
        LoginReq req = new LoginReq();
        String location = "";
        //  gpsTracker = new GPSTracker(SwipeViewHomeActivity.this);

        //if (gpsTracker.canGetLocation()) {
//        lat = ((ApplicationController) getApplication()).getMyLocation().getLatitude();
//        lng = ((ApplicationController) getApplication()).getMyLocation().getLongitude();
        //}

        //  String deviceToken = "";
        String email = "";
        String work = "";
        String homeTown = "";
        String education = "";
        int age = 25;
        ArrayList<LoginReq.FacebookFriendEntity> fbFrndList;
        // assing value coming from facebook

        Calendar calendar = Calendar.getInstance();
        if (sb.getEmail() != null)
            email = sb.getEmail();
        if (sb.getBirthday() != null) {
            Date birthday = new Date(sb.getBirthday());
            int year = calendar.get(Calendar.YEAR);
            age = year - (birthday.getYear() + 1900);
        }
        try {
            PrefUtils.setSharedPrefStringData(this, PrefUtils.PREF_LOCATION, sb.getHometown().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  location = PrefUtils.getSharedPrefString(this, PrefUtils.PREF_LOCATION);

        try {
            if (!TextUtils.isEmpty(sb.getWork().get(0).getLocation().getName())) {
                location = sb.getWork().get(0).getLocation().getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!TextUtils.isEmpty(sb.getWork().get(0).getPosition().getName())) {
                work = sb.getWork().get(0).getPosition().getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int size = sb.getEducation().size();
            if (!TextUtils.isEmpty(sb.getEducation().get(size - 1).getDegree().getName())) {
                education = sb.getEducation().get(size - 1).getDegree().getName();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        device_token = Utility.getStringSharedPreference(SwipeViewHomeActivity.this, AppConstant.PREF_DEVICE_TOKEN);
        req.setDevice_token(device_token);
        req.setLatitude(lat + "");
        req.setLocation(location);
        req.setLongitude(lng + "");
        req.setHometown(location);
        req.setWork(work);
        req.setEducation(education);


        if (friendsEntity != null) {
            fbFrndList = new ArrayList<LoginReq.FacebookFriendEntity>();
            for (int i = 0; i < friendsEntity.getData().size(); i++) {
                LoginReq.FacebookFriendEntity fbFrndEntity = new LoginReq.FacebookFriendEntity();
                fbFrndEntity.setId(friendsEntity.getData().get(i).getId());
                fbFrndEntity.setName(friendsEntity.getData().get(i).getName());
                fbFrndList.add(fbFrndEntity);
            }
            if (friendsEntity.getData().size() == 0) {
                LoginReq.FacebookFriendEntity fbFrndEntity = new LoginReq.FacebookFriendEntity();
                fbFrndEntity.setId("");
                fbFrndEntity.setName("");
                fbFrndList.add(fbFrndEntity);
            }
            req.setFacebook_friend(fbFrndList);
        }

        req.setEmail(email);
        req.setAge(age + "");
        req.setFacebook_id(sb.getId());
        req.setGender(sb.getGender());
        req.setProfile_pic(profilePic);

        req.setUser_name(sb.getFirst_name());
        return req;
    }

    private void callSignUpApi(SocialMediaInfoBean sb, String profilePic) throws Exception {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            Call<ObjResp<LoginRes>> call = mApis.signUp(getLoginReq(sb, profilePic));
            call.enqueue(new Callback<ObjResp<LoginRes>>() {
                @Override
                public void onResponse(Call<ObjResp<LoginRes>> call, Response<ObjResp<LoginRes>> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        Log.i("response", response.toString());
                        LoginRes loginRes = response.body().getData();
                        PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.USER_ID, loginRes.getUser_id());
                        PrefUtils.setSharedPrefBooleanData(SwipeViewHomeActivity.this, AppConstant.IS_LOGGED_IN, true);
                        PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.PREF_STATUS, loginRes.getStatus());
                        PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.PREF_DENOMINATION, loginRes.getDenomination());

                        PrefUtils.setSharedPrefStringData(SwipeViewHomeActivity.this, PrefUtils.PREF_CATSELECTED, loginRes.getIsCatSelected());

                        /*if (!response.body().getData().getIsCatSelected().equals("1")) {
                            Intent intentSeeYourSelf = new Intent(SwipeViewHomeActivity.this, OutLookActivity.class);
                            startActivity(intentSeeYourSelf);
                        } else {
                            Intent intentSeeYourSelf = new Intent(SwipeViewHomeActivity.this, HomeFindingPeopleActivity.class);
                            startActivity(intentSeeYourSelf);
                        }*/
                        Intent intentSeeYourSelf = new Intent(SwipeViewHomeActivity.this, SwipingActivity.class);

                        startActivity(intentSeeYourSelf);
                        updateLocation();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ObjResp<LoginRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        }
    }


    private void updateLocation() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            try {
                GeneralReq generalReq = new GeneralReq();
                String lat = mLocation.getLatitude() + "";
                String lng = mLocation.getLongitude() + "";
                generalReq.setLatitude(lat);
                generalReq.setLongitude(lng);
                String add = Utility.getAddressFromLatLong(SwipeViewHomeActivity.this,
                        mLocation.getLatitude(),
                        mLocation.getLongitude(), 0).get(0);
                generalReq.setAddress(add);

                generalReq.setUser_id(PrefUtils.getSharedPrefString(this, PrefUtils.USER_ID));
                generalReq.setLast_seen(System.currentTimeMillis() + "");


                PrefUtils.getSharedPrefString(getApplicationContext(), PrefUtils.USER_ID);
                Call<ObjResp> call = new ApiClient().getApis().updateLoc(generalReq);
//            showProgressDialog(true);
                call.enqueue(new Callback<ObjResp>() {

                    @Override
                    public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                        if (response.isSuccessful()) {
                            Lg.e("location", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjResp> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        GCMUtils.getRegId(this);
    }

    @Override
    public void mapReady(GoogleMap googleMap) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // mLocation = location;

    }


}
