package com.soul.app.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.constants.AppConstant;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Constants;
import com.soul.app.utils.FasterAnimationsContainer;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.flurry.android.FlurryAgent;

/*
*
* For Find the Match with Animation
* */

public class MatchActivity extends BaseActivity {

    private static final int ANIMATION_INTERVAL = 1;// 200ms
    private final int[] IMAGE_RESOURCES = {R.drawable.comp1_00014, R.drawable.comp1_00015, R.drawable.comp1_00016,
            R.drawable.comp1_00017, R.drawable.comp1_00018, R.drawable.comp1_00019,
            R.drawable.comp1_00020, R.drawable.comp1_00021, R.drawable.comp1_00022, R.drawable.comp1_00023, R.drawable.comp1_00024,
            R.drawable.comp1_00025, R.drawable.comp1_00026, R.drawable.comp1_00027, R.drawable.comp1_00028, R.drawable.comp1_00029,
            R.drawable.comp1_00030, R.drawable.comp1_00031, R.drawable.comp1_00032,
            R.drawable.comp1_00033, R.drawable.comp1_00034, R.drawable.comp1_00035, R.drawable.comp1_00036, R.drawable.comp1_00037, R.drawable.comp1_00038,
            R.drawable.comp1_00039, R.drawable.comp1_00040, R.drawable.comp1_00041,
            R.drawable.comp1_00042, R.drawable.comp1_00043, R.drawable.comp1_00044,
            R.drawable.comp1_00045, R.drawable.comp1_00046, R.drawable.comp1_00047, R.drawable.comp1_00048, R.drawable.comp1_00049, R.drawable.comp1_00050,
            R.drawable.comp1_00051, R.drawable.comp1_00052, R.drawable.comp1_00053, R.drawable.comp1_00054, R.drawable.comp1_00055, R.drawable.comp1_00056,
            R.drawable.comp1_00057, R.drawable.comp1_00058, R.drawable.comp1_00059, R.drawable.comp1_00060, R.drawable.comp1_00061, R.drawable.comp1_00062, R.drawable.comp1_00062, R.drawable.comp1_00063, R.drawable.comp1_00064,
            R.drawable.comp1_00065, R.drawable.comp1_00066, R.drawable.comp1_00067,
            R.drawable.comp1_00068, R.drawable.comp1_00069, R.drawable.comp1_00070, R.drawable.comp1_00071, R.drawable.comp1_00072, R.drawable.comp1_00073, R.drawable.comp1_00074, R.drawable.comp1_00075,
            R.drawable.comp1_00076, R.drawable.comp1_00077, R.drawable.comp1_00078, R.drawable.comp1_00079, R.drawable.comp1_00080, R.drawable.comp1_00081, R.drawable.comp1_00082, R.drawable.comp1_00083, R.drawable.comp1_00084,
            R.drawable.comp1_00085, R.drawable.comp1_00086, R.drawable.comp1_00087, R.drawable.comp1_00088, R.drawable.comp1_00089, R.drawable.comp1_00090, R.drawable.comp1_00091, R.drawable.comp1_00092, R.drawable.comp1_00093, R.drawable.comp1_00094, R.drawable.comp1_00095,
            R.drawable.comp1_00096, R.drawable.comp1_00097, R.drawable.comp1_00098, R.drawable.comp1_00099, R.drawable.comp1_00100, R.drawable.comp1_00101, R.drawable.comp1_00102, R.drawable.comp1_00103, R.drawable.comp1_00104, R.drawable.comp1_00105, R.drawable.comp1_00106,
            R.drawable.comp1_00107, R.drawable.comp1_00108, R.drawable.comp1_00109, R.drawable.comp1_00110, R.drawable.comp1_00111, R.drawable.comp1_00112, R.drawable.comp1_00113, R.drawable.comp1_00114, R.drawable.comp1_00115, R.drawable.comp1_00116, R.drawable.comp1_00117,

            R.drawable.comp1_00118, R.drawable.comp1_00119, R.drawable.comp1_00120, R.drawable.comp1_00121, R.drawable.comp1_00122, R.drawable.comp1_00123, R.drawable.comp1_00124, R.drawable.comp1_00125, R.drawable.comp1_00126, R.drawable.comp1_00127, R.drawable.comp1_00128,
            R.drawable.comp1_00129, R.drawable.comp1_00130, R.drawable.comp1_00131, R.drawable.comp1_00131, R.drawable.comp1_00132, R.drawable.comp1_00133, R.drawable.comp1_00134, R.drawable.comp1_00135, R.drawable.comp1_00136, R.drawable.comp1_00137, R.drawable.comp1_00138,
            R.drawable.comp1_00139, R.drawable.comp1_00140, R.drawable.comp1_00141, R.drawable.comp1_00142, R.drawable.comp1_00143, R.drawable.comp1_00144, R.drawable.comp1_00145, R.drawable.comp1_00146, R.drawable.comp1_00147, R.drawable.comp1_00148, R.drawable.comp1_00149,
            R.drawable.comp1_00150, R.drawable.comp1_00151, R.drawable.comp1_00152, R.drawable.comp1_00153, R.drawable.comp1_00154, R.drawable.comp1_00155, R.drawable.comp1_00156, R.drawable.comp1_00157, R.drawable.comp1_00158, R.drawable.comp1_00159, R.drawable.comp1_00160,
            R.drawable.comp1_00161, R.drawable.comp1_00162, R.drawable.comp1_00163, R.drawable.comp1_00164, R.drawable.comp1_00165, R.drawable.comp1_00166, R.drawable.comp1_00167, R.drawable.comp1_00168, R.drawable.comp1_00169, R.drawable.comp1_00170, R.drawable.comp1_00171,
            R.drawable.comp1_00172, R.drawable.comp1_00173, R.drawable.comp1_00174, R.drawable.comp1_00175, R.drawable.comp1_00176, R.drawable.comp1_00177, R.drawable.comp1_00178, R.drawable.comp1_00179, R.drawable.comp1_00180, R.drawable.comp1_00181, R.drawable.comp1_00182,
            R.drawable.comp1_00183, R.drawable.comp1_00184, R.drawable.comp1_00185, R.drawable.comp1_00185, R.drawable.comp1_00186, R.drawable.comp1_00187, R.drawable.comp1_00188};
    public boolean isFormPush = false;
    FasterAnimationsContainer mFasterAnimationsContainer;
    // private final int mAnimBreak = 250;
    private String mOtherId = "";
    private String mOtherName = "";
    private String mOtherPic = "";
    private TextView sendMsgTv;
    private ImageView matchedAnimation;
    private TextView liketxt;


