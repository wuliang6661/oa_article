package com.wul.oa_article.view.main.personlist;

import android.content.Context;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.http.PersonServiceImpl;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonListPresenter extends BasePresenterImpl<PersonListContract.View>
        implements PersonListContract.Presenter {


    public void getNeiUsers(OrderQueryRequest request) {
        PersonServiceImpl.getNeiPersonList(request).subscribe(new HttpResultSubscriber<String>() {
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


    public void getOutUsers(OrderQueryRequest request) {
        PersonServiceImpl.getOutPersonList(request).subscribe(new HttpResultSubscriber<String>() {
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
