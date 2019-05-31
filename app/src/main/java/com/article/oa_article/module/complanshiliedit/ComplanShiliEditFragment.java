package com.article.oa_article.module.complanshiliedit;


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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.blankj.utilcode.util.LogUtils;
import com.guoqi.actionsheet.ActionSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * MVPPlugin
 * 公司实力编辑
 */

public class ComplanShiliEditFragment extends MVPBaseFragment<ComplanShiliEditContract.View,
        ComplanShiliEditPresenter> implements ComplanShiliEditContract.View, ActionSheet.OnActionSheetSelected {


    @BindView(R.id.zizhi_recycle)
    RecyclerView zizhiRecycle;
    @BindView(R.id.rongyu_recycle)
    RecyclerView rongyuRecycle;
    Unbinder unbinder;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complany_shili, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zizhiRecycle.setLayoutManager(manager);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rongyuRecycle.setLayoutManager(manager1);

        setZiZhiAdapter();
        setRongYuAdapter();
    }


    /**
     * 设置资质的适配器
     */
    private void setZiZhiAdapter() {
        ZiZhiAdapter adapter = new ZiZhiAdapter(getActivity(), new ArrayList<>());
        zizhiRecycle.setAdapter(adapter);
    }

    /**
     * 设置荣誉的适配器
     */
    private void setRongYuAdapter() {
        RongYuAdapter adapter = new RongYuAdapter(getActivity(), new ArrayList<>());
        rongyuRecycle.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(int whichButton) {
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
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), permissions)) {
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
            uri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity()), "com.article.oa_article.fileprovider", cameraSavePath);
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
            showProgress();
            mPresenter.updateImage(new File(Objects.requireNonNull(photoPath)));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(getActivity(), data.getData());
            showProgress();
            mPresenter.updateImage(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void updateSourss(String name, String imageUrl) {
        stopProgress();
        ImageBO imageBO = new ImageBO();
        imageBO.name = name;
        imageBO.url = imageUrl;
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
        stopProgress();
    }

}
