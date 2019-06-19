package com.article.oa_article.module.tempmanager;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.CountNumBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TempManagerPresenter extends BasePresenterImpl<TempManagerContract.View>
        implements TempManagerContract.Presenter {


    public void getCount() {
        PersonServiceImpl.getCounts().subscribe(new HttpResultSubscriber<CountNumBO>() {
            @Override
            public void onSuccess(CountNumBO s) {
                if (mView != null) {
                    mView.getCount(s);
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


    public void getShareMsg() {
        PersonServiceImpl.getShareMsg().subscribe(new HttpResultSubscriber<ShareBo>() {
            @Override
            public void onSuccess(ShareBo s) {
                if (mView != null) {
                    mView.getShare(s);
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
