package com.wul.oa_article.view.createorder;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.mvp.BasePresenterImpl;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderPresenter extends BasePresenterImpl<CreateOrderContract.View>
        implements CreateOrderContract.Presenter {


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
