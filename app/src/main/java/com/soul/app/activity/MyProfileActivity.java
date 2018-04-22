package com.soul.app.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.SettingsActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.CircleImageViewForProfilePic;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.GetSettingRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.models.res.UserProfileRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends com.soul.app.activity.BaseActivity implements View.OnClickListener {

    public static String instagram;
    FrameLayout profileHeader;
    GetSettingRes getSettingRes;
    private ImageView homeIcon;
    private  ImageView chat_icon;
    private ImageView myProfilePic;
    private ImageView myProfileEditIcon;
  //  private ImageView settingsIcon;
    private UserProfileRes mUserProfile;
    private RelativeLayout inviteFriendsRl, feedBackRl;

    @Override
    public int setLayout() {
        /*change status bar Gradient color   */
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_my_profile;
    }

    @Override
    public void initUi() {

        profileHeader = (FrameLayout) findViewById(R.id.profile_header);
        profileHeader.setVisibility(View.VISIBLE);

        homeIcon = (ImageView) findViewById(R.id.ph_home_icon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chat_icon=(ImageView)findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MyProfileActivity.this, MatchesFoundActivity.class);
               // startActivity(intent);

                userMatchApi();
            }
        });

        myProfilePic = (ImageView) findViewById(R.id.my_profile_pic_ci);
        String imageFromPref = PrefUtils.getSharedPrefString(MyProfileActivity.this, PrefUtils.USER_PIC);
        try {
            // Picasso.with(this).load(imageFromPref).into(myProfilePic);
            Utility.glide(MyProfileActivity.this, myProfilePic, 0, imageFromPref);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myProfileEditIcon = (ImageView) findViewById(R.id.my_profile_edit_icon_iv);

       // settingsIcon = (ImageView) findViewById(R.id.ph_settings_icon);
       // settingsIcon.setOnClickListener(this);

        inviteFriendsRl = (RelativeLayout) findViewById(R.id.invite_friends_rl);
        inviteFriendsRl.setOnClickListener(this);
       /* inviteFriendsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Salaam Swipe is a wonderful application.");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "http://www.salaamswipe.com");
                startActivity(Intent.createChooser(sharingIntent, "Share link!"));
            }
        });*/

        feedBackRl = (RelativeLayout) findViewById(R.id.feedback_rl);
        feedBackRl.setOnClickListener(this);
       /* feedBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Utility.loadWebPage(MyProfileActivity.this, getString(R.string.urls_feedback), 4);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@salaamswipe.com"});
                intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                //i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    //  Toast.makeText(MyProfileActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    new SnackBar.Builder(MyProfileActivity.this)
                            .withMessage(getResources().getString(R.string.there_no_email_cleint)).show();
                }

            }
        });*/

        //call service


    }

    @Override
    protected void onResume() {
        super.onResume();
        profileApi();
    }


    public void userMatchApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MyProfileActivity.this, PrefUtils.USER_ID));
            Call<ListResp<UserMatchesRes>> call = mApis.getUserMatches(generalReq);
            //showProgressDialog(true);
            call.enqueue(new Callback<ListResp<UserMatchesRes>>() {


                @Override
                public void onResponse(Call<ListResp<UserMatchesRes>> call, Response<ListResp<UserMatchesRes>> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData().size() > 0) {
                                Intent intent = new Intent(MyProfileActivity.this, MatchesFoundActivity.class);
                                intent.putParcelableArrayListExtra(Constants.EXTRA_USER_MATCH, (ArrayList<? extends Parcelable>) response.body().getData());
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MyProfileActivity.this, NoMatchesActivity.class);
                                startActivity(intent);
                            }
                            //  showProgressDialog(false);
                          //  chatIcon.setEnabled(true);
                        }
                    } else {
                        // showProgressDialog(false);
                       // chatIcon.setEnabled(true);
                        new SnackBar.Builder(MyProfileActivity.this)
                                .withMessage(getResources().getString(R.string.please_try_again)).show();
                    }
                }

                @Override
                public void onFailure(Call<ListResp<UserMatchesRes>> call, Throwable t) {
                    //  showProgressDialog(false);
                   // chatIcon.setEnabled(true);
                }
            });
        } else {
            new SnackBar.Builder(MyProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    public String getName() {
        return MyProfileActivity.class.getName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ph_home_icon:
                finish();
                break;
            case R.id.my_profile_pic_ci:
                Intent intentMyFullProfile = new Intent(MyProfileActivity.this, MyFullProfileActivity.class);
                intentMyFullProfile.putExtra(Constants.EXTRA_USER_PROFILE, mUserProfile);
                startActivity(intentMyFullProfile);
                break;
            case R.id.feedback_rl:
                Intent intentEditProfile = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                intentEditProfile.putExtra(Constants.EXTRA_USER_PROFILE, mUserProfile);
                startActivity(intentEditProfile);
                break;
            case R.id.invite_friends_rl:
               Intent intentSettings = new Intent(MyProfileActivity.this, SettingsActivity.class);
               intentSettings.putExtra(AppConstant.SETTINGS_STATUS, "0");
               startActivity(intentSettings);
                //getSettingApi();
                break;
        }
    }

    private void profileApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MyProfileActivity.this, PrefUtils.USER_ID));
            Call<ObjResp<UserProfileRes>> call = mApis.userProfile(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<UserProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<UserProfileRes>> call, Response<ObjResp<UserProfileRes>> response) {
                    if (response.isSuccessful()) {
                        showProgressDialog(false);
                        if (response.body() != null) {

                            mUserProfile = response.body().getData();
                            setUi(response.body().getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ObjResp<UserProfileRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(MyProfileActivity.this)
                    .withMessage(getString(R.string.err_network)).show();
        }
    }

    private void setUi(UserProfileRes userProfileRes) {

        myProfilePic.setOnClickListener(MyProfileActivity.this);
       // myProfileEditIcon.setOnClickListener(MyProfileActivity.this);
        String profilePic = userProfileRes.getProfileDetails().getProfile_pic();
        PrefUtils.setSharedPrefStringData(MyProfileActivity.this, PrefUtils.USER_PIC, profilePic);
        if (!TextUtils.isEmpty(profilePic)) {
            //profilePic = profilePic.replace("http", "https");
        }
        try {
            // Glide.with(this).load(profilePic).into(myProfilePic);
            Utility.glide(MyProfileActivity.this, myProfilePic, 0, profilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = "";
        int age = 0;
        try {
            age = Integer.valueOf(userProfileRes.getProfileDetails().getAge());
            if (age > 1900) {
                age = age - 1900;

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        name = userProfileRes.getProfileDetails().getUser_name() + ", " + age;
        String aboutme = userProfileRes.getProfileDetails().getAbout_text();
        String edu = userProfileRes.getProfileDetails().getEducation();
        String work = userProfileRes.getProfileDetails().getWork();

        instagram = userProfileRes.getProfileDetails().getInstagram();

        ((TextView) findViewById(R.id.my_profile_user_name_tv)).setText(name);
        if (!TextUtils.isEmpty(aboutme)) {
            findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
            findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.my_profile_about_me_content_tv)).setText(aboutme);
            final TextView aboutMeContent = (TextView) findViewById(R.id.my_profile_about_me_content_tv);
            aboutMeContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Layout layout = aboutMeContent.getLayout();
                    if (layout != null) {
                        int lines = layout.getLineCount();
                        if (lines > 0) {
                            int ellipsisCount = layout.getEllipsisCount(lines - 1);
                            if (ellipsisCount > 0) {
                                Intent intentMyFullProfile = new Intent(MyProfileActivity.this, MyFullProfileActivity.class);
                                intentMyFullProfile.putExtra(Constants.EXTRA_USER_PROFILE, mUserProfile);
                                startActivity(intentMyFullProfile);
                            }
                        }
                    }
                }
            });


        } else {
            findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
            findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(edu)) {
            ((TextView) findViewById(R.id.edu_tv)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.edu_tv)).setText(edu);
        } else {
            ((TextView) findViewById(R.id.edu_tv)).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(work)) {
            findViewById(R.id.my_profile_designation_tv).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.my_profile_designation_tv)).setText(work);
        } else {
            findViewById(R.id.my_profile_designation_tv).setVisibility(View.VISIBLE);
        }
    }

    // for settings data
   /* public void getSettingApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MyProfileActivity.this, PrefUtils.USER_ID));
            Call<ListResp<GetSettingRes>> call = mApis.getSetting(generalReq);
            call.enqueue(new Callback<ListResp<GetSettingRes>>() {

                @Override
                public void onResponse(Call<ListResp<GetSettingRes>> call, Response<ListResp<GetSettingRes>> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getData().size() > 0) {
                            // setUi(response.body().getData().get(0));
                            getSettingRes = response.body().getData().get(0);
                            Intent intentSettings = new Intent(MyProfileActivity.this, SettingsActivity.class);
                            intentSettings.putExtra(AppConstant.SETTINGS_STATUS, "0");
                            intentSettings.putExtra(AppConstant.SETTINGS_DATA, getSettingRes);
                            startActivity(intentSettings);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListResp<GetSettingRes>> call, Throwable t) {
                    showProgressDialog(false);
                    t.printStackTrace();
                }
            });
        } else {
            new SnackBar.Builder(MyProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }
*/

}
