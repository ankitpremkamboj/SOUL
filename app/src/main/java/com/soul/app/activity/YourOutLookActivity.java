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

/**
 * Created by ashishkumar on 5/7/16.
 * For select the OutLook for current user.
 */
public class YourOutLookActivity extends com.soul.app.activity.BaseActivity {

    private FrameLayout editProfileHeader;
    private boolean[] outLookArray = new boolean[]{false, false, false, false, false};
    private CheckBox liberal, moderate, conservative, nonPractising, nonImportant;
    private boolean[] finalArray = new boolean[]{false};
    private TextView outLookDone;
    private String yourOutLook;
    List<GetFilterRes.DataBean> serverResponse;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_your_out_look;
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
        editProfile.setText(R.string.your_outlook);

        outLookDone = (TextView) findViewById(R.id.eph_done);
        outLookDone.setEnabled(false);
        outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));

        outLookDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String outlook = getOutLooked(outLookArray);
                //  String section = getIntent().getStringExtra(Constants.EXTRA_SECT);

                DenominationReq req = new DenominationReq();
                //req.setDenomination(section);
                req.setStatus(outlook);
                req.setUser_id(PrefUtils.getSharedPrefString(YourOutLookActivity.this, PrefUtils.USER_ID));

                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    callUpdateDenominationApi(req);
                }*/

                updateUserFilterApi();

            }
        });


        /*if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.EXTRA_OUTLOOK))) {
            yourOutLook = getIntent().getStringExtra(Constants.EXTRA_OUTLOOK).toString();

            if (yourOutLook.equalsIgnoreCase(getString(R.string.liberal))) {
                outLookArray = new boolean[]{true, false, false, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.moderate))) {
                outLookArray = new boolean[]{false, true, false, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.conservative))) {
                outLookArray = new boolean[]{false, false, true, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.non_practising))) {
                outLookArray = new boolean[]{false, false, false, true, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.not_important))) {
                outLookArray = new boolean[]{false, false, false, false, true};
            }
        }


        if (!TextUtils.isEmpty(PrefUtils.getSharedPrefString(this, PrefUtils.PREF_STATUS))) {
            yourOutLook = PrefUtils.getSharedPrefString(this, PrefUtils.PREF_STATUS);

            if (yourOutLook.equalsIgnoreCase(getString(R.string.liberal))) {
                outLookArray = new boolean[]{true, false, false, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.moderate))) {
                outLookArray = new boolean[]{false, true, false, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.conservative))) {
                outLookArray = new boolean[]{false, false, true, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.non_practising))) {
                outLookArray = new boolean[]{false, false, false, true, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.not_important))) {
                outLookArray = new boolean[]{false, false, false, false, true};
            }
        }*/

        /*YOUR OUTLOOK*/
        liberal = (CheckBox) findViewById(R.id.liberal_cb);
        liberal.setChecked(outLookArray[0]);
        moderate = (CheckBox) findViewById(R.id.moderate_cb);
        moderate.setChecked(outLookArray[1]);
        conservative = (CheckBox) findViewById(R.id.conservative_cb);
        conservative.setChecked(outLookArray[2]);
        nonPractising = (CheckBox) findViewById(R.id.non_practising_cb);
        nonPractising.setChecked(outLookArray[3]);
        nonImportant = (CheckBox) findViewById(R.id.non_important_cb);
        nonImportant.setChecked(outLookArray[4]);

        checkYourOutLookStatus();

        liberal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = liberal.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_LIBERAL);
                if (checkValue) {
                    //outLookArray = new boolean[]{true, false, false, false, false};
                    outLookArray[0] = true;
                } else {
                    // outLookArray = new boolean[]{false, false, false, false, false};
                    outLookArray[0] = false;
                }
                setValueAtYourOutLook();
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = moderate.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_MODERATE);
                if (checkValue) {
                    // outLookArray = new boolean[]{false, true, false, false, false};
                    outLookArray[1] = true;
                } else {
                    // outLookArray = new boolean[]{false, false, false, false, false};
                    outLookArray[1] = false;
                }
                setValueAtYourOutLook();
            }
        });

        conservative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = conservative.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_CONSERVATIVE);
                if (checkValue) {
                    // outLookArray = new boolean[]{false, false, true, false, false};
                    outLookArray[2] = true;
                } else {
                    //outLookArray = new boolean[]{false, false, false, false, false};
                    outLookArray[2] = false;
                }
                setValueAtYourOutLook();
            }
        });

        nonPractising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = nonPractising.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_NON_PRACTISING);
                if (checkValue) {
                    //outLookArray = new boolean[]{false, false, false, true, false};
                    outLookArray[3] = true;
                } else {
                    //outLookArray = new boolean[]{false, false, false, false, false};
                    outLookArray[3] = false;
                }
                setValueAtYourOutLook();
            }
        });

        nonImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = nonImportant.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_NOT_IMPORTANT);
                if (checkValue) {
                    //outLookArray = new boolean[]{false, false, false, false, true};
                    outLookArray[4] = true;
                } else {
                    //outLookArray = new boolean[]{false, false, false, false, false};
                    outLookArray[4] = false;
                }
                setValueAtYourOutLook();
            }

        });

        getUserFilterApi();
    }

    /*YOUR OUTLOOK*/
    private void setValueAtYourOutLook() {
        //   Log.d("sectArray", Arrays.toString(outLookArray));
        liberal.setChecked(outLookArray[0]);
        moderate.setChecked(outLookArray[1]);
        conservative.setChecked(outLookArray[2]);
        nonPractising.setChecked(outLookArray[3]);
        nonImportant.setChecked(outLookArray[4]);

        checkYourOutLookStatus();
    }

    /* Your OutLookStatus*/
    public void checkYourOutLookStatus() {

        for (int i = 0; i < outLookArray.length; i++) {
            if (outLookArray[i] == true) {
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

    public String getOutLooked(boolean[] outlook) {
        if (outlook[0]) {
            return getResources().getString(R.string.liberal);
        } else if (outlook[1]) {
            return getResources().getString(R.string.moderate);
        } else if (outlook[2]) {
            return getResources().getString(R.string.conservative);
        } else if (outlook[3]) {
            return getResources().getString(R.string.non_practising);
        } else {
            return getResources().getString(R.string.not_important);
        }
    }

    @Override
    public String getName() {
        return null;
    }


    private void callUpdateDenominationApi(final DenominationReq req) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<ObjResp> call = mApis.updateDenomination(req);
            showProgressDialog(true);
            final Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_OUTLOOK, req.getStatus());
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        // DialogUtils.showToast(YourOutLookActivity.this, response.body().getMsg());
                        PrefUtils.setSharedPrefStringData(YourOutLookActivity.this, PrefUtils.PREF_STATUS, req.getStatus());
                        setResult(Constants.RESULT_OUTLOOK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ObjResp> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourOutLookActivity.this)
                    .withMessage(getResources().getString(R.string.webpage_not_available)).show();
        }
    }

    private void getUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(YourOutLookActivity.this, PrefUtils.USER_ID));
            Call<GetFilterRes> call = mApis.getFilterRes(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<GetFilterRes>() {
                @Override
                public void onResponse(Call<GetFilterRes> call, Response<GetFilterRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        setOutlook(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<GetFilterRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourOutLookActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void setOutlook(List<GetFilterRes.DataBean> gfs) {
        serverResponse = gfs;
        if (gfs.get(0).getIs_liberal().equals("1")) {
            liberal.setChecked(true);
            outLookArray[0] = true;
        }
        if (gfs.get(0).getIs_moderate().equals("1")) {
            moderate.setChecked(true);
            outLookArray[1] = true;
        }
        if (gfs.get(0).getIs_conservative().equals("1")) {
            conservative.setChecked(true);
            outLookArray[2] = true;
        }
        if (gfs.get(0).getIs_nonpracticing().equals("1")) {
            nonPractising.setChecked(true);
            outLookArray[3] = true;
        }
        if (gfs.get(0).getIs_notimportant().equals("1")) {
            nonImportant.setChecked(true);
            outLookArray[4] = true;
        }
    }

    // UPDATE OUTLOOK
    private void updateUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(YourOutLookActivity.this, PrefUtils.USER_ID));
            if (outLookArray[0] == true) {
                generalReq.setIs_liberal("1");
            } else {
                generalReq.setIs_liberal("0");
            }//
            if (outLookArray[1] == true) {
                generalReq.setIs_moderate("1");
            } else {
                generalReq.setIs_moderate("0");
            }//
            if (outLookArray[2] == true) {
                generalReq.setIs_conservative("1");
            } else {
                generalReq.setIs_conservative("0");
            }//
            if (outLookArray[3] == true) {
                generalReq.setIs_nonpracticing("1");
            } else {
                generalReq.setIs_nonpracticing("0");
            }//
            if (outLookArray[4] == true) {
                generalReq.setIs_notimportant("1");
            } else {
                generalReq.setIs_notimportant("0");
            }

            generalReq.setIs_sunni(serverResponse.get(0).getIs_sunni());
            generalReq.setIs_shiaismaili(serverResponse.get(0).getIs_shiaismaili());
            generalReq.setIs_justmuslim(serverResponse.get(0).getIs_justmuslim());
            generalReq.setIs_convert(serverResponse.get(0).getIs_convert());

            Call<UpdateFilterRes> call = mApis.updateFilterRes(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<UpdateFilterRes>() {

                @Override
                public void onResponse(Call<UpdateFilterRes> call, Response<UpdateFilterRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
//                        new SnackBar.Builder(YourOutLookActivity.this)
//                                .withMessage(response.body().getMsg() + "").show();
                        // setResult(Constants.RESULT_OUTLOOK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UpdateFilterRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(YourOutLookActivity.this)
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

