package com.wul.oa_article.view.myorder;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyOrderActivity extends MVPBaseActivity<MyOrderContract.View, MyOrderPresenter>
        implements MyOrderContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_my_order;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("我的任务");
    }
}