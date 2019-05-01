package com.article.oa_article.view.main.home.compony;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.OrderNumBO;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComponyPresenter extends BasePresenterImpl<ComponyContract.View>
        implements ComponyContract.Presenter {

    public void getOrderNum(int id) {
        HttpServerImpl.getOrderNum(id).subscribe(new HttpResultSubscriber<OrderNumBO>() {
            @Override
            public void onSuccess(OrderNumBO s) {
                if (mView != null) {
                    mView.getOrderNum(s);
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