    @Override
    public void initUi() {
        mOtherId = getIntent().getStringExtra(Constants.EXTRA_OTHER_ID);
        mOtherName = getIntent().getStringExtra(Constants.EXTRA_NAME);
        mOtherPic = getIntent().getStringExtra(Constants.EXTRA_USER_PROFILE);
        liketxt = (TextView) findViewById(R.id.liketxt);

        //  String name = liketxt.getText().toString();

        // String newString = name.replace("Ben", "ankit");
        // liketxt.setText(newString);

        RelativeLayout send_message = (RelativeLayout) findViewById(R.id.send_message);
        sendMsgTv = (TextView) findViewById(R.id.send_msg_tv);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentChat = new Intent(MatchActivity.this, ChatActivity.class);
                Intent intentChat = new Intent(MatchActivity.this, SocketChatActivity.class);
                intentChat.putExtra(PrefUtils.OTHER_ID, mOtherId);
                intentChat.putExtra(Constants.EXTRA_OTHER_NAME, mOtherName);
                intentChat.putExtra(Constants.EXTRA_OTHER_PIC, mOtherPic);
                intentChat.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.NORMAL_TYPE);
                startActivity(intentChat);
                finish();
            }
        });

        ImageView imgLeft = (ImageView) findViewById(R.id.left_img);
        ImageView imgRight = (ImageView) findViewById(R.id.right_img);

        //  final pl.droidsonroids.gif.GifImageView frontImgVw = (GifImageView) findViewById(R.id.fron_gif_imgvw);
        //mOtherName="Ankit";
        String name = "You and" + " " + mOtherName + " like each other, you can \n now send a message";
        // FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_MATCHES);

        ((TextView) findViewById(R.id.liketxt)).setText(name);
        String userImage = PrefUtils.getSharedPrefString(this, PrefUtils.USER_PIC);
        if (!TextUtils.isEmpty(userImage)) {
            userImage = userImage.replace("http", "https");
        }
        // Picasso.with(this).load(userImage).placeholder(R.drawable.big_pic_placeholder).into(imgLeft);
        //Picasso.with(this).load(mOtherPic).placeholder(R.drawable.big_pic_placeholder).into(imgRight);

        Utility.glide(MatchActivity.this, imgLeft, R.drawable.big_pic_placeholder, userImage);
        Utility.glide(MatchActivity.this, imgRight, R.drawable.big_pic_placeholder, mOtherPic);

        moveViewToScreenCenter(imgLeft, imgRight);
