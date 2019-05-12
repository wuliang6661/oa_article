package com.article.oa_article.view.main.zaoxiaomi;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.request.DateTaskRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ZaoXiaoMiPresenter extends BasePresenterImpl<ZaoXiaoMiContract.View> implements ZaoXiaoMiContract.Presenter {

    public void getDateSchedule(String date) {
        HttpServerImpl.getDateSchedule(date).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    public void getTaskByDate(String date, int type) {
        DateTaskRequest request = new DateTaskRequest();
        request.setDate(date);
        request.setType(type);
        HttpServerImpl.getTaskByDate(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

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
