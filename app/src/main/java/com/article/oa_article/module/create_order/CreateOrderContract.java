package com.article.oa_article.module.create_order;

import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderContract {
    interface View extends BaseRequestView {


        void updateSourss(String name, String imageUrl);

        void addSuress(IdRequest request);

        void getOrderInfo(OrderInfoBo orderInfoBo);

        void updateSuress();

        void acceptSuress(int orderId);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
