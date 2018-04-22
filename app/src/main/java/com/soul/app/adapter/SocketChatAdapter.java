package com.soul.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.models.res.GetChatDataRes;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by ashishkumar on 9/8/16.
 */
public class SocketChatAdapter extends BaseAdapter {
    ArrayList<GetChatDataRes.DataBean> mChatList;
    private LayoutInflater mInflator;
    private Context mContext;
    private String mOtherId;

    public SocketChatAdapter(Context context, ArrayList<GetChatDataRes.DataBean> chatList, String otherId) {
        mContext = context;
        mInflator = LayoutInflater.from(mContext);
        mChatList = chatList;
        mOtherId = otherId;
    }

    @Override
    public int getCount() {
        return mChatList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        convertView = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflator.inflate(R.layout.row_message, parent, false);

            mViewHolder.chatMatchedUserMessageTv = (EmojiconTextView)
                    convertView.findViewById(R.id.chat_matched_user_message_tv);

            mViewHolder.chatMatchedUserTimeTv = (TextView)
                    convertView.findViewById(R.id.chat_matched_user_time_tv);

            mViewHolder.chatProfileUserMessageTv = (EmojiconTextView)
                    convertView.findViewById(R.id.chat_profile_user_message_tv);

            mViewHolder.chatProfileUserTimeTv = (TextView)
                    convertView.findViewById(R.id.chat_profile_user_time_tv);

            mViewHolder.chatProfileUserLayout = (LinearLayout)
                    convertView.findViewById(R.id.chat_profile_user_layout);

            mViewHolder.chatMatchedUserLayout = (LinearLayout)
                    convertView.findViewById(R.id.chat_matched_user_layout);

            mViewHolder.gifProfiledUserIv = (ImageView)
                    convertView.findViewById(R.id.gif_profiled_user_iv);

            mViewHolder.gifMatchedUserIv = (ImageView)
                    convertView.findViewById(R.id.gif_matched_user_iv);

            /*Date Like Whats App (header)*/
            mViewHolder.llDateHeader = (LinearLayout)
                    convertView.findViewById(R.id.ll_date_header);

            mViewHolder.tvDateHeader = (TextView)
                    convertView.findViewById(R.id.tv_date_header);

            /*..End..*/

            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();

        if (mChatList.get(position).getFrom().equals(PrefUtils.getSharedPrefString(mContext, PrefUtils.USER_ID))) {
            mViewHolder.chatMatchedUserLayout.setVisibility(View.GONE);
            if (mChatList.get(position).getMessage().equals("")) {
                mViewHolder.chatProfileUserLayout.setVisibility(View.GONE);
            } else {
                mViewHolder.chatProfileUserLayout.setVisibility(View.VISIBLE);
                try {
                    //  String timestamp = mChatList.get(position).getLocal_timestamp();
                    mViewHolder.chatProfileUserTimeTv.setText(convertTime(Long.parseLong(mChatList.get(position).getLocal_timestamp())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Message  -->
                if (mChatList.get(position).getMsg_type().equals("1")) {
                    mViewHolder.chatProfileUserMessageTv.setVisibility(View.GONE);
                    mViewHolder.gifProfiledUserIv.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(mChatList.get(position).getMessage())
                            .asGif()
                            .crossFade()
                            .into(mViewHolder.gifProfiledUserIv);
                    //Utility.glideGif(mContext, mViewHolder.gifProfiledUserIv, 0, mChatList.get(position).getMessage());
                } else {
                    mViewHolder.chatProfileUserMessageTv.setVisibility(View.VISIBLE);
                    mViewHolder.gifProfiledUserIv.setVisibility(View.GONE);
                    mViewHolder.chatProfileUserMessageTv.setText(mChatList.get(position).getMessage().trim());
                    /*try {
                        mViewHolder.chatProfileUserMessageTv.setText(Utility.decodeFromNonLossyAscii(URLDecoder.decode(mChatList.get(position).getMessage().trim(), "UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/

                }
            }
        }

        if (mChatList.get(position).getFrom().equals(mOtherId)) {
            mViewHolder.chatProfileUserLayout.setVisibility(View.GONE);
            if (mChatList.get(position).getMessage().equals("")) {
                mViewHolder.chatMatchedUserLayout.setVisibility(View.GONE);
            } else {

                mViewHolder.chatMatchedUserLayout.setVisibility(View.VISIBLE);
                try {
                    mViewHolder.chatMatchedUserTimeTv.setText(convertTime(Long.parseLong(mChatList.get(position).getLocal_timestamp())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Message
                if (mChatList.get(position).getMsg_type().equals("1")) {
                    mViewHolder.chatMatchedUserMessageTv.setVisibility(View.GONE);
                    mViewHolder.gifMatchedUserIv.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(mChatList.get(position).getMessage())
                            .asGif()
                            .crossFade()
                            .into(mViewHolder.gifMatchedUserIv);
                    //   Utility.glideGif(mContext, mViewHolder.gifMatchedUserIv, 0, mChatList.get(position).getMessage());
                } else {
                    mViewHolder.gifMatchedUserIv.setVisibility(View.GONE);
                    mViewHolder.chatMatchedUserMessageTv.setVisibility(View.VISIBLE);
                    mViewHolder.chatMatchedUserMessageTv.setText(mChatList.get(position).getMessage().trim());
                   /* try {
                        mViewHolder.chatMatchedUserMessageTv.setText(Utility.decodeFromNonLossyAscii(URLDecoder.decode(mChatList.get(position).getMessage().trim(), "UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        } /*else {
            mViewHolder.chatMatchedUserLayout.setVisibility(View.GONE);
        }*/
        if (mChatList.size() > 1) {
            try {

                if (position == 0) {
                    String date1 = getFormattedDate(Long.parseLong(mChatList.get(position).getLocal_timestamp()));
                    mViewHolder.llDateHeader.setVisibility(View.VISIBLE);
                    mViewHolder.tvDateHeader.setText(date1);
                } else {
                    String date1 = getFormattedDate(Long.parseLong(mChatList.get(position).getLocal_timestamp()));
                    String date2 = getFormattedDate(Long.parseLong(mChatList.get(position - 1).getLocal_timestamp()));
                    synchronized (this) {
                        if (date1.compareTo(date2) == 0) {
                            mViewHolder.llDateHeader.setVisibility(View.GONE);
                        } else {
                            mViewHolder.llDateHeader.setVisibility(View.VISIBLE);
                            mViewHolder.tvDateHeader.setText(date1);

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public String convertTime(long time) {
        // Date date = new Date(time * 1000L);
        Date date = new Date(time);
        // Format format = new SimpleDateFormat("dd MM yyyy");
        Format format = new SimpleDateFormat("h:mm a");
        return format.format(date);
    }

    public void notifyMe(ArrayList<GetChatDataRes.DataBean> chatList) {
        mChatList = chatList;
        notifyDataSetChanged();
    }

    // Return date with Today, Yesterday, and Date (MMMM dd yyyy)
    public String getFormattedDate(long timeInMilis) {

      /*  Date date = new Date(timeInMilis);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(date);*/

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(timeInMilis);
        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today ";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday ";
        } else {

            Date date = new Date(timeInMilis);
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            return format.format(date);
        }
    }

    private class ViewHolder {

        TextView chatMatchedUserTimeTv, chatProfileUserTimeTv;
        LinearLayout chatProfileUserLayout, chatMatchedUserLayout;
        ImageView gifProfiledUserIv, gifMatchedUserIv;
        LinearLayout llDateHeader;
        TextView tvDateHeader;
        EmojiconTextView chatMatchedUserMessageTv, chatProfileUserMessageTv;
    }
}