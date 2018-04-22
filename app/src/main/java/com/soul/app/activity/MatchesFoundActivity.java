package com.soul.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.activity.SocketChatActivity;
import com.soul.app.adapter.MatchesListAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.CircleImageView;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ChatConversationRes;
import com.soul.app.models.res.ListResp;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.UserMatchesRes;
import com.soul.app.swiping.SwipingActivity;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesFoundActivity extends BaseActivity implements View.OnClickListener {

    private ListView matchesMessageListView;
    private MatchesListAdapter matchesListAdapter;
    private RelativeLayout matchSearchBoxRl;
    private Button matchesCancelBtn;
    private TextView searchTv;
    private ImageView homeIcon;
    private ImageView profile_icon;
    private FrameLayout matchHeader;
    private List<UserMatchesRes> mMatchesList;
    private List<UserMatchesRes> mFilterMatchList;
    private List mFilterList = new ArrayList();
    private LinearLayout mMatchesLinLayout;
    private TextView profile_tv;
    private TextView home_tv;
    private TextView chat_tv;

    private EditText mSearchEdt;
    private ArrayList<ChatConversationRes> mChatConvList = new ArrayList<ChatConversationRes>();

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_matches_found;
    }

    @Override
    public void initUi() {

        Utility.hideKeyboard(this);
        mFilterMatchList = new ArrayList<UserMatchesRes>();
        mSearchEdt = (EditText) findViewById(R.id.search_box_et);
        mMatchesList = getIntent().getParcelableArrayListExtra(Constants.EXTRA_USER_MATCH);
        mMatchesLinLayout = (LinearLayout) findViewById(R.id.matches_ll);

        matchHeader = (FrameLayout) findViewById(R.id.match_header);
        matchHeader.setVisibility(View.VISIBLE);

        chat_tv = (TextView) findViewById(R.id.chat_tv);
        chat_tv.setTextColor(Color.WHITE);

        home_tv = (TextView) findViewById(R.id.home_tv);
        home_tv.setTextColor(ContextCompat.getColor(this, R.color.header_text_color));

        profile_tv = (TextView) findViewById(R.id.profile_tv);
        profile_tv.setTextColor(ContextCompat.getColor(this, R.color.header_text_color));

        homeIcon = (ImageView) findViewById(R.id.mh_home_icon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchesFoundActivity.this, SwipingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile_icon = (ImageView) findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchesFoundActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });
        matchSearchBoxRl = (RelativeLayout) findViewById(R.id.match_search_box_rl);
        searchTv = (TextView) findViewById(R.id.search_tv);
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchSearchBoxRl.setVisibility(View.VISIBLE);
            }
        });
        matchesCancelBtn = (Button) findViewById(R.id.matches_cancel_btn);
        matchesCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdt.getText().clear();
                matchSearchBoxRl.setVisibility(View.GONE);

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });

        matchesMessageListView = (ListView) findViewById(R.id.matches_message_lv);
        matchesListAdapter = new MatchesListAdapter(MatchesFoundActivity.this, mChatConvList);
        matchesMessageListView.setAdapter(matchesListAdapter);
        Utility.setListViewHeightBasedOnChildren(matchesMessageListView);
        //chatConvApi();

        mSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textlength = s.toString().trim().length();
                ArrayList<ChatConversationRes> tempArrayList = new ArrayList<ChatConversationRes>();
                for (ChatConversationRes c : mChatConvList) {

                    if (textlength <= c.getUser_name().length()) {
                        if (c.getUser_name().trim().toLowerCase()
                                .contains(s.toString().trim().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }

                }
                if (tempArrayList.size() == 0) {
                    matchesMessageListView.setVisibility(View.GONE);
                    findViewById(R.id.no_result_ll).setVisibility(View.VISIBLE);
                } else {
                    matchesMessageListView.setVisibility(View.VISIBLE);
                    findViewById(R.id.no_result_ll).setVisibility(View.GONE);
                }

                matchesListAdapter.notifyChange(tempArrayList);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public String getName() {
        return MatchesFoundActivity.class.getName();
    }

    public void inflateMatchesUi(List<UserMatchesRes> mFilterMatchList) {
        mMatchesLinLayout.removeAllViews();
        LinearLayout rowMacthesll;
        if (mFilterMatchList != null) {
            for (int i = 0; i < mFilterMatchList.size(); i++) {
                View child = getLayoutInflater().inflate(R.layout.row_matches, null);
                CircleImageView circleImageView = (CircleImageView) child.findViewById(R.id.matches_pic_ci);
                TextView nameTv = (TextView) child.findViewById(R.id.matches_name);

                makeExpired(mFilterMatchList.get(i), circleImageView, nameTv, child);
                rowMacthesll = (LinearLayout) child.findViewById(R.id.row_matches_ll);
                rowMacthesll.setTag(R.string.user_id, mFilterMatchList.get(i).getUser_id());
                rowMacthesll.setTag(R.string.profile_pic, mFilterMatchList.get(i).getProfile_pic());
                rowMacthesll.setTag(R.string.name, mFilterMatchList.get(i).getUser_name());
                rowMacthesll.setOnClickListener(this);
                String nameArray[] = mFilterMatchList.get(i).getUser_name().split(" ");
                String splitedName = nameArray[0];
                try {
                    String url = mFilterMatchList.get(i).getProfile_pic();//get(i).getProfile_pic();
                    Utility.glide(MatchesFoundActivity.this, circleImageView, R.drawable.small_pic_placeholder, url);
                    // Glide.with(this).load(url).placeholder(R.drawable.small_pic_placeholder).into(circleImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameTv.setText(splitedName);
                mMatchesLinLayout.addView(child);
            }
            if (this.mFilterMatchList.size() == 0) {
                findViewById(R.id.matched_hsw).setVisibility(View.VISIBLE);
                findViewById(R.id.matched_header_rl).setVisibility(View.VISIBLE);
                View child = getLayoutInflater().inflate(R.layout.row_placeholder_image, null);
                mMatchesLinLayout.removeAllViews();
                mMatchesLinLayout.addView(child);

            } else {
                findViewById(R.id.matched_hsw).setVisibility(View.VISIBLE);
                findViewById(R.id.matched_header_rl).setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public void onClick(View v) {
        String otherId = (String) v.getTag(R.string.user_id);
        String profilePic = (String) v.getTag(R.string.profile_pic);
        String name = (String) v.getTag(R.string.name);

        switch (v.getId()) {
            case R.id.row_matches_ll:
//                if (!PrefUtils.getSharedPrefBoolean(MatchesFoundActivity.this, otherId)) {
//                Intent intent = new Intent(this, MatchActivity.class);
//                intent.putExtra(Constants.EXTRA_OTHER_ID, otherId);
//                intent.putExtra(Constants.EXTRA_USER_PROFILE, profilePic);
//                String nameArray[] = name.split(" ");
//                String splitedName = nameArray[0];
//                intent.putExtra(Constants.EXTRA_NAME, splitedName);
//                startActivity(intent);
                Intent intent = new Intent(this, SocketChatActivity.class);
                intent.putExtra(PrefUtils.OTHER_ID, otherId);
                String nameArray[] = name.split(" ");
                intent.putExtra(Constants.EXTRA_OTHER_NAME, nameArray[0]);
                intent.putExtra(Constants.EXTRA_OTHER_PIC, profilePic);
                intent.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.NORMAL_TYPE);
                startActivity(intent);
                PrefUtils.setSharedPrefBooleanData(MatchesFoundActivity.this, otherId, true);
//                } else {
//                    Intent intent = new Intent(this, ChatActivity.class);
//                    intent.putExtra(PrefUtils.OTHER_ID, otherId);
//                    intent.putExtra(Constants.EXTRA_OTHER_NAME, name);
//                    intent.putExtra(Constants.EXTRA_OTHER_PIC, profilePic);
//                    intent.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.NORMAL_TYPE);
//                    startActivity(intent);
//                }


                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatConvApi();
    }

    public void chatConvApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(MatchesFoundActivity.this, PrefUtils.USER_ID));
            Call<ListResp<ChatConversationRes>> call = mApis.chatConversation(generalReq);
            call.enqueue(new Callback<ListResp<ChatConversationRes>>() {
                @Override
                public void onResponse(Call<ListResp<ChatConversationRes>> call, Response<ListResp<ChatConversationRes>> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            mChatConvList.clear();
                            mChatConvList.addAll(response.body().getData());
                            getFileterList(mChatConvList);

                            countUnReadMsg(mChatConvList);

                            matchesListAdapter.notifyDataSetChanged();
                            matchesListAdapter.notifyDataSetInvalidated();
                            showProgressDialog(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListResp<ChatConversationRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        }
    }

    // FOR UNREAD MESSAGE
    private void countUnReadMsg(ArrayList<ChatConversationRes> mChatConvList) {
        int count = 0;
        for (int i = 0; i < mChatConvList.size(); i++) {
            int unReadMsg = mChatConvList.get(i).getUnread_msg_count();
            if (unReadMsg > 0) {
                count++;
            }
        }
        TextView unReadMessageCounter = (TextView) findViewById(R.id.messages_count_tv);
        if (count == 0)
            unReadMessageCounter.setVisibility(View.GONE);
        else {
            unReadMessageCounter.setVisibility(View.VISIBLE);
            unReadMessageCounter.setText(String.valueOf(count));
        }
    }

    public void getFileterList(List<ChatConversationRes> chatConList) {
        mFilterMatchList.clear();
        // mFilterMatchList.addAll(mMatchesList);
        boolean flag = true;
        int size = mMatchesList.size();
        for (int c = 0; c < size; c++) {
            flag = true;
            for (int r = 0; r < chatConList.size(); r++) {
                if (mMatchesList.get(c).getUser_id().trim().equalsIgnoreCase(chatConList.get(r).getUser_id().trim())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                mFilterMatchList.add(mMatchesList.get(c));
            }

        }
        inflateMatchesUi(mFilterMatchList);

    }

    public void makeExpired(UserMatchesRes matchedUser, ImageView iv, TextView tv, View child) {
        if (matchedUser != null) {

            if (!TextUtils.isEmpty(matchedUser.getCreated_on())) {
                String timeStamp = matchedUser.getCreated_on();
                Long tsLong = Long.valueOf(timeStamp);
                Date date = new Date(tsLong * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                // for 3 days
                c.add(Calendar.DATE, 3);//add 3 days to expire
                Long expriedDateThreeMs = c.getTimeInMillis();

                // for 2 days
                c.setTime(date);
                c.add(Calendar.DATE, 2);
                Long expriedTwoDateMs = c.getTimeInMillis();

                //for 1 days
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                Long expriedOneDateMs = c.getTimeInMillis();


                Date currentDate = new Date();
                Long currentDateMs = currentDate.getTime();

                if (expriedDateThreeMs < currentDateMs) {
                    unmatchApi(matchedUser.getUser_id());
                } else if (expriedTwoDateMs < currentDateMs) {
                    child.setAlpha(0.6f);
                } else if (expriedOneDateMs < currentDateMs) {
                    child.setAlpha(0.8f);
                } else {
                    child.setAlpha(1.0f);
                }
            }
        }


    }

    private void unmatchApi(String mOtherId) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(true);
            GeneralReq req = new GeneralReq();
            req.setUser_id(PrefUtils.getSharedPrefString(MatchesFoundActivity.this, PrefUtils.USER_ID));
            req.setOther_id(mOtherId);
            req.setUnmatch_reason("expired");
            Call<ObjResp> call = mApis.unMatchUser(req);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    showProgressDialog(false);
                    if (response.isSuccessful()) {
                        FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_UNMATCHES);
                    }
                }

                @Override
                public void onFailure(Call<ObjResp> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            // DialogUtils.showToast(UnMatchActivity.this, getResources().getString(R.string.err_network));
            new SnackBar.Builder(MatchesFoundActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, AppConstant.FLURRY_API_KEY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }
}



