package com.soul.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.MatchesFoundActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ObjResp;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
*  using for submitted Report at Chat activity screen.
* */

public class SubmitReportActivity extends com.soul.app.activity.BaseActivity implements View.OnClickListener {
    private TextView mSubmitReportTv;
    private EditText mResonTv;
    private ImageView mCloseImgVw;
    private String mFlagType;
    private String mOtherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_submit_report;
    }

    @Override
    public void initUi() {
        mOtherId = getIntent().getStringExtra(Constants.EXTRA_OTHER_ID);
        mSubmitReportTv = (TextView) findViewById(R.id.submit_report_tv);
        mResonTv = (EditText) findViewById(R.id.report_reason_tv);
        mCloseImgVw = (ImageView) findViewById(R.id.close_imgvw);
        mFlagType = getIntent().getStringExtra(Constants.EXTRA_REPORT_REASON);
        mSubmitReportTv.setOnClickListener(this);
        mCloseImgVw.setOnClickListener(this);

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_imgvw:
                finish();
                break;
            case R.id.submit_report_tv:
                if (!TextUtils.isEmpty(mResonTv.getText()))
                    reportApi();
                else {
                    //  DialogUtils.showToast(SubmitReportActivity.this, getResources().getString(R.string.empty_reason));
                    new SnackBar.Builder(SubmitReportActivity.this)
                            .withMessage(getString(R.string.empty_reason)).show();
                }
                break;
        }
    }

    private void reportApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq req = new GeneralReq();
            req.setUser_id(mOtherId);
            req.setReported_by(PrefUtils.getSharedPrefString(this, PrefUtils.USER_ID));
            req.setFlaged_comment(mResonTv.getText() + "");
            req.setFlaged_type(mFlagType);
            Call<ObjResp> call = mApis.reportUser(req);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {

                        Intent intent = new Intent(SubmitReportActivity.this, MatchesFoundActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ObjResp> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            // DialogUtils.showToast(SubmitReportActivity.this, getResources().getString(R.string.err_network));
            new SnackBar.Builder(SubmitReportActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

}
