package com.soul.app.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.EditProfileActivity;
import com.soul.app.adapter.SlidingImageAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.customui.CirclePageIndicator;
import com.soul.app.models.res.UserProfileRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.Utility;

import java.util.ArrayList;

 /*
*  For User FullProfile Activity
 */

public class MyFullProfileActivity extends BaseActivity {


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private String instagram = "";
    // CollapsingToolbarLayout collapsingToolbarLayout;
    //LinearLayout viewPagerInflate;
    private ImageView fullProfileBackIcon;
    private TextView fullProfileEdit;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private SlidingImageAdapter mSlidingImageAdapter;
    private UserProfileRes userProfile;
    private RelativeLayout instagramRl;


    //  private Toolbar toolbar;
    //  private NestedScrollView nestedScrollView;
    // private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_my_full_profile;
    }

    @Override
    public void initUi() {
        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.black));


        userProfile = (UserProfileRes) getIntent().getSerializableExtra(Constants.EXTRA_USER_PROFILE);
        fullProfileBackIcon = (ImageView) findViewById(R.id.fpp_back_icon);
        fullProfileBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fullProfileEdit = (TextView) findViewById(R.id.fpp_edit);
        fullProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(MyFullProfileActivity.this, EditProfileActivity.class);
                intentEditProfile.putExtra(Constants.EXTRA_USER_PROFILE, userProfile);
                startActivity(intentEditProfile);
                finish();
            }
        });


        mPager = (ViewPager) findViewById(R.id.pager);

        mSlidingImageAdapter = new SlidingImageAdapter(this, ImagesArray);
        mPager.setAdapter(mSlidingImageAdapter);

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

        setUi(userProfile);
    }

    @Override
    public String getName() {
        return MyFullProfileActivity.class.getName();
    }

    private void setUi(UserProfileRes userProfile) {
        if (userProfile != null) {
            int age = Integer.valueOf(userProfile.getProfileDetails().getAge());
            if (age > 1900) {
                age = age - 1900;
            }
            String name = userProfile.getProfileDetails().getUser_name() + ", " + age;
            String profilePic = userProfile.getProfileDetails().getProfile_pic();
            String image1 = userProfile.getProfileDetails().getUser_image1();
            String image2 = userProfile.getProfileDetails().getUser_image2();
            String image3 = userProfile.getProfileDetails().getUser_image3();
            String image4 = userProfile.getProfileDetails().getUser_image4();
            String aboutme = userProfile.getProfileDetails().getAbout_text();
            String work = userProfile.getProfileDetails().getWork();
            String edu = userProfile.getProfileDetails().getEducation();
            String loc = userProfile.getProfileDetails().getHometown();
            String height = userProfile.getProfileDetails().getHeight();


                instagram = userProfile.getProfileDetails().getInstagram();
            if (TextUtils.isEmpty(instagram)) {
                findViewById(R.id.instagram_rl).setVisibility(View.GONE);
            } else {
                findViewById(R.id.instagram_rl).setVisibility(View.VISIBLE);
            }

            String interest = "";
            ImagesArray.add(profilePic);
            if (!TextUtils.isEmpty(image1)) {
                ImagesArray.add(image1);
            }
            if (!TextUtils.isEmpty(image2)) {
                ImagesArray.add(image2);
            }
            if (!TextUtils.isEmpty(image3)) {
                ImagesArray.add(image3);
            }
            if (!TextUtils.isEmpty(image4)) {
                ImagesArray.add(image4);
            }

            mSlidingImageAdapter.notifyDataSetChanged();

            ((TextView) findViewById(R.id.name_tv)).setText(name);

            if (!TextUtils.isEmpty(aboutme)) {
                ((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).setText(aboutme);
            } else {
                findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
                // findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(height)) {
                ((TextView) findViewById(R.id.heights_tv)).setText(height+" cm");
            } else {
                ((TextView) findViewById(R.id.heights_tv)).setText("N/A");

                //findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
                // findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
            }

            for (int i = 0; i < userProfile.getInterests().size(); i++) {
                interest = interest + userProfile.getInterests().get(i).getCategory_name();
                if (i < userProfile.getInterests().size() - 1) {
                    interest = interest + ", ";
                }
            }
            if (!TextUtils.isEmpty(interest)) {
                ((TextView) findViewById(R.id.my_full_profile_interest_tv)).setText(interest);
            } else {
                findViewById(R.id.interest_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(userProfile.getProfileDetails().getStatus())) {
                findViewById(R.id.outlook_rl).setVisibility(View.GONE);

                // ((TextView) findViewById(R.id.my_full_profile_outlook_tv)).setText(userProfile.getProfileDetails().getStatus());
            } else {
                findViewById(R.id.outlook_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(userProfile.getProfileDetails().getDenomination())) {
                findViewById(R.id.sect_rl).setVisibility(View.GONE);
                // ((TextView) findViewById(R.id.my_full_profile_sect_tv)).setText(userProfile.getProfileDetails().getDenomination());
            } else {
                findViewById(R.id.sect_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(work)) {
                ((TextView) findViewById(R.id.my_full_profile_work_tv)).setText(work);
            } else {
                findViewById(R.id.work_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(edu)) {
                ((TextView) findViewById(R.id.my_full_profile_education_tv)).setText(edu);
            } else {
                findViewById(R.id.edu_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(loc)) {
                ((TextView) findViewById(R.id.my_full_profile_location_tv)).setText(loc);
            } else {
                findViewById(R.id.location_rl).setVisibility(View.GONE);
            }
        }
    }
}
