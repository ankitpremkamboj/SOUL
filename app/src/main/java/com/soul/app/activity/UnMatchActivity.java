package com.soul.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ObjResp;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.utils.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnMatchActivity extends com.soul.app.activity.BaseActivity implements View.OnClickListener {
    private String mOtherId;
    private TextView mUnmatchTv, mNoResonTv, mImsgTv, mIphotoTv, mSpatmTv, mOtherTv, mBadBehTv;
    private ImageView mCloseIv;
    private LinearLayout mNoReasonll, mSpamll, mOtherll, mBadBehll, mIphotoll, mImsgll;
    private ArrayList<TextView> mSelectedList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_un_match;
    }

    @Override
    public void initUi() {

        mOtherId = getIntent().getStringExtra(Constants.EXTRA_OTHER_ID);
        mUnmatchTv = (TextView) findViewById(R.id.unmatch_tv);

        mSpamll = (LinearLayout) findViewById(R.id.spam_ll);
        mNoReasonll = (LinearLayout) findViewById(R.id.no_reason_ll);
        mOtherll = (LinearLayout) findViewById(R.id.others_ll);
        mBadBehll = (LinearLayout) findViewById(R.id.bad_offline_behaviour_ll);
        mIphotoll = (LinearLayout) findViewById(R.id.inappropriate_photo_ll);
        mImsgll = (LinearLayout) findViewById(R.id.inappropriate_msg_ll);

        mNoResonTv = (TextView) findViewById(R.id.no_reason_tv);
        mImsgTv = (TextView) findViewById(R.id.inapp_msg_tv);
        mIphotoTv = (TextView) findViewById(R.id.inap_photo_tv);
        mSpatmTv = (TextView) findViewById(R.id.spam_tv);
        mOtherTv = (TextView) findViewById(R.id.other_tv);
        mBadBehTv = (TextView) findViewById(R.id.bad_offline_tv);

        mCloseIv = (ImageView) findViewById(R.id.close_imgvw);

        mCloseIv.setOnClickListener(this);

        mUnmatchTv.setOnClickListener(this);

        mSpamll.setOnClickListener(this);
        mNoReasonll.setOnClickListener(this);
        mOtherll.setOnClickListener(this);
        mBadBehll.setOnClickListener(this);
        mIphotoll.setOnClickListener(this);
        mImsgll.setOnClickListener(this);

        //list change on click reasons
        mSelectedList.add(mNoResonTv);

        mSelectedList.add(mNoResonTv);


    }

    @Override
    public String getName() {
        return null;
    }


    private void unmatchApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq req = new GeneralReq();
            req.setUser_id(PrefUtils.getSharedPrefString(UnMatchActivity.this, PrefUtils.USER_ID));
            req.setOther_id(mOtherId);
            req.setUnmatch_reason(mSelectedList.get(1).getText() + "");
            Call<ObjResp> call = mApis.unMatchUser(req);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {

                        FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_UNMATCHES);
                        Intent intent = new Intent(UnMatchActivity.this, MatchesFoundActivity.class);
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
            // DialogUtils.showToast(UnMatchActivity.this, getResources().getString(R.string.err_network));
            new SnackBar.Builder(UnMatchActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_imgvw:
                finish();
                break;
            case R.id.spam_ll:
                changeList(mSpatmTv);
                break;
            case R.id.no_reason_ll:
                changeList(mNoResonTv);
                break;
            case R.id.others_ll:
                changeList(mOtherTv);
                break;
            case R.id.bad_offline_behaviour_ll:
                changeList(mBadBehTv);
                break;
            case R.id.inappropriate_msg_ll:
                changeList(mImsgTv);

                break;
            case R.id.inappropriate_photo_ll:
                changeList(mIphotoTv);
                break;
            case R.id.unmatch_tv:
                unmatchApi();
                break;
        }
    }

    public void changeList(TextView tv) {
        mSelectedList.set(0, mSelectedList.get(1));
        mSelectedList.set(1, tv);

        mSelectedList.get(0).setTextColor(getResources().getColor(R.color.colorDarkText));
        mSelectedList.get(1).setTextColor(getResources().getColor(R.color.colorPrimary));
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
