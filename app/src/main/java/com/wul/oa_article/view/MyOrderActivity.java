package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.api.http.TaskServiceImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.TaskDetails;
import com.wul.oa_article.bean.event.UpdateTaskEvent;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.bean.request.IdTypeRequest;
import com.wul.oa_article.module.my_complete.My_completeFragment;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.view.order_details.Order_detailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 自己完成界面
 */

public class MyOrderActivity extends BaseActivity {


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

    private int id;
    My_completeFragment completeFragment;
    Order_detailsFragment detailsFragment;

    private int parentId;
    TaskDetails parentTask;

    @Override
    protected int getLayout() {
        return R.layout.act_my_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("我的任务");
        EventBus.getDefault().register(this);

        id = getIntent().getExtras().getInt("taskId");
        detailsFragment = new Order_detailsFragment();
        completeFragment = new My_completeFragment();
        FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), completeFragment, R.id.my_complete);

        IdTypeRequest request = new IdTypeRequest();
        request.setType(1);  //任务
        request.setId(id);
        getOrderInfo(request);
        getTaskInfo(id);
    }


    /**
     * 获取订单详情
     */
    public void getOrderInfo(IdTypeRequest request) {
        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
            @Override
            public void onSuccess(OrderInfoBo orderInfoBo) {
                if (orderInfoBo != null && orderInfoBo.getOrderInfo() != null) {
                    setTitleText(orderInfoBo.getOrderInfo().getCompanyOrderName());
                    detailsFragment.setOrderInfo(orderInfoBo);
                } else {
                    showToast("订单数据为空！请检查订单数据！");
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 获取单个任务详情
     */
    public void getTaskInfo(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        TaskServiceImpl.getTaskInfo(request).subscribe(new HttpResultSubscriber<TaskDetails>() {
            @Override
            public void onSuccess(TaskDetails s) {
                getTaskInfo(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
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
        Bundle bundle = new Bundle();
        bundle.putInt("id", parentTask.getTaskInfo().getId());
        bundle.putBoolean("isOrder", false);
        gotoActivity(Order_detailsActivity.class, bundle, false);
    }


    public void getTaskInfo(TaskDetails details) {
        if (parentId == 0) {   //当前没有取过父级任务
            if (details != null && details.getTaskInfo() != null) {
                completeFragment.setTask(details);
                if (details.getTaskInfo().getParentId() != 0) {
                    parentId = details.getTaskInfo().getParentId();
                    getTaskInfo(parentId);
                }
            } else {
                showToast("任务数据为空！请检查订单！");
            }
        } else {
            if (details == null || details.getTaskInfo() == null) {
                shangjiLayout.setVisibility(View.GONE);
                shangjiTaskBar.setVisibility(View.GONE);
            } else {
                shangjiLayout.setVisibility(View.VISIBLE);
                shangjiTaskBar.setVisibility(View.VISIBLE);
                taskName.setText(details.getTaskInfo().getTaskName());
                taskDate.setText(TimeUtils.millis2String(details.getTaskInfo().getPlanCompleteDate(),
                        new SimpleDateFormat("yyyy/MM/dd")));
                taskPersonName.setText(details.getTaskInfo().getNickName());
                parentTask = details;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTask(UpdateTaskEvent event) {
        getTaskInfo(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
