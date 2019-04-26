package com.wul.oa_article.api.http;

import com.wul.oa_article.api.ApiManager;
import com.wul.oa_article.api.HttpService;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.TaskDetails;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.bean.request.CommitTaskRequest;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.bean.request.ShunYanRequest;
import com.wul.oa_article.bean.request.TaskModeRequest;
import com.wul.oa_article.bean.request.TaskNumRequest;
import com.wul.oa_article.util.rx.RxResultHelper;

import rx.Observable;


/**
 * 任务请求
 */
public class TaskServiceImpl {

    private static TaskService service;

    /**
     * 获取代理对象
     */
    public static TaskService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(TaskService.class, HttpService.URL);
        return service;
    }


    /**
     * 分派任务
     */
    public static Observable<String> addTaskByOrder(AddTaskRequest request) {
        request.setToken(MyApplication.token);
        return getService().addTaskByOrder(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 任务处理
     */
    public static Observable<String> setTaskMode(TaskModeRequest request) {
        request.setToken(MyApplication.token);
        return getService().setTaskMode(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 一键顺延
     */
    public static Observable<String> shunYanTask(ShunYanRequest request) {
        request.setToken(MyApplication.token);
        return getService().updateOrderPlanDate(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取任务详情
     */
    public static Observable<TaskDetails> getTaskInfo(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTaskInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 修改完成数量
     */
    public static Observable<String> updateTaskNum(TaskNumRequest request) {
        request.setToken(MyApplication.token);
        return getService().updateNum(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 完成任务
     */
    public static Observable<String> commitTask(CommitTaskRequest request) {
        request.setToken(MyApplication.token);
        return getService().completeTask(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 取消任务
     */
    public static Observable<String> cancleTask(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().cancleTask(request).compose(RxResultHelper.httpRusult());
    }

}
