package com.article.oa_article.module.create_order;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.event.UpdateOrderEvent;
import com.article.oa_article.bean.request.CreateOrderBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.UpdateOrderRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.PhotoFromPhotoAlbum;
import com.article.oa_article.view.EditPhotoNamePop;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.DateDialog;
import com.article.oa_article.widget.EditMsgText;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.guoqi.actionsheet.ActionSheet;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderFragment extends MVPBaseFragment<CreateOrderContract.View, CreateOrderPresenter>
        implements CreateOrderContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.edit_kehu_jiancheng)
    EditMsgText editKehuJiancheng;
    @BindView(R.id.edit_kehu_ordername)
    EditMsgText editKehuOrdername;
    @BindView(R.id.edit_kehu_ordernum)
    EditMsgText editKehuOrdernum;
    @BindView(R.id.ben_expand_layout)
    LinearLayout benExpandLayout;
    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recycleView;
    @BindView(R.id.pinglei_expand_layout)
    LinearLayout pingleiExpandLayout;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.kehu_expand_layout)
    LinearLayout kehuExpandLayout;
    @BindView(R.id.edit_beizhu)
    EditText editBeizhu;
    @BindView(R.id.edit_ben_orderName)
    EditMsgText editBenOrderName;
    @BindView(R.id.edit_ben_orderNum)
    EditMsgText editBenOrderNum;
    @BindView(R.id.edit_ben_num)
    EditMsgText editBenNum;
    @BindView(R.id.edit_ben_danwei)
    EditMsgText editBenDanwei;
    @BindView(R.id.date_order)
    TextView dateOrder;
    @BindView(R.id.mianview)
    LinearLayout mainView;
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
    Unbinder unbinder;
    @BindView(R.id.edit_kehu_num)
    TextView editKehuNum;
    @BindView(R.id.edit_kehu_danwei)
    TextView editKehuDanwei;
    @BindView(R.id.edit_kehu_clientdate)
    TextView editKehuClientdate;
    @BindView(R.id.client_kehu_layout)
    LinearLayout clientKehuLayout;
    @BindView(R.id.pinglei_bar)
    LinearLayout pingleiBar;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.image_hint)
    TextView imageHint;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    TimePickerView pvTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    List<PingLeiBO> pingLeiBOS;   //添加的品类列表
    List<ImageBO> imageBOS;      //添加的图片列表
    private int type = 1;    //1为创建订单   2为编辑订单
    private OrderInfoBo orderInfoBo;

    private String kehuName;
    private String kehuOrderName;
    private String kehuOrderNum;
    private String benOrderName;
    private String benOrderNum;
    private String benNum;
    private String benDanwei;
    private String beizhu;
    private String orderDate;

    private boolean isWaiBu = false;   //是否外部订单，默认不是
    private int taskId;    //外部任务ID
    ClientOrderBo clientOrderBo;

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
        recycleView.setLayoutManager(manager);
        recycleView.setNestedScrollingEnabled(false);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);
        setSwipeMenu();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        imageRecycle.setHasFixedSize(true);
        imageRecycle.setLayoutManager(gridLayoutManager);
        imageRecycle.setNestedScrollingEnabled(false);

        dateOrder.setText(TimeUtils.date2String(new Date(), format));

        setPingLeiAdapter();
        setImageAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.date_layout)
    public void selectDate() {
        initTimePicker();
    }


    @OnClick(R.id.next_button)
    public void commit() {
        kehuName = editKehuJiancheng.getText().toString().trim();
        kehuOrderName = editKehuOrdername.getText().toString().trim();
        kehuOrderNum = editKehuOrdernum.getText().toString().trim();
        benOrderName = editBenOrderName.getText().toString().trim();
        benOrderNum = editBenOrderNum.getText().toString().trim();
        benNum = editBenNum.getText().toString().trim();
        benDanwei = editBenDanwei.getText().toString().trim();
        beizhu = editBeizhu.getText().toString().trim();
        orderDate = dateOrder.getText().toString().trim();

        if (StringUtils.isEmpty(benOrderName)) {
            showToast("请输入本公司订单名称！");
            return;
        }
        if (StringUtils.isEmpty(benOrderNum)) {
            showToast("请输入本公司订单编号！");
            return;
        }
        if (StringUtils.isEmpty(benNum)) {
            showToast("请输入数量！");
            return;
        }
        if (StringUtils.isEmpty(benDanwei)) {
            showToast("请输入单位！");
            return;
        }
        if (type == 1) {
            createOrder();
        } else {
            updateOrder();
        }
    }


    /**
     * 创建订单
     */
    private void createOrder() {
        CreateOrderBO orderBO = new CreateOrderBO();
        orderBO.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        orderBO.setClientName(kehuName);
        orderBO.setClientOrderName(kehuOrderName);
        orderBO.setClientOrderNum(kehuOrderNum);
        if (isWaiBu) {
            orderBO.setClientNum(clientOrderBo.getClientNum());
            orderBO.setClientCompleteDate(clientOrderBo.getClientCompleteDate().replace("/", "-"));
            orderBO.setClientUnit(clientOrderBo.getClientUnit());
            orderBO.setParentOrderTaskId(taskId);
        }

        orderBO.setCompanyOrderName(benOrderName);
        orderBO.setCompanyOrderNum(benOrderNum);
        orderBO.setOrderNum(Long.parseLong(benNum));
        orderBO.setOrderUnit(benDanwei);
        orderBO.setImages(imageBOS);
        orderBO.setOrderSpecifications(pingLeiBOS);
        orderBO.setRemark(beizhu);
        orderBO.setPlanCompleteDate(orderDate.replaceAll("/", "-"));
        showProgress();
        mPresenter.createOrder(orderBO);
    }


    /**
     * 编辑订单
     */
    private void updateOrder() {
        UpdateOrderRequest orderBO = new UpdateOrderRequest();
        orderBO.setCompanyId(orderInfoBo.getOrderInfo().getCompanyId());
        orderBO.setId(orderInfoBo.getOrderInfo().getId());
        orderBO.setClientName(kehuName);
        orderBO.setClientOrderName(kehuOrderName);
        orderBO.setClientOrderNum(kehuOrderNum);

        orderBO.setCompanyOrderName(benOrderName);
        orderBO.setCompanyOrderNum(benOrderNum);
        orderBO.setOrderNum(Integer.parseInt(benNum));
        orderBO.setOrderUnit(benDanwei);
        orderBO.setImages(imageBOS);
        orderBO.setOrderSpecifications(pingLeiBOS);
        orderBO.setRemark(beizhu);
        orderBO.setPlanCompleteDate(orderDate.replaceAll("/", "-"));
        showProgress();
        mPresenter.updateOrder(orderBO);
    }


    /**
     * 品类添加
     */
    @OnClick(R.id.add_pinglei)
    public void addPingLei() {
        PopAddPinleiWindow window = new PopAddPinleiWindow(getActivity(), null, null, null, null);
        window.setListener((name, num, guige, danwei) -> {
            PingLeiBO pingLeiBO = new PingLeiBO(name, guige, num, danwei);
            pingLeiBOS.add(pingLeiBO);
            setPingLeiAdapter();
        });
        window.showAtLocation(Objects.requireNonNull(getActivity()).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @OnClick(R.id.edit_kehu_clientdate)
    public void clientDate() {
        DateDialog.show(getActivity(), editKehuClientdate);
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
        adapter.setOnItemClickListener(R.id.add_layout, (view, position) -> {
            PingLeiBO pingLeiBO = pingLeiBOS.get(position);
            PopAddPinleiWindow window = new PopAddPinleiWindow(getActivity(), pingLeiBO.name, pingLeiBO.num, pingLeiBO.size, pingLeiBO.unit);
            window.setListener((name, num, guige, danwei) -> {
                PingLeiBO pingLeiBO1 = new PingLeiBO(name, guige, num, danwei);
                pingLeiBOS.set(position, pingLeiBO1);
                setPingLeiAdapter();
            });
            window.showAtLocation(Objects.requireNonNull(getActivity()).getWindow().getDecorView(),
                    Gravity.BOTTOM, 0, 0);
        });
        recycleView.setAdapter(adapter);
    }


    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
            deleteItem.setText("删除")
                    .setBackgroundColor(getResources().getColor(R.color.item_delete))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(15) // 文字大小。
                    .setWidth(SizeUtils.dp2px(63))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);
        };
        // 设置监听器。
        recycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            new AlertDialog(Objects.requireNonNull(getActivity())).builder().setGone().setMsg("确定删除该品类？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> {
                        pingLeiBOS.remove(menuBridge.getAdapterPosition());
                        setPingLeiAdapter();
                    }).show();
        };
        // 菜单点击监听。
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
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
                    imageHint.setVisibility(View.GONE);
                    imageCheck.setChecked(true);
                } else {
                    imageRecycle.setVisibility(View.VISIBLE);
                    imageHint.setVisibility(View.VISIBLE);
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, 1);

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


    /**
     * 时间选择器
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker() {
        Calendar startDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(getActivity(), (date, v) -> dateOrder.setText(TimeUtils.date2String(date, format)))
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
    public void updateSourss(String name, String imageUrl) {
        stopProgress();
        addImage(name, imageUrl);
    }

    @Override
    public void addSuress(IdRequest request) {
        stopProgress();
        if (isWaiBu) {
            mPresenter.setTaskMode(taskId, request.getId());
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("id", request.getId());
            bundle.putBoolean("isCreateOrder", true);
            bundle.putBoolean("isOrder", true);
//            if (isWaiBu) {
//                bundle.putBoolean("isOrder", false);
//            } else {
//                bundle.putBoolean("isOrder", true);
//            }
            gotoActivity(Order_detailsActivity.class, bundle, true);
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void getOrderInfo(OrderInfoBo orderInfoBo) {
        new Handler().post(() -> {
            CreateOrderFragment.this.orderInfoBo = orderInfoBo;
            editKehuJiancheng.setText(orderInfoBo.getOrderInfo().getClientName());
            editKehuOrdername.setText(orderInfoBo.getOrderInfo().getClientOrderName());
            editKehuOrdernum.setText(orderInfoBo.getOrderInfo().getClientOrderNum());
            editBeizhu.setText(orderInfoBo.getOrderInfo().getRemark());
            editBenDanwei.setText(orderInfoBo.getOrderInfo().getUnit());
            editBenNum.setText(orderInfoBo.getOrderInfo().getNum() + "");
            editBenOrderName.setText(orderInfoBo.getOrderInfo().getCompanyOrderName());
            editBenOrderNum.setText(orderInfoBo.getOrderInfo().getCompanyOrderNum());
            imageBOS = orderInfoBo.getOrderInfo().getImage();
            dateOrder.setText(TimeUtils.millis2String(orderInfoBo.getOrderInfo().getPlanCompleteDate(), new SimpleDateFormat("yyyy/MM/dd")));
            nextButton.setText("保存");
            setImageAdapter();
            pingLeiBOS = orderInfoBo.getOrderSpecifications();
            setPingLeiAdapter();
        });
    }

    /**
     * 设置数据
     */
    public void setData(int type, OrderInfoBo infoBo) {
        this.type = type;
        this.orderInfoBo = infoBo;
        getOrderInfo(infoBo);
    }


    /**
     * 设置外部订单数据
     */
    public void setClientData(int taskId, ClientOrderBo clientOrderBo) {
        new Handler().post(() -> {
            CreateOrderFragment.this.clientOrderBo = clientOrderBo;
            isWaiBu = true;
            CreateOrderFragment.this.taskId = taskId;
            clientKehuLayout.setVisibility(View.VISIBLE);
            editKehuJiancheng.setText(clientOrderBo.getClientName());
//            editKehuJiancheng.setEnabled(false);
            editKehuOrdername.setText(clientOrderBo.getClientOrderName());
//            editKehuOrdername.setEnabled(false);
            editKehuOrdernum.setText(clientOrderBo.getClientOrderNum());
//            editKehuOrdernum.setEnabled(false);
            editKehuNum.setText(clientOrderBo.getClientNum() + "");
            editKehuDanwei.setText(clientOrderBo.getClientUnit());
            editKehuClientdate.setText(clientOrderBo.getClientCompleteDate());
        });
    }


    @Override
    public void updateSuress() {
        stopProgress();
        showToast("修改成功！");
        EventBus.getDefault().post(new UpdateOrderEvent());
        getActivity().finish();
    }

    @Override
    public void acceptSuress(int orderId) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", orderId);
        bundle.putBoolean("isOrder", true);
        gotoActivity(Order_detailsActivity.class, bundle, true);
    }
}
