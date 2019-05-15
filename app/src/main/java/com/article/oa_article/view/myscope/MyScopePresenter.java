package com.article.oa_article.view.myscope;

import android.content.Context;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyScopePresenter extends BasePresenterImpl<MyScopeContract.View>
        implements MyScopeContract.Presenter {


    public void getScopeList() {
        PersonServiceImpl.getScopeList().subscribe(new HttpResultSubscriber<List<ScopeBO>>() {
            @Override
            public void onSuccess(List<ScopeBO> s) {
                if (mView != null) {
                    mView.getScopeList(s);
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
