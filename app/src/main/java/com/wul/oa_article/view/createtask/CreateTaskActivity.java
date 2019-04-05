package com.wul.oa_article.view.createtask;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.mvp.MVPBaseActivity;


/**
 * 创建任务
 */

public class CreateTaskActivity extends MVPBaseActivity<CreateTaskContract.View, CreateTaskPresenter>
        implements CreateTaskContract.View {

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
