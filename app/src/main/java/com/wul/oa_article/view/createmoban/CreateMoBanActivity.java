package com.wul.oa_article.view.createmoban;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateMoBanActivity extends MVPBaseActivity<CreateMoBanContract.View, CreateMoBanPresenter>
        implements CreateMoBanContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_create_moban;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("新增模板");
    }


    /**
     * 初始化布局
     */
    private void initView(){

    }




}
