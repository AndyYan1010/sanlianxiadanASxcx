package com.bt.andy.sanlianASxcx.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.activity.UploadPicActivity;
import com.bt.andy.sanlianASxcx.util.GlideLoaderUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List<String>      mFileList;
    private Context           mContext;
    private PopupWindow       popupWindow;
    private              int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//相册权限申请码
    private              int IMAGE                              = 10086;//调用相册requestcode
    private static final int SHOT_CODE                          = 20;//调用系统相册-选择图片

    public AddPicAdapter(Context context, ArrayList<Bitmap> data, List<String> fileList) {
        this.mContext = context;
        this.mData = data;
        this.mFileList = fileList;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0) {
            //第一个条目不显示删除按键
            holder.img_delet.setVisibility(View.GONE);
            holder.img_add_photo.setImageBitmap(mData.get(position));
            // 设置点击事件添加图片
            holder.img_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出窗体，让用户选择相册还是拍照
                    showChoose(holder.img_add_photo);
                }
            });
        } else {
            holder.img_delet.setVisibility(View.VISIBLE);
            GlideLoaderUtil.showImageView(mContext, mFileList.get(position - 1), holder.img_add_photo);
            //            holder.img_add_photo.setImageBitmap(mData.get(position));
        }
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                mData.remove(position);
                mFileList.remove(position - 1);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //展示条目数
        return mFileList == null ? 0 : mFileList.size() + 1;
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

    private void showChoose(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_camera_pic_popup, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置背景色
                setBackgroundAlpha(1.0f);
            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    //设置PopupWindow的View点击事件
    private void setOnPopupViewClick(View view) {
        TextView tv_xc, tv_pz, tv_cancel;
        tv_xc = (TextView) view.findViewById(R.id.tv_xc);//相册
        tv_pz = (TextView) view.findViewById(R.id.tv_pz);//拍照
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消
        tv_xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    //调用相册
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity) mContext).startActivityForResult(intent, IMAGE);
                    popupWindow.dismiss();
                }
            }
        });
        tv_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    String mFilePath = Environment.getExternalStorageDirectory().getPath();//获取SD卡路径
                    long photoTime = System.currentTimeMillis();
                    mFilePath = mFilePath + "/temp" + photoTime + ".jpg";//指定路径
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    //调用相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoUri = Uri.fromFile(new File(mFilePath)); // 传递路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
                    //把指定路径传递给需保存的字段
                    ((UploadPicActivity) mContext).setPtRote(mFilePath);
                    ((UploadPicActivity) mContext).startActivityForResult(intent, SHOT_CODE);
                    popupWindow.dismiss();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
