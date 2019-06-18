package com.article.oa_article.module.complanziyuanedit;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.event.UpdateComplanEvent;
import com.article.oa_article.bean.request.UpdateZiYuanRequest;
import com.article.oa_article.mvp.BasePresenterImpl;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComplanZiyuanEditPresenter extends BasePresenterImpl<ComplanZiyuanEditContract.View>
        implements ComplanZiyuanEditContract.Presenter {

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

    public void updateComlanInfo2(UpdateZiYuanRequest request) {
        PersonServiceImpl.updateComplanInfo2(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    ToastUtils.showShort("认证正在审核中，请耐心等待");
                    EventBus.getDefault().post(new UpdateComplanEvent());
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
