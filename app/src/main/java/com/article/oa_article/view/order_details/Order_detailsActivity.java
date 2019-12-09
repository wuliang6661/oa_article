package com.article.oa_article.view.order_details;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.event.UpdateOrderEvent;
import com.article.oa_article.bean.event.UpdateTaskEvent;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.module.create_order.CreateOrderFragment;
import com.article.oa_article.module.order_details.Order_detailsFragment;
import com.article.oa_article.module.task_allot.Task_allotFragment;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.widget.AlertDialog;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Order_detailsActivity extends MVPBaseActivity<Order_detailsContract.View, Order_detailsPresenter>
        implements Order_detailsContract.View {

    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.order_edit)
    TextView orderEdit;
    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.order_details)
    FrameLayout orderDetails;
    @BindView(R.id.shangji_task_check)
    CheckBox shangjiTaskCheck;
    @BindView(R.id.task_right_button)
    TextView taskRightButton;
    @BindView(R.id.shangji_task_bar)
    LinearLayout shangjiTaskBar;
    @BindView(R.id.task_name)
    TextView taskName;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_person_name)
    TextView taskPersonName;
    @BindView(R.id.shangji_layout)
    LinearLayout shangjiLayout;
    @BindView(R.id.task_allot)
    FrameLayout taskAllot;
    @BindView(R.id.btn_album)
    Button btnAlbum;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.order_num)
    TextView orderNum;

    private boolean isOrder = true;   //是否是订单id
    private int id;
    private int parentId;
    TaskDetails parentTask;

    OrderInfoBo infoBo;
    Task_allotFragment fragment;
    IdTypeRequest request;

    Order_detailsFragment detailsFragment;


    int taskIsEdit = 0;   //可编辑
    private boolean isEditOrder = false;  //是否是修改订单

    @Override
    protected int getLayout() {
        return R.layout.act_order_details_two;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        back.setVisibility(View.VISIBLE);
        setTitleText("订单详情");

        EventBus.getDefault().register(this);
        isOrder = getIntent().getExtras().getBoolean("isOrder", true);
        id = getIntent().getExtras().getInt("id");
        isEditOrder = getIntent().getExtras().getBoolean("isEditOrder", false);

        fragment = new Task_allotFragment();
        request = new IdTypeRequest();
        request.setId(id);
        if (isOrder) {
            request.setType(0);
            shangjiLayout.setVisibility(View.GONE);
            shangjiTaskBar.setVisibility(View.GONE);
        } else {
            request.setType(1);
            shangjiLayout.setVisibility(View.VISIBLE);
            shangjiTaskBar.setVisibility(View.VISIBLE);
            mPresenter.getTaskInfo(id);
        }
        mPresenter.getOrderInfo(request);
    }


    @OnClick(R.id.shangji_task_bar)
    public void BarClick() {
        if (shangjiLayout.getVisibility() == View.VISIBLE) {
            shangjiLayout.setVisibility(View.GONE);
            shangjiTaskCheck.setChecked(true);
        } else {
            shangjiLayout.setVisibility(View.VISIBLE);
            shangjiTaskCheck.setChecked(false);
        }
    }

    /**
     * 返回上一级
     */
    @OnClick(R.id.task_right_button)
    public void goParent() {
        if (parentId != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", parentTask.getTaskInfo().getParentId());
            bundle.putBoolean("isOrder", false);
            gotoActivity(Order_detailsActivity.class, bundle, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("id", parentTask.getTaskInfo().getOrderId());
            bundle.putBoolean("isOrder", true);
            gotoActivity(Order_detailsActivity.class, bundle, false);
        }
    }

    @OnClick(R.id.back)
    public void back() {
        if (fragment.getIsTaskEdit()) {
            new AlertDialog(this).builder().setGone().setMsg("您分派的任务还未保存\n是否确认放弃分派任务？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> {
                        if (isEditOrder) {
                            finish();
                        } else {
                            AppManager.getAppManager().goHome();
                        }
                    }).show();
        } else {
            if (isEditOrder) {
                finish();
            } else {
                AppManager.getAppManager().goHome();
            }
        }
    }

    @OnClick(R.id.btn_album)
    public void cancleOrder() {
        new AlertDialog(Objects.requireNonNull(this)).builder().setGone().setMsg("是否确定取消订单？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> orderCancle()).show();

    }

    private void orderCancle() {
        IdRequest request = new IdRequest();
        request.setId(infoBo.getOrderInfo().getId());
        HttpServerImpl.cancleOrder(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                finish();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getOrderInfo(OrderInfoBo orderInfoBo) {
        this.infoBo = orderInfoBo;
        if (orderInfoBo.getOrderInfo() == null) {
            showToast("订单信息为空！");
            return;
        }
        setTitleText(orderInfoBo.getOrderInfo().getCompanyOrderName());
        orderNum.setText(orderInfoBo.getOrderInfo().getCompanyOrderNum());
        orderNum.setVisibility(View.VISIBLE);
        if (!isEditOrder) {   //显示订单详情
            if (detailsFragment == null) {
                detailsFragment = new Order_detailsFragment();
                FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
            }
            detailsFragment.setOrderInfo(orderInfoBo);
            detailsFragment.setIsTask(!isOrder);
            mPresenter.getTaskList(request);
        } else {    //显示修改订单
            CreateOrderFragment fragment = new CreateOrderFragment();
            FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.order_details);
            fragment.setData(2, infoBo);   //编辑订单
            btnAlbum.setText("取消订单");
            btnAlbum.setVisibility(View.VISIBLE);
        }
    }

    List<PenPaiTaskBO> taskBOS;

    /**
     * 任务列表
     */
    @Override
    public void getTaskList(List<PenPaiTaskBO> taskBOList) {
        taskBOS = taskBOList;
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.task_allot);
        fragment.setIsOrder(isOrder, id);
        mPresenter.tashCanEdit(request);
        if (isOrder) {
            fragment.setEndTime(infoBo.getOrderInfo().getPlanCompleteDate());
            fragment.setPlanNum(infoBo.getOrderInfo().getNum());
        } else {
            fragment.setEndTime(parentTask.getTaskInfo().getPlanCompleteDate());
            fragment.setPlanNum(parentTask.getTaskInfo().getPlanNum());
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void getTaskInfo(TaskDetails details) {
        if (details.getTaskInfo() == null) {
            showToast("获取的订单数据为空！");
            return;
        }
        parentId = details.getTaskInfo().getParentId();
        taskName.setText(details.getTaskInfo().getTaskName());
        taskDate.setText(TimeUtils.millis2String(details.getTaskInfo().getPlanCompleteDate(),
                new SimpleDateFormat("yyyy/MM/dd")));
        taskPersonName.setText(details.getTaskInfo().getNickName());
        parentTask = details;
    }

    @Override
    public void taskCanEdit(boolean isCanEdit) {
        if (isCanEdit) {
            taskIsEdit = 0;
        } else {
            taskIsEdit = 1;
        }
        fragment.setTaskList(taskIsEdit, taskBOS);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTask(UpdateTaskEvent event) {
        mPresenter.getTaskList(request);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateOrderInfo(UpdateOrderEvent event) {
        mPresenter.getOrderInfo(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
