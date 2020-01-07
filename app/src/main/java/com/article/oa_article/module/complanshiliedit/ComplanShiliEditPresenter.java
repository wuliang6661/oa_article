package com.article.oa_article.module.complanshiliedit;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.event.UpdateComplanEvent;
import com.article.oa_article.bean.request.UpdateShiliRequest;
import com.article.oa_article.mvp.BasePresenterImpl;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComplanShiliEditPresenter extends BasePresenterImpl<ComplanShiliEditContract.View>
        implements ComplanShiliEditContract.Presenter {

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


    public void updateShiliInfo(UpdateShiliRequest request) {
        PersonServiceImpl.updateComplanInfo3(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    ToastUtils.showShort(s);
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
