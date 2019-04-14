package com.wul.oa_article.module.order_details;

import android.content.Context;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Order_detailsPresenter extends BasePresenterImpl<Order_detailsContract.View>
        implements Order_detailsContract.Presenter {


    /**
     * 获取订单基本信息
     */
    public void getOrderInfo(int id) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setId(id);
        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
            @Override
            public void onSuccess(OrderInfoBo s) {
                if (mView != null) {
                    mView.getOrderInfo(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

}
