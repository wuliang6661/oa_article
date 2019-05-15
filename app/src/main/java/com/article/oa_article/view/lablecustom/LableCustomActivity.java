package com.article.oa_article.view.lablecustom;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 自定义标签页面
 */

public class LableCustomActivity extends MVPBaseActivity<LableCustomContract.View, LableCustomPresenter>
        implements LableCustomContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_lable_custom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("标签选择");

    }



}
