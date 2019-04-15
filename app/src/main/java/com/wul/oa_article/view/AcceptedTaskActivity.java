package com.wul.oa_article.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.TaskBO;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_accept.Task_acceptFragment;

import butterknife.BindView;


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

    private int taskId;

    Order_detailsFragment detailsFragment;
    Task_acceptFragment acceptFragment;

    @Override
    protected int getLayout() {
        return R.layout.act_accepted_task;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("接受任务");

        taskId = getIntent().getExtras().getInt("taskId");
        detailsFragment = Order_detailsFragment.newInstance(2, taskId);
        acceptFragment = new Task_acceptFragment();
        FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), acceptFragment, R.id.accept_task);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getOrderByTaskId(taskId);
    }

    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId(int id) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setId(id);
        HttpServerImpl.getOrderByTaskId(request).subscribe(new HttpResultSubscriber<TaskBO>() {
            @Override
            public void onSuccess(TaskBO s) {
                new Handler().post(() -> {
                    detailsFragment.setOrderInfo(s.getOrder());
                    acceptFragment.setTask(s);
                });
            }

            @Override
            public void onFiled(String message) {
                new Handler().post(() -> {
                    showToast(message);
                });
            }
        });
    }

}
