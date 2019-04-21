package com.wul.oa_article.view.main.personlist;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.http.PersonServiceImpl;
import com.wul.oa_article.bean.BumenBO;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonListPresenter extends BasePresenterImpl<PersonListContract.View>
        implements PersonListContract.Presenter {


    public void getNeiUsers(OrderQueryRequest request) {
        PersonServiceImpl.getNeiPersonList(request).subscribe(new HttpResultSubscriber<List<BumenBO>>() {
            @Override
            public void onSuccess(List<BumenBO> s) {
                if (mView != null) {
                    mView.getPersonListByNeiBu(s);
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


    public void getOutUsers(OrderQueryRequest request) {
        PersonServiceImpl.getOutPersonList(request).subscribe(new HttpResultSubscriber<List<BumenBO>>() {
            @Override
            public void onSuccess(List<BumenBO> s) {
                if (mView != null) {
                    mView.getPersonListByWaiBu(s);
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
