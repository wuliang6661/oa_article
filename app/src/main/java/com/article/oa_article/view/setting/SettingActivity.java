package com.article.oa_article.view.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 资料设置
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter>
        implements SettingContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("资料设置");
    }
}
