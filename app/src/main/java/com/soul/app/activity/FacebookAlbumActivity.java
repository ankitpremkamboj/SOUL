package com.soul.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.ImageGalleryActivity;
import com.soul.app.adapter.FbAlbumRclAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.res.PhotosEntityResp;
import com.soul.app.models.res.SocialMediaInfoBean;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;
import java.util.List;

public class FacebookAlbumActivity extends com.soul.app.activity.BaseActivity {

    List<SocialMediaInfoBean.AlbumsEntity.DataEntity> mAlbumList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_facebook_album;
    }

    @Override
    public void initUi() {
        FrameLayout headerFrame = (FrameLayout) findViewById(R.id.edit_profile_header);
        TextView doneTv = (TextView) findViewById(R.id.eph_done);
        ImageView backIcon = (ImageView) findViewById(R.id.eph_back_icon);
        TextView headerNameTv = (TextView) findViewById(R.id.eph_edit_profile);
        headerNameTv.setText(getResources().getString(R.string.lbl_fb_album));
        doneTv.setVisibility(View.GONE);
        headerFrame.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fb_album_rcl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAlbumList = PrefUtils.getSharedPreferencesLogList(this, PrefUtils.PREF_USER_ALBUM);
        FbAlbumRclAdapter adapter = new FbAlbumRclAdapter(this, mAlbumList) {
            @Override
            public void getUserImages(int pos) {
                if(ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    getFacebookImage(pos);
                }
                else {
                    new SnackBar.Builder(FacebookAlbumActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        };
        recyclerView.setAdapter(adapter);

        if (backIcon != null)
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }

    void getFacebookImage(int pos) {
        showProgressBar(true);
        String albumId = mAlbumList.get(pos).getId();
        Bundle paramtersphoto = new Bundle();
        paramtersphoto.putString("fields", "images.limit(10)");
        GraphRequest picGr = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/v2.4/" + albumId + "/photos",
                paramtersphoto,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        showProgressBar(false);
                        try {
                            //Log.e("responseProfilePictures", response.toString());
                            PhotosEntityResp photosEntity = Utility.fromJson(response.getJSONObject().toString(), PhotosEntityResp.class);
                            getData(photosEntity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }
        );
        picGr.setParameters(paramtersphoto);
        picGr.executeAsync();
    }

    private void getData(PhotosEntityResp photosEntity) {

        if (photosEntity != null && photosEntity.getData() != null) {
            ArrayList<String> imagesList=new ArrayList<String>();
            for (int i = 0; i < photosEntity.getData().size(); i++) {
                String sourceUrl = photosEntity.getData().get(i).getImages().get(0).getSource();
                imagesList.add(sourceUrl);
            }
            if(imagesList.size()>0) {
                Intent intent = new Intent(FacebookAlbumActivity.this,ImageGalleryActivity.class);
                intent.putStringArrayListExtra(Constants.EXTRA_FB_IMAGE,imagesList);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public String getName() {
        return null;
    }
}
