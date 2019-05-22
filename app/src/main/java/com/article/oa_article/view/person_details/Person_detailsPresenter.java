package com.article.oa_article.view.person_details;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.bean.request.UserInInfoRequest;
import com.article.oa_article.bean.request.UserOutRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Person_detailsPresenter extends BasePresenterImpl<Person_detailsContract.View>
        implements Person_detailsContract.Presenter {

    public void getInUserInfo(UserInInfoRequest request) {
        PersonServiceImpl.getUserInInfo(request).subscribe(new HttpResultSubscriber<UserInInfoBo>() {
            @Override
            public void onSuccess(UserInInfoBo s) {
                if (mView != null) {
                    mView.getUserInInfo(s);
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


    public void getOutUserInfo(UserOutRequest request) {
        PersonServiceImpl.getOutUserInfo(request).subscribe(new HttpResultSubscriber<UserOutInfo>() {
            @Override
            public void onSuccess(UserOutInfo s) {
                if (mView != null) {
                    mView.getUserOutInfo(s);
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


}
