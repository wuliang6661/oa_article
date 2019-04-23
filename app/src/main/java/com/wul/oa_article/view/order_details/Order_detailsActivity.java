package com.wul.oa_article.view.order_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.PenPaiTaskBO;
import com.wul.oa_article.bean.TaskDetails;
import com.wul.oa_article.bean.event.OrderEditSuressEvent;
import com.wul.oa_article.bean.request.IdTypeRequest;
import com.wul.oa_article.module.create_order.CreateOrderFragment;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;
import com.wul.oa_article.mvp.MVPBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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

    private boolean isOrder = true;   //是否是订单id
    private int id;

    OrderInfoBo infoBo;

    Task_allotFragment fragment;

    int taskIsEdit = 0;   //可编辑

    @Override
    protected int getLayout() {
        return R.layout.act_order_details_two;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("订单详情");

        EventBus.getDefault().register(this);
        isOrder = getIntent().getExtras().getBoolean("isOrder", true);
        id = getIntent().getExtras().getInt("id");

        fragment = new Task_allotFragment();
        IdTypeRequest request = new IdTypeRequest();
        request.setId(id);
        if (isOrder) {
            request.setType(0);
        } else {
            request.setType(1);
            mPresenter.getTaskInfo(id);
        }
        mPresenter.getOrderInfo(request);
        mPresenter.getTaskList(request);
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

    @OnClick(R.id.order_edit)
    public void editOrder() {
        kehuMsgBar.setVisibility(View.GONE);
        orderDetails.setVisibility(View.VISIBLE);
        CreateOrderFragment fragment = new CreateOrderFragment();
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.order_details);
        fragment.setData(2, infoBo);   //编辑订单
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getOrderInfo(OrderInfoBo orderInfoBo) {
        this.infoBo = orderInfoBo;
        if (orderInfoBo.getOrderInfo().getCanEdit() == 0) {  //不可编辑
            kehuMsgBar.setVisibility(View.GONE);
            orderDetails.setVisibility(View.VISIBLE);
            Order_detailsFragment fragment = new Order_detailsFragment();
            FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.order_details);
            fragment.setOrderInfo(orderInfoBo);
        } else {  //可编辑
            kehuMsgBar.setVisibility(View.VISIBLE);
            orderDetails.setVisibility(View.GONE);
            taskIsEdit = 0;
        }
    }

    /**
     * 任务列表
     */
    @Override
    public void getTaskList(List<PenPaiTaskBO> taskBOList) {
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.task_allot);
        fragment.setTaskList(0, taskBOList);
        fragment.setIsOrder(isOrder, id);
        fragment.isEdit(taskIsEdit);
    }

    @Override
    public void getTaskInfo(TaskDetails details) {
        if (details.getTaskInfo().getCanEdit() == 0) {
            taskIsEdit = 1;
        } else {
            taskIsEdit = 0;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OrderUpDateSuress(OrderEditSuressEvent event) {
        kehuMsgBar.setVisibility(View.VISIBLE);
        orderDetails.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
