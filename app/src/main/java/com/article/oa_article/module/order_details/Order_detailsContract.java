package com.article.oa_article.module.order_details;

import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Order_detailsContract {
    interface View extends BaseRequestView {

        void getOrderInfo(OrderInfoBo infoBo);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
