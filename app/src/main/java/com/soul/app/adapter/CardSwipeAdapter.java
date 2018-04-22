package com.soul.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soul.app.R;
import com.soul.app.interfaces.PagerListener;
import com.soul.app.models.res.UserListRes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by techahead on 30/6/16.
 */
public class CardSwipeAdapter extends BaseAdapter implements PagerListener {


    // public List<String> matchList;
    public Context context;
    ViewHolder viewHolder;
    List<UserListRes.DataBean> mData;
    private LayoutInflater inflater;
    //  CardSwipePagerAdapter mCardSwipePagerAdapter;
    private LinkedHashMap<Integer, ArrayList<String>> mLMap = new LinkedHashMap<Integer, ArrayList<String>>();
    // private ImageView imgView;
    // int count;

    public CardSwipeAdapter(List<UserListRes.DataBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;


        if (rowView == null) {
            rowView = inflater.inflate(R.layout.row_swipe_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
            viewHolder.txt_name = (TextView) rowView.findViewById(R.id.txt_name);
            viewHolder.txt_designation = (TextView) rowView.findViewById(R.id.txt_designation);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mData.get(position).getProfile_pic().contains("http")) {
            mData.get(position).getProfile_pic().replace("http", "https");
        }
        Glide.with(context).load(mData.get(position).getProfile_pic()).skipMemoryCache(true).into(viewHolder.cardImage);

        viewHolder.txt_name.setText(mData.get(position).getUser_name());
        viewHolder.txt_designation.setText(mData.get(position).getWork());
        viewHolder.cardImage.setTag(mData.get(position).getUser_id());
        return rowView;
    }

    @Override
    public void setCurrentPos(Context context, int pos) {
        Glide.with(context).load(mData.get(pos).getProfile_pic()).into(viewHolder.cardImage);
    }


    public static class ViewHolder {
        public ImageView cardImage;
        public TextView txt_name, txt_designation;

    }


}

