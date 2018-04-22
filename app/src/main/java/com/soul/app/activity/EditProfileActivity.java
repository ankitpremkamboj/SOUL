package com.soul.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.YourSectActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ChangeProfileImagesRes;
import com.soul.app.models.res.DeleteImagesRes;
import com.soul.app.models.res.EditProfileRes;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UserImageRes;
import com.soul.app.models.res.UserProfileRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.soul.app.utils.msupport.MSupport;
import com.soul.app.utils.msupport.MSupportConstants;
import com.github.mrengineer13.snackbar.SnackBar;
import com.rahul.media.main.MediaFactory;
import com.rahul.media.model.Define;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
*  For Edit Profile Like Update About me and Instagram name.
*  Upload picture from facebook, gallery and camera.
* */

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    //   private static final int REQUEST_CAMERA = 200;
    private static final String TAG = "EditProfileActivity";
    public static int i = 0;
    public ArrayList<String> profilePicList = new ArrayList<>();
    public int clickCounter = 1;
    // ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<String> stringArrayListTemp = new ArrayList<>();
    ImageView imageAdd, setSelectedImages, deleteImage;
    String permissionSet[] = new String[]{MSupportConstants.CAMERA, MSupportConstants.WRITE_EXTERNAL_STORAGE};
    //  private RelativeLayout editProfileParent;
    MediaFactory mediaFactory;
    //   private String instagramName;
    private FrameLayout editProfileHeader;
    private TextView editProfileDone;
    private ImageView editProfileBackIcon;
    private ImageView mProfileImage;
    private ImageView editProfileImageOne;
    private ImageView editProfileImageTwo;
    private ImageView editProfileImageThree;
    private ImageView editProfileImageFour;

    private ImageView editDeleteProfileImageOne;
    private ImageView editDeleteProfileImageTwo;
    private ImageView editDeleteProfileImageThree;
    private ImageView editDeleteProfileImageFour;


    // private TextView myEditProfileInterestsTv, myEditProfileOutLookTv, myEditProfileSectTv;
    // private UserProfileRes userProfile;
    private ImageView imageAddPic1, imageAddPic2, imageAddPic3, imageAddPic4;
    private EditText instagramEditTextView;
    private TextView aboutMeTv;

    private RelativeLayout heights_rl;
    private ArrayList<String> mInterestList;
    //   private String instagram = "";
    //   private String aboutMe;
    private String mPath = "";
    private String height, academic;
    private SwitchCompat macademic_switch, mlocation_switch, mwork_switch, medu_switch;

    @Override
    public void initUi() {

        //   editProfileParent = (RelativeLayout) findViewById(R.id.edit_profile_parent);
        //  editProfileParent.setOnClickListener(this);

        mInterestList = new ArrayList<String>();

        MSupport.checkPermission(this, null, permissionSet, MSupportConstants.PERMISSIONS_REQUEST_CODE);
        //  userProfile = (UserProfileRes) getIntent().getSerializableExtra(Constants.EXTRA_USER_PROFILE);
        macademic_switch = (SwitchCompat) findViewById(R.id.academic_switch);
        mlocation_switch = (SwitchCompat) findViewById(R.id.location_switch);
        mwork_switch = (SwitchCompat) findViewById(R.id.work_switch);
        medu_switch = (SwitchCompat) findViewById(R.id.edu_switch);

        editProfileHeader = (FrameLayout) findViewById(R.id.edit_profile_header);
        editProfileHeader.setVisibility(View.VISIBLE);

        editProfileDone = (TextView) findViewById(R.id.eph_done);
        editProfileDone.setVisibility(View.GONE);

        editProfileDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.putStringValueInSharedPreference(EditProfileActivity.this, "instagramName",
                        instagramEditTextView.getText().toString());
                //  url = "https://www.instagram.com/" + instagramEditTextView.getText().toString() + "/";
                //instagramName = instagramEditTextView.getText().toString();
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    callEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                }
            }
        });

        editProfileBackIcon = (ImageView) findViewById(R.id.eph_back_icon);
        editProfileBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.putStringValueInSharedPreference(EditProfileActivity.this, "instagramName",      instagramEditTextView.getText().toString());
                //  url = "https://www.instagram.com/" + instagramEditTextView.getText().toString() + "/";
                //instagramName = instagramEditTextView.getText().toString();
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    callEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                }
                finish();
            }
        });

        RelativeLayout mInterests = (RelativeLayout) findViewById(R.id.interests_rl);
        RelativeLayout outLook = (RelativeLayout) findViewById(R.id.outlook_rl);
        RelativeLayout sect = (RelativeLayout) findViewById(R.id.sect_rl);
        sect.setVisibility(View.GONE);
        mInterests.setOnClickListener(this);
        outLook.setOnClickListener(this);
        sect.setOnClickListener(this);

        // profileImage = (ImageView) findViewById(R.id.profile_image_iv);
        mProfileImage = (ImageView) findViewById(R.id.profile_image_iv);

        editProfileImageOne = (ImageView) findViewById(R.id.image1);
        editProfileImageTwo = (ImageView) findViewById(R.id.image2);
        editProfileImageThree = (ImageView) findViewById(R.id.image3);
        editProfileImageFour = (ImageView) findViewById(R.id.image4);

        editDeleteProfileImageOne = (ImageView) findViewById(R.id.image_delete_pic_1);
        editDeleteProfileImageTwo = (ImageView) findViewById(R.id.image_delete_pic_2);
        editDeleteProfileImageThree = (ImageView) findViewById(R.id.image_delete_pic_3);
        editDeleteProfileImageFour = (ImageView) findViewById(R.id.image_delete_pic_4);

        instagramEditTextView = (EditText) findViewById(R.id.instagram_et);
        instagramEditTextView.setText(Utility.getStringSharedPreference(EditProfileActivity.this, "instagram"));

        aboutMeTv = (TextView) findViewById(R.id.aboutme_tv);

        heights_rl = (RelativeLayout) findViewById(R.id.heights_rl);
        heights_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHeight = new Intent(EditProfileActivity.this, EditHeightActivity.class);
                intentHeight.putExtra(Constants.EXTRA_MAX_HEIGHT, height);
                startActivityForResult(intentHeight, 5);
            }
        });


        imageAddPic1 = (ImageView) findViewById(R.id.image_add_pic_1);
        imageAddPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter = 1;
                imageAdd = imageAddPic1;
                deleteImage = editDeleteProfileImageOne;
                //UPDATE ONLY SELECTED IMAGE
                setSelectedImages = editProfileImageOne;
                selectImage();

            }
        });
        imageAddPic2 = (ImageView) findViewById(R.id.image_add_pic_2);
        imageAddPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter = 2;
                imageAdd = imageAddPic2;
                //UPDATE ONLY SELECTED IMAGE
                setSelectedImages = editProfileImageTwo;
                deleteImage = editDeleteProfileImageTwo;

                selectImage();
            }
        });
        imageAddPic3 = (ImageView) findViewById(R.id.image_add_pic_3);
        imageAddPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter = 3;
                imageAdd = imageAddPic3;
                //UPDATE ONLY SELECTED IMAGE
                setSelectedImages = editProfileImageThree;
                deleteImage = editDeleteProfileImageThree;

                selectImage();
            }
        });
        imageAddPic4 = (ImageView) findViewById(R.id.image_add_pic_4);
        imageAddPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter = 4;
                imageAdd = imageAddPic4;
                //UPDATE ONLY SELECTED IMAGE
                setSelectedImages = editProfileImageFour;
                deleteImage = editDeleteProfileImageFour;

                selectImage();
            }
        });

        editProfileImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic1.isEnabled()) {
                    stringArrayListTemp.add(0, profilePicList.get(0));
                    profilePicList.set(0, profilePicList.get(1));
                    profilePicList.set(1, stringArrayListTemp.get(0));
                    //      updateImageEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                    setImage();

                }
            }
        });
        // DELETE IMAGE
        editDeleteProfileImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic1.isEnabled()) {
                    String user_image = "user_image1";
                    deleteImage(1, "", imageAddPic1, editDeleteProfileImageOne, user_image);
                }
                //  return false;
            }
        });

        editProfileImageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic2.isEnabled()) {
                    stringArrayListTemp.add(0, profilePicList.get(0));
                    profilePicList.set(0, profilePicList.get(2));
                    profilePicList.set(2, stringArrayListTemp.get(0));
                    //    updateImageEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                    setImage();
                }
            }
        });
        // DELETE IMAGE
        editDeleteProfileImageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic2.isEnabled()) {
                    String user_image = "user_image2";
                    deleteImage(2, "", imageAddPic2, editDeleteProfileImageTwo, user_image);
                }
                //  return false;
            }
        });

        editProfileImageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic3.isEnabled()) {
                    stringArrayListTemp.add(0, profilePicList.get(0));
                    profilePicList.set(0, profilePicList.get(3));
                    profilePicList.set(3, stringArrayListTemp.get(0));
                    //  updateImageEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                    setImage();
                }
            }
        });
        //         // DELETE IMAGE
        editDeleteProfileImageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!imageAddPic3.isEnabled()) {
                    String user_image = "user_image3";
                    deleteImage(3, "", imageAddPic3, editDeleteProfileImageThree, user_image);
                }
                // return false;
            }
        });

        editProfileImageFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic4.isEnabled()) {
                    stringArrayListTemp.add(0, profilePicList.get(0));
                    profilePicList.set(0, profilePicList.get(4));
                    profilePicList.set(4, stringArrayListTemp.get(0));
                    //  updateImageEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
                    setImage();
                }
            }
        });
        // DELETE IMAGE
        editDeleteProfileImageFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageAddPic4.isEnabled()) {
                    String user_image = "user_image4";
                    deleteImage(4, "", imageAddPic4, editDeleteProfileImageFour, user_image);
                }
                // return false;
            }
        });
        profileApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileApiforInterest();
        // image source url is saved in pref in  imagegalleryactivity
        String sourceUrl = PrefUtils.getSharedPrefString(this, PrefUtils.PREF_FB_IMAGE);
        if (!TextUtils.isEmpty(sourceUrl)) {
            setSelectFbImageUri(sourceUrl);
            // setSelectImageUri(sourceUrl);
            // Utility.getRealPathFromURI(this, Uri.parse(sourceUrl));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
        Utility.putStringValueInSharedPreference(EditProfileActivity.this, "instagramName",
                instagramEditTextView.getText().toString());
        //  url = "https://www.instagram.com/" + instagramEditTextView.getText().toString() + "/";
        //instagramName = instagramEditTextView.getText().toString();
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            callEditProfileApi((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID)));
        }
        finish();
    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);

        return R.layout.activity_edit_profile;
    }

    // ProfilePicList
    private void checkProfilePicList(ArrayList<String> profilePicList) {

        if (profilePicList.get(1).equals("")) {
            imageAddPic1.setVisibility(View.VISIBLE);
            imageAddPic1.setEnabled(true);
            editDeleteProfileImageOne.setVisibility(View.INVISIBLE);

        } else {
            imageAddPic1.setVisibility(View.INVISIBLE);
            imageAddPic1.setEnabled(false);
            editDeleteProfileImageOne.setVisibility(View.VISIBLE);
        }

        if (profilePicList.get(2).equals("")) {
            imageAddPic2.setVisibility(View.VISIBLE);
            imageAddPic2.setEnabled(true);
            editDeleteProfileImageTwo.setVisibility(View.INVISIBLE);

        } else {
            imageAddPic2.setVisibility(View.INVISIBLE);
            imageAddPic2.setEnabled(false);
            editDeleteProfileImageTwo.setVisibility(View.VISIBLE);

        }

        if (profilePicList.get(3).equals("")) {
            imageAddPic3.setVisibility(View.VISIBLE);
            imageAddPic3.setEnabled(true);
            editDeleteProfileImageThree.setVisibility(View.INVISIBLE);

        } else {
            imageAddPic3.setVisibility(View.INVISIBLE);
            imageAddPic3.setEnabled(false);
            editDeleteProfileImageThree.setVisibility(View.VISIBLE);

        }

        if (profilePicList.get(4).equals("")) {
            imageAddPic4.setVisibility(View.VISIBLE);
            imageAddPic4.setEnabled(true);
            editDeleteProfileImageFour.setVisibility(View.INVISIBLE);

        } else {
            imageAddPic4.setVisibility(View.INVISIBLE);
            imageAddPic4.setEnabled(false);
            editDeleteProfileImageFour.setVisibility(View.VISIBLE);

        }
        setImage();
    }

    // SET IMAGE
    private void setImage() {
        try {
            if (profilePicList.size() > 0) {
                for (int i = 0; i < profilePicList.size(); i++) {
                    switch (i) {
                        case 0:
                            Utility.glide(EditProfileActivity.this, mProfileImage, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                        case 1:
                            Utility.glide(EditProfileActivity.this, editProfileImageOne, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                        case 2:
                            Utility.glide(EditProfileActivity.this, editProfileImageTwo, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                        case 3:
                            Utility.glide(EditProfileActivity.this, editProfileImageThree, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                        case 4:
                            Utility.glide(EditProfileActivity.this, editProfileImageFour, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                        case 5:
                            Utility.glide(EditProfileActivity.this, mProfileImage, R.drawable.rounded_edit_profile, profilePicList.get(i));
                            break;
                    }
                }
                updateHttpContainImagesApi();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return EditProfileActivity.class.getName();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.interests_rl:
                Intent intentEditInterests = new Intent(EditProfileActivity.this, EditInterestActivity.class);
                intentEditInterests.putStringArrayListExtra(Constants.EXTRA_INTEREST_LIST, mInterestList);
                startActivityForResult(intentEditInterests, 1);
                break;
            case R.id.outlook_rl:
                Intent outlookIntent = new Intent(EditProfileActivity.this, AcademicActivity.class);
                outlookIntent.putExtra(Constants.EXTRA_ACADEMIC, ((TextView) findViewById(R.id.my_edit_profile_outlook_tv)).getText());
                startActivityForResult(outlookIntent, 4);
                break;
            case R.id.sect_rl:
                Intent sectIntent = new Intent(EditProfileActivity.this, YourSectActivity.class);
                sectIntent.putExtra(Constants.EXTRA_SECT, ((TextView) findViewById(R.id.my_edit_profile_sect_tv)).getText());
                startActivityForResult(sectIntent, 1);
                break;
        }
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
            String aboutMe = userProfile.getProfileDetails().getAbout_text().trim();
            String work = userProfile.getProfileDetails().getWork();
            String work_status = userProfile.getProfileDetails().getWork_status();
            String education_status = userProfile.getProfileDetails().getEducation_status();
            String location_status = userProfile.getProfileDetails().getLocation_status();

            //String edu = userProfile.getProfileDetails().getEducation();
            String loc = userProfile.getProfileDetails().getHometown();
            height = userProfile.getProfileDetails().getHeight();
            academic = userProfile.getProfileDetails().getEducation();
            try {
                String instagram = userProfile.getProfileDetails().getInstagram().trim();
                // String[] instagramName = instagram.split("/");
                instagramEditTextView.setText(instagram);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String interest = "";
            profilePicList.clear();

            //if ((PrefUtils.getSharedPrefBoolean(EditProfileActivity.this, PrefUtils.PREF_INTEREST))) {
            //if ((PrefUtils.getSharedPrefBoolean(EditProfileActivity.this, PrefUtils.PREF_PROFILE_PIC))) {
            if ((PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.PREF_CATSELECTED)).equals("1")) {
                //if (!TextUtils.isEmpty(profilePic)) {
                profilePicList.add(0, profilePic);
                profilePicList.add(1, image1);
                profilePicList.add(2, image2);
                profilePicList.add(3, image3);
                profilePicList.add(4, image4);
            } else {
                // PrefUtils.setSharedPrefBooleanData(EditProfileActivity.this, PrefUtils.PREF_PROFILE_PIC, true);
                String userProfilePic = PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_PIC1);
                String user_Image1 = PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_PIC2);
                String user_Image2 = PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_PIC3);
                String user_Image3 = PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_PIC4);
                String user_Image4 = PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_PIC5);

                profilePicList.add(0, userProfilePic);
                profilePicList.add(1, user_Image1);
                profilePicList.add(2, user_Image2);
                profilePicList.add(3, user_Image3);
                profilePicList.add(4, user_Image4);

                PrefUtils.setSharedPrefStringData(EditProfileActivity.this, PrefUtils.PREF_CATSELECTED, "1");
            }
            checkProfilePicList(profilePicList);

            //    profilePicList.add(profilePic);

            ((TextView) findViewById(R.id.name_tv)).setText(name);

            if (!TextUtils.isEmpty(aboutMe)) {
                EditText txtAboutMe = (EditText) findViewById(R.id.my_full_profile_about_me_content_tv);

                String upperString = aboutMe.substring(0, 1).toUpperCase() + aboutMe.substring(1);
                txtAboutMe.setText(upperString);
                txtAboutMe.setSelection(txtAboutMe.getText().length());

                //((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).setText(aboutMe);

            } else {
               // EditText txtAboutMe = (EditText) findViewById(R.id.my_full_profile_about_me_content_tv);
                //txtAboutMe.setText("N/A");
//                findViewById(R.id.aboutme_ll).setVisibility(View.GONE);
//                findViewById(R.id.aboutme_rl).setVisibility(View.GONE);
            }
            mInterestList.clear();


            for (int i = 0; i < userProfile.getInterests().size(); i++) {
                mInterestList.add(userProfile.getInterests().get(i).getCategory_name());
                interest = interest + userProfile.getInterests().get(i).getCategory_name();
                if (i < userProfile.getInterests().size() - 1) {
                    interest = interest + ", ";
                }
            }

            if (work_status.equals("0")) {
                mwork_switch.setChecked(false);

            } else {
                mwork_switch.setChecked(true);

            }
            if (education_status.equals("0")) {
                macademic_switch.setChecked(false);
            } else {
                macademic_switch.setChecked(true);
            }
            if (location_status.equals("0")) {
                mlocation_switch.setChecked(false);
            } else {
                mlocation_switch.setChecked(true);
            }

            if (!TextUtils.isEmpty(interest)) {
                ((TextView) findViewById(R.id.my_edit_profile_interests_tv)).setText(interest);
            } else {
//                findViewById(R.id.interest_rl).setVisibility(View.GONE);
//                findViewById(R.id.interests_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(userProfile.getProfileDetails().getEducation())) {
                ((TextView) findViewById(R.id.my_edit_profile_outlook_tv)).setText(userProfile.getProfileDetails().getEducation());
            } else {
//                findViewById(R.id.outlook_rl).setVisibility(View.GONE);
//                findViewById(R.id.outlooks_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(userProfile.getProfileDetails().getDenomination())) {
                ((TextView) findViewById(R.id.my_edit_profile_sect_tv)).setText(userProfile.getProfileDetails().getDenomination());
            } else {
                ((TextView) findViewById(R.id.my_edit_profile_sect_tv)).setText("N/A");

//                findViewById(R.id.sect_rl).setVisibility(View.GONE);
//                findViewById(R.id.sects_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(work)) {
                ((TextView) findViewById(R.id.my_edit_work_education_tv)).setText(work);
            } else {
                ((TextView) findViewById(R.id.my_edit_work_education_tv)).setText("N/A");
                // findViewById(R.id.work_rl).setVisibility(View.GONE);
                //findViewById(R.id.works_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(academic)) {
                ((TextView) findViewById(R.id.my_edit_profile_education_tv)).setText(academic);
            } else {
                ((TextView) findViewById(R.id.my_edit_profile_education_tv)).setText(getString(R.string.select_academic));
                //findViewById(R.id.edu_rl).setVisibility(View.GONE);
                // findViewById(R.id.edus_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(loc)) {
                ((TextView) findViewById(R.id.my_edit_work_location_tv)).setText(loc);
            } else {
                ((TextView) findViewById(R.id.my_edit_work_location_tv)).setText("N/A");

                // findViewById(R.id.location_rl).setVisibility(View.GONE);
                // findViewById(R.id.locations_rl).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(height)) {
                ((TextView) findViewById(R.id.height_tv)).setText(height + " cm");
            } else {
                ((TextView) findViewById(R.id.height_tv)).setText("N/A");
            }

        }
    }


    public void callEditProfileApi(final String userId) {
        Call<EditProfileRes> call = mApis.updateEditProfile(getImagesRequest(), getUpdateEditProfile(userId));
        showProgressDialog(true);
        call.enqueue(new Callback<EditProfileRes>() {

            @Override
            public void onResponse(Call<EditProfileRes> call, Response<EditProfileRes> response) {
                showProgressDialog(false);
                if (response.isSuccessful()) {
                    // Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<EditProfileRes> call, Throwable t) {
                showProgressDialog(false);
            }
        });
    }


    //Update edit profile Status Request Param
    private GeneralReq getUpdateEditProfile(String userId) {
        GeneralReq generalReq = new GeneralReq();
        generalReq.setUser_id(userId);
        generalReq.setInstagram(((TextView) findViewById(R.id.instagram_et)).getText().toString());
        generalReq.setAbout_text(((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).getText().toString());
        generalReq.setHeight(height);
        generalReq.setEducation(academic);


        if (macademic_switch.isChecked()) {
            generalReq.setEducation_status("1");

        } else {
            generalReq.setEducation_status("0");

        }
        if (mlocation_switch.isChecked()) {
            generalReq.setLocation_status("1");

        } else {
            generalReq.setLocation_status("0");

        }
        if (mwork_switch.isChecked()) {
            generalReq.setWork_status("1");

        } else {
            generalReq.setWork_status("0");

        }

        return generalReq;
    }

    private Map<String, RequestBody> getImagesRequest() {
        HashMap<String, RequestBody> imageMap = new HashMap<>();

        return imageMap;
    }

    // SELECT IMAGE
    private void selectImage() {
        Define.ACTIONBAR_COLOR = getResources().getColor(R.color.colorPrimary);

        final CharSequence[] options = {"Capture Photo", "Choose from Gallery", "Choose from Facebook", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.drawable.ic_soul_logo);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Capture Photo")) {
                    // mPath = Utility.takePicFromCamera(EditProfileActivity.this, Constants.CAMERA_APP);
                    MediaFactory.MediaBuilder mediaBuilder = new MediaFactory.MediaBuilder(EditProfileActivity.this)
                            .setPickCount(1)
                            .fromCamera()
                            .isSquareCrop(false)
                            .doCropping();
                    mediaFactory = MediaFactory.create().start(mediaBuilder);

                } else if (options[item].equals("Choose from Gallery")) {

                    MediaFactory.MediaBuilder mediaBuilder = new MediaFactory.MediaBuilder(EditProfileActivity.this)
                            .setPickCount(1)
                            .doCropping()
                            .isSquareCrop(false)
                            .fromGallery();
                    mediaFactory = MediaFactory.create().start(mediaBuilder);


                    // Intent intent = new Intent();
                    // intent.setType("image/*");
                    // intent.setAction(Intent.ACTION_GET_CONTENT);
                    // startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

                } else if (options[item].equals("Choose from Facebook")) {
                    startActivity(new Intent(EditProfileActivity.this, FacebookAlbumActivity.class));

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.RESULT_INTEREST) {
            try {
                String interest = data.getStringExtra(Constants.EXTRA_INTEREST);
                ((TextView) findViewById(R.id.my_edit_profile_interests_tv)).setText(interest);
//                String[] allInterest = interest.split(",");
//                mInterestList.clear();
//                for (int i = 0; i < allInterest.length; i++) {
//                    String intst = allInterest[i];
//                    if (!(intst.equals(" ")))
//                        mInterestList.add(intst);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == Constants.RESULT_SECT) {
            try {
                ((TextView) findViewById(R.id.my_edit_profile_sect_tv)).setText(data.getStringExtra(Constants.EXTRA_SECT));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (resultCode == Constants.RESULT_HEIGHT) {

            try {
                height = data.getStringExtra(Constants.EXTRA_MAX_HEIGHT);
                ((TextView) findViewById(R.id.height_tv)).setText(height + " cm");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == Constants.RESULT_ACADEMIC) {

            try {
                academic = data.getStringExtra(Constants.EXTRA_ACADEMIC);
                ((TextView) findViewById(R.id.my_edit_profile_outlook_tv)).setText(academic);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                // ((TextView) findViewById(R.id.my_edit_profile_outlook_tv)).setText(data.getStringExtra(Constants.EXTRA_ACADEMIC));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
      /* if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        // Get the path from the Uri
                        String path = Utility.getPath(EditProfileActivity.this, data.getData());
                        Log.i(TAG, "Image Path : " + path);
                        setSelectImageUri(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == Constants.CAMERA_APP) {
                // Get the url from data
                setSelectImageUri(mPath);
            }
        }*/
        if (resultCode == Activity.RESULT_OK && mediaFactory != null) {
            ArrayList<String> stringArrayList = mediaFactory.onActivityResult(requestCode, resultCode, data);
            if (stringArrayList != null && stringArrayList.size() > 0) {
                updateUserImageApi(stringArrayList.get(0));
                setSelectImageUri(stringArrayList.get(0));

            }
        }
    }

    //Set SelectImageUri for camera and gallery
    private void setSelectImageUri(String selectedImageUri) {
        for (int i = 0; i < profilePicList.size(); i++) {
            if (profilePicList.get(i).equals("")) {

                //  Glide.with(EditProfileActivity.this).load(selectedImageUri).placeholder(R.drawable.rounded_edit_profile).into(setSelectedImages);
                Utility.glide(EditProfileActivity.this, setSelectedImages, R.drawable.rounded_edit_profile, selectedImageUri);
                profilePicList.set(clickCounter, selectedImageUri);
                imageAdd.setVisibility(View.INVISIBLE);
                imageAdd.setEnabled(false);
                editDeleteProfileImageOne.setVisibility(View.VISIBLE);

                updateImageEditProfileApi(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
                if (profilePicList.get(i).contains("http") || profilePicList.get(i).contains("https")) {
                    updateHttpContainImagesApi();
                }
                break;
            }
        }
        PrefUtils.setSharedPrefStringData(this, PrefUtils.PREF_FB_IMAGE, "");
    }

    //Set SelectImageUri for camera and gallery  (Facebook Gallery)
    private void setSelectFbImageUri(String selectedImageUri) {
        for (int i = 0; i < profilePicList.size(); i++) {
            if (profilePicList.get(i).equals("")) {

                // Glide.with(EditProfileActivity.this).load(selectedImageUri).placeholder(R.drawable.rounded_edit_profile).into(setSelectedImages);
                Utility.glide(EditProfileActivity.this, setSelectedImages, R.drawable.rounded_edit_profile, selectedImageUri);
                profilePicList.set(clickCounter, selectedImageUri);
                imageAdd.setVisibility(View.INVISIBLE);
                imageAdd.setEnabled(false);

                updateImageEditProfileApi(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
                if (profilePicList.get(i).contains("http") || profilePicList.get(i).contains("https")) {
                    updateHttpContainImagesApi();
                }
                break;
            }
        }
        PrefUtils.setSharedPrefStringData(this, PrefUtils.PREF_FB_IMAGE, "");
    }

    // for Update Images
    public void updateImageEditProfileApi(final String userId) {
        Call<EditProfileRes> call = mApis.updateEditProfile(getImagesRequest1(), getUpdateEditProfile1(userId));
        //  showProgressDialog(true);
        call.enqueue(new Callback<EditProfileRes>() {

            @Override
            public void onResponse(Call<EditProfileRes> call, Response<EditProfileRes> response) {
                showProgressDialog(false);
                if (response.isSuccessful()) {
//                    Log.e("", response.body().getMsg());

                 /*   if (profilePicList.get(i).contains("http") || profilePicList.get(i).contains("https")) {
                       updateHttpContainImagesApi();
                    }*/
                    //  Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditProfileRes> call, Throwable t) {
                showProgressDialog(false);
            }
        });
    }

    //Update edit profile Status Request Param
    private GeneralReq getUpdateEditProfile1(String userId) {
        GeneralReq generalReq = new GeneralReq();
        generalReq.setUser_id(userId);
        generalReq.setAbout_text(((TextView) findViewById(R.id.my_full_profile_about_me_content_tv)).getText().toString());
        generalReq.setInstagram(((TextView) findViewById(R.id.instagram_et)).getText().toString());
        generalReq.setHeight(height);
        generalReq.setEducation(academic);
        generalReq.setEducation_status("1");
        return generalReq;
    }

    private Map<String, RequestBody> getImagesRequest1() {

        HashMap<String, RequestBody> imageMap = new HashMap<>();

        for (int i = 0; i < profilePicList.size(); i++) {
            RequestBody fileBody = null;

            String imagePath = profilePicList.get(i);
            File file = new File(imagePath);
            if (file.exists())
                fileBody = RequestBody.create(MediaType.parse("image/*"), file);

            if (fileBody != null) {
                if (i == 0) {
                    imageMap.put("profile_pic" + "\"; filename='" + file.getName() + "' ", fileBody);
//                        imageMap.put("profile_pic", fileBody);
                } else {
                    imageMap.put("user_image" + i + "\"; filename='" + file.getName() + "' ", fileBody);
                    //  String key = "user_image" + i;
                    //  imageMap.put(key, fileBody);
                }
            }
        }


        return imageMap;
    }


    // RETURN HTTP IMAGE
    public void updateUserImageApi(String imagePath) {
        Call<UserImageRes> call = mApis.updateUserImage(getUserImage(imagePath));
        showProgressDialog(true);
        call.enqueue(new Callback<UserImageRes>() {
            @Override
            public void onResponse(Call<UserImageRes> call, Response<UserImageRes> response) {
                showProgressDialog(false);
                if (response.isSuccessful()) {
                    // Glide.with(EditProfileActivity.this).load(selectedImageUri).placeholder(R.drawable.rounded_edit_profile).into(setSelectedImages);
                    //Utility.glide(EditProfileActivity.this, setSelectedImages, R.drawable.rounded_edit_profile, response.body().getData().getProfilePicUrl());
                    profilePicList.set(clickCounter, response.body().getData().getProfilePicUrl());
                    imageAdd.setVisibility(View.INVISIBLE);
                    imageAdd.setEnabled(false);
                    deleteImage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserImageRes> call, Throwable t) {
                showProgressDialog(false);
            }
        });
    }

    // multipart service return for image url
    private Map<String, RequestBody> getUserImage(String imagePath) {

        HashMap<String, RequestBody> imageMap = new HashMap<>();
        RequestBody fileBody = null;
        File file = new File(imagePath);
        if (file.exists())
            fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        if (fileBody != null) {
            imageMap.put("user_image" + "\"; filename='" + file.getName() + "' ", fileBody);
        }
        return imageMap;
    }

    // DELETE IMAGE
    private void deleteImage(final int value, final String deleteImageName, final ImageView imageAddPic, final ImageView imageDeletePic, final String imageName) {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                EditProfileActivity.this);
        alert.setIcon(R.drawable.ic_soul_logo);
        alert.setTitle(getString(R.string.app_name));
        alert.setMessage(getString(R.string.delete_image));
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                dialog.dismiss();

                profilePicList.set(value, deleteImageName);
                imageAddPic.setEnabled(true);
                imageAddPic.setVisibility(View.VISIBLE);
                imageDeletePic.setVisibility(View.INVISIBLE);

                deleteImagesApi(imageName);
                setImage();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();
    }

    // PROFILE API
    private void profileApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
            Call<ObjResp<UserProfileRes>> call = mApis.userProfile(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<UserProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<UserProfileRes>> call, Response<ObjResp<UserProfileRes>> response) {

                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        //  showProgressDialog(false);
                        if (response.body() != null) {
                            setUi(response.body().getData());
                            // Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }/* else {
                        showProgressDialog(false);
                    }*/
                }

                @Override
                public void onFailure(Call<ObjResp<UserProfileRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(EditProfileActivity.this)
                    .withMessage(getString(R.string.err_network)).show();
        }
    }

    // CALL HTTP Contains IMAGES
    private void updateHttpContainImagesApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<ChangeProfileImagesRes> call = mApis.changeHttpProfileImages(getHttpImages());
            showProgressDialog(true);
            call.enqueue(new Callback<ChangeProfileImagesRes>() {
                @Override
                public void onResponse(Call<ChangeProfileImagesRes> call, Response<ChangeProfileImagesRes> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        showProgressDialog(false);
                        if (response.body() != null) {
                            //  Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChangeProfileImagesRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(EditProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    //Update edit profile HttpImages
    private GeneralReq getHttpImages() {
        GeneralReq generalReq = new GeneralReq();
        generalReq.setUser_id(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
        for (int i = 0; i < profilePicList.size(); i++) {
            // if (profilePicList.get(i).contains("http") || profilePicList.get(i).contains("https")) {
            if (i == 0) {
                generalReq.setProfile_pic(profilePicList.get(i));
            } else if (i == 1) {
                generalReq.setUser_image1(profilePicList.get(i));
            } else if (i == 2) {
                generalReq.setUser_image2(profilePicList.get(i));
            } else if (i == 3) {
                generalReq.setUser_image3(profilePicList.get(i));
            } else if (i == 4) {
                generalReq.setUser_image4(profilePicList.get(i));
            }
            //  }
        }
        return generalReq;
    }

    // DELETE IMAGES RESPONSE
    private void deleteImagesApi(String imageName) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<DeleteImagesRes> call = mApis.deleteImagesRes(deleteImages(imageName));
            showProgressDialog(true);
            call.enqueue(new Callback<DeleteImagesRes>() {
                @Override
                public void onResponse(Call<DeleteImagesRes> call, Response<DeleteImagesRes> response) {
                    if (response.isSuccessful()) {
                        //  showProgressDialog(false);
                        if (response.body() != null) {
                            showProgressDialog(false);

                            //  Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<DeleteImagesRes> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(EditProfileActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    private GeneralReq deleteImages(String imageName) {
        GeneralReq generalReq = new GeneralReq();
        generalReq.setUser_id(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
        generalReq.setUser_image_column(imageName);

        return generalReq;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissionSet, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionSet, grantResults);
    }

    private void profileApiforInterest() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(EditProfileActivity.this, PrefUtils.USER_ID));
            Call<ObjResp<UserProfileRes>> call = mApis.userProfile(generalReq);
            //  showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<UserProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<UserProfileRes>> call, Response<ObjResp<UserProfileRes>> response) {

                    // showProgressDialog(false);
                    if (response.isSuccessful()) {
                        //  showProgressDialog(false);
                        if (response.body() != null) {
                            setUiForInt(response.body().getData());
                            // Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }/* else {
                        showProgressDialog(false);
                    }*/
                }

                @Override
                public void onFailure(Call<ObjResp<UserProfileRes>> call, Throwable t) {
                    // showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(EditProfileActivity.this)
                    .withMessage(getString(R.string.err_network)).show();
        }
    }

    void setUiForInt(UserProfileRes userProfile) {
        if (userProfile != null) {
            mInterestList.clear();
            for (int i = 0; i < userProfile.getInterests().size(); i++) {
                mInterestList.add(userProfile.getInterests().get(i).getCategory_name());
            }
        }
    }
}