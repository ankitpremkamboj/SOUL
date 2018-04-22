package com.soul.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Utility;

/*  Created by ashishkumar .
* If User did not found any match then show this screen
* */

public class NoMatchesActivity extends com.soul.app.activity.BaseActivity {

    FrameLayout matchHeader;
    private ImageView homeIcon;
    private ImageView profile_icon;
    private ImageView mh_chat_icon;
    private TextView chat_tv, home_tv, profile_tv;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_no_matches;
    }

    @Override
    public void initUi() {

        matchHeader = (FrameLayout) findViewById(R.id.match_header);
        matchHeader.setVisibility(View.VISIBLE);


        chat_tv = (TextView) findViewById(R.id.chat_tv);
        chat_tv.setTextColor(Color.WHITE);

        home_tv = (TextView) findViewById(R.id.home_tv);
        home_tv.setTextColor(ContextCompat.getColor(this, R.color.header_text_color));


        profile_tv = (TextView) findViewById(R.id.profile_tv);
        profile_tv.setTextColor(ContextCompat.getColor(this, R.color.header_text_color));

        homeIcon = (ImageView) findViewById(R.id.mh_home_icon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoMatchesActivity.this, SwipingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile_icon = (ImageView) findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoMatchesActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();            }
        });

        mh_chat_icon = (ImageView) findViewById(R.id.mh_chat_icon);


    }

    @Override
    public String getName() {
        return NoMatchesActivity.class.getName();
    }
}
