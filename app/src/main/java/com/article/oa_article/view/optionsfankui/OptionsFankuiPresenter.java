package com.article.oa_article.view.optionsfankui;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.request.FanKuiRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OptionsFankuiPresenter extends BasePresenterImpl<OptionsFankuiContract.View>
        implements OptionsFankuiContract.Presenter {


    public void addFeed(FanKuiRequest request) {
        PersonServiceImpl.addFeed(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.sourss();
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
