package com.soul.app.activity;

import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.google.android.gms.maps.GoogleMap;
import com.soul.app.R;
import com.soul.app.adapter.CardSwipeAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.tindercard.SwipeFlingAdapterView;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ChatConversationRes;
import com.soul.app.models.res.GetSettingRes;
import com.soul.app.models.res.LikeDislikeRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.UserListRes;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.retrofit.Apis;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
*  Finding Other Users Home Screen
* */

public class HomeFindingPeopleActivity extends BaseGpsActivity {

    List<UserListRes.DataBean> mData = new ArrayList<UserListRes.DataBean>();
    List<UserListRes.DataBean> mDataUndo = new ArrayList<UserListRes.DataBean>();

    GetSettingRes getSettingRes;
    private TextView mNameTv, mAgeTv, mDistanceTv;//, mProfileTv;
    private FrameLayout frameLayoutHomeHeader;
    //  private ScrollView homeNoCardLeftSv;
    //  private RelativeLayout findingPeopleHomeRl;
    private ImageView chatIcon;
    private ImageView findingPeopleAnimation;
    private ImageView profileIcon;
    private TextView mFindingPotentialMatch;
    // private ArrayList<String> al;
    // private int imageCount = 0;
    private CardSwipeAdapter cardSwAdapter;
    private SwipeFlingAdapterView flingContainer;
    //private RelativeLayout frameUp;
    private FrameLayout frameB, frameUp;
    private RelativeLayout findingPeopleHome;
    private ImageView mLeftArrowImg, mRightArrowImg;
    private int rightCount = 0;
    //for maintain user image on left right arrow
    private ArrayList<String> mUserImgList;
    private boolean mLikeDislikeFlag = true;
    private Location mLocation;
    private TextView txt_name, txt_designation;
    private ImageView likeBottom;
    private ImageView sDislikeBottom;
    private ImageView img_undo;
    int childPos;
    private boolean isLike = false;
    private ImageView swapedImageView;
    String userId;
    private ImageView img_undo_nocard;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_home_finding_people;
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatConvApi();
        if (mLikeDislikeFlag) {
            userListApi();
        }

        if (mUserImgList != null) {
            if (mUserImgList.size() > 1) {
                mRightArrowImg.setVisibility(View.VISIBLE);
            }
        }
        //  AppEventsLogger.activateApp(this);
    }

    @Override
    public void initUi() {
        if (Utility.isGpsOn(HomeFindingPeopleActivity.this)) {
            Log.d("GPS", "ON");
        } else {
            Utility.showSettingsAlert(HomeFindingPeopleActivity.this);
        }
        deviceInfoForFlurry();
        mUserImgList = new ArrayList<String>();
        mFindingPotentialMatch = (TextView) findViewById(R.id.finding_potential_match_tv);
        Animation bottomUpText = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up_text);
        mFindingPotentialMatch.setAnimation(bottomUpText);
        img_undo_nocard = (ImageView) findViewById(R.id.img_undo_nocard);
        mLeftArrowImg = (ImageView) findViewById(R.id.left_arrow_img);
        mRightArrowImg = (ImageView) findViewById(R.id.right_arrow_img);
        frameLayoutHomeHeader = (FrameLayout) findViewById(R.id.home_header_frame_layout);
        findingPeopleHome = (RelativeLayout) findViewById(R.id.finding_people_home_rl);
        findingPeopleHome.setVisibility(View.VISIBLE);
        findingPeopleAnimation = (ImageView) findViewById(R.id.finding_people_animation_iv);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(findingPeopleAnimation);
        Glide.with(HomeFindingPeopleActivity.this).load(R.raw.soul_logo).into(imageViewTarget);
        // frameUp = (RelativeLayout) findViewById(R.id.image_ll);
        frameUp = (FrameLayout) findViewById(R.id.image_ll);


        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame_sfav);
