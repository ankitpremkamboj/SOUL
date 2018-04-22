package com.soul.app.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.R;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.DenominationReq;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.GetFilterRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UpdateFilterRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashishkumar on 5/7/16.
 * For select the OutLook for current user.
 */
public class AcademicActivity extends BaseActivity {

    private FrameLayout editProfileHeader;
    private boolean[] outLookArray = new boolean[]{false, false, false, false, false};
    private CheckBox mHigh_schol_rb, mhigh_school_equivalent_rb, mdiploma_degree_rb, mbachelor_degree_rb, master_degree_rb;
    private boolean[] finalArray = new boolean[]{false};
    private TextView outLookDone, txt_academic;
    private String yourOutLook;
    List<GetFilterRes.DataBean> serverResponse;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_your_academic;
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
        editProfile.setText(R.string.academic);

        outLookDone = (TextView) findViewById(R.id.eph_done);
        outLookDone.setEnabled(true);
        outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));

        outLookDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                if (isValidate()) {
                    String outlook = getOutLooked(outLookArray);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.EXTRA_ACADEMIC, outlook);
                    DenominationReq req = new DenominationReq();
                    req.setStatus(outlook);
                    req.setUser_id(PrefUtils.getSharedPrefString(AcademicActivity.this, PrefUtils.USER_ID));
                    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                        // callUpdateDenominationApi(req);

                        setResult(Constants.RESULT_ACADEMIC, intent);
                        finish();
                    }

                }

            }
        });

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.EXTRA_ACADEMIC))) {
            yourOutLook = getIntent().getStringExtra(Constants.EXTRA_ACADEMIC).toString();

            if (yourOutLook.equalsIgnoreCase(getString(R.string.high_school_drop_out))) {
                outLookArray = new boolean[]{true, false, false, false, false};
              //  setValueAtYourOutLook();

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.high_school_equivalent))) {
                outLookArray = new boolean[]{false, true, false, false, false};

            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.diploma_degree))) {
                outLookArray = new boolean[]{false, false, true, false, false};
                //setValueAtYourOutLook();
            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.bachelor_degree))) {
                outLookArray = new boolean[]{false, false, false, true, false};
               // setValueAtYourOutLook();
            } else if (yourOutLook.equalsIgnoreCase(getString(R.string.master_degree))) {
                outLookArray = new boolean[]{false, false, false, false, true};
               // setValueAtYourOutLook();
            }
        }




        /*YOUR ACADEMIC*/
        mHigh_schol_rb = (CheckBox) findViewById(R.id.high_school_rb);
        mHigh_schol_rb.setChecked(outLookArray[0]);
        mhigh_school_equivalent_rb = (CheckBox) findViewById(R.id.high_school_equivalent_rb);
        mhigh_school_equivalent_rb.setChecked(outLookArray[1]);

        mdiploma_degree_rb = (CheckBox) findViewById(R.id.diploma_degree_rb);
       mdiploma_degree_rb.setChecked(outLookArray[2]);

        mbachelor_degree_rb = (CheckBox) findViewById(R.id.bachelor_degree_rb);
        mbachelor_degree_rb.setChecked(outLookArray[3]);

        master_degree_rb = (CheckBox) findViewById(R.id.master_degree_rb);
        master_degree_rb.setChecked(outLookArray[4]);

        // checkYourOutLookStatus();


        mHigh_schol_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = mHigh_schol_rb.isChecked();
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
        mhigh_school_equivalent_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = mhigh_school_equivalent_rb.isChecked();
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

        mdiploma_degree_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = mdiploma_degree_rb.isChecked();
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

        mbachelor_degree_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = mbachelor_degree_rb.isChecked();
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

        master_degree_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkValue = master_degree_rb.isChecked();
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


        // getUserFilterApi();
    }

    /*YOUR OUTLOOK*/
    private void setValueAtYourOutLook() {
        //   Log.d("sectArray", Arrays.toString(outLookArray));
        mHigh_schol_rb.setChecked(outLookArray[0]);
        mhigh_school_equivalent_rb.setChecked(outLookArray[1]);
        mdiploma_degree_rb.setChecked(outLookArray[2]);
        mbachelor_degree_rb.setChecked(outLookArray[3]);
        master_degree_rb.setChecked(outLookArray[4]);

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
            // outLookDone.setEnabled(true);
            outLookDone.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            //outLookDone.setEnabled(false);
            outLookDone.setTextColor(getResources().getColor(R.color.color_EBEBEB));
        }
    }

    public String getOutLooked(boolean[] outlook) {
        if (outlook[0]) {
            return getResources().getString(R.string.high_school_drop_out);
        } else if (outlook[1]) {
            return getResources().getString(R.string.high_school_equivalent);
        } else if (outlook[2]) {
            return getResources().getString(R.string.diploma_degree);
        } else if (outlook[3]) {
            return getResources().getString(R.string.bachelor_degree);
        } else {
            return getResources().getString(R.string.master_degree);
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
                        PrefUtils.setSharedPrefStringData(AcademicActivity.this, PrefUtils.PREF_STATUS, req.getStatus());
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
            new SnackBar.Builder(AcademicActivity.this)
                    .withMessage(getResources().getString(R.string.webpage_not_available)).show();
        }
    }

    private void getUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(AcademicActivity.this, PrefUtils.USER_ID));
            Call<GetFilterRes> call = mApis.getFilterRes(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<GetFilterRes>() {
                @Override
                public void onResponse(Call<GetFilterRes> call, Response<GetFilterRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        //setOutlook(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<GetFilterRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(AcademicActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void setOutlook(List<GetFilterRes.DataBean> gfs) {
        serverResponse = gfs;
        if (gfs.get(0).getIs_liberal().equals("1")) {
            mHigh_schol_rb.setChecked(true);
            outLookArray[0] = true;
        }
        if (gfs.get(0).getIs_moderate().equals("1")) {
            mhigh_school_equivalent_rb.setChecked(true);
            outLookArray[1] = true;
        }
        if (gfs.get(0).getIs_conservative().equals("1")) {
            mdiploma_degree_rb.setChecked(true);
            outLookArray[2] = true;
        }
        if (gfs.get(0).getIs_nonpracticing().equals("1")) {
            mbachelor_degree_rb.setChecked(true);
            outLookArray[3] = true;
        }
        if (gfs.get(0).getIs_notimportant().equals("1")) {
            master_degree_rb.setChecked(true);
            outLookArray[4] = true;
        }
    }

    // UPDATE OUTLOOK
    private void updateUserFilterApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(AcademicActivity.this, PrefUtils.USER_ID));
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
            new SnackBar.Builder(AcademicActivity.this)
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


    public boolean isValidate() {
        boolean outLookFlag = false;
        //boolean sectFlag = false;
        for (int i = 0; i < outLookArray.length; i++) {
            if (outLookArray[i]) {
                outLookFlag = true;
                break;

            }
        }

        if (outLookFlag)
            return true;
        else if (!outLookFlag) {
            //DialogUtils.showToast(OutLookActivity.this, getResources().getString(R.string.err_empty_outlook));
            new SnackBar.Builder(AcademicActivity.this)
                    .withMessage(getResources().getString(R.string.err_empty_outlook)).show();
            return false;
        } else {
            // DialogUtils.showToast(OutLookActivity.this, getResources().getString(R.string.err_empty_sect));
            new SnackBar.Builder(AcademicActivity.this)
                    .withMessage(getResources().getString(R.string.err_empty_sect)).show();
            return false;
        }
    }
}

