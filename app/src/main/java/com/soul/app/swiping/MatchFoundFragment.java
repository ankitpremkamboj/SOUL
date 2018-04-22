package com.soul.app.swiping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.R;
import com.soul.app.activity.HomeFindingPeopleActivity;
import com.soul.app.activity.MatchesFoundActivity;
import com.soul.app.activity.MyProfileActivity;
import com.soul.app.activity.NoMatchesActivity;
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
import com.soul.app.retrofit.ApiClient;
import com.soul.app.retrofit.Apis;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ankit on 1/12/2018.
 */

public class MatchFoundFragment extends Fragment implements View.OnClickListener {
    private View view;

    private ListView matchesMessageListView;
    private MatchesListAdapter matchesListAdapter;
    private RelativeLayout matchSearchBoxRl;
    private Button matchesCancelBtn;
    private TextView searchTv;
    /* private ImageView homeIcon;
     private ImageView profile_icon;*/
    private FrameLayout matchHeader;
    private List<UserMatchesRes> mMatchesList = new ArrayList<>();
    ;
    private List<UserMatchesRes> mFilterMatchList = new ArrayList<UserMatchesRes>();
    ;
    private List mFilterList = new ArrayList();
    private LinearLayout mMatchesLinLayout;
    private TextView profile_tv;
    private TextView home_tv;
    private TextView chat_tv;
    private EditText mSearchEdt;
    private ArrayList<ChatConversationRes> mChatConvList = new ArrayList<ChatConversationRes>();
    protected Apis mApis;
    private LinearLayout list_layout;
    private RelativeLayout no_match_found;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApis = new ApiClient().getApis();
        userMatchApi();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_matches_found, container, false);
        initUi();
        chatConvApi();

        return view;
    }

    public void initUi() {

        Utility.hideKeyboard(getActivity());
        mFilterMatchList = new ArrayList<UserMatchesRes>();
        mSearchEdt = (EditText) view.findViewById(R.id.search_box_et);
        mMatchesLinLayout = (LinearLayout) view.findViewById(R.id.matches_ll);
        no_match_found = (RelativeLayout) view.findViewById(R.id.no_match_found);
        list_layout = (LinearLayout) view.findViewById(R.id.list_layout);
        matchHeader = (FrameLayout) view.findViewById(R.id.match_header);
        matchHeader.setVisibility(View.VISIBLE);
        chat_tv = (TextView) view.findViewById(R.id.chat_tv);
        chat_tv.setTextColor(Color.WHITE);
        home_tv = (TextView) view.findViewById(R.id.home_tv);
        home_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.header_text_color));
        profile_tv = (TextView) view.findViewById(R.id.profile_tv);
        profile_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.header_text_color));


        matchSearchBoxRl = (RelativeLayout) view.findViewById(R.id.match_search_box_rl);
        searchTv = (TextView) view.findViewById(R.id.search_tv);
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchSearchBoxRl.setVisibility(View.VISIBLE);
            }
        });
        matchesCancelBtn = (Button) view.findViewById(R.id.matches_cancel_btn);
        matchesCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdt.getText().clear();
                matchSearchBoxRl.setVisibility(View.GONE);

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });

        matchesMessageListView = (ListView) view.findViewById(R.id.matches_message_lv);
        matchesListAdapter = new MatchesListAdapter(getActivity(), mChatConvList);
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
                    view.findViewById(R.id.no_result_ll).setVisibility(View.VISIBLE);
                } else {
                    matchesMessageListView.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.no_result_ll).setVisibility(View.GONE);
                }

                matchesListAdapter.notifyChange(tempArrayList);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void inflateMatchesUi(List<UserMatchesRes> mFilterMatchList) {
        mMatchesLinLayout.removeAllViews();
        LinearLayout rowMacthesll;
        if (mFilterMatchList != null) {
            for (int i = 0; i < mFilterMatchList.size(); i++) {
                View child = getActivity().getLayoutInflater().inflate(R.layout.row_matches, null);
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
                    Utility.glide(getActivity(), circleImageView, R.drawable.small_pic_placeholder, url);
                    // Glide.with(this).load(url).placeholder(R.drawable.small_pic_placeholder).into(circleImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameTv.setText(splitedName);
                mMatchesLinLayout.addView(child);
            }
            if (this.mFilterMatchList.size() == 0) {
                view.findViewById(R.id.matched_hsw).setVisibility(View.VISIBLE);
                view.findViewById(R.id.matched_header_rl).setVisibility(View.VISIBLE);
                View child = getActivity().getLayoutInflater().inflate(R.layout.row_placeholder_image, null);
                mMatchesLinLayout.removeAllViews();
                mMatchesLinLayout.addView(child);

            } else {
                view.findViewById(R.id.matched_hsw).setVisibility(View.VISIBLE);
                view.findViewById(R.id.matched_header_rl).setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        chatConvApi();
        userMatchApi();
    }

    public void chatConvApi() {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            //showProgressDialog(true);
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(getActivity(), PrefUtils.USER_ID));
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
                            // showProgressDialog(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListResp<ChatConversationRes>> call, Throwable t) {
                    // showProgressDialog(false);
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
        TextView unReadMessageCounter = (TextView) view.findViewById(R.id.messages_count_tv);
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
            //showProgressDialog(true);
            GeneralReq req = new GeneralReq();
            req.setUser_id(PrefUtils.getSharedPrefString(getActivity(), PrefUtils.USER_ID));
            req.setOther_id(mOtherId);
            req.setUnmatch_reason("expired");
            Call<ObjResp> call = mApis.unMatchUser(req);
            // showProgressDialog(true);
            call.enqueue(new Callback<ObjResp>() {

                @Override
                public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                    //  showProgressDialog(false);
                    if (response.isSuccessful()) {
                        FlurryAgent.logEvent(AppConstant.FLURRY_EVENT_UNMATCHES);
                    }
                }

                @Override
                public void onFailure(Call<ObjResp> call, Throwable t) {
                    //showProgressDialog(false);
                }
            });
        } else {
            // DialogUtils.showToast(UnMatchActivity.this, getResources().getString(R.string.err_network));
            new SnackBar.Builder(getActivity())
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity(), AppConstant.FLURRY_API_KEY);
    }

    @Override
    public void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }


    @Override
    public void onClick(View v) {
        String otherId = (String) v.getTag(R.string.user_id);
        String profilePic = (String) v.getTag(R.string.profile_pic);
        String name = (String) v.getTag(R.string.name);

        switch (v.getId()) {
            case R.id.row_matches_ll:

                Intent intent = new Intent(getActivity(), SocketChatActivity.class);
                intent.putExtra(PrefUtils.OTHER_ID, otherId);
                String nameArray[] = name.split(" ");
                intent.putExtra(Constants.EXTRA_OTHER_NAME, nameArray[0]);
                intent.putExtra(Constants.EXTRA_OTHER_PIC, profilePic);
                intent.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.NORMAL_TYPE);
                startActivity(intent);
                PrefUtils.setSharedPrefBooleanData(getActivity(), otherId, true);

                break;

        }
    }

    public void userMatchApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(getActivity(), PrefUtils.USER_ID));
            Call<ListResp<UserMatchesRes>> call = mApis.getUserMatches(generalReq);
            //showProgressDialog(true);
            call.enqueue(new Callback<ListResp<UserMatchesRes>>() {


                @Override
                public void onResponse(Call<ListResp<UserMatchesRes>> call, Response<ListResp<UserMatchesRes>> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            mMatchesList = response.body().getData();

                            if (mMatchesList.size() > 0) {
                                list_layout.setVisibility(View.VISIBLE);
                                no_match_found.setVisibility(View.GONE);

                            } else {
                                list_layout.setVisibility(View.GONE);
                                no_match_found.setVisibility(View.VISIBLE);

                            }

                        }
                    } else {
                        // showProgressDialog(false);
                        //chatIcon.setEnabled(true);
                        new SnackBar.Builder(getActivity())
                                .withMessage(getResources().getString(R.string.please_try_again)).show();
                    }
                }

                @Override
                public void onFailure(Call<ListResp<UserMatchesRes>> call, Throwable t) {
                    //  showProgressDialog(false);
                    // chatIcon.setEnabled(true);
                }
            });
        } else {
            new SnackBar.Builder(getActivity())
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }
}
