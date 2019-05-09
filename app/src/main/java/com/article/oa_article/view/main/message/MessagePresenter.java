package com.article.oa_article.view.main.message;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.MessageServiceImpl;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessagePresenter extends BasePresenterImpl<MessageContract.View>
        implements MessageContract.Presenter {


    public void getMessageList(int id) {
        PageRequest request = new PageRequest();
        request.setId(id);
        MessageServiceImpl.getMessageList(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    public void getReadCount(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        MessageServiceImpl.getNoReadCounts(request).subscribe(new HttpResultSubscriber<Integer>() {
            @Override
            public void onSuccess(Integer s) {
                if (mView != null) {
                    mView.getNoReadCount(s);
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
