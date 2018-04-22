package com.soul.app.swiping;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mrengineer13.snackbar.SnackBar;
import com.google.android.gms.maps.GoogleMap;
import com.soul.app.R;
import com.soul.app.activity.BaseGpsActivity;
import com.soul.app.activity.HomeFindingPeopleActivity;
import com.soul.app.activity.MatchesFoundActivity;
import com.soul.app.activity.NoMatchesActivity;
import com.soul.app.activity.SplashActivity;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.Apis;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 1/12/2018.
 */

public class SwipingActivity extends BaseGpsActivity {
    private Apis mApis;

    private TabLayout tabs;
    private ViewPager viewpager;
    private Location mLocation;

    private int[] tabIcons = {
            R.drawable.ic_open_profile,
            R.drawable.ic_un_select_home,
            R.drawable.ic_chat
    };
    private int[] tabHomeIcons = {
            R.drawable.ic_profile,
            R.drawable.ic_home,
            R.drawable.ic_chat
    };
    private int[] tabChatIcons = {
            R.drawable.ic_profile,
            R.drawable.ic_un_select_home,
            R.drawable.ic_chat1
    };


    @Override
    public void mapReady(GoogleMap googleMap) {

    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabHomeIcons[0]);
        tabs.getTabAt(1).setIcon(tabHomeIcons[1]);
        tabs.getTabAt(2).setIcon(tabHomeIcons[2]);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    tabs.getTabAt(0).setIcon(tabIcons[0]);
                    tabs.getTabAt(1).setIcon(tabIcons[1]);
                    tabs.getTabAt(2).setIcon(tabIcons[2]);

                }
                if (tab.getPosition() == 1) {
                    tabs.getTabAt(0).setIcon(tabHomeIcons[0]);
                    tabs.getTabAt(1).setIcon(tabHomeIcons[1]);
                    tabs.getTabAt(2).setIcon(tabHomeIcons[2]);

                }

                if (tab.getPosition() == 2) {
                    tabs.getTabAt(0).setIcon(tabChatIcons[0]);
                    tabs.getTabAt(1).setIcon(tabChatIcons[1]);
                    tabs.getTabAt(2).setIcon(tabChatIcons[2]);
                }


                // same thing goes with other tabs too
                // Just change your tab text on selected/deselected as above

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ProfileFragment(), "Profile");
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new MatchFoundFragment(), "Chat");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }

    @Override
    public int setLayout() {
        mApis = new ApiClient().getApis();
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_swipe;
    }

    @Override
    public void initUi() {

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        viewpager.setCurrentItem(1);
        viewpager.setOffscreenPageLimit(2);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewpager);
        tabs.setSelectedTabIndicatorHeight(0);
        tabs.setupWithViewPager(viewpager);
        setupTabIcons();

    }

    @Override
    public String getName() {
        return SwipingActivity.class.getName();
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        //   AppEventsLogger.deactivateApp(this);
    }

}
