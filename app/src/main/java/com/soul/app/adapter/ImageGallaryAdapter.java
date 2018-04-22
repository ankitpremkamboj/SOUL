package com.soul.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soul.app.R;
import com.soul.app.customui.RoundedImageView;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.List;

import crop.Crop;

/**
 * Created by techahead on 9/8/16.
 */
public class ImageGallaryAdapter extends RecyclerView.Adapter<ImageGallaryAdapter.DataObjectHolder> {
    Context mContext;
    List<String> mList;
    private Uri fbImageUri;

    public ImageGallaryAdapter(Context context, List<String> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_imageview, parent, false);


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Utility.glide(mContext, holder.userImg, 0, mList.get(position));

        holder.userImgll.setTag(mList.get(position));
        holder.userImgll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceUrl = v.getTag() + "";
                //PrefUtils.setSharedPrefStringData(mContext, PrefUtils.PREF_FB_IMAGE, sourceUrl);
                PrefUtils.setSharedPrefStringData(mContext, PrefUtils.PREF_FB_SOURCE_IMAGE, sourceUrl);
                try {
                    Glide.with(mContext).load(sourceUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            fbImageUri = Utility.getImageUri(mContext, resource);
                            String ext = Utility.getFileExt(mList.get(0));
                            Uri destination = Uri.fromFile(new File(mContext.getCacheDir(), Constants.fbCroppedImg + "." + ext));
                            Crop.of(fbImageUri, destination).start((Activity) mContext);


                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private ImageView userImg;
        private LinearLayout userImgll;

        public DataObjectHolder(View itemView) {
            super(itemView);
            userImg = (RoundedImageView) itemView.findViewById(R.id.images);
            userImgll = (LinearLayout) itemView.findViewById(R.id.image_ll);
        }
    }


}
