package com.soul.app.fragment;

import android.view.View;

import com.soul.app.R;
import com.soul.app.fragment.*;

public class SwipePageOneFragment extends com.soul.app.fragment.BaseFragment {

    private View mParentSwipeViewOne;

    public SwipePageOneFragment() {
        // Required empty public constructor
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_swipe_page_one;
    }

    @Override
    public void initUi() {
        mParentSwipeViewOne = findViewById(R.id.parent_swipe_view_one);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onDone() {

    }
}
