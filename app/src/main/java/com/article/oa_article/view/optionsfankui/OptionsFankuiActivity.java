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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.FankuiTypeBO;
import com.article.oa_article.bean.request.FanKuiRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.guoqi.actionsheet.ActionSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.type_text)
    TextView typeText;
    @BindView(R.id.msg_num)
    TextView msgNum;


    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private List<String> images;
    private FankuiTypeBO fankuiTypeBO;

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

        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                msgNum.setText(editable.toString().length() + "/100");
            }
        });
    }


    private void setImagesAdapter() {
        ImageAdapter adapter = new ImageAdapter(this, images);
        adapter.setListener(new ImageAdapter.onAddImageAdapterListener() {
            @Override
            public void addImage() {
                ActionSheet.showSheet(OptionsFankuiActivity.this, OptionsFankuiActivity.this, null);
            }

            @Override
            public void deleteImage(int position) {
                images.remove(position);
                setImagesAdapter();
            }
        });
        imageRecycle.setAdapter(adapter);
    }


    @OnClick(R.id.fankui_type)
    public void typeClick() {
        Intent intent = new Intent(this, OptionsTypeActivity.class);
        startActivityForResult(intent, 0x11);
    }


    @OnClick(R.id.next_button)
    public void buttonClick() {
        if (fankuiTypeBO == null) {
            showToast("请选择反馈类型！");
            return;
        }
        String text = editMessage.getText().toString().trim();
        if (StringUtils.isEmpty(text)) {
            showToast("请填写反馈内容！");
            return;
        }
        String phone = editLianxifangshi.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast("请填写联系方式！");
            return;
        }
        if (images.size() == 0) {
            showToast("请上传反馈图片！");
            return;
        }
        StringBuilder image = new StringBuilder();
        for (String url : images) {
            image.append(url).append(",");
        }
        FanKuiRequest request = new FanKuiRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setContact(phone);
        request.setContent(text);
        request.setType(fankuiTypeBO.getCode());
        request.setImage(image.substring(0, image.length() - 1));
        mPresenter.addFeed(request);
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
        } else if (resultCode == 0x11) {
            fankuiTypeBO = (FankuiTypeBO) data.getSerializableExtra("type");
            typeText.setText(fankuiTypeBO.getName());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void updateImage(File file) {
        showProgress();
        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                images.add(s);
                setImagesAdapter();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void sourss() {
        showToast("反馈提交成功！");
        finish();
    }
}
