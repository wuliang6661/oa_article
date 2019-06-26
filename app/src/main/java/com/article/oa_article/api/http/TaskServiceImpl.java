package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.CanEditTaskBO;
import com.article.oa_article.bean.TaskCenterBo;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.request.AddTaskRequest;
import com.article.oa_article.bean.request.CommitTaskRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.bean.request.ShunYanRequest;
import com.article.oa_article.bean.request.TaskModeRequest;
import com.article.oa_article.bean.request.TaskNumRequest;
import com.article.oa_article.util.rx.RxResultHelper;

import java.util.List;

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
     * 任务下分派任务
     */
    public static Observable<String> addTaskByTask(AddTaskRequest request) {
        request.setToken(MyApplication.token);
        return getService().addTaskByTask(request).compose(RxResultHelper.httpRusult());
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

    /**
     * 获取任务数据列表
     */
    public static Observable<List<TaskCenterBo>> getTaskList(PageRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTaskCenterList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 任务列表是否可编辑
     */
    public static Observable<CanEditTaskBO> taskCanEdit(IdTypeRequest request) {
        request.setToken(MyApplication.token);
        request.setPageNum(1);
        request.setPageSize(1000);
        return getService().taskCanEdit(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 接受任务
     */
    public static Observable<String> acceptTask(int id) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        return getService().acceptTask(request).compose(RxResultHelper.httpRusult());
    }
}
