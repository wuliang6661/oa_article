package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 自己完成界面
 */

public class MyOrderActivity extends BaseActivity {


    My_completeFragment completeFragment;
    Order_detailsFragment detailsFragment;

    private int id;

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
                completeFragment.setTask(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
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
