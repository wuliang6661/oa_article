package com.article.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.event.UpdateTaskEvent;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.module.my_complete.My_completeFragment;
import com.article.oa_article.module.order_details.Order_detailsFragment;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.blankj.utilcode.util.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.next_button)
    Button nextButton;

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

        setTitleText("执行任务");
        back.setOnClickListener(view -> AppManager.getAppManager().goHome());
        EventBus.getDefault().register(this);

        id = getIntent().getExtras().getInt("taskId");
        detailsFragment = new Order_detailsFragment();
        completeFragment = new My_completeFragment();
        FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), completeFragment, R.id.my_complete);

        shangjiLayout.setVisibility(View.GONE);
        shangjiTaskBar.setVisibility(View.GONE);

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
                    orderNum.setText(orderInfoBo.getOrderInfo().getCompanyOrderNum());
                    orderNum.setVisibility(View.VISIBLE);
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
        if (isUpdate) {
            isUpdate = false;
            if (details != null && details.getTaskInfo() != null) {
                completeFragment.setTask(details);
                if (details.getTaskInfo().getAddButton() == 1) {
                    nextButton.setVisibility(View.VISIBLE);
                } else {
                    nextButton.setVisibility(View.GONE);
                }
            }
        }
//        if (parentId == 0) {   //当前没有取过父级任务
        if (details != null && details.getTaskInfo() != null) {
            completeFragment.setTask(details);
            if (details.getTaskInfo().getAddButton() == 1) {
                nextButton.setVisibility(View.VISIBLE);
            } else {
                nextButton.setVisibility(View.GONE);
            }
//            if (details.getTaskInfo().getParentId() != 0) {
//                parentId = details.getTaskInfo().getParentId();
////                    getTaskInfo(parentId);
//                shangjiLayout.setVisibility(View.VISIBLE);
//                shangjiTaskBar.setVisibility(View.VISIBLE);
//                taskName.setText(details.getTaskInfo().getTaskName());
//                taskDate.setText(TimeUtils.millis2String(details.getTaskInfo().getPlanCompleteDate(),
//                        new SimpleDateFormat("yyyy/MM/dd")));
//                taskPersonName.setText(details.getTaskInfo().getNickName());
//                parentTask = details;
//            }
        } else {
            showToast("任务数据为空！请检查订单！");
        }
//        } else {
//            if (details == null || details.getTaskInfo() == null) {
//                shangjiLayout.setVisibility(View.GONE);
//                shangjiTaskBar.setVisibility(View.GONE);
//            } else {
////                completeFragment.setTask(details);
//                shangjiLayout.setVisibility(View.VISIBLE);
//                shangjiTaskBar.setVisibility(View.VISIBLE);
//                taskName.setText(details.getTaskInfo().getTaskName());
//                taskDate.setText(TimeUtils.millis2String(details.getTaskInfo().getPlanCompleteDate(),
//                        new SimpleDateFormat("yyyy/MM/dd")));
//                taskPersonName.setText(details.getTaskInfo().getNickName());
//                parentTask = details;
//            }
//        }
    }

    private boolean isUpdate = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTask(UpdateTaskEvent event) {
        isUpdate = true;
        getTaskInfo(id);
    }


    @OnClick(R.id.next_button)
    public void commitTask() {
        completeFragment.commitTask();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
