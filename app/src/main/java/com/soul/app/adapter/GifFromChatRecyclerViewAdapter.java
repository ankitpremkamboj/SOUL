package com.soul.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soul.app.R;
import com.soul.app.models.res.GifResponse;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ashishkumar on 2/8/16.
 */
public abstract class GifFromChatRecyclerViewAdapter extends RecyclerView.Adapter<GifFromChatRecyclerViewAdapter.MyViewHolder> {

    public Context mContext;
    public List<GifResponse.DataBean> mDataBeanList;

    public GifFromChatRecyclerViewAdapter(Context context, List<GifResponse.DataBean> dataBeanList) {
        this.mContext = context;
        this.mDataBeanList = dataBeanList;

    }

    public abstract void getPos(int pos);

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_gif, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // if (mDataBeanList.size() > 0) {
        try {

            Glide.with(mContext)
                    //.load(mDataBeanList.get(position).getImages().getDownsized().getUrl())
                    .load(mDataBeanList.get(position).getImages().getFixed_width_downsampled().getUrl())
                    .asGif()
                    .crossFade()
                    .into(holder.chatGifIv);

            // Utility.glideGif(mContext, holder.chatGifIv, 0, mDataBeanList.get(position).getImages().getDownsized().getUrl());

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.chatgifll.setTag(position);
        holder.chatgifll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (Integer) v.getTag();
                getPos(pos);

            }
        });
        // }

    }


    public void notifyMe(List<GifResponse.DataBean> mDataBeanList) {
        this.mDataBeanList = mDataBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView chatGifIv;
        public LinearLayout chatgifll;

        public MyViewHolder(View itemView) {
            super(itemView);
            chatGifIv = (ImageView) itemView.findViewById(R.id.row_gif_image);
            chatgifll = (LinearLayout) itemView.findViewById(R.id.chat_gif_ll);

        }
    }


}
