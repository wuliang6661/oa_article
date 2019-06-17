package com.article.oa_article.module.complanmsg;

import android.content.Context;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ComplanMsgPresenter extends BasePresenterImpl<ComplanMsgContract.View> implements ComplanMsgContract.Presenter{



    /**
     * 查询企业认证信息
     */
    public void getComplanMsg() {
        PersonServiceImpl.getComplanMsg().subscribe(new HttpResultSubscriber<ComplanBO>() {
            @Override
            public void onSuccess(ComplanBO s) {
                if (mView != null) {
                    mView.getComplanInfo(s);
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

}
