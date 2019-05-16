package com.article.oa_article.view.alreadyscope;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.bean.request.ScopeRequest;
import com.article.oa_article.mvp.BasePresenterImpl;
import com.blankj.utilcode.util.ToastUtils;

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


    public void getToScope() {
        PersonServiceImpl.getToScope().subscribe(new HttpResultSubscriber<List<AlreadyScopeBO>>() {
            @Override
            public void onSuccess(List<AlreadyScopeBO> alreadyScopeBOS) {
                if (mView != null) {
                    mView.getToScope(alreadyScopeBOS);
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


    public void scope(ScopeRequest request) {
        PersonServiceImpl.scope(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.showShort("评分完成！");
                getToScope();
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
