package com.soul.app.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.adapter.ChatListAdapter;
import com.soul.app.adapter.GifFromChatRecyclerViewAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.customui.ActionEditText;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.req.GetChatDataReq;
import com.soul.app.models.req.GetUnreadChatDataReq;
import com.soul.app.models.req.SendMessageReq;
import com.soul.app.models.res.GetChatDataRes;
import com.soul.app.models.res.GifResponse;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.OtherProfileRes;
import com.soul.app.models.res.SendMessageRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.EndlessRecyclerOnScrollListener;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.github.mrengineer13.snackbar.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* Chat Using Web service
* */

public class ChatActivity extends BaseActivity {

    // final int intervalTime = 5000;//5 sec
    public GifResponse responseData;
    GifFromChatRecyclerViewAdapter gifFromChatRecyclerViewAdapter;
    //private List<GetUnreadChatDataRes.DataBean> getUnreadChatDataBean = new ArrayList<>();
    private String lastMsgId = "1";
    private Timer timer;
    private ImageView chatPicCi;
    private ActionEditText chatMessageEt;
    private ImageView chatSendIv;
    private String otherName;
    private String otherPic;
    //  SwipeRefreshLayout gifChatSwipeRefreshLayout;
    private ImageView chatGifIv;
    private RecyclerView chatGifRecyclerView;
    private ImageView mSettingImgVw;
    private String timeStamp = "";
    private FrameLayout chatHeader2;
    private ListView chatListView;
    private ChatListAdapter chatListAdapter;
    private ImageView chatBackIcon;
    // private SwipeRefreshLayout mSwiperefreshlayput;
    private TextView chatUserName;
    private String otherId;
    private int flaggif = 0;
    private String jsonData;
    private String TAG = ChatActivity.class.getSimpleName();
    private List<GifResponse.DataBean> dataBeanList = new ArrayList<GifResponse.DataBean>();
    // private String lastGifSearch = "";
    private int pageNumber;
    private int gifLimit = 10;


    //    private List<GetChatDataRes.DataBean> getChaDataBeen = new ArrayList<>();
    private ArrayList<GetChatDataRes.DataBean> mUserChatList = new ArrayList<GetChatDataRes.DataBean>();

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_chat;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initUi() {

        chatHeader2 = (FrameLayout) findViewById(R.id.chat_header2);
        chatHeader2.setVisibility(View.VISIBLE);

        mSettingImgVw = (ImageView) findViewById(R.id.info_icon_iv);

        //mSwiperefreshlayput = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_refresh_layout);
        chatUserName = (TextView) findViewById(R.id.chat_user_name);
        chatPicCi = (ImageView) findViewById(R.id.chat_pic_ci);

        chatGifIv = (ImageView) findViewById(R.id.chat_gif_iv);
        chatGifIv.setImageDrawable(getResources().getDrawable(R.drawable.gif_button));

        chatMessageEt = (ActionEditText) findViewById(R.id.chat_message_et);

        //gifChatSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.gif_chat_swipe_refresh_layout);

        chatSendIv = (ImageView) findViewById(R.id.chat_send_iv);

        chatGifRecyclerView = (RecyclerView) findViewById(R.id.chat_gif_recycler_view);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        chatGifRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        chatGifRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
//                dataBeanList.addAll(dataBeanList);
//                gifFromChatRecyclerViewAdapter.notifyMe(dataBeanList);
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    pageNumber = currentPage;
                    Log.e(TAG, "onLoadMore: end" + currentPage);
                    String giphyTrendingUrl = "";
                    if (!TextUtils.isEmpty(chatMessageEt.getText().toString())) {
                        //  if ()
                        //  lastGifSearch=
                        giphyTrendingUrl = "http://api.giphy.com/v1/gifs/search" + "?q=" + chatMessageEt.getText().toString().trim() + "&api_key=" + AppConstant.apiKey + "&limit=gifLimit&offset=" + (pageNumber - 1);
                    } else {
                        giphyTrendingUrl = "http://api.giphy.com/v1/gifs/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + "gifLimit&offset=" + (pageNumber - 1);
                    }
                    Log.e(TAG, giphyTrendingUrl);
                    //For Trending Url
                    callGifApi(giphyTrendingUrl);
                } else {
                    new SnackBar.Builder(ChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        gifFromChatRecyclerViewAdapter = new GifFromChatRecyclerViewAdapter(ChatActivity.this, dataBeanList) {
            @Override
            public void getPos(int pos) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    String messageType = "1";
                    String urlGif = dataBeanList.get(pos).getImages().getDownsized().getUrl();
                    sendMessageDataApi(urlGif, messageType);
                } else {
                    new SnackBar.Builder(ChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        };
        chatGifRecyclerView.setAdapter(gifFromChatRecyclerViewAdapter);
        chatGifIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    if (flaggif == 0) {
                        chatGifIv.setImageDrawable(getResources().getDrawable(R.drawable.gif_delete));
                        chatSendIv.setVisibility(View.GONE);
                        chatGifRecyclerView.setVisibility(View.VISIBLE);
                        chatMessageEt.setHint(R.string.send_gif);

                        chatMessageEt.getText().clear();
                        pageNumber = 0;
                        String giphyTrendingUrl = "http://api.giphy.com/v1/gifs/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;
                        //For Trending Url
                        callGifApi(giphyTrendingUrl);

                        flaggif++;

                    } else {
                        chatGifIv.setImageDrawable(getResources().getDrawable(R.drawable.gif_button));
                        chatSendIv.setVisibility(View.VISIBLE);
                        chatGifRecyclerView.setVisibility(View.GONE);
                        chatMessageEt.setHint(R.string.send_message);
                        chatMessageEt.getText().clear();

                        flaggif--;
                    }
                } else {
                    new SnackBar.Builder(ChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        if (getIntent().getStringExtra(Constants.EXTRA_PUSH_TYPE).equals(Constants.CHAT_TYPE)) {
            otherId = getIntent().getStringExtra(PrefUtils.OTHER_ID);
            otherProfileApi(otherId);
        } else {
            if (!TextUtils.isEmpty(getIntent().getStringExtra(PrefUtils.OTHER_ID))) {
                otherId = getIntent().getStringExtra(PrefUtils.OTHER_ID);
                otherName = getIntent().getStringExtra(Constants.EXTRA_OTHER_NAME);
                chatUserName.setText(otherName);
                otherPic = getIntent().getStringExtra(Constants.EXTRA_OTHER_PIC);
                Utility.glide(ChatActivity.this, chatPicCi, 0, otherPic);
            }
        }

        chatBackIcon = (ImageView) findViewById(R.id.chat_back_icon);
        chatBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        chatSendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatMessageEt.getText().toString().length() > 0) {
                    String messageType = "0";
                    sendMessageDataApi(chatMessageEt.getText().toString(), messageType);
                }
            }
        });

