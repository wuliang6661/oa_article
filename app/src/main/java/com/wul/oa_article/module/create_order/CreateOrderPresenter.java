package com.wul.oa_article.module.create_order;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.request.CreateOrderBO;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.bean.request.UpdateOrderRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderPresenter extends BasePresenterImpl<CreateOrderContract.View>
        implements CreateOrderContract.Presenter {


    public void updateImage(File file) {
        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateSourss(file.getName(), s);
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


    /**
     * 创建订单
     */
    public void createOrder(CreateOrderBO createOrderBO) {
        HttpServerImpl.createOrder(createOrderBO).subscribe(new HttpResultSubscriber<IdRequest>() {
            @Override
            public void onSuccess(IdRequest s) {
                if (mView != null) {
                    mView.addSuress(s);
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


    /**
     * 编辑订单
     */
    public void updateOrder(UpdateOrderRequest orderBO) {
        HttpServerImpl.updateOrder(orderBO).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateSuress();
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


    /**
     * 获取订单基本信息
     */
    public void getOrderInfo(int id) {
        IdRequest request = new IdRequest();
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
