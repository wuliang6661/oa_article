package com.article.oa_article.view.addcomplan;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.base.BaseActivity;

public class SuressActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.act_add_complan_suress;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("加入企业");
    }
}
