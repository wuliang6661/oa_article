package com.article.oa_article.module.task_allot;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.bean.request.AddTaskRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.ShunYanRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

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
                if (mView != null) {
                    mView.shunyanSourss();
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

    //取消任务
    public void cancleTask(int id, int position, boolean isDelete) {
        IdRequest request = new IdRequest();
        request.setId(id);
        TaskServiceImpl.cancleTask(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.cancleSuress(position, isDelete);
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


    /**
     * 任务下分派任务
     */
    public void addTaskByTask(AddTaskRequest request) {
        TaskServiceImpl.addTaskByTask(request).subscribe(new HttpResultSubscriber<String>() {
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


}
