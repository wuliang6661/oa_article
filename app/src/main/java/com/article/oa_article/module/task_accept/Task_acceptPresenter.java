package com.article.oa_article.module.task_accept;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.bean.request.TaskModeRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Task_acceptPresenter extends BasePresenterImpl<Task_acceptContract.View>
        implements Task_acceptContract.Presenter {

    /**
     * 自己完成还是向下分配
     */
    public void setTaskMode(int taskId, int comType) {
        TaskModeRequest request = new TaskModeRequest();
        request.setTaskId(taskId);
        request.setCompleteType(comType);
        TaskServiceImpl.setTaskMode(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.sourss(comType);
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
