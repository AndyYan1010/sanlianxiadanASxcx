package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.DistriActivity;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/4/18 16:13
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private Context      mContext;
    private List<String> mData;

    public IconAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview, parent, false);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String title = mData.get(position);
        holder.tv_text.setText(title);
        if ("配送".equals(title)) {
            holder.iv_icon.setImageResource(R.drawable.distribution);
        } else if ("安装".equals(title)) {
            holder.iv_icon.setImageResource(R.drawable.anzhuang);
        } else if ("维修".equals(title)) {
            holder.iv_icon.setImageResource(R.drawable.weixiu);
        }
        holder.mydada_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mData.get(position);
                //跳转对应内容
                jumpToOtherAct(title);
            }
        });
    }

    //跳转对应内容act
    private void jumpToOtherAct(String title) {
        if ("配送".equals(title)) {
            Intent intent = new Intent(mContext, DistriActivity.class);
            mContext.startActivity(intent);
        }
        if ("安装".equals(title)) {
            Intent intent = new Intent(mContext, DistriActivity.class);
            mContext.startActivity(intent);
        }
        if ("维修".equals(title)) {
            Intent intent = new Intent(mContext, DistriActivity.class);
            mContext.startActivity(intent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mydada_item_ll;
        ImageView    iv_icon;
        TextView     tv_text;

        public ViewHolder(View itemView) {
            super(itemView);
            mydada_item_ll = (LinearLayout) itemView.findViewById(R.id.mydada_item_ll);
            iv_icon = (ImageView) itemView.findViewById(R.id.mydada_icon_img);
            tv_text = (TextView) itemView.findViewById(R.id.mydada_text_tv);
        }
    }
}
