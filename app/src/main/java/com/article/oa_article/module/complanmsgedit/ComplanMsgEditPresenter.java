package com.article.oa_article.module.complanmsgedit;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComplanMsgEditPresenter extends BasePresenterImpl<ComplanMsgEditContract.View>
        implements ComplanMsgEditContract.Presenter {


    public void updateImage(File file) {
        HttpServerImpl.updateFile(file).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateSourss(file.getName(), s);
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


    public void updateComlanInfo1(AddComplanRequest.CompanyInfoBean request) {
        PersonServiceImpl.updateComplanyinfo1(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateSourss();
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
