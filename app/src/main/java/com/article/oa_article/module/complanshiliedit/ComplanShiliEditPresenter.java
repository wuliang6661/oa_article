package com.article.oa_article.module.complanshiliedit;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.mvp.BasePresenterImpl;

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
}
