package com.soul.app.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.R;
import com.soul.app.activity.SwipeViewHomeActivity;
import com.soul.app.activity.YourOutLookActivity;
import com.soul.app.activity.YourSectActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.CommonUserRes;
import com.soul.app.models.res.GetSettingRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.LogoutRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UserProfileRes;
import com.soul.app.retrofit.ApiConstants;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.util.ArrayList;
import java.util.Collections;

import io.apptik.widget.MultiSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
*  For Application Settings, here we can change the app Preference
* */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    FrameLayout settingsHeader;
    private ImageView profileIcon;
    private TextView mAgeMinTv, mAgeMaxTv, mDistanceTv;
    private MultiSlider mAgeSeekBar, mDistanceSeekBar;
    private SwitchCompat mMaleSwitch, mFmaleSwitch, mInCogSwitch, mNewMatchSwitch, mNewMsgSwitch;
    private RelativeLayout logoutRl;
    private boolean logoutFlag;
    private RelativeLayout termsOfServicesRl, privacyPolicyRl, contactUsRl, mRemoveAcRl, height_rl;
    private String minHeight = "", maxHeight = "";
    private String academic;
    private ArrayList<String> mInterestList;
    private String interestId;

    @Override
    protected void onPause() {
        super.onPause();
        //if (!logoutFlag)
        //updateSetting();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!logoutFlag) {
            updateSetting();
        }
    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_settings;
    }

    @Override
    public void initUi() {
        mInterestList = new ArrayList<String>();

        mRemoveAcRl = (RelativeLayout) findViewById(R.id.remove_ac_rl);
        height_rl = (RelativeLayout) findViewById(R.id.height_rl);
        settingsHeader = (FrameLayout) findViewById(R.id.settings_header);
        settingsHeader.setVisibility(View.VISIBLE);
        mAgeMaxTv = (TextView) findViewById(R.id.ages_max_tv);
        mAgeMinTv = (TextView) findViewById(R.id.ages_min_tv);
        mDistanceTv = (TextView) findViewById(R.id.miles);
        mAgeSeekBar = (MultiSlider) findViewById(R.id.age_seekbar);
        mDistanceSeekBar = (MultiSlider) findViewById(R.id.distance_seekbar);
        mDistanceSeekBar.getThumb(0).setValue(0);
        mAgeSeekBar.getThumb(0).setValue(0);
        mAgeSeekBar.getThumb(1).setValue(1);

        mMaleSwitch = (SwitchCompat) findViewById(R.id.male_switch);
        mFmaleSwitch = (SwitchCompat) findViewById(R.id.female_switch);
        mInCogSwitch = (SwitchCompat) findViewById(R.id.cognito_switch);
        mNewMatchSwitch = (SwitchCompat) findViewById(R.id.new_match_switch);
        mNewMsgSwitch = (SwitchCompat) findViewById(R.id.new_msg_switch);
       /* mMaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMaleSwitch.isChecked()) {
                    mFmaleSwitch.setChecked(false);
                } else {
                    mFmaleSwitch.setChecked(true);

                }


            }
        });*/

        /*mFmaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mFmaleSwitch.isChecked()) {
                    mMaleSwitch.setChecked(false);
                } else {
                    mMaleSwitch.setChecked(true);

                }


            }
        });*/


        termsOfServicesRl = (RelativeLayout) findViewById(R.id.terms_of_services_rl);
        termsOfServicesRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SettingsActivity.this, ApiConstants.URL_TERMS, 3);
            }
        });
        privacyPolicyRl = (RelativeLayout) findViewById(R.id.privacy_policy_rl);
        privacyPolicyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SettingsActivity.this, ApiConstants.URL_PRIVACY, 2);
            }
        });

        contactUsRl = (RelativeLayout) findViewById(R.id.contact_us_rl);
        contactUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.loadWebPage(SettingsActivity.this, ApiConstants.URL_CONTACT, 1);
            }
        });

        logoutRl = (RelativeLayout) findViewById(R.id.logout_rl);
        logoutRl.setOnClickListener(this);

        profileIcon = (ImageView) findViewById(R.id.sh_profile_icon);

        String settingsStatus = getIntent().getStringExtra(AppConstant.SETTINGS_STATUS);
        // 0 for profile / 1 for home
        if (settingsStatus.equals("0")) {
            profileIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        } else {
            profileIcon.setImageDrawable(getResources().getDrawable(R.drawable.home_icon));
        }
        height_rl.setOnClickListener(this);
        findViewById(R.id.outlook_rl).setOnClickListener(this);
        findViewById(R.id.sect_rl).setOnClickListener(this);
        profileIcon.setOnClickListener(this);
        mAgeSeekBar.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (thumbIndex == 0) {
                    mAgeMinTv.setText(value + "");

                } else {
                    mAgeMaxTv.setText(value + "");
                }
            }
        });

        mDistanceSeekBar.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (value == 300)
                    mDistanceTv.setText(value + "+");
                else
                    mDistanceTv.setText(value + "");
            }
        });

        mInCogSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateIncognitoApi(isChecked);

            }
        });
        mRemoveAcRl.setOnClickListener(this);

        getSettingApi();
        profileApi();
        //setUi((GetSettingRes) getIntent().getSerializableExtra(AppConstant.SETTINGS_DATA));
        //ArrayList<String> porfilePicList=PrefUtils.getSharedPrefListData(SettingsActivity.this,PrefUtils.PROFILE_PIC_LIST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // profileApi();
        profileApi();


        // profileApiforInterest();

    }

    private void logout() {

        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                SettingsActivity.this);

        // Setting Dialog Title
        alertDialog2.setTitle("Logout...");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to logout?");

        // Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_soul_logo);

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        signOutApi();

                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();
    }

    @Override
    public String getName() {
        return SettingsActivity.class.getName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sh_profile_icon:
                if (!logoutFlag) {
                    updateSetting();
                }
                finish();
                break;
            case R.id.outlook_rl:
                //startActivity(new Intent(this, YourOutLookActivity.class));

                Intent intentEditInterests = new Intent(SettingsActivity.this, InterestActivity.class);
                intentEditInterests.putStringArrayListExtra(Constants.EXTRA_INTEREST_LIST, mInterestList);
                startActivityForResult(intentEditInterests, 3);
                break;
            case R.id.sect_rl:
                //startActivity(new Intent(this, AcademicActivity.class));
                Intent intentAcademic = new Intent(SettingsActivity.this, AcademicActivity.class);
                intentAcademic.putExtra(Constants.EXTRA_ACADEMIC, academic);
                startActivityForResult(intentAcademic, 4);


                break;
            case R.id.remove_ac_rl:
                removeAc();
                break;
            case R.id.height_rl:
                // startActivity(new Intent(this, HeightActivity.class));

                Intent intentHeight = new Intent(SettingsActivity.this, HeightActivity.class);
                intentHeight.putExtra(Constants.EXTRA_MIN_HEIGHT, minHeight);
                intentHeight.putExtra(Constants.EXTRA_MAX_HEIGHT, maxHeight);
                startActivityForResult(intentHeight, 5);

                break;
            case R.id.logout_rl:
                logout();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.RESULT_INTEREST) {
            try {
                interestId = data.getStringExtra(Constants.EXTRA_INTEREST);
                Log.e("interest", interestId);
                //((TextView) findViewById(R.id.my_edit_profile_interests_tv)).setText(interest);
            /*here get interest data for sending on setting  */

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Constants.RESULT_ACADEMIC) {
            try {
                academic = data.getStringExtra(Constants.EXTRA_ACADEMIC);
                /*get academic data*/
                //((TextView) findViewById(R.id.my_edit_profile_sect_tv)).setText(data.getStringExtra(Constants.EXTRA_SECT));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (resultCode == Constants.RESULT_HEIGHT) {

            minHeight = data.getStringExtra(Constants.EXTRA_MIN_HEIGHT);
            maxHeight = data.getStringExtra(Constants.EXTRA_MAX_HEIGHT);


            /*get height data here */

        } /*else {
            try {
                ((TextView) findViewById(R.id.my_edit_profile_outlook_tv)).setText(data.getStringExtra(Constants.EXTRA_OUTLOOK));
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
        // }

       /* if (resultCode == Activity.RESULT_OK && mediaFactory != null) {
            ArrayList<String> stringArrayList = mediaFactory.onActivityResult(requestCode, resultCode, data);
            if (stringArrayList != null && stringArrayList.size() > 0) {
                updateUserImageApi(stringArrayList.get(0));
                setSelectImageUri(stringArrayList.get(0));

            }
        }*/
    }

    public void getSettingApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            Call<ObjResp<GetSettingRes>> call = mApis.getSetting(generalReq);

            call.enqueue(new Callback<ObjResp<GetSettingRes>>() {
                @Override
                public void onResponse(Call<ObjResp<GetSettingRes>> call, Response<ObjResp<GetSettingRes>> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        setUi(response.body().getData());
                        /*if (response.body().getData().size() > 0) {
                            setUi(response.body().getData().get(0));
                        }*/
                    }
                }

                @Override
                public void onFailure(Call<ObjResp<GetSettingRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void setUi(GetSettingRes response) {
        mAgeMaxTv.setText(response.getMax_age());
        mAgeMinTv.setText(response.getMin_age());
        maxHeight = response.getMax_height();
        minHeight = response.getMin_height();
        academic = response.getAcademy();
        interestId = response.getIntrestid();
        String distance = response.getDistance();
        if (distance.length() > 3) {
            distance = distance.substring(0, 2);
            //mDistanceSeekBar.getThumb(0).setValue(20);
        }
        try {
            int minAge = Integer.valueOf(response.getMin_age());
            int maxAge = Integer.valueOf(response.getMax_age());
            int dist = Integer.valueOf(distance);
            mDistanceTv.setText(distance);
            mDistanceSeekBar.getThumb(0).setValue(dist);
            mAgeSeekBar.getThumb(0).setValue(minAge);
            mAgeSeekBar.getThumb(1).setValue(maxAge);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.getMale().equals("0")) {
            mMaleSwitch.setChecked(false);
        } else {
            mMaleSwitch.setChecked(true);
        }

        if (response.getFemale().equals("0")) {
            mFmaleSwitch.setChecked(false);
        } else {
            mFmaleSwitch.setChecked(true);
        }
        if (response.getChat_notification().equals("0")) {
            mNewMsgSwitch.setChecked(false);
        } else {
            mNewMsgSwitch.setChecked(true);
        }
        if (response.getCogonito_status().equals("0")) {
            mInCogSwitch.setChecked(false);
        } else {
            mInCogSwitch.setChecked(true);
        }
        if (response.getMatch_notification().equals("0")) {
            mNewMatchSwitch.setChecked(false);
        } else {
            mNewMatchSwitch.setChecked(true);
        }
    }

    public void updateSetting() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            generalReq.setDistance(mDistanceSeekBar.getThumb(0).getValue() + "");
            generalReq.setMax_age(mAgeSeekBar.getThumb(1).getValue() + "");
            generalReq.setMin_age(mAgeSeekBar.getThumb(0).getValue() + "");
            generalReq.setMax_height(maxHeight + "");
            generalReq.setMin_height(minHeight + "");

            generalReq.setAcademy(academic + "");
            generalReq.setInterest(interestId + "");

            if (mNewMatchSwitch.isChecked()) {
                generalReq.setMatch_notification("1");
            } else {
                generalReq.setMatch_notification("0");
            }

            if (mInCogSwitch.isChecked()) {
                generalReq.setIncogonito_mode("1");
            } else {
                generalReq.setIncogonito_mode("0");
            }

            if (mNewMsgSwitch.isChecked()) {
                generalReq.setChat_notification("1");
            } else {
                generalReq.setChat_notification("0");
            }

            if (mMaleSwitch.isChecked()) {
                generalReq.setIs_male("1");
            } else {
                generalReq.setIs_male("0");
            }
            if (mFmaleSwitch.isChecked()) {
                generalReq.setIs_female("1");
            } else {
                generalReq.setIs_female("0");
            }

            generalReq.setDistance_unit("miles");
            if (mMaleSwitch.isChecked()) {
                generalReq.setIs_male("1");
            } else {
                generalReq.setIs_male("0");
            }
            if (mFmaleSwitch.isChecked()) {
                generalReq.setIs_female("1");
            } else {
                generalReq.setIs_female("0");
            }
            if (mNewMatchSwitch.isChecked()) {
                generalReq.setIs_match("1");
            } else {
                generalReq.setIs_match("0");
            }
            if (mNewMsgSwitch.isChecked()) {
                generalReq.setChat_notification("1");
            } else {
                generalReq.setChat_notification("0");
            }

            Call<CommonUserRes> call = mApis.updateSetting(generalReq);
            // showProgressDialog(true);
            call.enqueue(new Callback<CommonUserRes>() {

                @Override
                public void onResponse(Call<CommonUserRes> call, Response<CommonUserRes> response) {
                    if (response.isSuccessful()) {
                        // showProgressDialog(false);
                        if (response.body() != null) {
                            // showProgressDialog(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<CommonUserRes> call, Throwable t) {
                    //  showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void updateIncognitoApi(boolean isChecked) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            showProgressDialog(true);
            if (isChecked) {
                generalReq.setCognito_status("1");
            } else {
                generalReq.setCognito_status("0");
            }

            Call<CommonUserRes> call = mApis.updateIncognitoMode(generalReq);
            call.enqueue(new Callback<CommonUserRes>() {


                @Override
                public void onResponse(Call<CommonUserRes> call, Response<CommonUserRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            // DialogUtils.showToast(SettingsActivity.this, response.body().getMsg());
                            showProgressDialog(false);
                        }

                    } else {
                        showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<CommonUserRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void signOutApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            showProgressDialog(true);

            Call<LogoutRes> call = mApis.signOut(generalReq);
            call.enqueue(new Callback<LogoutRes>() {


                @Override
                public void onResponse(Call<LogoutRes> call, Response<LogoutRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            logoutFlag = true;
                            //  DialogUtils.showToast(SettingsActivity.this, response.body().getMsg());
                            showProgressDialog(false);
                            LoginManager.getInstance().logOut();

                            Utility.clearAllSharedPrefData(SettingsActivity.this);  // for device token (GCM)
                            PrefUtils.logoutUser(SettingsActivity.this);

                            Intent intentSwipeHomeActivity = new Intent(SettingsActivity.this, SwipeViewHomeActivity.class);
                            ComponentName componentName = intentSwipeHomeActivity.getComponent();
                            Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
                            startActivity(mainIntent);
                            finish();
                        }
                    } else {
                        showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<LogoutRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void removeAccountApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            showProgressDialog(true);

            Call<CommonUserRes> call = mApis.removeAccount(generalReq);
            call.enqueue(new Callback<CommonUserRes>() {


                @Override
                public void onResponse(Call<CommonUserRes> call, Response<CommonUserRes> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            LoginManager.getInstance().logOut();
                            showProgressDialog(false);
                            Utility.clearAllSharedPrefData(SettingsActivity.this);  // for device token (GCM)
                            PrefUtils.logoutUser(SettingsActivity.this);
                            Intent intentSwipeHomeActivity = new Intent(SettingsActivity.this, SwipeViewHomeActivity.class);
                            ComponentName componentName = intentSwipeHomeActivity.getComponent();
                            Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
                            startActivity(mainIntent);
                            finish();
                        }
                    } else {
                        showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<CommonUserRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void removeAc() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                SettingsActivity.this);

        // Setting Dialog Title
        alertDialog2.setTitle("Remove Account");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want remove account?");

        // Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.ic_soul_logo);

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        removeAccountApi();
                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        // Showing Alert Dialog
        alertDialog2.show();
    }


    private void profileApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SettingsActivity.this, PrefUtils.USER_ID));
            Call<ObjResp<UserProfileRes>> call = mApis.userProfile(generalReq);
            // showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<UserProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<UserProfileRes>> call, Response<ObjResp<UserProfileRes>> response) {

                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        //  showProgressDialog(false);
                        if (response.body() != null) {
                            getIntrestData(response.body().getData());
                            // Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }/* else {
                        showProgressDialog(false);
                    }*/
                }

                @Override
                public void onFailure(Call<ObjResp<UserProfileRes>> call, Throwable t) {
                    //showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(SettingsActivity.this)
                    .withMessage(getString(R.string.err_network)).show();
        }
    }

    public void getIntrestData(UserProfileRes userProfile) {

        mInterestList.clear();
        for (int i = 0; i < userProfile.getInterests().size(); i++) {
            mInterestList.add(userProfile.getInterests().get(i).getCategory_name());

        }

    }

}