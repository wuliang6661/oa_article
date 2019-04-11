package com.wul.oa_article.view.createtask;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;


/**
 * 创建任务
 */

public class CreateTaskActivity extends MVPBaseActivity<CreateTaskContract.View, CreateTaskPresenter>
        implements CreateTaskContract.View {


    @Override
    protected int getLayout() {
        return R.layout.act_create_task;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("订单详情");
    }
}
