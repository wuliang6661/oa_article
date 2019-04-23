package com.wul.oa_article.module.my_complete;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.http.TaskServiceImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.request.CommitTaskRequest;
import com.wul.oa_article.bean.request.TaskNumRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class My_completePresenter extends BasePresenterImpl<My_completeContract.View>
        implements My_completeContract.Presenter {

    /**
     * 修改完成数量
     */
    public void updateNum(int id, String num) {
        TaskNumRequest request = new TaskNumRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setTaskId(id);
        request.setActualNum(Integer.parseInt(num));
        TaskServiceImpl.updateTaskNum(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateNumSuress();
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


    public void commitTask(int id) {
        CommitTaskRequest request = new CommitTaskRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setTaskId(id);
        TaskServiceImpl.commitTask(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.commitSuress();
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
