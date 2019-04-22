package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.TaskBO;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.module.my_complete.My_completeFragment;
import com.wul.oa_article.module.order_details.Order_detailsFragment;


/**
 * 自己完成界面
 */

public class MyOrderActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.act_my_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("我的任务");

        FragmentUtils.replace(getSupportFragmentManager(), new Order_detailsFragment(), R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), new My_completeFragment(), R.id.my_complete);
    }


    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        HttpServerImpl.getOrderByTaskId(request).subscribe(new HttpResultSubscriber<TaskBO>() {
            @Override
            public void onSuccess(TaskBO s) {
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 根据订单ID获取任务数据
     */
    private void getTaskByOrder() {

    }

}
