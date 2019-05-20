package com.article.oa_article.view.setting;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.GlideApp;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.blankj.utilcode.util.LogUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * MVPPlugin
 * 资料设置
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter>
        implements SettingContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.person_img)
    RoundedImageView personImg;
    @BindView(R.id.person_name)
    TextView personName;
    @BindView(R.id.person_name_text)
    TextView personNameText;
    @BindView(R.id.person_phone)
    TextView personPhone;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("资料设置");

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @OnClick({R.id.person_name_layout, R.id.person_phone_layout, R.id.password_layout, R.id.switch_img})
    public void clickLayout(View view) {
        switch (view.getId()) {
            case R.id.person_name_layout:
                gotoActivity(PersonNameSettingAct.class, false);
                break;
            case R.id.person_phone_layout:
                gotoActivity(PhoneSettingAct.class, false);
                break;
            case R.id.password_layout:
                gotoActivity(PasswordSettingAct.class, false);
                break;
            case R.id.switch_img:
                ActionSheet.showSheet(this, this, null);
                break;
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpServerImpl.getUserinfo().subscribe(new HttpResultSubscriber<UserBo>() {
            @Override
            public void onSuccess(UserBo s) {
                MyApplication.userBo = s;
                personName.setText(s.getName());
                personNameText.setText(s.getName());
                personPhone.setText(s.getPhone());
                GlideApp.with(SettingActivity.this).load(s.getImage()).error(R.drawable.person_img_defailt)
                        .placeholder(R.drawable.person_img_defailt).into(personImg);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @Override
    public void onClick(int whichButton) {
        getPermission();
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                goPhotoAlbum();
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                goCamera();
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }


    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(this), permissions)) {
            //已经打开权限
            LogUtils.d("已经申请相关权限");
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }


    //激活相册操作
    private void goPhotoAlbum() {
        getPermission();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        getPermission();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.article.oa_article.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            updateImage(new File(Objects.requireNonNull(photoPath)));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            updateImage(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void updateImage(File file) {
        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                updateImg(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 更换头像
     */
    private void updateImg(String imageUrl) {
        PersonServiceImpl.updateImg(imageUrl).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getUserInfo();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
