package com.soul.app.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soul.app.R;

import java.util.ArrayList;

/**
 * Created by ashishkumar on 29/6/16.
 */
public class HomeSlidingImageAdapter extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Integer> TITLE;


    public HomeSlidingImageAdapter(Context context, ArrayList<Integer> IMAGES, ArrayList<Integer> pageTitleArray) {
        this.context = context;
        this.IMAGES = IMAGES;
        this.TITLE = pageTitleArray;
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
        View imageLayout = inflater.inflate(R.layout.home_swipe_row, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.row_image);

        final TextView textView = (TextView) imageLayout.findViewById(R.id.page_title);

        imageView.setImageResource(IMAGES.get(position));
        textView.setText(TITLE.get(position));

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
