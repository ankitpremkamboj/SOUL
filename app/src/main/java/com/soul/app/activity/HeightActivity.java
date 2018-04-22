package com.soul.app.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.adapter.InterestRecycleViewAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.InterestListRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import io.apptik.widget.MultiSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HeightActivity extends BaseActivity {

    FrameLayout edit_profile_header;
    TextView mHeight_min_tv, mHeight_max_tv;
    private MultiSlider mHeightSeekBar;
    private ImageView backIcon;
    private TextView editProfileDone;
    String max, min;


    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_height;
    }

    @Override
    public void initUi() {
        edit_profile_header = (FrameLayout) findViewById(R.id.edit_profile_header);
        edit_profile_header.setVisibility(View.VISIBLE);
        TextView editProfile = (TextView) findViewById(R.id.eph_edit_profile);
        editProfile.setText(R.string.your_height);
        mHeight_min_tv = (TextView) findViewById(R.id.height_min_tv);
        mHeight_max_tv = (TextView) findViewById(R.id.height_max_tv);
        mHeightSeekBar = (MultiSlider) findViewById(R.id.height_seekbar);

        mHeightSeekBar.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (thumbIndex == 0) {
                    mHeight_min_tv.setText(value + " cm");
                    //mHeightSeekBar.getThumb(0).setValue(value);
                    min = String.valueOf(value);
                } else {
                    mHeight_max_tv.setText(value + "cm");
                    max = String.valueOf(value);
                    //  mHeightSeekBar.getThumb(1).setValue(value);

                }
            }
        });

        backIcon = (ImageView) findViewById(R.id.eph_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editProfileDone = (TextView) findViewById(R.id.eph_done);
        editProfileDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  userInterestApi();
                Intent intent = new Intent();

                intent.putExtra(Constants.EXTRA_MIN_HEIGHT, min);
                intent.putExtra(Constants.EXTRA_MAX_HEIGHT, max);
                setResult(Constants.RESULT_HEIGHT, intent);

                finish();

            }
        });
        //  int minAge = Integer.valueOf(response.getMin_age());
        //int maxAge = Integer.valueOf(response.getMax_age());
        //mHeightSeekBar.getThumb(0).setValue(minAge);
        //mHeightSeekBar.getThumb(1).setValue(maxAge);


        try {
            if (getIntent() != null) {
                String maxHeight = getIntent().getStringExtra(Constants.EXTRA_MAX_HEIGHT);
                String minHeight = getIntent().getStringExtra(Constants.EXTRA_MIN_HEIGHT);
                if (minHeight != null) {
                    mHeight_min_tv.setText(minHeight + " cm");
                } else {
                    mHeight_min_tv.setText("0" + " cm");
                    minHeight = "0";
                }

                if (maxHeight != null) {
                    mHeight_max_tv.setText(maxHeight + " cm");
                } else {
                    mHeight_max_tv.setText("0" + " cm");
                    maxHeight = "0";
                }


                mHeightSeekBar.getThumb(1).setValue(Integer.valueOf(maxHeight));
                mHeightSeekBar.getThumb(0).setValue(Integer.valueOf(minHeight));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return HeightActivity.class.getName();
    }


}

