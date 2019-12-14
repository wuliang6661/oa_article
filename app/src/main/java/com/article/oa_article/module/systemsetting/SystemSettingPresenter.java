package com.article.oa_article.module.systemsetting;

import android.content.Context;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SystemSettingPresenter extends BasePresenterImpl<SystemSettingContract.View> implements SystemSettingContract.Presenter {


    public void getShareMsg(int flag) {
        PersonServiceImpl.getShareMsg().subscribe(new HttpResultSubscriber<ShareBo>() {
            @Override
            public void onSuccess(ShareBo s) {
                if (mView != null) {
                    mView.getShare(flag, s);
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
