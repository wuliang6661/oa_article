package com.wul.oa_article.module.task_allot;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.http.TaskServiceImpl;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.bean.request.ShunYanRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Task_allotPresenter extends BasePresenterImpl<Task_allotContract.View>
        implements Task_allotContract.Presenter {


    public void addTaskByOrder(AddTaskRequest request) {
        TaskServiceImpl.addTaskByOrder(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.taskSourss();
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

    //一键顺延
    public void updatePlanDate(int day, int id, int type) {
        ShunYanRequest request = new ShunYanRequest();
        request.setDay(day);
        request.setId(id);
        request.setType(type);
        TaskServiceImpl.shunYanTask(request).subscribe(new HttpResultSubscriber<String>() {
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

}
