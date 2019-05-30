package com.article.oa_article.view.newlycomplan;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewlyComplanActivity extends MVPBaseActivity<NewlyComplanContract.View, NewlyComplanPresenter>
        implements NewlyComplanContract.View {

    @Override
    protected int getLayout() {
        return R.layout.act_newly_complan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("新增企业");
    }
}
