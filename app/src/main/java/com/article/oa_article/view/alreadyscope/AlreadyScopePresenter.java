package com.article.oa_article.view.alreadyscope;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AlreadyScopePresenter extends BasePresenterImpl<AlreadyScopeContract.View>
        implements AlreadyScopeContract.Presenter {


    public void getHaveScope() {
        PersonServiceImpl.getHaveScope().subscribe(new HttpResultSubscriber<List<AlreadyScopeBO>>() {
            @Override
            public void onSuccess(List<AlreadyScopeBO> s) {
                if (mView != null) {
                    mView.getAlreadyScope(s);
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