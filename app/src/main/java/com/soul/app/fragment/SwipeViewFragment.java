package com.soul.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.soul.app.R;
import com.soul.app.adapter.SwipeViewPagerAdapter;
import com.soul.app.fragment.*;

/**
 * Created by ashishkumar on 17/6/16.
 */
public class SwipeViewFragment extends com.soul.app.fragment.BaseFragment {

    private ViewPager mViewPager;
    private SwipeViewPagerAdapter swipeViewPagerAdapter;
    private ImageView footerIndicator1, footerIndicator2, footerIndicator3, footerIndicator4;

    public SwipeViewFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        setTab();
        onCircleButtonClick();
    }

    private void onCircleButtonClick() {

        footerIndicator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerIndicator1.setImageResource(R.drawable.page_dot_1);
                mViewPager.setCurrentItem(0);
            }
        });

        footerIndicator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerIndicator2.setImageResource(R.drawable.page_dot_1);
                mViewPager.setCurrentItem(1);
            }
        });
        footerIndicator3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerIndicator3.setImageResource(R.drawable.page_dot_1);
                mViewPager.setCurrentItem(2);
            }
        });
        footerIndicator4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerIndicator4.setImageResource(R.drawable.page_dot_1);
                mViewPager.setCurrentItem(3);
            }
        });
    }

    private void setUpView() {
        mViewPager = (ViewPager) getView().findViewById(R.id.view_pager);
        swipeViewPagerAdapter = new SwipeViewPagerAdapter(getActivity(), getFragmentManager());
        mViewPager.setAdapter(swipeViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        initImageIndicator();
    }

    private void initImageIndicator() {
        footerIndicator1 = (ImageView) getView().findViewById(R.id.footer_indicator1_iv);
        footerIndicator1.setImageResource(R.drawable.page_dot_1);
        footerIndicator2 = (ImageView) getView().findViewById(R.id.footer_indicator2_iv);
        footerIndicator3 = (ImageView) getView().findViewById(R.id.footer_indicator3_iv);
        footerIndicator4 = (ImageView) getView().findViewById(R.id.footer_indicator4_iv);
    }

    private void setTab() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                footerIndicator1.setImageResource(R.drawable.page_dot_2);
                footerIndicator2.setImageResource(R.drawable.page_dot_2);
                footerIndicator3.setImageResource(R.drawable.page_dot_2);
                footerIndicator4.setImageResource(R.drawable.page_dot_2);
                indicatorAction(position);
            }

        });

    }

    private void indicatorAction(int action) {
        switch (action) {
            case 0:
                footerIndicator1.setImageResource(R.drawable.page_dot_1);

                break;

            case 1:
                footerIndicator2.setImageResource(R.drawable.page_dot_1);

                break;
            case 2:
                footerIndicator3.setImageResource(R.drawable.page_dot_1);

                break;

            case 3:
                footerIndicator4.setImageResource(R.drawable.page_dot_1);

                break;
        }
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_swipe_view;
    }

    @Override
    public void initUi() {

    }


    @Override
    public String getName() {
        return SwipeViewFragment.class.getName();
    }

    @Override
    public void onDone() {

    }
}
