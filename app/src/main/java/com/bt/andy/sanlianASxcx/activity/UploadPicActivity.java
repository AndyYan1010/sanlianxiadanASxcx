package com.bt.andy.sanlianASxcx.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.bt.andy.sanlianASxcx.BaseActivity;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.AddPicAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 14:34
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class UploadPicActivity extends BaseActivity {
    private TextView          tv_title;
    private RecyclerView      rec_up_photo;
    private Button            bt_submit;
    private List<Bitmap>      mBitmapList;
    private GridLayoutManager mLayoutManager;
    private AddPicAdapter     mAddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        getView();
        setData();
    }

    private void getView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        rec_up_photo = (RecyclerView) findViewById(R.id.rec_up_photo);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    private void setData() {
        tv_title.setText("上传图片");
        //添加初始展示的图片
        Resources res = getResources();
        Bitmap mBm = BitmapFactory.decodeResource(res, R.drawable.add_pic);
        if (null == mBitmapList) {
            mBitmapList = new ArrayList<Bitmap>();
            mBitmapList.add(mBm);
        }
        //需上传的照片墙
        mLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mAddAdapter = new AddPicAdapter(this, (ArrayList<Bitmap>) mBitmapList);
        // 设置布局管理器
        rec_up_photo.setLayoutManager(mLayoutManager);
        // 设置adapter
        rec_up_photo.setAdapter(mAddAdapter);
    }
}