//        frameUp = (FrameLayout) findViewById(R.id.image_ll);
        frameB = (FrameLayout) findViewById(R.id.no_image);
        img_undo = (ImageView) findViewById(R.id.img_undo);

        img_undo_nocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("undo list size=" + mDataUndo.size(), "child" + childPos);
                if (mDataUndo.size() != 0) {
                    frameUp.setVisibility(View.VISIBLE);
                    frameB.setVisibility(View.GONE);
                    isLike = true;
                    final UserListRes.DataBean deletedItem = mDataUndo.get(childPos);
                    mData.add(childPos, deletedItem);
                    mDataUndo.remove(deletedItem);
                    flingContainer.removeAllViewsInLayout();
                    cardSwAdapter = new CardSwipeAdapter(mData, HomeFindingPeopleActivity.this);
                    flingContainer.setAdapter(cardSwAdapter);
                    cardSwAdapter.notifyDataSetChanged();
                    cardSwAdapter.notifyDataSetInvalidated();
                }
            }
        });

        img_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("undo list size=" + mDataUndo.size(), "child" + childPos);
                if (mDataUndo.size() != 0) {
                    isLike = true;
                    final UserListRes.DataBean deletedItem = mDataUndo.get(childPos);
                    mData.add(childPos, deletedItem);
                    mDataUndo.remove(deletedItem);
                    flingContainer.removeAllViewsInLayout();
                    cardSwAdapter = new CardSwipeAdapter(mData, HomeFindingPeopleActivity.this);
                    flingContainer.setAdapter(cardSwAdapter);
                    cardSwAdapter.notifyDataSetChanged();
                    cardSwAdapter.notifyDataSetInvalidated();
                }
            }
        });

        frameUp.setVisibility(View.GONE);
        frameLayoutHomeHeader.setVisibility(View.VISIBLE);
        mNameTv = (TextView) findViewById(R.id.name_tv);
        mAgeTv = (TextView) findViewById(R.id.age_tv);
        mDistanceTv = (TextView) findViewById(R.id.distance_tv);
        profileIcon = (ImageView) findViewById(R.id.hh_profile_icon);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_designation = (TextView) findViewById(R.id.txt_designation);
        likeBottom = (ImageView) findViewById(R.id.like_bottom);
        sDislikeBottom = (ImageView) findViewById(R.id.dislike_bottom);
        sDislikeBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_LEFTCARDSWIPE);

                //dislike=0

                // callLikeDislike("0");

                img_undo.setVisibility(View.VISIBLE);
                userId = mData.get(0).getUser_id();

                CountDownTimer timer2 = new CountDownTimer(4000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        img_undo.setVisibility(View.GONE);
                        img_undo_nocard.setVisibility(View.GONE);//(or GONE)
                        if (isLike) {
                            isLike = false;
                        } else {
                            callLikeDislike("0");
                        }
                    }

                }.start();

                if (!mDataUndo.isEmpty()) {
                    mDataUndo.remove(childPos);
                }
                mDataUndo.add(mData.get(childPos));
                mData.remove(childPos);
                if (mData.size() == 0) {
                    img_undo_nocard.setVisibility(View.VISIBLE);
                    frameUp.setVisibility(View.GONE);
                    frameB.setVisibility(View.VISIBLE);
                } else {
                    img_undo_nocard.setVisibility(View.GONE);

                    setProfileInfo(mData);
                    Log.e("User Id", mData.get(0).getUser_id());
                    // Log.e("User Name", mData.get(0).getUser_name());
                }
                mUserImgList.clear();
                if (mData != null) {
                    if (mData.size() > 0) {
                        if (!TextUtils.isEmpty(mData.get(0).getProfile_pic())) {
                            mUserImgList.add(mData.get(0).getProfile_pic());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image1())) {
                            mUserImgList.add(mData.get(0).getUser_image1());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image2())) {
                            mUserImgList.add(mData.get(0).getUser_image2());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image3())) {
                            mUserImgList.add(mData.get(0).getUser_image3());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image4())) {
                            mUserImgList.add(mData.get(0).getUser_image4());
                        }
                    }
                }

                cardSwAdapter.notifyDataSetChanged();
            }
        });
        likeBottom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_RIGHTCARDSWIPE);
                // like=1
                // callLikeDislike("1");


                img_undo.setVisibility(View.VISIBLE);
                userId = mData.get(0).getUser_id();

                CountDownTimer timer2 = new CountDownTimer(4000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        img_undo.setVisibility(View.GONE); //(or GONE)
                        img_undo_nocard.setVisibility(View.GONE);
                        if (isLike) {
                            isLike = false;
                        } else {
                            callLikeDislike("1");
                        }
                    }
                }.start();


                if (!mDataUndo.isEmpty()) {
                    mDataUndo.remove(childPos);
                }
                mDataUndo.add(mData.get(childPos));
                mData.remove(childPos);
                if (mData.size() == 0) {
                    frameUp.setVisibility(View.GONE);
                    frameB.setVisibility(View.VISIBLE);
                    img_undo_nocard.setVisibility(View.VISIBLE);

                } else {
                    img_undo_nocard.setVisibility(View.GONE);

                    setProfileInfo(mData);
                    Log.e("User Id", mData.get(0).getUser_id());
                    // Log.e("User Name", mData.get(0).getUser_name());
                }
                mUserImgList.clear();
                if (mData != null) {
                    if (mData.size() > 0) {
                        if (!TextUtils.isEmpty(mData.get(0).getProfile_pic())) {
                            mUserImgList.add(mData.get(0).getProfile_pic());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image1())) {
                            mUserImgList.add(mData.get(0).getUser_image1());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image2())) {
                            mUserImgList.add(mData.get(0).getUser_image2());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image3())) {
                            mUserImgList.add(mData.get(0).getUser_image3());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image4())) {
                            mUserImgList.add(mData.get(0).getUser_image4());
                        }
                    }
                }

                cardSwAdapter.notifyDataSetChanged();
            }
        });


        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    Intent intentMyProfile = new Intent(HomeFindingPeopleActivity.this, MyProfileActivity.class);
                  // overridePendingTransition(R.anim.left,R.anim.right);
                    startActivity(intentMyProfile);
                } else {
                    new SnackBar.Builder(HomeFindingPeopleActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        chatIcon = (ImageView) findViewById(R.id.hh_chat_icon);
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chatIcon.setEnabled(false);
                userMatchApi();

            }
        });

       /* FrameLayout mProfilell = (FrameLayout) findViewById(R.id.profile_lin_layout);
        if (mProfilell != null) {
            mProfilell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                            Intent intent = new Intent(HomeFindingPeopleActivity.this, com.soul.app.activity.MatchedProfileActivity.class);
                            intent.putExtra(Constants.EXTRA_OTHER_ID, mData.get(0).getUser_id());
                            Log.e("User Id", mData.get(0).getUser_id());
                            intent.putExtra(Constants.HIDE_LIKE_AND_DISLIKE, "0");  //0 for show
                            startActivityForResult(intent, Constants.LIKEDISLIKE);
                        } else {
                            new SnackBar.Builder(HomeFin,dingPeopleActivity.this)
                                    .withMessage(getResources().getString(R.string.err_network)).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/

        TextView settingTv = (TextView) findViewById(R.id.setting_tv);
        if (settingTv != null) {
            settingTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                        Intent intentSettings = new Intent(HomeFindingPeopleActivity.this, SettingsActivity.class);
                        intentSettings.putExtra(AppConstant.SETTINGS_STATUS, "0");
                        startActivity(intentSettings);
                        getSettingApi();
                    } else {
                        new SnackBar.Builder(HomeFindingPeopleActivity.this)
                                .withMessage(getResources().getString(R.string.err_network)).show();
                    }
                }
            });
        }
        RelativeLayout inviteFrndBtn = (RelativeLayout) findViewById(R.id.invite_frnd_btn);
        if (inviteFrndBtn != null)
            inviteFrndBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                        // Add data to the intent, the receiving app will decide
                        // what to do with it.
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Soul Dating app");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Soul Dating is a wonderful application. Please download to find your best match \n" +
                                "https://play.google.com/store/apps/details?id=com.soul.app");
                        startActivity(Intent.createChooser(sharingIntent, "Share link!"));
                    } else {
                        new SnackBar.Builder(HomeFindingPeopleActivity.this)
                                .withMessage(getResources().getString(R.string.err_network)).show();
                    }
                }
            });
        //call api
        //setSwipeviewTemp();
        setSwipeview();

    }

    //  Device info for flurry account salaam swipe
    private void deviceInfoForFlurry() {
        try {
            String deviceInfo = "android info :";
            //  deviceInfo += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
            deviceInfo += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
            deviceInfo += "\n Device: " + android.os.Build.DEVICE;
            deviceInfo += "\n Model (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
            FlurryAgent.logEvent(deviceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSwipeview() {
       /* flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame_sfav);
//        frameUp = (FrameLayout) findViewById(R.id.image_ll);
        frameB = (FrameLayout) findViewById(R.id.no_image);
*/
        mData.clear();
        for (int i = 0; i < 10; i++) {

            UserListRes.DataBean db = new UserListRes.DataBean();
            // userLis.set(i,db).setProfile_pic("http://soulsmatcher.com//soulapp//assets//profileimages//2016-04-28WqHprofile_pic.png");
            // UserListRes.DataBean db = new UserListRes.DataBean();
            db.setProfile_pic("http://soulsmatcher.com//soulapp//assets//profileimages//2016-04-28WqHprofile_pic.png");
            mData.add(db);

        }

        // mData=userLis;
        cardSwAdapter = new CardSwipeAdapter(mData, this);
        flingContainer.setAdapter(cardSwAdapter);
        childPos = flingContainer.getChildCount();
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_LEFTCARDSWIPE);

                img_undo.setVisibility(View.VISIBLE);
                userId = mData.get(0).getUser_id();

                CountDownTimer timer2 = new CountDownTimer(4000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        img_undo.setVisibility(View.GONE); //(or GONE)
                        img_undo_nocard.setVisibility(View.GONE);
                        if (isLike) {
                            isLike = false;
                        } else {
                            callLikeDislike("0");
                        }
                    }
                }.start();


                Log.e("swap Userid", "" + mData.get(childPos).getUser_id());
                if (!mDataUndo.isEmpty()) {
                    mDataUndo.remove(childPos);
                }
                mDataUndo.add(mData.get(childPos));
                mData.remove(childPos);
                if (mData.size() == 0) {
                    frameUp.setVisibility(View.GONE);
                    frameB.setVisibility(View.VISIBLE);
                    img_undo_nocard.setVisibility(View.VISIBLE);

                } else {
                    img_undo_nocard.setVisibility(View.GONE);

                    setProfileInfo(mData);
                }
                mUserImgList.clear();
                if (mData != null) {
                    if (mData.size() > 0) {
                        if (!TextUtils.isEmpty(mData.get(0).getProfile_pic())) {
                            mUserImgList.add(mData.get(0).getProfile_pic());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image1())) {
                            mUserImgList.add(mData.get(0).getUser_image1());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image2())) {
                            mUserImgList.add(mData.get(0).getUser_image2());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image3())) {
                            mUserImgList.add(mData.get(0).getUser_image3());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image4())) {
                            mUserImgList.add(mData.get(0).getUser_image4());
                        }
                    }
                }
                cardSwAdapter.notifyDataSetChanged();

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_CARDSWIPE);
                FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_RIGHTCARDSWIPE);
                img_undo.setVisibility(View.VISIBLE);

                userId = mData.get(0).getUser_id();


                CountDownTimer timer2 = new CountDownTimer(4000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (isLike) {
                            Log.e("time", "test");
                        }
                    }

                    @Override
                    public void onFinish() {
                        img_undo.setVisibility(View.GONE); //(or GONE)
                        img_undo_nocard.setVisibility(View.GONE);
                        // callLikeDislike("1");
                        if (isLike) {
                            isLike = false;
                            Log.e("time", "callLikeDislike");
                        } else {
                            callLikeDislike("1");
                        }
                    }
                }.start();

                if (!mDataUndo.isEmpty()) {
                    mDataUndo.remove(childPos);
                }
                mDataUndo.add(mData.get(childPos));
                mData.remove(childPos);
                if (mData.size() == 0) {
                    frameUp.setVisibility(View.GONE);
                    frameB.setVisibility(View.VISIBLE);
                    img_undo_nocard.setVisibility(View.VISIBLE);
                } else {
                    img_undo_nocard.setVisibility(View.GONE);
                    setProfileInfo(mData);
                    // Log.e("User Name", mData.get(0).getUser_name());
                }
                mUserImgList.clear();
                if (mData != null) {
                    if (mData.size() > 0) {
                        if (!TextUtils.isEmpty(mData.get(0).getProfile_pic())) {
                            mUserImgList.add(mData.get(0).getProfile_pic());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image1())) {
                            mUserImgList.add(mData.get(0).getUser_image1());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image2())) {
                            mUserImgList.add(mData.get(0).getUser_image2());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image3())) {
                            mUserImgList.add(mData.get(0).getUser_image3());
                        }
                        if (!TextUtils.isEmpty(mData.get(0).getUser_image4())) {
                            mUserImgList.add(mData.get(0).getUser_image4());
                        }
                    }
                }

                cardSwAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                    mRightArrowImg.setVisibility(View.GONE);
                    mLeftArrowImg.setVisibility(View.GONE);
                    if ((scrollProgressPercent == 0) && mUserImgList.size() > 1) {
                        if (mUserImgList.size() == rightCount) {
                            mLeftArrowImg.setVisibility(View.VISIBLE);
                            mRightArrowImg.setVisibility(View.GONE);

                        } else if (rightCount == 0) {
                            mLeftArrowImg.setVisibility(View.GONE);
                            mRightArrowImg.setVisibility(View.VISIBLE);
                        } else {
                            mLeftArrowImg.setVisibility(View.VISIBLE);
                            mRightArrowImg.setVisibility(View.VISIBLE);
                        }
                        if (mUserImgList.size() == 2) {
                            if (rightCount == 1) {
                                mLeftArrowImg.setVisibility(View.VISIBLE);
                                mRightArrowImg.setVisibility(View.GONE);
                            } else {
                                mLeftArrowImg.setVisibility(View.GONE);
                                mRightArrowImg.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override

            public void onScrollUp(Object dataObject) {
//                        Integer pos = (Integer) dataObject;
//                        if (imageCount < 3) {
//                            Glide.with(HomeFindingPeopleActivity.this).load(mLMap.get(pos + 1).get(imageCount)).into(cardSwAdapter.imgView);
//                            ++imageCount;
//                        }

            }

            @Override
            public void onScrollDown(Object dataObject) {
//                        Integer pos = (Integer) dataObject;
//                        if (imageCount > 0) {
//                            Glide.with(HomeFindingPeopleActivity.this).load(mLMap.get(pos + 1).get(imageCount)).into(cardSwAdapter.imgView);
//                            --imageCount;
//                        }


            }

        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {

                    View view = flingContainer.getSelectedView();
                    Intent intent = new Intent(HomeFindingPeopleActivity.this, MatchedProfileActivity.class);
                    intent.putExtra(Constants.EXTRA_OTHER_ID, mData.get(0).getUser_id());
                    Log.e("User Id", mData.get(0).getUser_id());
                    intent.putExtra(Constants.HIDE_LIKE_AND_DISLIKE, "0"); // 0 for show
                    startActivityForResult(intent, Constants.LIKEDISLIKE);


                } else {
                    new SnackBar.Builder(HomeFindingPeopleActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

    }

    @Override
    public String getName() {
        return HomeFindingPeopleActivity.class.getName();
    }


    public void showHideView(View prevFilledView, View prevBlankView, View currBlankView, View currFilledView) {
        prevFilledView.setVisibility(View.GONE);
        prevBlankView.setVisibility(View.VISIBLE);
        currBlankView.setVisibility(View.GONE);
        currFilledView.setVisibility(View.VISIBLE);
    }

    public void showHideViewDown(View prevFilledView, View prevBlankView, View currBlankView, View currFilledView) {
        prevFilledView.setVisibility(View.VISIBLE);
        prevBlankView.setVisibility(View.GONE);
        currBlankView.setVisibility(View.VISIBLE);
        currFilledView.setVisibility(View.GONE);
    }


    public void userListApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            findingPeopleHome.setVisibility(View.VISIBLE);
            // showProgressDialog(true);
            frameB.setVisibility(View.GONE);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(HomeFindingPeopleActivity.this, PrefUtils.USER_ID));
            Call<UserListRes> call = mApis.getUserList(generalReq);
            call.enqueue(new Callback<UserListRes>() {

                @Override
                public void onResponse(Call<UserListRes> call, Response<UserListRes> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData().size() > 0) {
                                //  showProgressDialog(false);
                                // mLikeDislikeFlag=false;
                                // mLikeDislikeFlag = false;

                                mData.clear();

                                mData.addAll(response.body().getData());
                                setProfileInfo(mData);
                                cardSwAdapter.notifyDataSetChanged();
                                ((CardSwipeAdapter) flingContainer.getAdapter()).notifyDataSetChanged();
                                findingPeopleHome.setVisibility(View.GONE);
                                frameB.setVisibility(View.GONE);
                                frameUp.setVisibility(View.VISIBLE);
                                // setSwipeview(response.body().getData());

                                mUserImgList.clear();
                                if (!TextUtils.isEmpty(mData.get(0).getProfile_pic())) {
                                    mUserImgList.add(mData.get(0).getProfile_pic());
                                }
                                if (!TextUtils.isEmpty(mData.get(0).getUser_image1())) {
                                    mUserImgList.add(mData.get(0).getUser_image1());
                                }
                                if (!TextUtils.isEmpty(mData.get(0).getUser_image2())) {
                                    mUserImgList.add(mData.get(0).getUser_image2());
                                }
                                if (!TextUtils.isEmpty(mData.get(0).getUser_image3())) {
                                    mUserImgList.add(mData.get(0).getUser_image3());
                                }
                                if (!TextUtils.isEmpty(mData.get(0).getUser_image4())) {
                                    mUserImgList.add(mData.get(0).getUser_image4());
                                }
                                if (mUserImgList.size() > 1) {
                                    mRightArrowImg.setVisibility(View.VISIBLE);

                                }
                            } else {
                                frameUp.setVisibility(View.GONE);
                                frameB.setVisibility(View.VISIBLE);
                                findingPeopleHome.setVisibility(View.GONE);
                            }

                            handleLeftRightClick();
                        }


                    } else {
                        // showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<UserListRes> call, Throwable t) {
                    //showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(HomeFindingPeopleActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }


    void setProfileInfo(List<UserListRes.DataBean> userList) {
        try {
            if (userList != null)
                if (userList.size() > 0) {

                    ///  setSwipeview(userList);
                    String name = userList.get(0).getUser_name() + ", ";
                    String designation = userList.get(0).getWork();
                    String edu = userList.get(0).getEducation();
                    // mNameTv.setText(name);
                    txt_name.setText(name);
                    txt_designation.setText(edu);
                    mAgeTv.setText(userList.get(0).getAge());
                    String distance = userList.get(0).getDistance();
                    Double dis = Double.valueOf(userList.get(0).getDistance());
                    distance = dis.intValue() + " Miles";
                    try {

                        mDistanceTv.setText(distance);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                   /* if (!TextUtils.isEmpty(designation)) {
                        ((TextView) findViewById(R.id.txt_designation)).setText(designation);
                    } else if (!TextUtils.isEmpty(edu)) {
                        ((TextView) findViewById(R.id.txt_designation)).setText(edu);
                    } else {
                        findViewById(R.id.txt_designation).setVisibility(View.INVISIBLE);
                    }*/


                   /* if (!TextUtils.isEmpty(designation)) {
                        ((TextView) findViewById(R.id.designation_tv)).setText(designation);
                    } else if (!TextUtils.isEmpty(edu)) {
                        ((TextView) findViewById(R.id.designation_tv)).setText(edu);
                    } else {
                        findViewById(R.id.designation_tv).setVisibility(View.INVISIBLE);
                    }*/

                    LinearLayout linearLayout = (LinearLayout) ((FrameLayout) flingContainer.getSelectedView()).getChildAt(0);
                    FrameLayout frameLayout = (FrameLayout) linearLayout.getChildAt(0);
                    final ImageView imageView = (ImageView) frameLayout.getChildAt(0);
                    cardSwAdapter.notifyDataSetInvalidated();

                    try {

                        Glide.with(HomeFindingPeopleActivity.this).load(userList.get(0).getProfile_pic()).into(imageView);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    cardSwAdapter.notifyDataSetChanged();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callLikeDislike(String isMatch) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            // showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(HomeFindingPeopleActivity.this, PrefUtils.USER_ID));
            generalReq.setOther_id(userId);
            generalReq.setIs_match(isMatch);
            Call<LikeDislikeRes> call = mApis.userLikeDislike(generalReq);
            call.enqueue(new Callback<LikeDislikeRes>() {


                @Override
                public void onResponse(Call<LikeDislikeRes> call, Response<LikeDislikeRes> response) {
                    //   showProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            isLike = false;
                            //DialogUtils.showToast(HomeFindingPeopleActivity.this,response.body().getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LikeDislikeRes> call, Throwable t) {
                    // showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(HomeFindingPeopleActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void userMatchApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(HomeFindingPeopleActivity.this, PrefUtils.USER_ID));
            Call<ListResp<UserMatchesRes>> call = mApis.getUserMatches(generalReq);
            //showProgressDialog(true);
            call.enqueue(new Callback<ListResp<UserMatchesRes>>() {


                @Override
                public void onResponse(Call<ListResp<UserMatchesRes>> call, Response<ListResp<UserMatchesRes>> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData().size() > 0) {
                                Intent intent = new Intent(HomeFindingPeopleActivity.this, MatchesFoundActivity.class);
                                intent.putParcelableArrayListExtra(Constants.EXTRA_USER_MATCH, (ArrayList<? extends Parcelable>) response.body().getData());
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(HomeFindingPeopleActivity.this, NoMatchesActivity.class);
                                startActivity(intent);
                            }
                            //  showProgressDialog(false);
                            chatIcon.setEnabled(true);
                        }
                    } else {
                        // showProgressDialog(false);
                        chatIcon.setEnabled(true);
                        new SnackBar.Builder(HomeFindingPeopleActivity.this)
                                .withMessage(getResources().getString(R.string.please_try_again)).show();
                    }
                }

                @Override
                public void onFailure(Call<ListResp<UserMatchesRes>> call, Throwable t) {
                    //  showProgressDialog(false);
                    chatIcon.setEnabled(true);
                }
            });
        } else {
            new SnackBar.Builder(HomeFindingPeopleActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void handleLeftRightClick() {

        if (mUserImgList.size() > 0) {
            if (mUserImgList.size() == 2) {
                mRightArrowImg.setVisibility(View.VISIBLE);
                mLeftArrowImg.setVisibility(View.GONE);
            } else if (mUserImgList.size() == 1) {
                mRightArrowImg.setVisibility(View.GONE);
                mLeftArrowImg.setVisibility(View.GONE);
            }

        }

        mRightArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) ((FrameLayout) flingContainer.getSelectedView()).getChildAt(0);
                FrameLayout frameLayout = (FrameLayout) linearLayout.getChildAt(0);
                final ImageView imageView = (ImageView) frameLayout.getChildAt(0);

                if (mUserImgList.size() > rightCount) {
                    ++rightCount;

                    mLeftArrowImg.setVisibility(View.VISIBLE);
                    cardSwAdapter.notifyDataSetChanged();
                    cardSwAdapter.notifyDataSetInvalidated();
                    try {
                        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.big_pic_placeholder));
                        // Utility.glide(HomeFindingPeopleActivity.this,imageView,0,mUserImgList.get(rightCount));
                        String url = mUserImgList.get(rightCount);
                        if (url.contains("graph") || url.contains("facebook")) {
                            url = url.replace("http:", "https:");
                        }

                        Picasso.with(HomeFindingPeopleActivity.this).load(url).into(imageView);
                        // Utility.glide(HomeFindingPeopleActivity.this, imageView, 0, url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (rightCount == mUserImgList.size() - 1) {
                        mRightArrowImg.setVisibility(View.GONE);
                        mLeftArrowImg.setVisibility(View.VISIBLE);
                    } else {
                        mRightArrowImg.setVisibility(View.VISIBLE);
                    }
                } else {
                    mRightArrowImg.setVisibility(View.GONE);
                    mLeftArrowImg.setVisibility(View.VISIBLE);
                }


                //mData.set(0,data);
                //cardSwAdapter.notifyDataSetChanged();


            }
        });
        mLeftArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) ((FrameLayout) flingContainer.getSelectedView()).getChildAt(0);
                FrameLayout frameLayout = (FrameLayout) linearLayout.getChildAt(0);
                final ImageView imageView = (ImageView) frameLayout.getChildAt(0);

                if (rightCount > 0) {
                    if (mUserImgList.size() > 1)
                        --rightCount;
                    mRightArrowImg.setVisibility(View.VISIBLE);
                    cardSwAdapter.notifyDataSetChanged();
                    cardSwAdapter.notifyDataSetInvalidated();
                    try {
                        String url = mUserImgList.get(rightCount);
                        if (url.contains("graph") || url.contains("facebook")) {
                            url = url.replace("http:", "https:");
                        }

                        Picasso.with(HomeFindingPeopleActivity.this).load(url).into(imageView);
                        // Utility.glide(HomeFindingPeopleActivity.this, imageView, 0, url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (rightCount == 0) {
                        mLeftArrowImg.setVisibility(View.GONE);
                        mRightArrowImg.setVisibility(View.VISIBLE);
                    } else {
                        mLeftArrowImg.setVisibility(View.VISIBLE);
                    }
                } else {
                    mRightArrowImg.setVisibility(View.VISIBLE);
                    mLeftArrowImg.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.LIKEDISLIKE) {
            if (mData != null) {
                mLikeDislikeFlag = false;
                mData.remove(0);
                LinearLayout linearLayout = (LinearLayout) ((FrameLayout) flingContainer.getSelectedView()).getChildAt(0);
                FrameLayout frameLayout = (FrameLayout) linearLayout.getChildAt(0);
                final ImageView imageView = (ImageView) frameLayout.getChildAt(0);
                cardSwAdapter.notifyDataSetInvalidated();
                setProfileInfo(mData);
                try {
                    Glide.with(HomeFindingPeopleActivity.this).load(mData.get(0).getProfile_pic()).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cardSwAdapter.notifyDataSetChanged();
            }
        }
    }

    public void chatConvApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressBar(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(HomeFindingPeopleActivity.this, PrefUtils.USER_ID));
            Call<ListResp<ChatConversationRes>> call = mApis.chatConversation(generalReq);
            call.enqueue(new Callback<ListResp<ChatConversationRes>>() {
                @Override
                public void onResponse(Call<ListResp<ChatConversationRes>> call, Response<ListResp<ChatConversationRes>> response) {
                    showProgressBar(false);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            showChatIndicator(response.body().getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListResp<ChatConversationRes>> call, Throwable t) {
                    showProgressBar(false);
                }
            });
        }
    }

    // for settings data
    public void getSettingApi() {
    /*    if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(HomeFindingPeopleActivity.this, PrefUtils.USER_ID));
            Call<ListResp<GetSettingRes>> call = mApis.getSetting(generalReq);
            call.enqueue(new Callback<ListResp<GetSettingRes>>() {

                @Override
                public void onResponse(Call<ListResp<GetSettingRes>> call, Response<ListResp<GetSettingRes>> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        if (response.body().getData().size() > 0) {
                            // setUi(response.body().getData().get(0));
                            getSettingRes = response.body().getData().get(0);
                            Intent intentSettings = new Intent(HomeFindingPeopleActivity.this, SettingsActivity.class);
                            intentSettings.putExtra(AppConstant.SETTINGS_STATUS, "1");
                            intentSettings.putExtra(AppConstant.SETTINGS_DATA, getSettingRes);
                            startActivity(intentSettings);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListResp<GetSettingRes>> call, Throwable t) {
                    showProgressDialog(false);
                    t.printStackTrace();
                }
            });
        } else {
            new SnackBar.Builder(HomeFindingPeopleActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }*/
    }

    public void showChatIndicator(List<ChatConversationRes> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUnread_msg_count() > 0) {
                    chatIcon.setImageResource(R.drawable.message_indicator);
                    break;
                } else {
                    chatIcon.setImageResource(R.drawable.ic_chat);
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }

    @Override
    public void mapReady(GoogleMap googleMap) {

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        //   AppEventsLogger.deactivateApp(this);
    }
}

