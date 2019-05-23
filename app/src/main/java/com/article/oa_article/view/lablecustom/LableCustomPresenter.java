package com.article.oa_article.view.lablecustom;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LableCustomPresenter extends BasePresenterImpl<LableCustomContract.View>
        implements LableCustomContract.Presenter {


    public void getAllLables() {
        PersonServiceImpl.getAllLables().subscribe(new HttpResultSubscriber<LableBo>() {
            @Override
            public void onSuccess(LableBo s) {
                if (mView != null) {
                    mView.getLable(s);
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


    public void deleteLable(int id) {
        PersonServiceImpl.deleteLable(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getAllLables();
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    public void addLable(String name) {
        PersonServiceImpl.addLable(name).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getAllLables();
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
