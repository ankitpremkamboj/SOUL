package com.soul.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.soul.app.fragment.SwipePageFourFragment;
import com.soul.app.fragment.SwipePageOneFragment;
import com.soul.app.fragment.SwipePageThreeFragment;
import com.soul.app.fragment.SwipePageTwoFragment;

/**
 * Created by ashishkumar on 17/6/16.
 */
public class SwipeViewPagerAdapter extends FragmentPagerAdapter {
    public static int totalPage = 4;
    private Context mContext;

    public SwipeViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new SwipePageOneFragment();
                break;

            case 1:
                fragment = new SwipePageTwoFragment();
                break;

            case 2:
                fragment = new SwipePageThreeFragment();
                break;

            case 3:
                fragment = new SwipePageFourFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}

