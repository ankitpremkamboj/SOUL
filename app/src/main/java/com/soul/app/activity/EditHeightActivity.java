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


public class EditHeightActivity extends BaseActivity {

    FrameLayout edit_profile_header;
    TextView height_tv;
    private MultiSlider mHeightSeekBar;
    private ImageView backIcon;
    private TextView editProfileDone;


    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_edit_height;
    }

    @Override
    public void initUi() {
        edit_profile_header = (FrameLayout) findViewById(R.id.edit_profile_header);
        edit_profile_header.setVisibility(View.VISIBLE);
        TextView editProfile = (TextView) findViewById(R.id.eph_edit_profile);
        editProfile.setText(R.string.your_height);
        height_tv = (TextView) findViewById(R.id.height_tv);
        mHeightSeekBar = (MultiSlider) findViewById(R.id.height_seekbar);

        mHeightSeekBar.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if (thumbIndex == 0) {
                    height_tv.setText(value + "");
                    //mHeightSeekBar.getThumb(0).setValue(value);

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

                String maxHeight = height_tv.getText().toString();
                intent.putExtra(Constants.EXTRA_MAX_HEIGHT, maxHeight);
                setResult(Constants.RESULT_HEIGHT, intent);

                finish();

            }
        });


        try {
            if (getIntent() != null) {
                String minHeight = getIntent().getStringExtra(Constants.EXTRA_MAX_HEIGHT);
                if (minHeight != null) {
                    height_tv.setText(minHeight);
                }
                mHeightSeekBar.getThumb(0).setValue(Integer.valueOf(minHeight));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return null;
    }


}

