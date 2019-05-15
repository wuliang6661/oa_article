package com.article.oa_article.module.taskcenter;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.bean.TaskCenterBo;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TaskCenterPresenter extends BasePresenterImpl<TaskCenterContract.View>
        implements TaskCenterContract.Presenter {


    public void getTaskList(PageRequest request) {
        request.setPageSize(1000);
        request.setPageNum(1);
        TaskServiceImpl.getTaskList(request).subscribe(new HttpResultSubscriber<List<TaskCenterBo>>() {
            @Override
            public void onSuccess(List<TaskCenterBo> s) {
                if (mView != null) {
                    mView.getTaskList(s);
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
