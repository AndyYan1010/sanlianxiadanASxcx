package com.bt.andy.sanlianASxcx.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.andy.sanlianASxcx.BaseActivity;
import com.bt.andy.sanlianASxcx.NetConfig;
import com.bt.andy.sanlianASxcx.R;
import com.bt.andy.sanlianASxcx.adapter.AddPicAdapter;
import com.bt.andy.sanlianASxcx.utils.HttpOkhUtils;
import com.bt.andy.sanlianASxcx.utils.ProgressDialogUtil;
import com.bt.andy.sanlianASxcx.utils.RequestParamsFM;
import com.bt.andy.sanlianASxcx.utils.ToastUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/28 14:34
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class UploadPicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView    img_back;
    private TextView     tv_title;
    private RecyclerView rec_up_photo;
    private EditText     et_code, et_fback;
    private Button            bt_submit;
    private List<Bitmap>      mBitmapList;
    private List<String>      filePathList;
    private GridLayoutManager mLayoutManager;
    private AddPicAdapter     mAddAdapter;
    private int               recordTime;//记录第几次上传
    private ImageView         img_scan;
    private int IMAGE                              = 10086;//获取图片地址，请求值
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;//申请照相机权限结果
    private int REQUEST_CODE                       = 1003;//接收扫描结果
    private String         scanCode;
    private String         markNote;
    private String         orderID;//订单id
    private String         subTimes;
    private String         mKind;
    private RelativeLayout rel_scan;
    private TextView       tv_fktt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        mKind = getIntent().getStringExtra("kind");
        orderID = getIntent().getStringExtra("orderID");
        subTimes = getIntent().getStringExtra("subtimes");
        getView();
        setData();
    }

    private void getView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rel_scan = (RelativeLayout) findViewById(R.id.rel_scan);
        tv_fktt = (TextView) findViewById(R.id.tv_fktt);
        et_code = (EditText) findViewById(R.id.et_code);
        et_fback = (EditText) findViewById(R.id.et_fback);
        img_scan = (ImageView) findViewById(R.id.img_scan);
        rec_up_photo = (RecyclerView) findViewById(R.id.rec_up_photo);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    private void setData() {
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        tv_title.setText("服务结果提交");
        //添加初始展示的图片
        Resources res = getResources();
        Bitmap mBm = BitmapFactory.decodeResource(res, R.drawable.add_pic);
        if (null == mBitmapList) {
            mBitmapList = new ArrayList<Bitmap>();
            mBitmapList.add(mBm);
        }
        filePathList = new ArrayList<>();
        //需上传的照片墙
        mLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mAddAdapter = new AddPicAdapter(this, (ArrayList<Bitmap>) mBitmapList, filePathList);
        // 设置布局管理器
        rec_up_photo.setLayoutManager(mLayoutManager);
        // 设置adapter
        rec_up_photo.setAdapter(mAddAdapter);
        img_scan.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        if ("0".equals(mKind)) {
            rel_scan.setVisibility(View.GONE);
            et_code.setVisibility(View.GONE);
            et_fback.setVisibility(View.GONE);
            tv_fktt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_scan:
                //扫描二维码
                //动态申请照相机权限,开启照相机
                scanningCode();
                break;
            case R.id.bt_submit:
                if ("0".equals(mKind)) {//配送
                    recordTime = 0;
                    //上传图片
                    for (int i = 0; i < filePathList.size(); i++) {
                        File file = new File(filePathList.get(i));
                        if (file != null) {
                            sendPSPic(file, i);
                        }
                    }
                } else {
                    String code = String.valueOf(et_code.getText()).trim();
                    String fback = String.valueOf(et_fback.getText()).trim();
                    if ("".equals(code) || "请输入或扫码填入机器二维码".equals(code)) {
                        //                        ToastUtils.showToast(UploadPicActivity.this, "请输入或扫码填入机器二维码");
                        code = "";
                        //                        return;
                    }
                    if ("".equals(fback) || "请输入服务反馈".equals(fback)) {
                        ToastUtils.showToast(UploadPicActivity.this, "请输入服务反馈");
                        return;
                    }
                    scanCode = code;
                    markNote = fback;
                    recordTime = 0;
                    //上传图片
                    for (int i = 0; i < filePathList.size(); i++) {
                        File file = new File(filePathList.get(i));
                        if (file != null) {
                            sendPic(file, i);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //获取扫描信息，填写到对应et
                    if (requestCode == REQUEST_CODE) {
                        et_code.setText(result);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void scanningCode() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            Intent intent = new Intent(this, SaomiaoUIActivity.class);//这是一个自定义的扫描界面，扫描UI框放大了。
            // Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    //加载图片
    private void showImage(String imgPath) {
        Bitmap bm = BitmapFactory.decodeFile(imgPath);
        //        File file = new File(imgPath);
        //        //压缩图片
        //        File file = new File(imgPath);
        //        File newFile = new CompressHelper.Builder(this)
        //                .setMaxWidth(720)  // 默认最大宽度为720
        //                .setMaxHeight(960) // 默认最大高度为960
        //                .setQuality(100)    // 默认压缩质量为80
        //                .setFileName("sendPic") // 设置你需要修改的文件名
        //                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
        //                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
        //                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
        //                .build()
        //                .compressToFile(file);
        //        Bitmap bm = BitmapFactory.decodeFile(newFile.getPath());

        //添加到bitmap集合中
        mBitmapList.add(bm);
        filePathList.add(imgPath);
        mAddAdapter.notifyDataSetChanged();
    }

    private void sendPSPic(File file, final int position) {
        String upLoadpsPic = NetConfig.PSIMAGE;
        ProgressDialogUtil.startShow(this, "正在上传");
        RequestParamsFM params = new RequestParamsFM();
        params.put("id", orderID);
        HttpOkhUtils.getInstance().uploadFile(upLoadpsPic, params, "file", file, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(UploadPicActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(UploadPicActivity.this, "网络错误");
                    return;
                }
                recordTime++;
                if ("1".equals(resbody)) {
                    ToastUtils.showToast(UploadPicActivity.this, "图片" + (position + 1) + "上传成功");
                    if (recordTime == filePathList.size()) {
                        ProgressDialogUtil.hideDialog();
                        finish();
                    }
                } else if ("2".equals(resbody)) {
                    ProgressDialogUtil.hideDialog();
                    ToastUtils.showToast(UploadPicActivity.this, "图片上传失败，二维码不匹配");
                }else {
                    ProgressDialogUtil.hideDialog();
                    ToastUtils.showToast(UploadPicActivity.this, "图片上传失败");
                }
            }
        });
    }

    private void sendPic(File file, final int position) {
        String upLoadPic = NetConfig.INSERTIMG;
        if ("7".equals(subTimes)) {
            upLoadPic = NetConfig.INSERTIMG1;
        }
        ProgressDialogUtil.startShow(this, "正在上传");
        RequestParamsFM params = new RequestParamsFM();
        params.put("Note", markNote);
        params.put("id", orderID);
        params.put("QCode", scanCode);
        HttpOkhUtils.getInstance().uploadFile(upLoadPic, params, "file", file, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(UploadPicActivity.this, "网络连接错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                if (code != 200) {
                    ToastUtils.showToast(UploadPicActivity.this, "网络错误");
                    return;
                }
                if ("1".equals(resbody)) {
                    ToastUtils.showToast(UploadPicActivity.this, "图片" + (position + 1) + "上传成功");
                    recordTime++;
                    if (recordTime == filePathList.size()) {
                        ProgressDialogUtil.hideDialog();
                        finish();
                    }
                }else {
                    ProgressDialogUtil.hideDialog();
                    ToastUtils.showToast(UploadPicActivity.this, "上传结果"+resbody);
                }
            }
        });
    }
}
