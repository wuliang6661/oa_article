package com.wul.oa_article.module.order_details;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.TaskBO;
import com.wul.oa_article.bean.request.IdRequest;
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
//        IdRequest request = new IdRequest();
//        request.setId(id);
//        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
//            @Override
//            public void onSuccess(OrderInfoBo s) {
//                if (mView != null) {
//                    mView.getOrderInfo(s);
//                }
//            }
//
//            @Override
//            public void onFiled(String message) {
//                if (mView != null) {
//                    mView.onRequestError(message);
//                }
//            }
//        });
    }


    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        HttpServerImpl.getOrderByTaskId(request).subscribe(new HttpResultSubscriber<TaskBO>() {
            @Override
            public void onSuccess(TaskBO s) {

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
