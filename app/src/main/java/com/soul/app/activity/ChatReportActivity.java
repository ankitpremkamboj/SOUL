package com.soul.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.utils.Constants;
import com.soul.app.utils.Utility;

/*
* Chat Report
* */
public class ChatReportActivity extends com.soul.app.activity.BaseActivity implements View.OnClickListener {
    LinearLayout mInappLl, mSpamLl, mOtherLl;
    ImageView mCloseImg;
    String mOtherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_chat_report;
    }

    @Override
    public void initUi() {
        mOtherId = getIntent().getStringExtra(Constants.EXTRA_OTHER_ID);
        mInappLl = (LinearLayout) findViewById(R.id.inappropriate_ll);
        mSpamLl = (LinearLayout) findViewById(R.id.spam_ll);
        mOtherLl = (LinearLayout) findViewById(R.id.others_ll);
        mCloseImg = (ImageView) findViewById(R.id.close_imgvw);
        mCloseImg.setOnClickListener(this);
        mOtherLl.setOnClickListener(this);
        mSpamLl.setOnClickListener(this);
        mInappLl.setOnClickListener(this);

    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(ChatReportActivity.this, SubmitReportActivity.class);
        intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
        switch (v.getId()) {

            case R.id.inappropriate_ll:
                intent.putExtra(Constants.EXTRA_REPORT_REASON, Constants.INAP_MSG);
                startActivity(intent);
                break;
            case R.id.spam_ll:
                intent.putExtra(Constants.EXTRA_REPORT_REASON, Constants.SPAM);
                startActivity(intent);
                break;
            case R.id.others_ll:
                intent.putExtra(Constants.EXTRA_REPORT_REASON, Constants.OTHER);
                startActivity(intent);

                break;
            case R.id.close_imgvw:
                finish();
                break;
        }
        finish();
    }
}
