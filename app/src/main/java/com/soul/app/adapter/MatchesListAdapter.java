package com.soul.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.activity.SocketChatActivity;
import com.soul.app.customui.CircleImageView;
import com.soul.app.models.res.ChatConversationRes;
import com.soul.app.utils.Constants;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by ashishkumar on 21/6/16.
 */
public class MatchesListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflator;
    private ArrayList<ChatConversationRes> mChatConversationList;

    public MatchesListAdapter(Context context, ArrayList<ChatConversationRes> chatConversationList) {
        this.mContext = context;
        mInflator = LayoutInflater.from(mContext);
        mChatConversationList = chatConversationList;
    }

    @Override
    public int getCount() {
        return mChatConversationList.size();
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
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflator.inflate(R.layout.row_message_listview, parent, false);
            mViewHolder.rowMatchesPicCi = (CircleImageView) convertView.findViewById(R.id.row_matches_pic_ci);
            mViewHolder.rowNameTv = (TextView) convertView.findViewById(R.id.row_name_tv);
            mViewHolder.rowMessageTv = (EmojiconTextView) convertView.findViewById(R.id.row_message_tv);
            mViewHolder.rowMatchesRl = (RelativeLayout) convertView.findViewById(R.id.row_matches_rl);
            mViewHolder.textView_new = (TextView) convertView.findViewById(R.id.textView_new);
            mViewHolder.ivGifIcon = (ImageView) convertView.findViewById(R.id.iv_gif_icon);
            //  mViewHolder.ivReplyIcon = (ImageView) convertView.findViewById(R.id.iv_reply_icon);

            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();
        mViewHolder.rowNameTv.setText(mChatConversationList.get(position).getUser_name() + ",  " + mChatConversationList.get(position).getAge());

        if (mChatConversationList.get(position).getUnread_msg_count() == 0) {
            mViewHolder.rowMessageTv.setTextColor(Color.parseColor("#6a6a6a"));
            mViewHolder.textView_new.setVisibility(View.GONE);
        } else {
            mViewHolder.rowMessageTv.setTextColor(Color.parseColor("#ff4b52"));
            mViewHolder.textView_new.setVisibility(View.VISIBLE);
            mViewHolder.textView_new.setTextColor(Color.parseColor("#ff4b52"));
        }

//        String profileUserId = PrefUtils.getSharedPrefString(mContext, PrefUtils.USER_ID);
//        if (profileUserId.equals(mChatConversationList.get(position).getUser_id())) {
//            mViewHolder.ivReplyIcon.setVisibility(View.VISIBLE);
//        } else {
//            mViewHolder.ivReplyIcon.setVisibility(View.GONE);
//        }

        if (mChatConversationList.get(position).getMsg_type().equals("1")) {
            mViewHolder.rowMessageTv.setText("GIF");
            mViewHolder.ivGifIcon.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.rowMessageTv.setText(mChatConversationList.get(position).getMessage());
           /* try {
                mViewHolder.rowMessageTv.setText(Utility.decodeFromNonLossyAscii(URLDecoder.decode(mChatConversationList.get(position).getMessage(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            mViewHolder.ivGifIcon.setVisibility(View.GONE);
        }

        mViewHolder.rowMatchesRl.setTag(R.string.name, mChatConversationList.get(position).getUser_name());
        mViewHolder.rowMatchesRl.setTag(R.string.user_id, mChatConversationList.get(position).getUser_id());
        mViewHolder.rowMatchesRl.setTag(R.string.profile_pic, mChatConversationList.get(position).getProfile_pic());

        mViewHolder.rowMatchesRl.setOnClickListener(this);
        String url = mChatConversationList.get(position).getProfile_pic();
        Utility.glide(mContext, mViewHolder.rowMatchesPicCi, R.drawable.small_pic_placeholder, url);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        String otherId = (String) v.getTag(R.string.user_id);
        String name = (String) v.getTag(R.string.name);
        String otherPic = (String) v.getTag(R.string.profile_pic);
        switch (v.getId()) {

            case R.id.row_matches_rl:
                //  Intent intent = new Intent(mContext, ChatActivity.class);
                Intent intent = new Intent(mContext, SocketChatActivity.class);
                intent.putExtra(PrefUtils.OTHER_ID, otherId);
                intent.putExtra(Constants.EXTRA_OTHER_NAME, name);
                intent.putExtra(Constants.EXTRA_OTHER_PIC, otherPic);
                intent.putExtra(Constants.EXTRA_PUSH_TYPE, Constants.NORMAL_TYPE);
                mContext.startActivity(intent);
                break;
        }
    }

    public void notifyChange(ArrayList<ChatConversationRes> list) {
        mChatConversationList = list;
        notifyDataSetChanged();
    }

    private class ViewHolder {

        public CircleImageView rowMatchesPicCi;
        public ImageView ivGifIcon;//, ivReplyIcon;
        public TextView rowNameTv, textView_new;
        public RelativeLayout rowMatchesRl;
        EmojiconTextView rowMessageTv;

    }

}
