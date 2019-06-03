package com.article.oa_article.module.complanshiliedit;


import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.bean.request.UpdateShiliRequest;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.article.oa_article.widget.EditMsgText;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.guoqi.actionsheet.ActionSheet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.add_zizhi)
    LinearLayout addZizhi;
    @BindView(R.id.add_rongyu)
    LinearLayout addRongyu;
    @BindView(R.id.next_button)
    Button nextButton;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ZiZhiAdapter adapter;
    private List<AddComplanRequest.CompanyQualificationsBean> qualificationsBeans;
    private int zizhiPosition;

    private RongYuAdapter rongYuAdapter;
    private List<AddComplanRequest.CompanyHonorsBean> honorsBeans;
    private int rongyuPosition;

    boolean isAddZiZhi = true;   //true 为上传资质图片   false 为上传荣誉图片

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
        qualificationsBeans = new ArrayList<>();
        qualificationsBeans.add(new AddComplanRequest.CompanyQualificationsBean());
        honorsBeans = new ArrayList<>();
        honorsBeans.add(new AddComplanRequest.CompanyHonorsBean());

        addZizhi.setVisibility(View.VISIBLE);
        addRongyu.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        zizhiRecycle.setLayoutManager(manager);
        zizhiRecycle.setNestedScrollingEnabled(false);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rongyuRecycle.setLayoutManager(manager1);
        rongyuRecycle.setNestedScrollingEnabled(false);

        setZiZhiAdapter();
        setRongYuAdapter();
    }


    /**
     * 设置资质的适配器
     */
    private void setZiZhiAdapter() {
        adapter = new ZiZhiAdapter(getActivity(), qualificationsBeans);
        adapter.setListener(new ZiZhiAdapter.onMakeZizhi() {
            @Override
            public void addImage(int position) {
                isAddZiZhi = true;
                zizhiPosition = position;
                ActionSheet.showSheet(getActivity(), ComplanShiliEditFragment.this, null);
            }

            @Override
            public void deleteImage(int position, List<ImageBO> imageBOS) {
                qualificationsBeans.get(position).setQualificationImage(imageBOS);
            }
        });
        zizhiRecycle.setAdapter(adapter);
    }

    /**
     * 设置荣誉的适配器
     */
    private void setRongYuAdapter() {
        rongYuAdapter = new RongYuAdapter(getActivity(), honorsBeans);
        rongYuAdapter.setListener(new RongYuAdapter.onMakeZizhi() {
            @Override
            public void addImage(int position) {
                isAddZiZhi = false;
                rongyuPosition = position;
                ActionSheet.showSheet(getActivity(), ComplanShiliEditFragment.this, null);
            }

            @Override
            public void deleteImage(int position, List<ImageBO> imageBOS) {
                honorsBeans.get(position).setHonorImage(imageBOS);
            }
        });
        rongyuRecycle.setAdapter(rongYuAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_zizhi)
    public void addZizhi() {
        if (getData() != null) {
            qualificationsBeans.add(new AddComplanRequest.CompanyQualificationsBean());
            setZiZhiAdapter();
        }
    }


    @OnClick(R.id.add_rongyu)
    public void addRongyu() {
        if (getHonorDatas() != null) {
            honorsBeans.add(new AddComplanRequest.CompanyHonorsBean());
            setRongYuAdapter();
        }
    }


    @OnClick(R.id.next_button)
    public void commitEdit() {
        if(getData() != null && getHonorDatas() != null){
            UpdateShiliRequest request = new UpdateShiliRequest();
            request.setCompanyHonors(getHonorDatas());
            request.setCompanyQualifications(getData());
            mPresenter.updateShiliInfo(request);
        }
    }


    public List<AddComplanRequest.CompanyQualificationsBean> getData() {
        for (int i = 0; i < zizhiRecycle.getChildCount(); i++) {
            View view = zizhiRecycle.getChildAt(i);
            if (!isGiveZiZhi(i, view)) {
                return null;
            }
            EditMsgText name = view.findViewById(R.id.zizhi_name);
            EditMsgText danwei = view.findViewById(R.id.banfa_danwei);
            TextView date = view.findViewById(R.id.person_name);
            EditMsgText num = view.findViewById(R.id.certificate_num);
            AddComplanRequest.CompanyQualificationsBean qualificationsBean = new AddComplanRequest.CompanyQualificationsBean();
            qualificationsBean.setQualificationName(name.getText());
            qualificationsBean.setQualificationImage(adapter.getImageByPosition(i));
            qualificationsBean.setQualificationNumber(num.getText());
            qualificationsBean.setIssueUnit(danwei.getText());
            qualificationsBean.setIssueDate(date.getText().toString().trim().replaceAll("/", "-"));
            if(qualificationsBeans.size() > i){
                qualificationsBean.setQualificationId(qualificationsBeans.get(i).getQualificationId());
            }
            qualificationsBeans.set(i, qualificationsBean);
        }
        return qualificationsBeans;
    }

    /**
     * 判断是否可增加一个新的资质
     */
    public boolean isGiveZiZhi(int i, View view) {
        EditMsgText name = view.findViewById(R.id.zizhi_name);
        EditMsgText danwei = view.findViewById(R.id.banfa_danwei);
        TextView date = view.findViewById(R.id.person_name);
        EditMsgText num = view.findViewById(R.id.certificate_num);
        if (StringUtils.isEmpty(name.getText())) {
            showToast("请补全资质名称！");
            return false;
        }
        if (StringUtils.isEmpty(danwei.getText())) {
            showToast("请补全颁发单位！");
            return false;
        }
        if (StringUtils.isEmpty(date.getText().toString())) {
            showToast("请补全有效期！");
            return false;
        }
        if (StringUtils.isEmpty(num.getText())) {
            showToast("请补全证书编号！");
            return false;
        }
        if (adapter.getImageByPosition(i) == null || adapter.getImageByPosition(i).isEmpty()) {
            showToast("请补全证书图片！");
            return false;
        }
        return true;
    }


    /**
     * 返回荣誉列表
     */
    public List<AddComplanRequest.CompanyHonorsBean> getHonorDatas() {
        for (int i = 0; i < rongyuRecycle.getChildCount(); i++) {
            View view = rongyuRecycle.getChildAt(i);
            if (!isGiveRongYu(i, view)) {
                return null;
            }
            EditMsgText name = view.findViewById(R.id.rongyu_name);
            EditMsgText danwei = view.findViewById(R.id.banfa_danwei);
            TextView date = view.findViewById(R.id.person_name);
            AddComplanRequest.CompanyHonorsBean qualificationsBean = new AddComplanRequest.CompanyHonorsBean();
            qualificationsBean.setHonorName(name.getText());
            qualificationsBean.setHonorImage(adapter.getImageByPosition(i));
            qualificationsBean.setIssueUnit(danwei.getText());
            qualificationsBean.setIssueDate(date.getText().toString().trim().replaceAll("/", "-"));
            if(honorsBeans.size() > i){
                qualificationsBean.setHonorId(honorsBeans.get(i).getHonorId());
            }
            honorsBeans.set(i, qualificationsBean);
        }
        return honorsBeans;
    }

    /**
     * 判断是否可以新增一个荣誉
     */
    public boolean isGiveRongYu(int i, View view) {
        EditMsgText name = view.findViewById(R.id.rongyu_name);
        EditMsgText danwei = view.findViewById(R.id.banfa_danwei);
        TextView date = view.findViewById(R.id.person_name);
        if (StringUtils.isEmpty(name.getText())) {
            showToast("请补全资质名称！");
            return false;
        }
        if (StringUtils.isEmpty(danwei.getText())) {
            showToast("请补全颁发单位！");
            return false;
        }
        if (StringUtils.isEmpty(date.getText().toString())) {
            showToast("请补全有效期！");
            return false;
        }
        if (rongYuAdapter.getImageByPosition(i) == null || rongYuAdapter.getImageByPosition(i).isEmpty()) {
            showToast("请补全荣誉图片！");
            return false;
        }
        return true;
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
        if (isAddZiZhi) {
            adapter.addImage(zizhiPosition, imageBO);
        } else {
            rongYuAdapter.addImage(rongyuPosition, imageBO);
        }
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
        stopProgress();
    }


    /**
     * 设置修改公司实力时显示
     */
    public void setEditCommon(ComplanBO commonBo) {
        new Handler().post(new Runnable() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void run() {
                if (!commonBo.getCompanyQualifications().isEmpty()) {
                    qualificationsBeans.clear();
                }
                if (!commonBo.getCompanyHonors().isEmpty()) {
                    honorsBeans.clear();
                }
                for (int i = 0; i < commonBo.getCompanyQualifications().size(); i++) {
                    AddComplanRequest.CompanyQualificationsBean qualificationsBean =
                            new AddComplanRequest.CompanyQualificationsBean();
                    qualificationsBean.setIssueDate(TimeUtils.millis2String(commonBo.getCompanyQualifications().get(i).getIssueDate(),
                            new SimpleDateFormat("yyyy/MM/dd")));
                    qualificationsBean.setIssueUnit(commonBo.getCompanyQualifications().get(i).getIssueUnit());
                    qualificationsBean.setQualificationNumber(commonBo.getCompanyQualifications().get(i).getQualificationNumber());
                    qualificationsBean.setQualificationImage(commonBo.getCompanyQualifications().get(i).getQualificationImage());
                    qualificationsBean.setQualificationName(commonBo.getCompanyQualifications().get(i).getQualificationName());
                    qualificationsBean.setQualificationId(commonBo.getCompanyQualifications().get(i).getQualificationId());
                    qualificationsBeans.add(qualificationsBean);
                }
                setZiZhiAdapter();
                for (int i = 0; i < commonBo.getCompanyHonors().size(); i++) {
                    AddComplanRequest.CompanyHonorsBean honorsBean = new AddComplanRequest.CompanyHonorsBean();
                    honorsBean.setHonorImage(commonBo.getCompanyHonors().get(i).getHonorImage());
                    honorsBean.setHonorId(commonBo.getCompanyHonors().get(i).getHonorId());
                    honorsBean.setHonorName(commonBo.getCompanyHonors().get(i).getHonorName());
                    honorsBean.setIssueDate(TimeUtils.millis2String(commonBo.getCompanyHonors().get(i).getIssueDate(),
                            new SimpleDateFormat("yyyy/MM/dd")));
                    honorsBean.setIssueUnit(commonBo.getCompanyHonors().get(i).getIssueUnit());
                    honorsBeans.add(honorsBean);
                }
                setRongYuAdapter();
                nextButton.setVisibility(View.VISIBLE);
            }
        });
    }

}
