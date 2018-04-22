package com.soul.app.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soul.app.R;
import com.soul.app.activity.MatchedProfileActivity;
import com.soul.app.utils.Utility;

import java.util.ArrayList;


/**
 * Created by techahead on 27/6/16.
 */
public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImageAdapter(Context context, ArrayList<String> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {


        View imageLayout = inflater.inflate(R.layout.sliding_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        String profilePic = IMAGES.get(position);
        //if (!TextUtils.isEmpty(profilePic)) {
//            if(position==0)
//            profilePic = profilePic.replace("http", "https");
        //Glide.with(context).load(profilePic).placeholder(R.drawable.home_placeholder).into(imageView);
        try {
            Utility.glide(context, imageView, R.drawable.placeholder_image, profilePic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   }


        view.addView(imageLayout, 0);
        imageLayout.setOnClickListener(new View.OnClickListener() {
            final int[] number_of_clicks = {0};
            final boolean[] thread_started = {false};
            final int DELAY_BETWEEN_CLICKS_IN_MILLISECONDS = 250;

            @Override
            public void onClick(View v) {
                ++number_of_clicks[0];
                if (!thread_started[0]) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            thread_started[0] = true;
                            try {
                                Thread.sleep(DELAY_BETWEEN_CLICKS_IN_MILLISECONDS);
                                if (number_of_clicks[0] == 1) {

                                } else if (number_of_clicks[0] == 2) {
                                    try {
                                        ((MatchedProfileActivity) context).finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                number_of_clicks[0] = 0;
                                thread_started[0] = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
