package com.article.oa_article.view.main.home.compony;

import com.article.oa_article.bean.OrderNumBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;
import com.article.oa_article.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComponyContract {
    interface View extends BaseRequestView {

        void getOrderNum(OrderNumBO orderNumBO);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