        // FOR GIF CHAT
        chatMessageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


             /*   String giphyUrl = "http://api.giphy.com/v1/gifs/search" + "?q=" + s.toString().trim() + "&api_key=" + apiKey + "&limit=" + "5";
                //For Trending Url
                callGifApi(giphyUrl);*/
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {

                    String giphyUrl = null;
                    pageNumber = 0;
                    if (s.length() > 0) {
                        //pageNumber = 0;
                        giphyUrl = "http://api.giphy.com/v1/gifs/search" + "?q=" + s.toString().trim() + "&api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;

                    } else {
                        //pageNumber = 0;
                        giphyUrl = "http://api.giphy.com/v1/gifs/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;

                    }

                    //For Trending Url
                    callGifApi(giphyUrl);
                } else {
                    new SnackBar.Builder(ChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        chatListView = (ListView) findViewById(R.id.chat_list_view);
        chatListAdapter = new ChatListAdapter(ChatActivity.this, mUserChatList, otherId);
        chatListView.setAdapter(chatListAdapter);


//        mSwiperefreshlayput.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getChatDataApi();
//            }
//        });


        getChatDataApi();
        mSettingImgVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(mSettingImgVw);
            }
        });

        chatPicCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    Intent intent = new Intent(ChatActivity.this, MatchedProfileActivity.class);
                    intent.putExtra(Constants.EXTRA_OTHER_ID, otherId);
                    startActivity(intent);
                } else {
                    new SnackBar.Builder(ChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {

                        getUnReadChatDataApi();
                        // getChatDataApi();
                    }
                },
                0,      // run first occurrence immediately
                5000); // run every two seconds
    }

    // getChatDataApi  SERVICE
    public void getChatDataApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<GetChatDataRes> call = mApis.getChatData(getChatData());

            call.enqueue(new Callback<GetChatDataRes>() {

                @Override
                public void onResponse(Call<GetChatDataRes> call, Response<GetChatDataRes> response) {
                  /*  if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/

                    if (response.isSuccessful()) {

                        if (response.body().getData().size() > 0)
                            lastMsgId = response.body().getData().get(response.body().getData().size() - 1).getChat_screenid();
//                    mUserChatList.clear();
                        mUserChatList.addAll(response.body().getData());
//                    getChaDataBeen.addAll(response.body().getData());
//                    chatListAdapter.notifyDataSetInvalidated();
                        chatListAdapter.notifyMe(mUserChatList);


                    } else {

                    }
                }

                @Override
                public void onFailure(Call<GetChatDataRes> call, Throwable t) {
                   /* if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/

                }
            });
        } else {
            new SnackBar.Builder(ChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    // SEND CHAT DATA REQ
    private GetChatDataReq getChatData() {

        GetChatDataReq getChatDataReq = new GetChatDataReq();
        getChatDataReq.setFrom(PrefUtils.getSharedPrefString(ChatActivity.this, PrefUtils.USER_ID));
        getChatDataReq.setTo(otherId);
        getChatDataReq.setEnd_limit("10");
        getChatDataReq.setStart_limit("0");
        getChatDataReq.setCreated_on(timeStamp);
//        if (mUserChatList.size() > 0) {
//            timeStamp = mUserChatList.get((mUserChatList.size() - 1)).getCreated_on();
//            getChatDataReq.setCreated_on(timeStamp);
//        } else {
//            getChatDataReq.setCreated_on(timeStamp);
//        }

        return getChatDataReq;
    }

    // SEND MESSAGE SERVICE
    public void sendMessageDataApi(String message, String messageType) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<SendMessageRes> call = mApis.sendMessageData(sendMessage(message, messageType));
            // showProgressDialog(true);
            call.enqueue(new Callback<SendMessageRes>() {

                @Override
                public void onResponse(Call<SendMessageRes> call, Response<SendMessageRes> response) {
                  /*  if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/

                    if (response.isSuccessful()) {
//                    getChatDataApi();
                        //  showProgressDialog(false);
                        chatMessageEt.getText().clear();
//                    SendMessageRes.DataBean sendMessageDataBeen = response.body().getData();
//                    lastMsgId = sendMessageDataBeen.getChat_screenid() + "";
//                    GetChatDataRes.DataBean dataBean = new GetChatDataRes.DataBean();
//                    dataBean.setMessage(sendMessageDataBeen.getMessage());
//                    dataBean.setMsg_type(sendMessageDataBeen.getMsg_type());
//                    dataBean.setCreated_on(sendMessageDataBeen.getCreated_on());
//                    dataBean.setTo(sendMessageDataBeen.getTo());
//                    dataBean.setChat_screenid(sendMessageDataBeen.getChat_screenid() + "");
//                    dataBean.setFrom(sendMessageDataBeen.getFrom());
//
//                    mUserChatList.add(dataBean);
//                    chatListAdapter.notifyMe(mUserChatList);

                    } else {
                        //   showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<SendMessageRes> call, Throwable t) {
                    /*if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/
                    //   showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(ChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    // SEND MESSAGE SERVICE  REQ
    private SendMessageReq sendMessage(String message, String messageType) {
        SendMessageReq sendMessageReq = new SendMessageReq();
        sendMessageReq.setFrom(PrefUtils.getSharedPrefString(ChatActivity.this, PrefUtils.USER_ID));
        sendMessageReq.setTo(otherId);
        sendMessageReq.setMsg_type(messageType);
        sendMessageReq.setAttachment("");
        sendMessageReq.setVideo_thumb("");
        sendMessageReq.setMessage(message);
        return sendMessageReq;
    }

    //GET UNREAD CHAT DATA SERVICE (UNU)
    public void getUnReadChatDataApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<GetChatDataRes> call = mApis.getUnreadChatData(getUnreadChatData());
            call.enqueue(new Callback<GetChatDataRes>() {
                @Override
                public void onResponse(Call<GetChatDataRes> call, Response<GetChatDataRes> response) {
                   /* if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/

                    if (response.isSuccessful()) {
//                        for (int i =0;i<response.body().getData().size();i++){
//                            if (lastMsgId.equalsIgnoreCase(response.body().getData().get(i).getChat_screenid()))
//                            {
//                                response.body().getData().remove(i);
//                                break;
//                            }
//                        }
                        if (response.body().getData().size() > 0) {
                            lastMsgId = response.body().getData().get(response.body().getData().size() - 1).getChat_screenid();
                            mUserChatList.addAll(response.body().getData());
                            chatListAdapter.notifyMe(mUserChatList);
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<GetChatDataRes> call, Throwable t) {
                 /*   if (mSwiperefreshlayput.isRefreshing())
                        mSwiperefreshlayput.setRefreshing(false);*/

                }
            });
        } else {
            new SnackBar.Builder(ChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    //GET UNREAD CHAT DATA REQ
    private GetUnreadChatDataReq getUnreadChatData() {
        GetUnreadChatDataReq getUnreadChatDataReq = new GetUnreadChatDataReq();

        getUnreadChatDataReq.setFrom(PrefUtils.getSharedPrefString(ChatActivity.this, PrefUtils.USER_ID));
        getUnreadChatDataReq.setTo(otherId);
        getUnreadChatDataReq.setId(lastMsgId);

        return getUnreadChatDataReq;
    }

    // Other Profile Api
    private void otherProfileApi(String otherId) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(ChatActivity.this, PrefUtils.USER_ID));
            generalReq.setOther_id(otherId);
            Call<ObjResp<OtherProfileRes>> call = mApis.otherProfile(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<OtherProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<OtherProfileRes>> call, Response<ObjResp<OtherProfileRes>> response) {
                    if (response.isSuccessful()) {
                        Log.e("response", response.toString());
                        try {
                            otherName = response.body().getData().getProfileDetails().getUser_name();
                            String nameArray[] = otherName.split(" ");
                            String spilledName = nameArray[0];
                            chatUserName.setText(spilledName);
                            otherPic = response.body().getData().getProfileDetails().getProfile_pic();
                            Utility.glide(ChatActivity.this, chatPicCi, 0, otherPic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        showProgressDialog(false);
                    } else {
                        showProgressDialog(false);
                    }
                }

                @Override
                public void onFailure(Call<ObjResp<OtherProfileRes>> call, Throwable t) {
                    showProgressDialog(false);
                }
            });
        } else {
            new SnackBar.Builder(ChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    public void showPopup(ImageView iv) {
        PopupMenu popup = new PopupMenu(this, iv);
        popup.getMenuInflater().inflate(R.menu.chat_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {
                    case R.id.report:
                        intent = new Intent(ChatActivity.this, ChatReportActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, otherId);
                        startActivity(intent);
                        break;
                    case R.id.unmatch:
                        intent = new Intent(ChatActivity.this, UnMatchActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, otherId);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        popup.show();
    }

    @Override
    public String getName() {
        return ChatActivity.class.getName();
    }


    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        Utility.putStringValueInSharedPreference(ChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "inActive");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Utility.putStringValueInSharedPreference(ChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "inActive");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.putStringValueInSharedPreference(ChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "active");
    }


    /*  private void UnmatchApi() {
          if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
              showProgressDialog(true);
              GeneralReq req = new GeneralReq();
              req.setUser_id(PrefUtils.getSharedPrefString(ChatActivity.this, PrefUtils.USER_ID));
              req.setOther_id(otherId);
              Call<ObjResp> call = mApis.unMatchUser(req);
              showProgressDialog(true);
              call.enqueue(new Callback<ObjResp>() {

                  @Override
                  public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                      if (response.isSuccessful()) {
                          showProgressDialog(false);
                          DialogUtils.showToast(ChatActivity.this, response.body().getMsg());
                          finish();
                      } else {
                          showProgressDialog(false);
                      }
                  }

                  @Override
                  public void onFailure(Call<ObjResp> call, Throwable t) {
                      showProgressDialog(false);
                  }
              });
          } else {
              // DialogUtils.showToast(ChatActivity.this, getResources().getString(R.string.err_network));
              new SnackBar.Builder(ChatActivity.this)
                      .withMessage(getResources().getString(R.string.err_network)).show();
          }
      }
  */
    // FOR Trending GIFs (first time call the service automatically)
    private void callGifApi(String giphyTrendingUrl) {

        //http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC
        // String apiKey = "dc6zaTOxFJmzC"; //Giphy's Public API Key
        // String giphyTrendingUrl = "http://api.giphy.com/v1/gifs/trending" + "?api_key=" + apiKey + "&limit=" + "10";

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(giphyTrendingUrl)
                    .build();
            okhttp3.Call call = client.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                    if (response.isSuccessful()) {
                        jsonData = response.body().string();
                        try {
                            getGifResponse(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v(TAG, "Giphy ResponseData:" + jsonData);
                    } else {
                        Log.i(TAG, "ResponseData Unsuccessful");
                    }
                }
            });

        } else {
            //  Log.e(TAG, "Internet connection not available");
            new SnackBar.Builder(ChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    private void getGifResponse(String jsonData) throws JSONException {
        JSONObject giphy = new JSONObject(jsonData);
        JSONArray data = giphy.getJSONArray("data");
        if (data.length() > 0) {
            responseData = Utility.fromJson(giphy.toString(), GifResponse.class);
            if (pageNumber == 0)
                dataBeanList.clear();
            dataBeanList.addAll(responseData.getData());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gifFromChatRecyclerViewAdapter.notifyMe(dataBeanList);
                }
            });
        }
    }
}


