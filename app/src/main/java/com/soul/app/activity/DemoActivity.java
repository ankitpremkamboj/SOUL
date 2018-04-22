package com.soul.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.utils.Constants;
import com.soul.app.utils.Utility;

import io.apptik.widget.MultiSlider;


public class DemoActivity extends BaseActivity {

    FrameLayout edit_profile_header;
    TextView mHeight_min_tv, mHeight_max_tv;
    private MultiSlider mHeightSeekBar;
    private ImageView backIcon;
    private TextView editProfileDone;


    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.actvity_match1;
    }

    @Override
    public void initUi() {
       /* edit_profile_header = (FrameLayout) findViewById(R.id.edit_profile_header);
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
                    mHeight_min_tv.setText(value + "");
                    //mHeightSeekBar.getThumb(0).setValue(value);

                } else {
                    mHeight_max_tv.setText(value + "");
                    // mHeightSeekBar.getThumb(1).setValue(value);

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

                *//*int maxHeight = mHeightSeekBar.getThumb(0).getValue();
                int minHeight = mHeightSeekBar.getThumb(1).getValue();
                intent.putExtra(Constants.EXTRA_MIN_HEIGHT, String.valueOf(maxHeight));
                intent.putExtra(Constants.EXTRA_MAX_HEIGHT, String.valueOf(minHeight));*//*
                String maxHeight = mHeight_max_tv.getText().toString();
                String minHeight = mHeight_min_tv.getText().toString();
                intent.putExtra(Constants.EXTRA_MIN_HEIGHT, maxHeight);
                intent.putExtra(Constants.EXTRA_MAX_HEIGHT, minHeight);
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
                    mHeight_min_tv.setText(minHeight);
                } else if (maxHeight != null) {
                    mHeight_max_tv.setText(maxHeight);
                }


                mHeightSeekBar.getThumb(0).setValue(Integer.valueOf(maxHeight));
                mHeightSeekBar.getThumb(1).setValue(Integer.valueOf(minHeight));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public String getName() {
        return null;
    }


}

