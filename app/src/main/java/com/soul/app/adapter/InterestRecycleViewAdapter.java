package com.soul.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soul.app.Bean.SelectedState;
import com.soul.app.R;
import com.soul.app.models.res.InterestListRes;
import com.soul.app.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by techahead on 18/7/16.
 */
public class InterestRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public int mInterestCount = 0;
    public String[] checkedInterest = new String[3];
    Context mContext;
    List<InterestListRes> mData;
    ArrayList<SelectedState> checkedIntst = new ArrayList<SelectedState>();
    private int count = 0;
    private boolean isListChecked;

    public InterestRecycleViewAdapter(Context mContext, List<InterestListRes> mData, int interestCount) {
        this.mContext = mContext;
        this.mData = mData;
        this.count = interestCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_interest_list, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DataObjectHolder) {
            final DataObjectHolder dataObjectHolder = (DataObjectHolder) holder;


            dataObjectHolder.interestTv.setText(mData.get(position).getCategory_name());
            dataObjectHolder.interestTv.setTag(position);
            dataObjectHolder.interestCb.setTag(position);
            dataObjectHolder.interest_list.setTag(position);

            if (mData.get(position).isSelected()) {
                dataObjectHolder.interestCb.setChecked(true);
                //isListChecked = true;
            } else {
                dataObjectHolder.interestCb.setChecked(false);
               // isListChecked = false;
            }
           /* dataObjectHolder.interest_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) dataObjectHolder.interest_list.getTag();

                    if (dataObjectHolder.interestCb.isChecked()) {

                        //isListChecked = true;
                        mData.get(pos).setSelected(true);
                        dataObjectHolder.interestCb.setChecked(true);
                        if (count > 4) {
                            dataObjectHolder.interestCb.setChecked(false);
                            mData.get(pos).setSelected(false);
                            Toast.makeText(mContext, "You can select max 5 interest", Toast.LENGTH_LONG).show();
                        } else {
                            ++count;
                        }
                    } else {
                        mData.get(pos).setSelected(false);
                        dataObjectHolder.interestCb.setChecked(false);
                        --count;


                    }
                }
            });
*/
            dataObjectHolder.interestCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = (Integer) buttonView.getTag();
                    if (isChecked) {
                        isListChecked = true;
                        mData.get(pos).setSelected(true);
                        buttonView.setChecked(true);
                        if (count > 4) {
                            buttonView.setChecked(false);
                            mData.get(pos).setSelected(false);
                            Toast.makeText(mContext, "You can select max 5 interest", Toast.LENGTH_LONG).show();
                        } else {
                            ++count;
                        }
                    } else {
                        mData.get(pos).setSelected(false);
                        buttonView.setChecked(false);
                        --count;
                    }


                }


            });

            dataObjectHolder.interestTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = (Integer) v.getTag();
                    if (mData.get(pos).isSelected()) {
                        for (int i = 0; i < mData.size(); i++) {
                            mData.get(pos).setIs_primary("0");
                        }
                        mData.get(pos).setIs_primary("1");
                    }
                    return false;
                }
            });
        } else {
            HeaderObjectHolder headerObjectHolder = (HeaderObjectHolder) holder;

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView interestTv;
        CheckBox interestCb;
        LinearLayout interest_list;


        public DataObjectHolder(View itemView) {
            super(itemView);
            interestTv = (TextView) itemView.findViewById(R.id.row_interest_tv);
            interestCb = (CheckBox) itemView.findViewById(R.id.row_interest_cb);
            interest_list = (LinearLayout) itemView.findViewById(R.id.interest_list);

        }
    }

    public class HeaderObjectHolder extends RecyclerView.ViewHolder {
        TextView addInterestTv;
        ImageView addImageVw;

        public HeaderObjectHolder(View itemView) {
            super(itemView);
            addInterestTv = (TextView) itemView.findViewById(R.id.add_interest_tv);
            addImageVw = (ImageView) itemView.findViewById(R.id.add_interest_imgvw);
        }
    }


}
