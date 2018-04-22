package com.soul.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mrengineer13.snackbar.SnackBar;
import com.soul.app.R;
import com.soul.app.adapter.GifFromChatRecyclerViewAdapter;
import com.soul.app.adapter.SocketChatAdapter;
import com.soul.app.application.ApplicationController;
import com.soul.app.constants.AppConstant;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.req.GetChatDataReq;
import com.soul.app.models.res.GetChatDataRes;
import com.soul.app.models.res.GifResponse;
import com.soul.app.models.res.ObjResp;
import com.soul.app.models.res.OtherProfileRes;
import com.soul.app.socketchat.WebSocketClient;
import com.soul.app.utils.Constants;
import com.soul.app.utils.EndlessRecyclerOnScrollListener;
import com.soul.app.utils.Lg;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashishkumar on 8/8/16.
 * Chat Using Socket
 */
public class SocketChatActivity extends BaseActivity {
    private static final String TAG = SocketChatActivity.class.getSimpleName();
    public static String myGiphyUrl = "http://api.giphy.com/v1/gifs";
    public GifResponse responseData;
    //   protected Snackbar mSnackbar;
    GifFromChatRecyclerViewAdapter gifFromChatRecyclerViewAdapter;
    // private String lastMsgId = "1";
    private List<GifResponse.DataBean> dataBeanList = new ArrayList<GifResponse.DataBean>();
    private int pageNumber;
    private int gifLimit = 50;  // 10
    private RecyclerView chatGifRecyclerView;
    private EmojiconEditText chatMessageEt;
    private String jsonData;
    private LinearLayout mGifHeader;
    private String mMessageType = "0";
    private FrameLayout chatHeader2;
    private ListView mChatListView;
    private SocketChatAdapter socketChatAdapter;
    private ImageView mSettingImgVw;
    private ImageView chatBackIcon;
    //  private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView chatUserName;
    private String mOtherId;
    private String mOtherName;
    private String mOtherPic;
    private ImageView chatPicCi;
    private ImageView chatGifIv;
    private EmojiconEditText mChatMsgActionEditText;
    // private RelativeLayout mParent;
    private WebSocketClient mClient;
    //  private String timeStamp = "";
    private List<BasicNameValuePair> mExtraHeaders = Arrays.asList(new BasicNameValuePair("Cookie", "session=test"));
    private ImageView chatSendIv;
    private ArrayList<GetChatDataRes.DataBean> mUserChatList;
    private String mCreatedOn;
    //  private String mLocalTimeStamp;
    private boolean ispreviousData = false;
    private String urlGif;
    private int flaggif = 0;
    EmojIconActions emojIcon;
    ImageView emojiButton;
    View rootView;
//    private CountDownTimer mTimer = new CountDownTimer(30000, 1000) {
//        public void onTick(long millisUntilFinished) {
//        }
//
//        public void onFinish() {
//            initChat();
//            start();
//        }
//    };

    @Override
    public int setLayout() {
        Utility.setStatusBarGradiant(this);
        return R.layout.activity_chat;
    }

    @Override
    public void initUi() {
        rootView = findViewById(R.id.message_parent);
        mUserChatList = new ArrayList<GetChatDataRes.DataBean>();
        chatHeader2 = (FrameLayout) findViewById(R.id.chat_header2);
        chatHeader2.setVisibility(View.VISIBLE);
        mSettingImgVw = (ImageView) findViewById(R.id.info_icon_iv);
        //   mParent = (RelativeLayout) findViewById(R.id.swipe_home_activity);
        // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_refresh_layout);
        chatUserName = (TextView) findViewById(R.id.chat_user_name);
        chatPicCi = (ImageView) findViewById(R.id.chat_pic_ci);
        chatGifIv = (ImageView) findViewById(R.id.chat_gif_iv);
        chatGifIv.setImageDrawable(getResources().getDrawable(R.drawable.gif_button));
        chatGifIv.setVisibility(View.GONE);
        chatSendIv = (ImageView) findViewById(R.id.chat_send_iv);
        mChatMsgActionEditText = (EmojiconEditText) findViewById(R.id.chat_message_et);
        mChatMsgActionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!mClient.isConnected())
                        mClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        chatMessageEt = (EmojiconEditText) findViewById(R.id.chat_message_et);

