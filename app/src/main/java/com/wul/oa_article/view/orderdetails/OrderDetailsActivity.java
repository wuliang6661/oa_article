package com.wul.oa_article.view.orderdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wul.oa_article.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter>
        implements OrderDetailsContract.View {

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
