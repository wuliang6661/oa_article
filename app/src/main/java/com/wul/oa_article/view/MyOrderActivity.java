package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.module.my_complete.My_completeFragment;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.view.main.home.myorder.MyOrderFragment;


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
}
