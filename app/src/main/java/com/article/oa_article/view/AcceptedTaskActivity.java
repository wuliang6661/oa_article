package com.article.oa_article.view;


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
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.event.UpdateButtonEvent;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.module.order_details.Order_detailsFragment;
import com.article.oa_article.module.task_accept.Task_acceptFragment;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 接受任务界面
 */

public class AcceptedTaskActivity extends BaseActivity {

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
    @BindView(R.id.accept_task)
    FrameLayout acceptTask;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.my_suress)
    Button mySuress;
    @BindView(R.id.task_fenpai)
    Button taskFenpai;
    @BindView(R.id.buttom_layout)
    LinearLayout buttomLayout;

    private int taskId;

    Order_detailsFragment detailsFragment;
    Task_acceptFragment acceptFragment;

    private int parentId;
    TaskDetails parentTask;

    private boolean isHome;   //是否从我的任务跳入
    private boolean isNoPai;   //是否未分派

    @Override
    protected int getLayout() {
        return R.layout.act_accepted_task;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        setTitleText("接受任务");
        back.setOnClickListener(view -> AppManager.getAppManager().goHome());

        taskId = getIntent().getExtras().getInt("taskId");
        isHome = getIntent().getExtras().getBoolean("isHome", false);
        isNoPai = getIntent().getExtras().getBoolean("isNoPai", false);
        detailsFragment = new Order_detailsFragment();
        acceptFragment = new Task_acceptFragment();
        FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), acceptFragment, R.id.accept_task);
        if (isHome) {
            nextButton.setVisibility(View.GONE);
        }

        IdTypeRequest request = new IdTypeRequest();
        request.setId(taskId);
        request.setType(1);
        getTaskInfo(taskId);
        getOrderInfo(request);

    }

    @Subscribe
    public void onEvent(UpdateButtonEvent event) {
        nextButton.setVisibility(View.GONE);
        buttomLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 接受任务
     */
    @OnClick(R.id.next_button)
    public void acceptTask() {
        acceptFragment.acceptTask();
    }


    @OnClick({R.id.my_suress, R.id.task_fenpai})
    public void buttonClick(View view) {
        acceptFragment.buttonClick(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取订单详情
     */
    public void getOrderInfo(IdTypeRequest request) {
        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
            @Override
            public void onSuccess(OrderInfoBo orderInfoBo) {
                setTitleText(orderInfoBo.getOrderInfo().getCompanyOrderName());
                orderNum.setText(orderInfoBo.getOrderInfo().getClientOrderNum());
                orderNum.setVisibility(View.VISIBLE);
                detailsFragment.setOrderInfo(orderInfoBo);
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
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", parentTask.getTaskInfo().getId());
//        bundle.putBoolean("isOrder", false);
//        gotoActivity(Order_detailsActivity.class, bundle, false);
        if (parentId != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", parentId);
            bundle.putBoolean("isOrder", false);
            gotoActivity(Order_detailsActivity.class, bundle, false);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("id", orderId);
            bundle.putBoolean("isOrder", true);
            gotoActivity(Order_detailsActivity.class, bundle, false);
        }
    }

    int orderId;


    public void getTaskInfo(TaskDetails details) {
        if (parentId == 0) {   //当前没有取过父级任务
            acceptFragment.setTask(details);
            if (details.getTaskInfo().getStatus() == 4) {
                nextButton.setVisibility(View.GONE);
                buttomLayout.setVisibility(View.VISIBLE);
            } else if (details.getTaskInfo().getStatus() == 0) {
                nextButton.setVisibility(View.VISIBLE);
                buttomLayout.setVisibility(View.GONE);
            }
            parentId = details.getTaskInfo().getParentId();
            orderId = details.getTaskInfo().getOrderId();
            if (details.getTaskInfo().getParentId() != 0) {
                getTaskInfo(parentId);
            }
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
