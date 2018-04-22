package com.soul.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.adapter.ImageGallaryAdapter;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.util.ArrayList;

import crop.Crop;

/*
* Image Gallery for Facebook Images
*
**/

public class ImageGalleryActivity extends com.soul.app.activity.BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_image_gallery;
    }

    @Override
    public void initUi() {
        FrameLayout headerFrame = (FrameLayout) findViewById(R.id.edit_profile_header);
        TextView doneTv = (TextView) findViewById(R.id.eph_done);
        ImageView backIcon = (ImageView) findViewById(R.id.eph_back_icon);
        TextView headerNameTv = (TextView) findViewById(R.id.eph_edit_profile);
        headerNameTv.setText(getResources().getString(R.string.lbl_fb_images));
        doneTv.setVisibility(View.GONE);
        headerFrame.setVisibility(View.VISIBLE);

        if (backIcon != null)
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ImageGalleryActivity.this, FacebookAlbumActivity.class));
                    finish();
                }
            });

        ArrayList<String> imageList = getIntent().getStringArrayListExtra(Constants.EXTRA_FB_IMAGE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fb_image_rcl);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        ImageGallaryAdapter adapter = new ImageGallaryAdapter(ImageGalleryActivity.this, imageList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String source = Utility.getPath(this, uri);
            String sourceUrl = PrefUtils.getSharedPrefString(ImageGalleryActivity.this, PrefUtils.PREF_FB_SOURCE_IMAGE);
            // PrefUtils.setSharedPrefStringData(this, PrefUtils.PREF_FB_IMAGE, source);
            PrefUtils.setSharedPrefStringData(this, PrefUtils.PREF_FB_IMAGE, sourceUrl);
            finish();
            // ImageView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
