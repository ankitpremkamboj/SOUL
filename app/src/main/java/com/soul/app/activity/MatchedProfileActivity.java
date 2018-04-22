package com.soul.app.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.HFPChatReportActivity;
import com.soul.app.adapter.SlidingImageAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.CirclePageIndicator;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.LikeDislikeRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.OtherProfileRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.utils.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 *
* */

public class MatchedProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "matchedProfile";
    public static ImageView sDislikeBottom;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ArrayList<String> profilePic = new ArrayList<>();
    private SlidingImageAdapter adapter;
    private String instagram = "";
    private RelativeLayout instagramRl;
    private TextView mLastSeenTv;
    private FrameLayout mProfilePicFl;

    private RelativeLayout likeDislikeBottom;
    // private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView backIcon;
    private ImageView infoIcon;
    private ImageView likeBottom;
    private  TextView heights_tv;
    //  private NestedScrollView nestedScrollMatchedProfile;
    private String mOtherId;


    public void initUi() {

        mOtherId = getIntent().getStringExtra(Constants.EXTRA_OTHER_ID);

        instagramRl = (RelativeLayout) findViewById(R.id.instagram_rl);
        instagramRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instagram.equals("") || instagram.equals(null)) {

                } else {
                    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                        String url = "https://www.instagram.com/" + instagram + "/";
                        Uri uri = Uri.parse(url);
                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                        likeIng.setPackage("com.instagram.android");
                        try {
                            startActivity(likeIng);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url)));
                        }
                    }
                }
            }
        });

        mLastSeenTv = (TextView) findViewById(R.id.time_ago_tv);
        backIcon = (ImageView) findViewById(R.id.mph_back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPager = (ViewPager) findViewById(R.id.pager);
        adapter = new SlidingImageAdapter(this, profilePic);
        mPager.setAdapter(adapter);

        // Show the panel
        Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        sDislikeBottom = (ImageView) findViewById(R.id.dislike_bottom);
        sDislikeBottom.startAnimation(bottomUp);
        likeBottom = (ImageView) findViewById(R.id.like_bottom);
        likeBottom.startAnimation(bottomUp);
        likeBottom.setOnClickListener(this);
        sDislikeBottom.setOnClickListener(this);

        likeDislikeBottom = (RelativeLayout) findViewById(R.id.like_dislike_bottom);
        likeDislikeBottom.startAnimation(bottomUp);
        String likeAndDislike = getIntent().getStringExtra(Constants.HIDE_LIKE_AND_DISLIKE);
        if (likeAndDislike.equals("0")) { // for visible bottom
            likeDislikeBottom.setVisibility(View.VISIBLE);
        } else { //// for hide bottom
            likeDislikeBottom.setVisibility(View.GONE);
        }

/*        toolbar = (Toolbar) findViewById(R.id.toolbar_matched_profile);
        nestedScrollMatchedProfile = (NestedScrollView) findViewById(R.id.nested_scroll_matched_profile);
        nestedScrollMatchedProfile.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //Scroll DOWN
//                    if (scrollY > oldScrollY) {
//                        toolbar.setBackgroundResource(android.R.color.transparent);
//                    }
//
//                    //Scroll UP
//                    if (scrollY < oldScrollY) {
//                        toolbar.setBackgroundResource(R.drawable.header_shadow);
//                    }
                //TOP SCROLL
                if (scrollY == 0) {
                    toolbar.setBackgroundResource(R.drawable.header_shadow);
                }
                //BOTTOM SCROLL
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    toolbar.setBackgroundResource(android.R.color.transparent);
                }
            }
        });*/

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
        otherProfileApi(mOtherId);

        infoIcon = (ImageView) findViewById(R.id.info_icon_iv);
        infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(infoIcon);
            }
        });


    }

    public void showPopup(ImageView iv) {
        PopupMenu popup = new PopupMenu(this, iv);
        popup.getMenuInflater().inflate(R.menu.hfp_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {
                    case R.id.report:
                        intent = new Intent(MatchedProfileActivity.this, HFPChatReportActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
                        startActivity(intent);
                        break;
              /*      case R.id.unmatch:
                        intent = new Intent(MatchedProfileActivity.this, HFPUnMatchActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
                        startActivity(intent);
                        break;*/
                }

                return true;
            }
        });

        popup.show();
    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_matched_profile;
    }

    @Override
    public String getName() {
        return null;
    }


    private void otherProfileApi(String otherId) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MatchedProfileActivity.this, PrefUtils.USER_ID));
            generalReq.setOther_id(otherId);
            Call<ObjResp<OtherProfileRes>> call = mApis.otherProfile(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<OtherProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<OtherProfileRes>> call, Response<ObjResp<OtherProfileRes>> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {

                        Log.e("response", response.toString());
                        setValue(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ObjResp<OtherProfileRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(MatchedProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void setValue(OtherProfileRes otherProfileRes) {
        String name = otherProfileRes.getProfileDetails().getUser_name() + ", ";
        Log.e("Name", name);
        String distance = "";
        String aboutMe = otherProfileRes.getProfileDetails().getAbout_text();
        String lastSeenTimeStamp = otherProfileRes.getProfileDetails().getLastActivity_time();
        String height=otherProfileRes.getProfileDetails().getHeight();
        long currentTime = System.currentTimeMillis() / 1000L;
        long seenTime = Long.valueOf(lastSeenTimeStamp);
        long diffTime = currentTime - seenTime;
        long secTime = diffTime;
        String lastSeen;

        instagram = otherProfileRes.getProfileDetails().getInstagram();
        //if (instagram.equals("") || instagram.equals(null)) {
        if (TextUtils.isEmpty(instagram)) {
            findViewById(R.id.instagram_rl).setVisibility(View.GONE);
        } else {
            findViewById(R.id.instagram_rl).setVisibility(View.VISIBLE);
        }


        Double dis = Double.valueOf(otherProfileRes.getProfileDetails().getDistance());
        distance = dis.intValue() + " Miles";
       /* if (secTime > 3600 * 24 * 7) {
            //long year = secTime / (3600 * 24 * 365);
            mLastSeenTv.setText("> a week ago");
        } else if (secTime > 3600 * 24) {
            long day = secTime / (3600 * 24);
            mLastSeenTv.setText(day + " d ago");
        } else if (secTime > 3600) {
            long hrTime = secTime / 3600;
            mLastSeenTv.setText(hrTime + " h ago");

        } else if (secTime > 60) {
            long minTime = secTime / 60;
            mLastSeenTv.setText(minTime + " m ago");
        } else {
            mLastSeenTv.setText(secTime + " s ago");
        }*/


        String interest = "";
        String work = otherProfileRes.getProfileDetails().getWork();
        String edu = otherProfileRes.getProfileDetails().getEducation();
        String loc = otherProfileRes.getProfileDetails().getLocation();
        ((TextView) findViewById(R.id.name_tv)).setText(name);
        ((TextView) findViewById(R.id.age_tv)).setText(otherProfileRes.getProfileDetails().getAge());
        ((TextView) findViewById(R.id.distance_tv)).setText(distance + "\n away");
        if (!TextUtils.isEmpty(aboutMe)) {
            ((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).setText(aboutMe);
            // findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
            // findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
        }else {
            ((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).setText("N/A");
        }

        if (!TextUtils.isEmpty(height)) {
            ((TextView) findViewById(R.id.heights_tv)).setText(height+" cm");
            // findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
            // findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
        }else {
            ((TextView) findViewById(R.id.heights_tv)).setText("N/A");
        }

        for (int i = 0; i < otherProfileRes.getInterests().size(); i++) {
            interest = interest + otherProfileRes.getInterests().get(i).getCategory_name();
            if (i < otherProfileRes.getInterests().size() - 1) {
                interest = interest + ", ";
            }
        }
        if (!TextUtils.isEmpty(interest)) {
            ((TextView) findViewById(R.id.my_full_profile_interest_tv)).setText(interest);
        } else {
            ((TextView) findViewById(R.id.my_full_profile_interest_tv)).setText("N/A");
            // findViewById(R.id.interest_rl).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getStatus())) {
            ((TextView) findViewById(R.id.my_full_profile_outlook_tv)).setText(otherProfileRes.getProfileDetails().getStatus());
        } else {
            ((TextView) findViewById(R.id.my_full_profile_outlook_tv)).setText("N/A");

            // findViewById(R.id.outlook_rl).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getDenomination())) {
            ((TextView) findViewById(R.id.my_full_profile_sect_tv)).setText(otherProfileRes.getProfileDetails().getDenomination());
        } else {
            ((TextView) findViewById(R.id.my_full_profile_sect_tv)).setText("N/A");
            //  findViewById(R.id.sect_rl).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(work)) {
            ((TextView) findViewById(R.id.my_full_profile_work_tv)).setText(work);
            mLastSeenTv.setText(work);
        } else {
            ((TextView) findViewById(R.id.my_full_profile_work_tv)).setText("N/A");
            // findViewById(R.id.work_rl).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(edu)) {
            ((TextView) findViewById(R.id.my_full_profile_education_tv)).setText(edu);
        } else {
            ((TextView) findViewById(R.id.my_full_profile_education_tv)).setText("N/A");
            // findViewById(R.id.edu_rl).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(loc)) {
            ((TextView) findViewById(R.id.my_full_profile_location_tv)).setText(loc);
        } else {
            ((TextView) findViewById(R.id.my_full_profile_location_tv)).setText("N/A");
            // findViewById(R.id.location_rl).setVisibility(View.GONE);
        }

        profilePic.clear();
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getProfile_pic()))
            profilePic.add(otherProfileRes.getProfileDetails().getProfile_pic());
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getUser_image1()))
            profilePic.add(otherProfileRes.getProfileDetails().getUser_image1());
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getUser_image2()))
            profilePic.add(otherProfileRes.getProfileDetails().getUser_image2());
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getUser_image3()))
            profilePic.add(otherProfileRes.getProfileDetails().getUser_image3());
        if (!TextUtils.isEmpty(otherProfileRes.getProfileDetails().getUser_image4()))
            profilePic.add(otherProfileRes.getProfileDetails().getUser_image4());

        adapter.notifyDataSetChanged();


    }

    void callLikeDislike(String isMatch) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MatchedProfileActivity.this, PrefUtils.USER_ID));
            generalReq.setOther_id(mOtherId);
            generalReq.setIs_match(isMatch);
            Call<LikeDislikeRes> call = mApis.userLikeDislike(generalReq);
            call.enqueue(new Callback<LikeDislikeRes>() {

                @Override
                public void onResponse(Call<LikeDislikeRes> call, Response<LikeDislikeRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //DialogUtils.showToast(MatchedProfileActivity.this, response.body().getMsg());
                            setResult(Constants.LIKEDISLIKE);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeDislikeRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(MatchedProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    public void onClick(View v) {
        final int[] number_of_clicks = {0};
        final boolean[] thread_started = {false};
        final int DELAY_BETWEEN_CLICKS_IN_MILLISECONDS = 250;
        switch (v.getId()) {
            case R.id.like_bottom:
                callLikeDislike("1");
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_RIGHTCARDSWIPE);
                break;
            case R.id.dislike_bottom:
                callLikeDislike("0");
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_LEFTCARDSWIPE);
                break;


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

}
