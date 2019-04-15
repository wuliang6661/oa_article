package com.wul.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.module.create_order.CreateOrderFragment;

/**
 * 创建订单页面
 */
public class CreateActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.act_create_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("创建订单");
        FragmentUtils.replace(getSupportFragmentManager(), CreateOrderFragment.newInstance(1, 0), R.id.fragment_container);
    }

}