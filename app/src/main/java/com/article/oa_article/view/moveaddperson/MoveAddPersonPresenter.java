package com.article.oa_article.view.moveaddperson;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoveAddPersonPresenter extends BasePresenterImpl<MoveAddPersonContract.View>
        implements MoveAddPersonContract.Presenter {

    public void addUser(AddUserRequest request) {
        PersonServiceImpl.addUser(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addUserSuress();
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
