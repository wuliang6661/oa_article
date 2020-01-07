package com.article.oa_article.module.complanmsgedit;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.event.UpdateComplanEvent;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.article.oa_article.widget.EditMsgText;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * MVPPlugin
 * 修改公司信息
 */

public class ComplanMsgEditFragment extends MVPBaseFragment<ComplanMsgEditContract.View, ComplanMsgEditPresenter>
        implements ComplanMsgEditContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.complan_name)
    EditMsgText complanName;
    @BindView(R.id.complan_jiancheng)
    EditMsgText complanJiancheng;
    @BindView(R.id.complan_address)
    EditMsgText complanAddress;
    @BindView(R.id.complan_phone)
    EditMsgText complanPhone;
    @BindView(R.id.complan_email)
    EditMsgText complanEmail;
    @BindView(R.id.card_fanmian)
    ImageView cardFanmian;
    @BindView(R.id.delete_fanmian)
    ImageView deleteFanmian;
    @BindView(R.id.card_zhengmian)
    ImageView cardZhengmian;
    @BindView(R.id.delete_img)
    ImageView deleteImg;
    @BindView(R.id.zhizhao)
    ImageView zhizhao;
    @BindView(R.id.delete_zhizhao)
    ImageView deleteZhizhao;
    Unbinder unbinder;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.complan_logo)
    ImageView complanLogo;
    @BindView(R.id.delete_logo)
    ImageView deleteLogo;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int selectPosition = 0;  //0：身份证正面 1：身份证反面 2：执照,3:公司Logo

    private String strName;
    private String strJiancheng;
    private String address;
    private String phone;
    private String email;

    private String zhengmianUrl, fanmianUrl, zhizhaoUrl, logoUrl;
    private String zhengmianName, fanmianName, zhizhaoName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_edit_complan_detail, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.zhizhao, R.id.card_fanmian, R.id.card_zhengmian, R.id.complan_logo})
    public void imageSelect(View view) {
        switch (view.getId()) {
            case R.id.complan_logo:
                selectPosition = 3;
                break;
            case R.id.zhizhao:
                selectPosition = 2;
                break;
            case R.id.card_fanmian:
                selectPosition = 1;
                break;
            case R.id.card_zhengmian:
                selectPosition = 0;
                break;
        }
        ActionSheet.showSheet(getActivity(), ComplanMsgEditFragment.this, null);
    }


    @OnClick({R.id.delete_fanmian, R.id.delete_img, R.id.delete_zhizhao, R.id.delete_logo})
    public void deleteImage(View view) {
        switch (view.getId()) {
            case R.id.delete_zhizhao:
                zhizhao.setImageResource(R.drawable.image_update_add);
                deleteZhizhao.setVisibility(View.GONE);
                zhizhaoUrl = null;
                zhizhaoName = null;
                break;
            case R.id.delete_img:
                cardZhengmian.setImageResource(R.drawable.image_update_add);
                deleteImg.setVisibility(View.GONE);
                zhengmianUrl = null;
                zhengmianName = null;
                break;
            case R.id.delete_fanmian:
                cardFanmian.setImageResource(R.drawable.image_update_add);
                deleteFanmian.setVisibility(View.GONE);
                fanmianUrl = null;
                fanmianName = null;
                break;
            case R.id.delete_logo:
                complanLogo.setImageResource(R.drawable.image_update_add);
                deleteLogo.setVisibility(View.GONE);
                logoUrl = null;
                break;
        }
    }

    @OnClick(R.id.next_button)
    public void editCommit() {
        if (isCommit()) {
            AddComplanRequest request = getData(new AddComplanRequest());
            mPresenter.updateComlanInfo1(request.getCompanyInfo());
        }
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
        switch (selectPosition) {
            case 0:
                Glide.with(getActivity()).load(imageUrl).into(cardZhengmian);
                deleteImg.setVisibility(View.VISIBLE);
                zhengmianUrl = imageUrl;
                zhengmianName = name;
                break;
            case 1:
                Glide.with(getActivity()).load(imageUrl).into(cardFanmian);
                deleteFanmian.setVisibility(View.VISIBLE);
                fanmianUrl = imageUrl;
                fanmianName = name;
                break;
            case 2:
                Glide.with(getActivity()).load(imageUrl).into(zhizhao);
                deleteZhizhao.setVisibility(View.VISIBLE);
                zhizhaoUrl = imageUrl;
                zhizhaoName = name;
                break;
            case 3:
                Glide.with(getActivity()).load(imageUrl).into(complanLogo);
                deleteLogo.setVisibility(View.VISIBLE);
                logoUrl = imageUrl;
                break;
        }
    }

    @Override
    public void updateSourss(String s) {
        ToastUtils.showShort(s);
        EventBus.getDefault().post(new UpdateComplanEvent());
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
        stopProgress();
    }

    /**
     * 修改时设置数据
     */
    public void setData(ComplanBO complanBO) {
        new Handler().post(() -> {
            complanName.setText(complanBO.getCompanyInfos().getCompanyName());
            complanJiancheng.setText(complanBO.getCompanyInfos().getShortName());
            complanAddress.setText(complanBO.getCompanyInfos().getCompanyAddress());
            complanPhone.setText(complanBO.getCompanyInfos().getContactWay());
            complanEmail.setText(complanBO.getCompanyInfos().getCompanyEmail());
            zhengmianUrl = complanBO.getCompanyInfos().getIdFrontImage().url;
            zhengmianName = complanBO.getCompanyInfos().getIdFrontImage().name;
            if (!StringUtils.isEmpty(zhengmianUrl)) {
                Glide.with(getActivity()).load(zhengmianUrl).into(cardZhengmian);
                deleteImg.setVisibility(View.VISIBLE);
            }
            fanmianName = complanBO.getCompanyInfos().getIdBackImage().name;
            fanmianUrl = complanBO.getCompanyInfos().getIdBackImage().url;
            if (!StringUtils.isEmpty(fanmianUrl)) {
                Glide.with(getActivity()).load(fanmianUrl).into(cardFanmian);
                deleteFanmian.setVisibility(View.VISIBLE);
            }
            zhizhaoName = complanBO.getCompanyInfos().getBusinessLicense().name;
            zhizhaoUrl = complanBO.getCompanyInfos().getBusinessLicense().url;
            if (!StringUtils.isEmpty(zhizhaoUrl)) {
                Glide.with(getActivity()).load(zhizhaoUrl).into(zhizhao);
                deleteZhizhao.setVisibility(View.VISIBLE);
            }
            logoUrl = complanBO.getCompanyInfos().getCompanyImage();
            if (!StringUtils.isEmpty(logoUrl)) {
                Glide.with(getActivity()).load(logoUrl).into(complanLogo);
                deleteLogo.setVisibility(View.VISIBLE);
            }
            nextButton.setVisibility(View.VISIBLE);
        });
    }


    /**
     * 获取页面里已经填好的数据
     */
    public AddComplanRequest getData(AddComplanRequest request) {
        AddComplanRequest.CompanyInfoBean companyInfoBean;
        if (request.getCompanyInfo() == null) {
            companyInfoBean = new AddComplanRequest.CompanyInfoBean();
        } else {
            companyInfoBean = request.getCompanyInfo();
        }
        companyInfoBean.setCompanyName(strName);
        companyInfoBean.setShortName(strJiancheng);
        companyInfoBean.setCompanyAddress(address);
        companyInfoBean.setCompanyEmail(email);
        companyInfoBean.setContactWay(phone);
        ImageBO zhengmian = new ImageBO();
        zhengmian.name = zhengmianName;
        zhengmian.url = zhengmianUrl;
        companyInfoBean.setIdFrontImage(zhengmian);
        ImageBO fanmian = new ImageBO();
        fanmian.name = fanmianName;
        fanmian.url = fanmianUrl;
        companyInfoBean.setIdBackImage(fanmian);
        ImageBO zhizhao = new ImageBO();
        zhizhao.name = zhizhaoName;
        zhizhao.url = zhizhaoUrl;
        companyInfoBean.setCompanyImage(logoUrl);
        companyInfoBean.setBusinessLicense(zhizhao);
        request.setCompanyInfo(companyInfoBean);
        return request;
    }


    /**
     * 信息是否填完
     */
    public boolean isCommit() {
        strName = complanName.getText();
        strJiancheng = complanJiancheng.getText();
        address = complanAddress.getText();
        phone = complanPhone.getText();
        email = complanEmail.getText();
        if (StringUtils.isEmpty(strName)) {
            showToast("请输入公司名称！");
            return false;
        }
        if (StringUtils.isEmpty(strJiancheng)) {
            showToast("请输入公司简称！");
            return false;
        }
        if (StringUtils.isEmpty(address)) {
            showToast("请输入公司详细地址！");
            return false;
        }
        if (StringUtils.isEmpty(phone)) {
            showToast("请输入联系方式！");
            return false;
        }
        if (StringUtils.isEmpty(email)) {
            showToast("请输入企业邮箱！");
            return false;
        }
        if (StringUtils.isEmpty(zhengmianUrl)) {
            showToast("请上传身份证正面照！");
            return false;
        }
        if (StringUtils.isEmpty(fanmianUrl)) {
            showToast("请上传身份证反面照！");
            return false;
        }
        if (StringUtils.isEmpty(zhizhaoUrl)) {
            showToast("请上传企业执照！");
            return false;
        }
        if (StringUtils.isEmpty(logoUrl)) {
            showToast("请上传企业Logo！");
            return false;
        }
        return true;
    }

}
