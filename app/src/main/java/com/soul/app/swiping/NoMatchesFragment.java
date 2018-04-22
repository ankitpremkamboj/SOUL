package com.soul.app.swiping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.HomeFindingPeopleActivity;
import com.soul.app.activity.MyProfileActivity;
import com.soul.app.activity.NoMatchesActivity;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.Apis;

/**
 * Created by ankit on 1/13/2018.
 */

public class NoMatchesFragment extends Fragment {
    private View view;
    private FrameLayout matchHeader;
    private ImageView homeIcon;
    private ImageView profile_icon;
    private ImageView mh_chat_icon;
    private TextView chat_tv, home_tv, profile_tv;
    private Apis mApis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_no_matches, container, false);
        initUi();
        mApis = new ApiClient().getApis();

        // setFont();
        return view;
    }

    public void initUi() {

        matchHeader = (FrameLayout)view. findViewById(R.id.match_header);
        matchHeader.setVisibility(View.VISIBLE);


        chat_tv = (TextView) view.findViewById(R.id.chat_tv);
        chat_tv.setTextColor(Color.WHITE);

        home_tv = (TextView)view. findViewById(R.id.home_tv);
        home_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.header_text_color));


        profile_tv = (TextView) view.findViewById(R.id.profile_tv);
        profile_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.header_text_color));

        homeIcon = (ImageView)view. findViewById(R.id.mh_home_icon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SwipingActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        profile_icon = (ImageView)view. findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
               // finish();
                  }
        });

        mh_chat_icon = (ImageView) view.findViewById(R.id.mh_chat_icon);


    }
}
