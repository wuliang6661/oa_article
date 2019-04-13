package com.wul.oa_article.module.create_order;

import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderContract {
    interface View extends BaseRequestView {


        void updateSourss(String name, String imageUrl);

        void addSuress(OrderQueryRequest request);

        void getOrderInfo(OrderInfoBo orderInfoBo);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
