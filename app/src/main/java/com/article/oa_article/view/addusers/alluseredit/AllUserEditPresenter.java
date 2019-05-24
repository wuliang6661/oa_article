package com.article.oa_article.view.addusers.alluseredit;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AllUserEditPresenter extends BasePresenterImpl<AllUserEditContract.View>
        implements AllUserEditContract.Presenter {


    public void addUsers(List<AddUserRequest> requests) {
        PersonServiceImpl.addUsers(requests).subscribe(new HttpResultSubscriber<String>() {
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
