package com.article.oa_article.module.complanziyuanedit;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.article.oa_article.widget.EditMsgText;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.guoqi.actionsheet.ActionSheet;

import java.io.File;
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
 * 编辑公司资源
 */

public class ComplanZiyuanEditFragment extends MVPBaseFragment<ComplanZiyuanEditContract.View,
        ComplanZiyuanEditPresenter> implements ComplanZiyuanEditContract.View, ActionSheet.OnActionSheetSelected {


    @BindView(R.id.complan_guanli_num)
    EditMsgText complanGuanliNum;
    @BindView(R.id.complan_jishu_num)
    EditMsgText complanJishuNum;
    @BindView(R.id.complan_pugong_num)
    EditMsgText complanPugongNum;
    @BindView(R.id.shebei_recycle)
    RecyclerView shebeiRecycle;
    @BindView(R.id.complan_changfang_mianji)
    EditMsgText complanChangfangMianji;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    Unbinder unbinder;
    @BindView(R.id.person_name)
    TextView personName;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ImageRecycleAdapter adapter;

    List<AddComplanRequest.CompanyDevicesBean> devices;
    private String guanliNum;
    private String jishuNum;
    private String pugongNum;
    private String changfangmianji;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complan_ziyuan, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
        devices = new ArrayList<>();
        setImageRecycleAdapter();
        setDeviceAdapter();
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycle.setLayoutManager(manager);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        shebeiRecycle.setLayoutManager(manager1);
        shebeiRecycle.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        shebeiRecycle.addItemDecoration(itemDecoration);
    }


    private void setImageRecycleAdapter() {
        adapter = new ImageRecycleAdapter(getActivity(), new ArrayList<>());
        adapter.setImageMakeListener(() -> ActionSheet.showSheet(getActivity(), ComplanZiyuanEditFragment.this, null));
        imageRecycle.setAdapter(adapter);
    }


    private void setDeviceAdapter() {
        LGRecycleViewAdapter<AddComplanRequest.CompanyDevicesBean> adapter =
                new LGRecycleViewAdapter<AddComplanRequest.CompanyDevicesBean>(devices) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_shebei;
                    }

                    @Override
                    public void convert(LGViewHolder holder, AddComplanRequest.CompanyDevicesBean imageBO, int position) {
                        holder.setText(R.id.device_name, imageBO.getDeviceName());
                        holder.setText(R.id.device_value, imageBO.getDeviceNum() + "");
                    }
                };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            PopAddDevice addDevice = new PopAddDevice(getActivity());
            addDevice.setData(true, devices.get(position).getDeviceName(), devices.get(position).getDeviceNum());
            addDevice.setListener(new PopAddDevice.onCommitListener() {
                @Override
                public void commit(String name, String num) {

                }

                @Override
                public void update(String name, String num) {
                    AddComplanRequest.CompanyDevicesBean devicesBean = new AddComplanRequest.CompanyDevicesBean();
                    devicesBean.setDeviceName(name);
                    devicesBean.setDeviceNum(Integer.parseInt(num));
                    devices.set(position, devicesBean);
                    adapter.setData(devices);
                }
            });
            addDevice.showAtLocation(getActivity().getWindow().getDecorView());
        });
        shebeiRecycle.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_pinglei)
    public void addDevice() {
        PopAddDevice addDevice = new PopAddDevice(getActivity());
        addDevice.setListener(new PopAddDevice.onCommitListener() {
            @Override
            public void commit(String name, String num) {
                AddComplanRequest.CompanyDevicesBean devicesBean = new AddComplanRequest.CompanyDevicesBean();
                devicesBean.setDeviceName(name);
                devicesBean.setDeviceNum(Integer.parseInt(num));
                devices.add(devicesBean);
                setDeviceAdapter();
            }

            @Override
            public void update(String name, String num) {

            }
        });
        addDevice.showAtLocation(getActivity().getWindow().getDecorView());
    }


    @OnClick(R.id.select_person)
    public void selectXingzhi() {
        PopXingZhi xingZhi = new PopXingZhi(getActivity());
        xingZhi.setListener((position, item) -> personName.setText(item));
        xingZhi.showAtLocation(getActivity().getWindow().getDecorView());
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
        adapter.addImageBOS(imageBO);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
        stopProgress();
    }


    /**
     * 获取数据
     */
    public AddComplanRequest getData(AddComplanRequest request) {
        AddComplanRequest.CompanyInfoBean companyInfoBean;
        if (request.getCompanyInfo() == null) {
            companyInfoBean = new AddComplanRequest.CompanyInfoBean();
        } else {
            companyInfoBean = request.getCompanyInfo();
        }
        companyInfoBean.setPlantImage(adapter.getImageBOS());
        companyInfoBean.setAdminNumber(Integer.parseInt(guanliNum));
        companyInfoBean.setTechnicalNumber(Integer.parseInt(jishuNum));
        companyInfoBean.setOrdinaryNumber(Integer.parseInt(pugongNum));
        companyInfoBean.setPlantArea(Integer.parseInt(changfangmianji));
        String nature = personName.getText().toString().trim();
        companyInfoBean.setPlantNature("自建".equals(nature) ? 0 : 1);
        request.setCompanyInfo(companyInfoBean);
        request.setCompanyDevices(devices);
        return request;
    }

    /**
     * 是否填完数据
     */
    public boolean isCommit() {
        guanliNum = complanGuanliNum.getText();
        jishuNum = complanJishuNum.getText();
        pugongNum = complanPugongNum.getText();
        changfangmianji = complanChangfangMianji.getText();
        if (StringUtils.isEmpty(guanliNum)) {
            showToast("请输入管理人员数！");
            return false;
        }
        if (StringUtils.isEmpty(jishuNum)) {
            showToast("请输入技术人员数！");
            return false;
        }
        if (StringUtils.isEmpty(pugongNum)) {
            showToast("请输入普工人员数！");
            return false;
        }
        if (StringUtils.isEmpty(changfangmianji)) {
            showToast("请输入厂房面积！");
            return false;
        }
        if (StringUtils.isEmpty(personName.getText().toString().trim())) {
            showToast("请选择厂房性质！");
            return false;
        }
        return true;
    }

}
