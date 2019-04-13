package com.wul.oa_article.module.create_order;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.wul.oa_article.R;
import com.wul.oa_article.bean.request.CreateOrderBO;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.util.PhotoFromPhotoAlbum;
import com.wul.oa_article.view.EditPhotoNamePop;
import com.wul.oa_article.view.CreateTaskActivity;
import com.wul.oa_article.widget.AlertDialog;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderFragment extends MVPBaseFragment<CreateOrderContract.View, CreateOrderPresenter>
        implements CreateOrderContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.edit_kehu_jiancheng)
    EditText editKehuJiancheng;
    @BindView(R.id.edit_kehu_ordername)
    EditText editKehuOrdername;
    @BindView(R.id.edit_kehu_ordernum)
    EditText editKehuOrdernum;
    @BindView(R.id.ben_expand_layout)
    LinearLayout benExpandLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.pinglei_expand_layout)
    LinearLayout pingleiExpandLayout;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.kehu_expand_layout)
    LinearLayout kehuExpandLayout;
    @BindView(R.id.edit_pinglei_name)
    EditText editPingleiName;
    @BindView(R.id.edit_pinglei_guige)
    EditText editPingleiGuige;
    @BindView(R.id.edit_pinglei_num)
    EditText editPingleiNum;
    @BindView(R.id.edit_pinglei_danwei)
    EditText editPingleiDanwei;
    @BindView(R.id.edit_beizhu)
    EditText editBeizhu;
    @BindView(R.id.edit_ben_orderName)
    EditText editBenOrderName;
    @BindView(R.id.edit_ben_orderNum)
    EditText editBenOrderNum;
    @BindView(R.id.edit_ben_num)
    EditText editBenNum;
    @BindView(R.id.edit_ben_danwei)
    EditText editBenDanwei;
    @BindView(R.id.date_order)
    TextView dateOrder;
    @BindView(R.id.mianview)
    LinearLayout mainView;


    List<PingLeiBO> pingLeiBOS;   //添加的品类列表
    List<ImageBO> imageBOS;      //添加的图片列表
    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.ben_check)
    CheckBox benCheck;
    @BindView(R.id.pinglei_check)
    CheckBox pingleiCheck;
    @BindView(R.id.image_check)
    CheckBox imageCheck;
    @BindView(R.id.beizhu_check)
    CheckBox beizhuCheck;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    TimePickerView pvTime;

    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy年 MM月 dd日");

    Unbinder unbinder;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPermission();
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");
        pingLeiBOS = new ArrayList<>();
        imageBOS = new ArrayList<>();
        initView();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_edit_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        imageRecycle.setHasFixedSize(true);
        imageRecycle.setLayoutManager(gridLayoutManager);
        imageRecycle.setNestedScrollingEnabled(false);

        setPingLeiAdapter();
        setImageAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.date_order)
    public void selectDate() {
        initTimePicker();
    }


    @OnClick(R.id.next_button)
    public void commit() {
        String kehuName = editKehuJiancheng.getText().toString().trim();
        String kehuOrderName = editKehuOrdername.getText().toString().trim();
        String kehuOrderNum = editKehuOrdernum.getText().toString().trim();
        String benOrderName = editBenOrderName.getText().toString().trim();
        String benOrderNum = editBenOrderNum.getText().toString().trim();
        String benNum = editBenNum.getText().toString().trim();
        String benDanwei = editBenDanwei.getText().toString().trim();
        String beizhu = editBeizhu.getText().toString().trim();
        String orderDate = dateOrder.getText().toString().trim();

        CreateOrderBO orderBO = new CreateOrderBO();
//        orderBO.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        orderBO.setClientName(kehuName);
        orderBO.setClientOrderName(kehuOrderName);
        orderBO.setClientOrderNum(kehuOrderNum);

        orderBO.setClientOrderName(benOrderName);
        orderBO.setCompanyOrderNum(benOrderNum);
        orderBO.setOrderNum(Long.parseLong(benNum));
        orderBO.setOrderUnit(benDanwei);
        orderBO.setImages(imageBOS);
        orderBO.setOrderSpecifications(pingLeiBOS);
        orderBO.setRemark(beizhu);
        orderBO.setPlanCompleteDate(orderDate.replace("年 ", "-")
                .replace("月 ", "-").replace("日", ""));
        mPresenter.createOrder(orderBO);
//        gotoActivity(OrderDetailsActivity.class, false);
//        gotoActivity(PcUpdateAct.class, false);
        gotoActivity(CreateTaskActivity.class, false);
    }


    /**
     * 品类添加
     */
    @OnClick(R.id.add_pinglei)
    public void addPingLei() {
        String pingName = editPingleiName.getText().toString().trim();
        String pingGuige = editPingleiGuige.getText().toString().trim();
        String pingNum = editPingleiNum.getText().toString().trim();
        String pingDanWei = editPingleiDanwei.getText().toString().trim();
        if (StringUtils.isEmpty(pingName)) {
            showToast("请输入自定义名称！");
            return;
        }
        if (StringUtils.isEmpty(pingGuige)) {
            showToast("请输入规格！");
            return;
        }
        if (StringUtils.isEmpty(pingNum)) {
            showToast("请输入数量！");
            return;
        }
        if (StringUtils.isEmpty(pingDanWei)) {
            showToast("请输入单位！");
            return;
        }
        editPingleiName.setText("");
        editPingleiGuige.setText("");
        editPingleiNum.setText("");
        editPingleiDanwei.setText("");
        PingLeiBO pingLeiBO = new PingLeiBO(pingName, pingGuige, pingNum, pingDanWei);
        pingLeiBOS.add(pingLeiBO);
        setPingLeiAdapter();
    }


    /**
     * 设置品类列表显示
     */
    private void setPingLeiAdapter() {
        LGRecycleViewAdapter<PingLeiBO> adapter = new LGRecycleViewAdapter<PingLeiBO>(pingLeiBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_pinglei;
            }

            @Override
            public void convert(LGViewHolder holder, PingLeiBO pingLeiBO, int position) {
                holder.setText(R.id.pinglei_name, pingLeiBO.name);
                holder.setText(R.id.pinglei_guige, pingLeiBO.size);
                holder.setText(R.id.pinglei_num, pingLeiBO.num);
                holder.setText(R.id.pinglei_danwei, pingLeiBO.unit);
            }
        };
        recycleView.setAdapter(adapter);
    }


    /**
     * 设置添加图片的适配器
     */
    private void setImageAdapter() {
        ImageAddAdapter addAdapter = new ImageAddAdapter(getActivity(), imageBOS);
        addAdapter.setListener(new ImageAddAdapter.onAddImageAdapterListener() {
            @Override
            public void addImage() {
                ActionSheet.showSheet(Objects.requireNonNull(getActivity()), CreateOrderFragment.this, null);
            }

            @Override
            public void deleteImage(int position, ImageBO imageBO) {
                new AlertDialog(Objects.requireNonNull(getActivity())).builder().setGone().setMsg("是否删除照片？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> removeImage(position)).show();
            }

            @Override
            public void editName(int position) {
                EditPhotoNamePop pop = new EditPhotoNamePop(getActivity());
                pop.setListener(text -> {
                    ImageBO imageBO = imageBOS.get(position);
                    imageBO.name = text;
                    imageBOS.set(position, imageBO);
                    addAdapter.notifyDataSetChanged();
                });
                pop.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
            }
        });
        imageRecycle.setAdapter(addAdapter);
    }


    /**
     * 折叠布局的展开收缩处理
     */
    @OnClick({R.id.kehu_msg_bar, R.id.bengongsi_msg_bar, R.id.pinglei_bar,
            R.id.update_img_bar, R.id.beizhu_bar})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.kehu_msg_bar:
                if (kehuExpandLayout.getVisibility() == View.VISIBLE) {
                    kehuExpandLayout.setVisibility(View.GONE);
                    kehuOrderCheck.setChecked(true);
                } else {
                    kehuExpandLayout.setVisibility(View.VISIBLE);
                    kehuOrderCheck.setChecked(false);
                }
                break;
            case R.id.bengongsi_msg_bar:
                if (benExpandLayout.getVisibility() == View.VISIBLE) {
                    benExpandLayout.setVisibility(View.GONE);
                    benCheck.setChecked(true);
                } else {
                    benExpandLayout.setVisibility(View.VISIBLE);
                    benCheck.setChecked(false);
                }
                break;
            case R.id.pinglei_bar:
                if (pingleiExpandLayout.getVisibility() == View.VISIBLE) {
                    pingleiExpandLayout.setVisibility(View.GONE);
                    pingleiCheck.setChecked(true);
                } else {
                    pingleiExpandLayout.setVisibility(View.VISIBLE);
                    pingleiCheck.setChecked(false);
                }
                break;
            case R.id.update_img_bar:
                if (imageRecycle.getVisibility() == View.VISIBLE) {
                    imageRecycle.setVisibility(View.GONE);
                    imageCheck.setChecked(true);
                } else {
                    imageRecycle.setVisibility(View.VISIBLE);
                    imageCheck.setChecked(false);
                }
                break;
            case R.id.beizhu_bar:
                if (editBeizhu.getVisibility() == View.VISIBLE) {
                    editBeizhu.setVisibility(View.GONE);
                    beizhuCheck.setChecked(true);
                } else {
                    editBeizhu.setVisibility(View.VISIBLE);
                    kehuOrderCheck.setChecked(false);
                }
                break;
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

    /**
     * 获取到新的照片
     */
    private void addImage(String name, String imagePath) {
        ImageBO imageBO = new ImageBO();
        imageBO.name = name;
        imageBO.url = imagePath;
        imageBOS.add(imageBO);
        setImageAdapter();
    }

    /**
     * 删除照片
     */
    private void removeImage(int position) {
        imageBOS.remove(position);
        setImageAdapter();
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
            uri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity()), "com.wul.oa_article.fileprovider", cameraSavePath);
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
            mPresenter.updateImage(new File(photoPath));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(getActivity(), data.getData());
            showProgress();
            mPresenter.updateImage(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker() {
        Calendar startDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(getActivity(), (date, v) -> {
            dateOrder.setText(TimeUtils.date2String(date, format));
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setDate(startDate)
                .setLineSpacingMultiplier(1.8f)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        pvTime.show();
    }


    @Override
    public void onRequestError(String msg) {
        stopProgress();
        showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void updateSourss(String name, String imageUrl) {
        stopProgress();
        addImage(name, imageUrl);
    }
}
