package com.article.oa_article.view.main.home.myorder;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.TaskNumBO;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyOrderPresenter extends BasePresenterImpl<MyOrderContract.View>
        implements MyOrderContract.Presenter {

    /**
     * 获取任务统计数据
     */
    public void getTaskNum(int id) {
        HttpServerImpl.getTaskNum(id).subscribe(new HttpResultSubscriber<TaskNumBO>() {
            @Override
            public void onSuccess(TaskNumBO s) {
                if (mView != null) {
                    mView.getTaskNum(s);
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
