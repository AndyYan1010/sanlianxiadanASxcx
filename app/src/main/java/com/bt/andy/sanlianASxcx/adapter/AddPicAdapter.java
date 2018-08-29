package com.bt.andy.sanlianASxcx.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bt.andy.sanlianASxcx.R;

import java.util.ArrayList;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 14:52
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class AddPicAdapter extends RecyclerView.Adapter<AddPicAdapter.ViewHolder> {
    private ArrayList<Bitmap> mData;
    private Context           mContext;

    public AddPicAdapter(Context context, ArrayList<Bitmap> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(mContext).inflate(R.layout.photo_recy_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            //第一个条目不显示删除按键
            holder.img_delet.setVisibility(View.GONE);
            holder.img_add_photo.setImageBitmap(mData.get(position));
            // 设置点击事件添加图片
            holder.img_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用相册
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //                    startActivityForResult(intent, IMAGE);
                }
            });
        } else {
            holder.img_add_photo.setImageBitmap(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        //展示条目数
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_add_photo;
        ImageView img_delet;

        public ViewHolder(View itemView) {
            super(itemView);
            img_delet = itemView.findViewById(R.id.img_delet);
            img_add_photo = itemView.findViewById(R.id.img_add_photo);
        }
    }
}
