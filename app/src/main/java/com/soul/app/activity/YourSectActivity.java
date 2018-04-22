package com.soul.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.DenominationReq;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.GetFilterRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UpdateFilterRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.utils.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Created by ashishkumar on 20/6/16.
*  For select the Sect for current user.
* */

public class YourSectActivity extends com.soul.app.activity.BaseActivity {

    private boolean[] sectArray = new boolean[]{false, false, false, false};
    private boolean[] finalArray = new boolean[]{false};
    private FrameLayout editProfileHeader;
    private CheckBox sunni, shia, justMuslim, willingToConvert;
    private TextView outLookDone;
    private String yourSect;
    List<GetFilterRes.DataBean> serverResponse;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_your_sect;
    }

    @Override
    public void initUi() {

        editProfileHeader = (FrameLayout) findViewById(R.id.edit_profile_header);
        editProfileHeader.setVisibility(View.VISIBLE);

        ImageView backIcon = (ImageView) findViewById(R.id.eph_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView editProfile = (TextView) findViewById(R.id.eph_edit_profile);
        editProfile.setText(R.string.your_sect);

        outLookDone = (TextView) findViewById(R.id.eph_done);
        outLookDone.setEnabled(false);
        outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));
        outLookDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String outlook = getIntent().getStringExtra(Constants.EXTRA_OUTLOOK);
               /* String section = getYourSect(sectArray);
                DenominationReq req = new DenominationReq();
                req.setDenomination(section);
                //req.setStatus(outlook);
                req.setUser_id(PrefUtils.getSharedPrefString(YourSectActivity.this, PrefUtils.USER_ID));
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    callUpdateDenominationApi(req);
                }*/
                updateUserFilterApi();
            }
        });

        /*if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.EXTRA_SECT))) {
            yourSect = getIntent().getStringExtra(Constants.EXTRA_SECT).toString();
            if (yourSect.equalsIgnoreCase(getString(R.string.sunni))) {
                sectArray = new boolean[]{true, false, false, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.shia))) {
                sectArray = new boolean[]{false, true, false, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.just_muslim))) {
                sectArray = new boolean[]{false, false, true, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.willing_to_convert))) {
                sectArray = new boolean[]{false, false, false, true};
            }
            //setValueAtYourSect();
        }

        if (!TextUtils.isEmpty(PrefUtils.getSharedPrefString(this, PrefUtils.PREF_DENOMINATION))) {
            yourSect = PrefUtils.getSharedPrefString(this, PrefUtils.PREF_DENOMINATION);
            if (yourSect.equalsIgnoreCase(getString(R.string.sunni))) {
                sectArray = new boolean[]{true, false, false, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.shia))) {
                sectArray = new boolean[]{false, true, false, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.just_muslim))) {
                sectArray = new boolean[]{false, false, true, false};

            } else if (yourSect.equalsIgnoreCase(getString(R.string.willing_to_convert))) {
                sectArray = new boolean[]{false, false, false, true};
            }
            //setValueAtYourSect();
        }*/

          /*YOUR SECT*/
        sunni = (CheckBox) findViewById(R.id.sunni_cb);
        sunni.setChecked(sectArray[0]);
        shia = (CheckBox) findViewById(R.id.shia_cb);
        shia.setChecked(sectArray[1]);
        justMuslim = (CheckBox) findViewById(R.id.just_muslim_cb);
        justMuslim.setChecked(sectArray[2]);
        willingToConvert = (CheckBox) findViewById(R.id.willing_to_convert_cb);
        willingToConvert.setChecked(sectArray[3]);

        checkYourSectStatus();

        sunni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = sunni.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_SUNNI);
                if (checkValue) {
                    //sectArray = new boolean[]{true, false, false, false};
                    sectArray[0] = true;
                } else {
                    //sectArray = new boolean[]{false, false, false, false};
                    sectArray[0] = false;
                }
                setValueAtYourSect();
            }
        });
        shia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = shia.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_SHIA);
                if (checkValue) {
                    //sectArray = new boolean[]{false, true, false, false};
                    sectArray[1] = true;
                } else {
                    // sectArray = new boolean[]{false, false, false, false};
                    sectArray[1] = false;
                }
                setValueAtYourSect();
            }
        });
        justMuslim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = justMuslim.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_JUST_MUSLIM);
                if (checkValue) {
                    // sectArray = new boolean[]{false, false, true, false};
                    sectArray[2] = true;
                } else {
                    // sectArray = new boolean[]{false, false, false, false};
                    sectArray[2] = false;
                }
                setValueAtYourSect();
            }
        });
        willingToConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = willingToConvert.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_WILLING_TO_CONVERT);
                if (checkValue) {
                    // sectArray = new boolean[]{false, false, false, true};
                    sectArray[3] = true;
                } else {
                    // sectArray = new boolean[]{false, false, false, false};
                    sectArray[3] = false;
                }
                setValueAtYourSect();
            }
        });

        getUserFilterApi();
    }


    /*YOUR SECT*/
    private void setValueAtYourSect() {

        //  Log.d("sectArray", Arrays.toString(sectArray));

        sunni.setChecked(sectArray[0]);
        shia.setChecked(sectArray[1]);
        justMuslim.setChecked(sectArray[2]);
        willingToConvert.setChecked(sectArray[3]);

        checkYourSectStatus();
    }

    private void checkYourSectStatus() {
        for (int i = 0; i < sectArray.length; i++) {
            if (sectArray[i] == true) {
                finalArray[0] = true;
                break;
            } else {
                finalArray[0] = false;
            }
        }
        checkDoneStatus();
    }

    private void checkDoneStatus() {

        if (finalArray[0] == true) {
            outLookDone.setEnabled(true);
            outLookDone.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            outLookDone.setEnabled(false);
            outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));
        }
    }


    @Override
    public String getName() {
        return null;
    }

    public String getYourSect(boolean[] sectArray) {
        if (sectArray[0]) {
            return getResources().getString(R.string.sunni);

        } else if (sectArray[1]) {
            return getResources().getString(R.string.shia);
        } else if (sectArray[2]) {
            return getResources().getString(R.string.just_muslim);

        } else {
            return getResources().getString(R.string.willing_to_convert);
        }
    }

    private void callUpdateDenominationApi(final DenominationReq req) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<ObjResp> call = mApis.updateDenomination(req);
            showProgressDialog(true);
            final Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_SECT, req.getDenomination());
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        // DialogUtils.showToast(YourSectActivity.this, response.body().getMsg());
                        PrefUtils.setSharedPrefStringData(YourSectActivity.this, PrefUtils.PREF_DENOMINATION, req.getDenomination());
                        setResult(Constants.RESULT_SECT, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ObjResp> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourSectActivity.this)
                    .withMessage(getResources().getString(R.string.webpage_not_available)).show();
        }
    }

    private void getUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(YourSectActivity.this, PrefUtils.USER_ID));
            Call<GetFilterRes> call = mApis.getFilterRes(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<GetFilterRes>() {

                @Override
                public void onResponse(Call<GetFilterRes> call, Response<GetFilterRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        setSect(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<GetFilterRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourSectActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    private void setSect(List<GetFilterRes.DataBean> gfs) {
        serverResponse = gfs;
        if (gfs.get(0).getIs_sunni().equals("1")) {
            sunni.setChecked(true);
            sectArray[0] = true;
        }
        if (gfs.get(0).getIs_shiaismaili().equals("1")) {
            shia.setChecked(true);
            sectArray[1] = true;
        }
        if (gfs.get(0).getIs_justmuslim().equals("1")) {
            justMuslim.setChecked(true);
            sectArray[2] = true;
        }
        if (gfs.get(0).getIs_convert().equals("1")) {
            willingToConvert.setChecked(true);
            sectArray[3] = true;
        }
    }

    // UPDATE SECT
    private void updateUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(YourSectActivity.this, PrefUtils.USER_ID));
            if (sectArray[0] == true) {
                generalReq.setIs_sunni("1");
            } else {
                generalReq.setIs_sunni("0");
            }//
            if (sectArray[1] == true) {
                generalReq.setIs_shiaismaili("1");
            } else {
                generalReq.setIs_shiaismaili("0");
            }//
            if (sectArray[2] == true) {
                generalReq.setIs_justmuslim("1");
            } else {
                generalReq.setIs_justmuslim("0");
            }//
            if (sectArray[3] == true) {
                generalReq.setIs_convert("1");
            } else {
                generalReq.setIs_convert("0");
            }//


            generalReq.setIs_liberal(serverResponse.get(0).getIs_liberal());
            generalReq.setIs_moderate(serverResponse.get(0).getIs_moderate());
            generalReq.setIs_conservative(serverResponse.get(0).getIs_conservative());
            generalReq.setIs_nonpracticing(serverResponse.get(0).getIs_nonpracticing());
            generalReq.setIs_notimportant(serverResponse.get(0).getIs_notimportant());


            Call<UpdateFilterRes> call = mApis.updateFilterRes(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<UpdateFilterRes>() {

                @Override
                public void onResponse(Call<UpdateFilterRes> call, Response<UpdateFilterRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
//                        new SnackBar.Builder(YourSectActivity.this)
//                                .withMessage(response.body().getMsg() + "").show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdateFilterRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourSectActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, AppConstant.FLURRY_API_KEY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }
}
