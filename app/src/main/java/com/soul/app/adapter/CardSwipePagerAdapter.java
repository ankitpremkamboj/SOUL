package com.soul.app.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.soul.app.R;
import com.soul.app.models.ImageData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by techahead on 7/7/16.
 */
public class CardSwipePagerAdapter extends PagerAdapter {


    private ArrayList<ImageData.userImageListEntity> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public CardSwipePagerAdapter(Context context, ArrayList<ImageData.userImageListEntity> IMAGES) {
        this.context = context;
        this.IMAGES= IMAGES;
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
        View imageLayout = inflater.inflate(R.layout.row_rounded_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.cardImage);

        Glide.with(context).load(IMAGES.get(position).getImagePath()).into(imageView);
        view.addView(imageLayout, 0);

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