        chatGifRecyclerView = (RecyclerView) findViewById(R.id.chat_gif_recycler_view);
        mGifHeader = (LinearLayout) findViewById(R.id.gif_header);
        mGifHeader.setVisibility(View.GONE);
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
                        giphyTrendingUrl = myGiphyUrl + "/search" + "?q=" + chatMessageEt.getText().toString().trim() + "&api_key=" + AppConstant.apiKey + "&limit=gifLimit&offset=" + (pageNumber - 1);
                    } else {
                        giphyTrendingUrl = myGiphyUrl + "/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + "gifLimit&offset=" + (pageNumber - 1);
                    }
                    Log.e(TAG, giphyTrendingUrl);
                    //For Trending Url
                    callGifApi(giphyTrendingUrl);
                } else {
                    new SnackBar.Builder(SocketChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        gifFromChatRecyclerViewAdapter = new GifFromChatRecyclerViewAdapter(SocketChatActivity.this, dataBeanList) {
            @Override
            public void getPos(int pos) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    mMessageType = "1";
                    urlGif = dataBeanList.get(pos).getImages().getDownsized().getUrl();
                    sendChat(AppConstant.CHAT_MID_LAUNCH, mMessageType);
                } else {
                    new SnackBar.Builder(SocketChatActivity.this)
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
                        chatSendIv.setVisibility(View.VISIBLE);
                        chatGifRecyclerView.setVisibility(View.VISIBLE);
                        mGifHeader.setVisibility(View.VISIBLE);
                        chatMessageEt.setHint(R.string.send_gif);

                        chatMessageEt.getText().clear();
                        pageNumber = 0;
                        String giphyTrendingUrl = myGiphyUrl + "/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;
                        //For Trending Url
                        callGifApi(giphyTrendingUrl);

                        flaggif++;

                    } else {
                        chatGifIv.setImageDrawable(getResources().getDrawable(R.drawable.gif_button));
                        chatSendIv.setVisibility(View.VISIBLE);
                        chatGifRecyclerView.setVisibility(View.GONE);
                        mGifHeader.setVisibility(View.GONE);
                        chatMessageEt.setHint(R.string.send_message);
                        chatMessageEt.getText().clear();

                        flaggif--;
                    }
                } else {
                    new SnackBar.Builder(SocketChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        }); // FOR GIF CHAT
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
                        giphyUrl = myGiphyUrl + "/search" + "?q=" + s.toString().trim() + "&api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;

                    } else {
                        //pageNumber = 0;
                        giphyUrl = myGiphyUrl + "/trending" + "?api_key=" + AppConstant.apiKey + "&limit=" + gifLimit;

                    }

                    //For Trending Url
                    callGifApi(giphyUrl);
                } else {
                    new SnackBar.Builder(SocketChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            if (getIntent().getStringExtra(Constants.EXTRA_PUSH_TYPE).equals(Constants.CHAT_TYPE)) {
                mOtherId = getIntent().getStringExtra(PrefUtils.OTHER_ID);
                otherProfileApi(mOtherId);
            } else {

                try {
                    if (!TextUtils.isEmpty(getIntent().getStringExtra(PrefUtils.OTHER_ID))) {
                        mOtherId = getIntent().getStringExtra(PrefUtils.OTHER_ID);
                        mOtherName = getIntent().getStringExtra(Constants.EXTRA_OTHER_NAME);
                        chatUserName.setText(mOtherName);
                        mOtherPic = getIntent().getStringExtra(Constants.EXTRA_OTHER_PIC);
                        Glide.with(this).load(mOtherPic).into(chatPicCi);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        socketChatAdapter = new SocketChatAdapter(SocketChatActivity.this, mUserChatList, mOtherId);
        mChatListView.setAdapter(socketChatAdapter);

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ispreviousData = true;
                getChatDataApi();
            }
        });*/
        chatBackIcon = (ImageView) findViewById(R.id.chat_back_icon);
        chatBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setResult(Constants.CHAT,);
                finish();
            }
        });

        mSettingImgVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(mSettingImgVw);
            }
        });

        chatSendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChatMsgActionEditText.getText().toString().trim().length() > 0) {
                    mMessageType = "0";
                    sendChat(AppConstant.CHAT_MID_LAUNCH, mMessageType);
                    //  mChatMsgActionEditText.setText("");
                }
            }
        });

        chatPicCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                    Intent intent = new Intent(SocketChatActivity.this, MatchedProfileActivity.class);
                    intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
                    intent.putExtra(Constants.HIDE_LIKE_AND_DISLIKE, "1"); // 1 for hide
                    startActivity(intent);
                } else {
                    new SnackBar.Builder(SocketChatActivity.this)
                            .withMessage(getResources().getString(R.string.err_network)).show();
                }
            }
        });

        getChatDataApi();

        emojiButton = (ImageView) findViewById(R.id.emoji_icon);
        emojIcon = new EmojIconActions(this, rootView, chatMessageEt, emojiButton);
        emojIcon.ShowEmojIcon();

        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });
    }

    private void initChat() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            if (mClient == null) {
                final String userId = PrefUtils.getSharedPrefString(SocketChatActivity.this, PrefUtils.USER_ID);
                mClient = new WebSocketClient(URI.create(AppConstant.CHAT_BASE_URL + userId), new WebSocketClient.Listener() {
                    @Override
                    public void onConnect() {
                        showProgressDialog(false);
                        mClient.send("FT_CONNECT||||" + userId + "||||^^^^^^");
                        Lg.i(TAG, "Chat Connected");
                        sendChat(AppConstant.CHAT_FIRST_LAUNCH, mMessageType);
                        // mTimer.start();
                        //mChatMsgActionEditText.setEnabled(true);
                    }

                    @Override
                    public void onMessage(String message) {
                        Lg.i("Salaam Swipe Chat", "Chat Received " + message);
                        try {
                            JSONObject chatJson = new JSONObject(message);
                            String fromUserId = chatJson.optString(AppConstant.FROM);
                            //  String side = chatJson.getString(AppConstant.SIDE);
                            String userId = PrefUtils.getSharedPrefString(SocketChatActivity.this, PrefUtils.USER_ID);
                            if (chatJson.optString(AppConstant.MESSAGE).isEmpty())
                                return;
                            if (!userId.equals(fromUserId)) {
                                // mUserChatList.add(getMessageListEntity(chatJson));
                                mUserChatList.add(getMessageListEntity_OTHERUSER(chatJson));
                                setAdapter(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Message from server
                    }

                    @Override
                    public void onMessage(byte[] data) {

                    }

                    @Override
                    public void onDisconnect(int code, String reason) {
                        Lg.i("Salaam Swipe Chat", "Chat Disconnected" + reason);
                    }

                    @Override
                    public void onError(Exception error) {
                        try {
                            Lg.i("Salaam Swipe Chat", "Chat Error " + error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, mExtraHeaders);
                showProgressDialog(true);
                //mChatMsgActionEditText.setEnabled(true);
                mClient.connect();
            } else {
                if (!mClient.isConnected())
                    mClient.connect();
                else {
                    sendChat(AppConstant.CHAT_FIRST_LAUNCH, mMessageType);
                }
            }
        } else {
            new SnackBar.Builder(SocketChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    // FROM DEVICE ID MESAGE
    private GetChatDataRes.DataBean getMessageListEntity(JSONObject chatJson) {
        GetChatDataRes.DataBean resultEntity = new GetChatDataRes.DataBean();
        resultEntity.setFrom(chatJson.optString(AppConstant.FROM));
        resultEntity.setDeviceTimeStamp(System.currentTimeMillis() + "");
        resultEntity.setTo(chatJson.optString(AppConstant.TO));
        resultEntity.setMessage(chatJson.optString(AppConstant.MESSAGE));
        resultEntity.setMsg_type(chatJson.optString(AppConstant.MESSAGE_TYPE)); //message_type
        resultEntity.setLocal_timestamp(String.valueOf(chatJson.optString(AppConstant.LOCAL_TIME_STAMP)));
        String string = UUID.randomUUID().toString();

        return resultEntity;

    }

    // FROM OTHER ID MESAGE
    private GetChatDataRes.DataBean getMessageListEntity_OTHERUSER(JSONObject chatJson) {
        GetChatDataRes.DataBean resultEntity = new GetChatDataRes.DataBean();
        resultEntity.setFrom(chatJson.optString(AppConstant.FROM));
        resultEntity.setDeviceTimeStamp(System.currentTimeMillis() + "");
        resultEntity.setTo(chatJson.optString(AppConstant.TO));
        resultEntity.setMessage(chatJson.optString(AppConstant.MESSAGE));
        resultEntity.setMsg_type(chatJson.optString(AppConstant.MSG_TYPE)); //message_type
        resultEntity.setLocal_timestamp(String.valueOf(chatJson.optString(AppConstant.LOCAL_TIME_STAMP)));
        String string = UUID.randomUUID().toString();

        return resultEntity;

    }


    private void sendChat(final int launchState, final String messageType) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            callSendChatAPi(launchState, messageType);
        } else {
           /* mSnackbar = SnackBarBuilder.make(mParent, getString(R.string.err_network))
                    .setActionText(getString(R.string.retry))
                    .setActionTextColor(ContextCompat.getColor(SocketChatActivity.this, R.color.colorWhite))
                    .onSnackBarClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendChat(launchState, messageType);
                        }
                    }).build();*/
            new SnackBar.Builder(SocketChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    private void callSendChatAPi(int launchState, String messageType) {
        if (mClient != null) {
            if (mClient.isConnected()) {
                String chatJson = getChatJson(messageType);
                mClient.send(chatJson);

                if (launchState == AppConstant.CHAT_MID_LAUNCH) {
                    try {
                        mUserChatList.add(getMessageListEntity(new JSONObject(chatJson)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setAdapter(true);
                }

            } else {
                /*mSnackbar = SnackBarBuilder.make(mParent, getString(R.string.not_connected))
                        .setActionText(getString(R.string.connect)).setActionTextColor(ContextCompat.getColor(SocketChatActivity.this, R.color.colorWhite))
                        .onSnackBarClicked(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initChat();
                            }
                        }).build();*/
                new SnackBar.Builder(SocketChatActivity.this)
                        .withMessage(getResources().getString(R.string.connection_failed)).show();
            }
        }
    }

    private String getChatJson(String messageType) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConstant.REQUEST_TYPE, "TEXT_USER");
            jsonObject.put(AppConstant.FROM, PrefUtils.getSharedPrefString(SocketChatActivity.this, PrefUtils.USER_ID));
            jsonObject.put(AppConstant.TO, mOtherId);
            jsonObject.put(AppConstant.MESSAGE_TYPE, messageType);
            jsonObject.put(AppConstant.MSG_UNIQUE_ID, UUID.randomUUID().toString());
            if (messageType.equals("0")) {
                //jsonObject.put(AppConstant.MESSAGE, URLEncoder.encode(Utility.encodeToNonLossyAscii(mChatMsgActionEditText.getText().toString()), "UTF-8"));

                jsonObject.put(AppConstant.MESSAGE, mChatMsgActionEditText.getText().toString());
            } else
                jsonObject.put(AppConstant.MESSAGE, urlGif);
            jsonObject.put(AppConstant.LOCAL_TIME_STAMP, System.currentTimeMillis());

        } catch (JSONException e) {
            e.printStackTrace();
        } /*catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return jsonObject.toString();
    }


    private void setAdapter(final boolean isClearEditTextData) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (socketChatAdapter != null) {
                    socketChatAdapter.notifyDataSetChanged();
                    if (isClearEditTextData)
                        mChatMsgActionEditText.setText("");
                } else {
                    socketChatAdapter = new SocketChatAdapter(SocketChatActivity.this, mUserChatList, mOtherId);
                    mChatListView.setAdapter(socketChatAdapter);
                }
                mChatListView.post(new Runnable() {
                    @Override
                    public void run() {
                        mChatListView.setSelection(socketChatAdapter.getCount());
                    }
                });
            }
        }, 100);
    }

    public void showPopup(ImageView iv) {
        PopupMenu popup = new PopupMenu(this, iv);
        popup.getMenuInflater().inflate(R.menu.chat_popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {
                    case R.id.report:
                        intent = new Intent(SocketChatActivity.this, ChatReportActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
                        startActivity(intent);
                        break;
                    case R.id.unmatch:
                        intent = new Intent(SocketChatActivity.this, UnMatchActivity.class);
                        intent.putExtra(Constants.EXTRA_OTHER_ID, mOtherId);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        popup.show();
    }

    // Get Chat Data Response
    public void getChatDataApi() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            Call<GetChatDataRes> call = mApis.getChatData(getChatData());

            call.enqueue(new Callback<GetChatDataRes>() {

                @Override
                public void onResponse(Call<GetChatDataRes> call, Response<GetChatDataRes> response) {
                   /* if (mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);*/
                    // ispreviousData = false;
                    if (response.isSuccessful()) {

                        if (response.body().getData().size() > 0)
                            // lastMsgId = response.body().getData().get(response.body().getData().size() - 1).getChat_screenid();

                            if (ispreviousData) {
                                ArrayList<GetChatDataRes.DataBean> tempList = new ArrayList<GetChatDataRes.DataBean>(response.body().getData());
                                tempList.addAll(mUserChatList);
                                mUserChatList.clear();
                                mUserChatList.addAll(tempList);
                                ispreviousData = false;

                            } else {
                                mUserChatList.clear();
                                mUserChatList.addAll(response.body().getData());
                            }
                        socketChatAdapter.notifyMe(mUserChatList);

                    }
                }

                @Override
                public void onFailure(Call<GetChatDataRes> call, Throwable t) {
                    ispreviousData = false;
                    /*if (mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);*/

                }
            });
        } else {
            new SnackBar.Builder(SocketChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    // SEND CHAT DATA REQ
    private GetChatDataReq getChatData() {
        GetChatDataReq getChatDataReq = new GetChatDataReq();
        getChatDataReq.setFrom(PrefUtils.getSharedPrefString(SocketChatActivity.this, PrefUtils.USER_ID));
        getChatDataReq.setTo(mOtherId);
        getChatDataReq.setEnd_limit("500");   // 100
        getChatDataReq.setStart_limit("0");
        if (!mUserChatList.isEmpty()) {
            mCreatedOn = mUserChatList.get(0).getCreated_on(); //mUserChatList.size() - 1
            getChatDataReq.setCreated_on(mCreatedOn);
        } else {
            getChatDataReq.setCreated_on(mCreatedOn);
        }

        return getChatDataReq;
    }

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
            new SnackBar.Builder(SocketChatActivity.this)
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

    // Other Profile Api  ......................
    private void otherProfileApi(String otherId) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            GeneralReq generalReq = new GeneralReq();
            generalReq.setUser_id(PrefUtils.getSharedPrefString(SocketChatActivity.this, PrefUtils.USER_ID));
            generalReq.setOther_id(otherId);
            Call<ObjResp<OtherProfileRes>> call = mApis.otherProfile(generalReq);
            showProgressDialog(true);
            call.enqueue(new Callback<ObjResp<OtherProfileRes>>() {
                @Override
                public void onResponse(Call<ObjResp<OtherProfileRes>> call, Response<ObjResp<OtherProfileRes>> response) {
                    if (response.isSuccessful()) {
                        Log.e("response", response.toString());
                        try {
                            mOtherName = response.body().getData().getProfileDetails().getUser_name();
                            String nameArray[] = mOtherName.split(" ");
                            String spilledName = nameArray[0];
                            chatUserName.setText(spilledName);
                            mOtherPic = response.body().getData().getProfileDetails().getProfile_pic();
                            Glide.with(SocketChatActivity.this).load(mOtherPic).into(chatPicCi);
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
            new SnackBar.Builder(SocketChatActivity.this)
                    .withMessage(getResources().getString(R.string.err_network)).show();
        }
    }

    @Override
    public String getName() {
        return SocketChatActivity.class.getName();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mClient != null) {
            mClient.disconnect();
//            mTimer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (mTimer != null)
//            mTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.putStringValueInSharedPreference(SocketChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "active");
        initChat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  mTimer.cancel();
        Utility.putStringValueInSharedPreference(SocketChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "inActive");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.putStringValueInSharedPreference(SocketChatActivity.this, Constants.CHAT_ACTIVITY_AVAILABLE, "inActive");
    }

}
