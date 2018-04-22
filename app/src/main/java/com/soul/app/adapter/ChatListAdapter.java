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
import com.bumptech.glide.Glide;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ashishkumar on 25/7/16.
 */
public class ChatListAdapter extends BaseAdapter {
    ArrayList<GetChatDataRes.DataBean> mChatList;
    private LayoutInflater mInflator;
    private Context mContext;
    private ViewHolder mViewHolder;
    private String mOtherId;


    public ChatListAdapter(Context context, ArrayList<GetChatDataRes.DataBean> chatList, String otherId) {
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
        convertView = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflator.inflate(R.layout.row_message, parent, false);

            mViewHolder.chatMatchedUserMessageTv = (TextView)
                    convertView.findViewById(R.id.chat_matched_user_message_tv);

            mViewHolder.chatMatchedUserTimeTv = (TextView)
                    convertView.findViewById(R.id.chat_matched_user_time_tv);

            mViewHolder.chatProfileUserMessageTv = (TextView)
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


            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();

        if (mChatList.get(position).getFrom().equals(PrefUtils.getSharedPrefString(mContext, PrefUtils.USER_ID))) {
            if (mChatList.get(position).getMessage().equals("")) {
                mViewHolder.chatProfileUserLayout.setVisibility(View.INVISIBLE);
            } else {
                mViewHolder.chatMatchedUserLayout.setVisibility(View.GONE);
                mViewHolder.chatProfileUserLayout.setVisibility(View.VISIBLE);
                mViewHolder.chatProfileUserTimeTv.setText(convertTime(Long.parseLong(mChatList.get(position).getCreated_on())));
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

                }

            }

        }
        if (mChatList.get(position).getFrom().equals(mOtherId)) {
            if (mChatList.get(position).getMessage().equals("")) {
                mViewHolder.chatMatchedUserLayout.setVisibility(View.INVISIBLE);
            } else {


                mViewHolder.chatProfileUserLayout.setVisibility(View.GONE);
                mViewHolder.chatMatchedUserLayout.setVisibility(View.VISIBLE);

                mViewHolder.chatMatchedUserTimeTv.setText(convertTime(Long.parseLong(mChatList.get(position).getCreated_on())));
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
                }

            }
        }

        return convertView;
    }

    public String convertTime(long time) {
        Date date = new Date(time * 1000L);
        // Format format = new SimpleDateFormat("dd MM yyyy HH:mm a");
        Format format = new SimpleDateFormat("h:mm a");
        return format.format(date);
    }

    public void notifyMe(ArrayList<GetChatDataRes.DataBean> chatList) {
        mChatList = chatList;
        notifyDataSetChanged();
    }


    private class ViewHolder {

        TextView chatMatchedUserMessageTv, chatMatchedUserTimeTv, chatProfileUserMessageTv, chatProfileUserTimeTv;
        LinearLayout chatProfileUserLayout, chatMatchedUserLayout;
        ImageView gifProfiledUserIv, gifMatchedUserIv;

    }
}