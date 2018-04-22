package com.soul.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.DenominationReq;
import com.soul.app.models.res.ObjResp;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.PrefUtils;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashishkumar on 20/6/16.
 * When User come first time in this application.
 */
public class OutLookActivity extends com.soul.app.activity.BaseActivity {

    boolean[] outLookArray = new boolean[]{false, false, false, false, false};
    boolean[] sectArray = new boolean[]{false, false, false, false};
    boolean[] finalArray = new boolean[]{false, false};

    CheckBox liberal, moderate, conservative, nonPractising, nonImportant;
    CheckBox sunni, shia, justMuslim, willingToConvert;
    FrameLayout outlookHeader;
    private TextView outLookDone;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_out_look;
    }

    @Override
    public void initUi() {

        outlookHeader = (FrameLayout) findViewById(R.id.outlook_header);
        outlookHeader.setVisibility(View.VISIBLE);

        outLookDone = (TextView) findViewById(R.id.oh_done);
        // outLookDone.setEnabled(true);
        outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));
        outLookDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    String outlook = getOutLooked(outLookArray);
                    String section = getYourSect(sectArray);

                    DenominationReq req = new DenominationReq();
                    req.setDenomination(section);
                    req.setStatus(outlook);
                    req.setUser_id(PrefUtils.getSharedPrefString(OutLookActivity.this, PrefUtils.USER_ID));
                    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                        callUpdateDenominationApi(req);
                    }

                }
            }
        });

        PrefUtils.setSharedPrefBooleanData(OutLookActivity.this, PrefUtils.PREF_INTEREST, false);
        /*YOUR OURLOOK*/
        liberal = (CheckBox) findViewById(R.id.liberal_cb);
        moderate = (CheckBox) findViewById(R.id.moderate_cb);
        conservative = (CheckBox) findViewById(R.id.conservative_cb);
        nonPractising = (CheckBox) findViewById(R.id.non_practising_cb);
        nonImportant = (CheckBox) findViewById(R.id.non_important_cb);

        liberal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = liberal.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_LIBERAL);
                if (checkValue) {
                    outLookArray = new boolean[]{true, false, false, false, false};
                    setValueAtYourOutLook();
                } else {
                    outLookArray = new boolean[]{false, false, false, false, false};
                    setValueAtYourOutLook();
                }
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = moderate.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_MODERATE);
                if (checkValue) {
                    outLookArray = new boolean[]{false, true, false, false, false};
                    setValueAtYourOutLook();
                } else {
                    outLookArray = new boolean[]{false, false, false, false, false};
                    setValueAtYourOutLook();
                }
            }
        });

        conservative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = conservative.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_CONSERVATIVE);
                if (checkValue) {
                    outLookArray = new boolean[]{false, false, true, false, false};
                    setValueAtYourOutLook();
                } else {
                    outLookArray = new boolean[]{false, false, false, false, false};
                    setValueAtYourOutLook();
                }
            }
        });

        nonPractising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = nonPractising.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_NON_PRACTISING);
                if (checkValue) {
                    outLookArray = new boolean[]{false, false, false, true, false};
                    setValueAtYourOutLook();
                } else {
                    outLookArray = new boolean[]{false, false, false, false, false};
                    setValueAtYourOutLook();
                }
            }
        });

        nonImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = nonImportant.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_NOT_IMPORTANT);
                if (checkValue) {
                    outLookArray = new boolean[]{false, false, false, false, true};
                    setValueAtYourOutLook();
                } else {
                    outLookArray = new boolean[]{false, false, false, false, false};
                    setValueAtYourOutLook();
                }
            }
        });

        /*YOUR SECT*/
        sunni = (CheckBox) findViewById(R.id.sunni_cb);
        shia = (CheckBox) findViewById(R.id.shia_cb);
        justMuslim = (CheckBox) findViewById(R.id.just_muslim_cb);
        willingToConvert = (CheckBox) findViewById(R.id.willing_to_convert_cb);

        sunni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = sunni.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_SUNNI);
                if (checkValue) {
                    sectArray = new boolean[]{true, false, false, false};
                    setValueAtYourSect();
                } else {
                    sectArray = new boolean[]{false, false, false, false};
                    setValueAtYourSect();
                }
            }
        });
        shia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = shia.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_SHIA);
                if (checkValue) {
                    sectArray = new boolean[]{false, true, false, false};
                    setValueAtYourSect();
                } else {
                    sectArray = new boolean[]{false, false, false, false};
                    setValueAtYourSect();
                }
            }
        });
        justMuslim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = justMuslim.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_JUST_MUSLIM);
                if (checkValue) {
                    sectArray = new boolean[]{false, false, true, false};
                    setValueAtYourSect();
                } else {
                    sectArray = new boolean[]{false, false, false, false};
                    setValueAtYourSect();
                }
            }
        });
        willingToConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = willingToConvert.isChecked();
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_FILTER_WILLING_TO_CONVERT);
                if (checkValue) {
                    sectArray = new boolean[]{false, false, false, true};
                    setValueAtYourSect();
                } else {
                    sectArray = new boolean[]{false, false, false, false};
                    setValueAtYourSect();
                }
            }
        });
    }

    /*YOUR OUTLOOK*/
    private void setValueAtYourOutLook() {
        liberal.setChecked(outLookArray[0]);
        moderate.setChecked(outLookArray[1]);
        conservative.setChecked(outLookArray[2]);
        nonPractising.setChecked(outLookArray[3]);
        nonImportant.setChecked(outLookArray[4]);

        checkYourOutLookStatus();
    }

    /*YOUR SECT*/
    private void setValueAtYourSect() {
        sunni.setChecked(sectArray[0]);
        shia.setChecked(sectArray[1]);
        justMuslim.setChecked(sectArray[2]);
        willingToConvert.setChecked(sectArray[3]);

        checkYourSectStatus();
    }

    private void checkYourSectStatus() {
        for (int i = 0; i < sectArray.length; i++) {
            if (sectArray[i] == true) {
                finalArray[1] = true;
                break;
            } else {
                finalArray[1] = false;
            }
        }

        checkDoneStatus();
    }

    private void checkDoneStatus() {

        if (finalArray[0] == true && finalArray[1] == true) {
            //outLookDone.setEnabled(true);
            outLookDone.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            //outLookDone.setEnabled(false);
            outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));
        }
    }

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

        showProgressDialog(true);
        Call<ObjResp> call = mApis.updateDenomination(req);
        showProgressDialog(true);
        call.enqueue(new Callback<ObjResp>() {

            @Override
            public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                showProgressDialog(false);
                if (response.isSuccessful()) {

                    //DialogUtils.showToast(OutLookActivity.this, response.body().getMsg());
                    Intent intentHomeFindingPeople = new Intent(OutLookActivity.this, SwipingActivity.class);
                    PrefUtils.setSharedPrefBooleanData(OutLookActivity.this, PrefUtils.PREF_INTEREST, true);
                    startActivity(intentHomeFindingPeople);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ObjResp> call, Throwable t) {
                showProgressDialog(false);
            }
        });
    }

    @Override
    public String getName() {
        return OutLookActivity.class.getName();
    }

    public boolean isValidate() {
        boolean outLookFlag = false;
        boolean sectFlag = false;
        for (int i = 0; i < outLookArray.length; i++) {
            if (outLookArray[i]) {
                outLookFlag = true;
                break;

            }
        }
        for (int i = 0; i < sectArray.length; i++) {
            if (sectArray[i]) {
                sectFlag = true;
                break;
            }
        }
        if (outLookFlag && sectFlag)
            return true;
        else if (!outLookFlag) {
            //DialogUtils.showToast(OutLookActivity.this, getResources().getString(R.string.err_empty_outlook));
            new SnackBar.Builder(OutLookActivity.this)
                    .withMessage(getResources().getString(R.string.err_empty_outlook)).show();
            return false;
        } else {
            // DialogUtils.showToast(OutLookActivity.this, getResources().getString(R.string.err_empty_sect));
            new SnackBar.Builder(OutLookActivity.this)
                    .withMessage(getResources().getString(R.string.err_empty_sect)).show();
            return false;
        }
    }

//    @Override
//    public void mapReady(GoogleMap googleMap) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }

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
