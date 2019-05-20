package com.article.oa_article.view.optionsfankui;


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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.blankj.utilcode.util.LogUtils;
import com.guoqi.actionsheet.ActionSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * MVPPlugin
 * 意见反馈
 */

public class OptionsFankuiActivity extends MVPBaseActivity<OptionsFankuiContract.View, OptionsFankuiPresenter>
        implements OptionsFankuiContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.fankui_type)
    LinearLayout fankuiType;
    @BindView(R.id.edit_message)
    EditText editMessage;
    @BindView(R.id.edit_lianxifangshi)
    EditText editLianxifangshi;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.next_button)
    Button nextButton;


    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private List<String> images;

    @Override
    protected int getLayout() {
        return R.layout.act_options_fankui;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("意见反馈");

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        images = new ArrayList<>();
        initView();
        setImagesAdapter();
    }


    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        imageRecycle.setHasFixedSize(true);
        imageRecycle.setLayoutManager(gridLayoutManager);
        imageRecycle.setNestedScrollingEnabled(false);
    }


    private void setImagesAdapter() {
        ImageAdapter adapter = new ImageAdapter(this, images);
        adapter.setListener(() -> ActionSheet.showSheet(OptionsFankuiActivity.this, OptionsFankuiActivity.this, null));
        imageRecycle.setAdapter(adapter);
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
                images.add(s);
                setImagesAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }
}
