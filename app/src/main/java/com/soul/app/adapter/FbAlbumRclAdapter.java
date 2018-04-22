package com.soul.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soul.app.R;
import com.soul.app.models.res.SocialMediaInfoBean;

import java.util.List;

/**
 * Created by techahead on 9/8/16.
 */
public abstract class FbAlbumRclAdapter extends RecyclerView.Adapter<FbAlbumRclAdapter.DataObjectHolder> implements View.OnClickListener {
    List<SocialMediaInfoBean.AlbumsEntity.DataEntity> mList;
    Context mContext;


    public FbAlbumRclAdapter(Context context, List<SocialMediaInfoBean.AlbumsEntity.DataEntity> list) {
        mList = list;
        mContext=context;
    }

    public abstract  void getUserImages(int pos);

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fb_album, parent, false);


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
       holder.albumRl.setTag(position);
       holder.albumTv.setText(mList.get(position).getName());
       holder.albumRl.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        int pos=(Integer)v.getTag();
        getUserImages(pos);

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private RelativeLayout albumRl;
        private TextView albumTv;
        public DataObjectHolder(View itemView) {
            super(itemView);
             albumRl=(RelativeLayout)itemView.findViewById(R.id.fb_album_name_rl);
             albumTv=(TextView)itemView.findViewById(R.id.fb_album_name_tv);


        }
    }
}
