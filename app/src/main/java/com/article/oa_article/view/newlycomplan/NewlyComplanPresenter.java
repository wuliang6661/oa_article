package com.article.oa_article.view.newlycomplan;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewlyComplanPresenter extends BasePresenterImpl<NewlyComplanContract.View>
        implements NewlyComplanContract.Presenter {

    public void addComplan(AddComplanRequest request) {
        PersonServiceImpl.addComplanInfo(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addSourss();
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
