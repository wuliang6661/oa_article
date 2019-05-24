package com.article.oa_article.view.main.mine;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MinePresenter extends BasePresenterImpl<MineContract.View> implements MineContract.Presenter {


    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        HttpServerImpl.getUserinfo().subscribe(new HttpResultSubscriber<UserBo>() {
            @Override
            public void onSuccess(UserBo s) {
                MyApplication.userBo = s;
                if (mView != null) {
                    mView.getUser(s);
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

    public void addComplan(String name) {
        PersonServiceImpl.addComplanName(name).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addComplanSuress();
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
