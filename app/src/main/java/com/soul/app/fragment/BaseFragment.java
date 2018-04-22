package com.soul.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soul.app.interfaces.BaseListener;

/**
 * Created by ashishkumar on 17/6/16.
 */
public abstract class BaseFragment extends Fragment implements BaseListener {

    protected Context mContext;
    protected Snackbar mSnackBar;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(setLayout(), container, false);
        return view;
    }

    @Override
    public void onArrowPress() {

    }

    @Override
    public void showProgressDialog(boolean iShow) {

    }

    @Override
    public void showProgressBar(boolean iShow) {

    }

    /**
     * Finds a view with the given id and returns it
     *
     * @param resId The id to search for.
     * @return The view that has the given id in the hierarchy or null
     */
    public View findViewById(int resId) {
        return view.findViewById(resId);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mSnackBar != null)
            mSnackBar.dismiss();
    }
}