//        try {
//            final GifDrawable gifFromResource = new GifDrawable(getResources(), R.raw.front);
//            frontImgVw.setImageDrawable(gifFromResource);
//            gifFromResource.start();
//
//
//            gifFromResource.addAnimationListener(new AnimationListener() {
//                @Override
//                public void onAnimationCompleted(int loopNumber) {
//                    frontImgVw.setImageDrawable(gifFromResource);
//                    gifFromResource.reset();
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        matchedAnimation = (ImageView) findViewById(R.id.matched_animation);
        mFasterAnimationsContainer = FasterAnimationsContainer.getInstance(matchedAnimation);
        mFasterAnimationsContainer.addAllFrames(IMAGE_RESOURCES, ANIMATION_INTERVAL);
        mFasterAnimationsContainer.start();

        RelativeLayout keep_swiping = (RelativeLayout) findViewById(R.id.keep_swiping);

        keep_swiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchActivity.this, SwipingActivity.class));
                finish();
            }
        });

        FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_MATCHES);
    }


    private void moveViewToScreenCenter(View viewLeft, View viewRight) {
        FrameLayout root = (FrameLayout) findViewById(R.id.root_frame);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int originalPos[] = new int[2];
        viewLeft.getLocationOnScreen(originalPos);
        viewRight.getLocationOnScreen(originalPos);

        int xDest = dm.widthPixels / 2;
        xDest -= (viewLeft.getMeasuredWidth() / 2);
        int yDest = dm.heightPixels / 2 - (viewLeft.getMeasuredHeight() / 2) - statusBarOffset;

        int xRDest = dm.widthPixels / 2;
        xRDest -= (viewRight.getMeasuredWidth() / 2);
        int yRDest = dm.heightPixels / 2 - (viewLeft.getMeasuredHeight() / 2) - statusBarOffset;

        TranslateAnimation animLeft = new TranslateAnimation(yDest - originalPos[1], (xDest / 4 - originalPos[0]) / 5, 0, 0);
        animLeft.setDuration(1000);
        animLeft.setFillAfter(true);
        viewLeft.startAnimation(animLeft);

        TranslateAnimation animRight = new TranslateAnimation(xRDest - originalPos[0], (yRDest / 4 - originalPos[1]) / 5, 0, 0);
        animRight.setDuration(1000);
        animRight.setFillAfter(true);
        viewRight.startAnimation(animRight);


    }


    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.actvity_match1;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFasterAnimationsContainer != null) {
            mFasterAnimationsContainer = FasterAnimationsContainer
                    .getInstance(matchedAnimation);
            mFasterAnimationsContainer.addAllFrames(IMAGE_RESOURCES,
                    ANIMATION_INTERVAL);
            mFasterAnimationsContainer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFasterAnimationsContainer.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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