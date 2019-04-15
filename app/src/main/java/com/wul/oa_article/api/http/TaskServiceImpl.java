package com.wul.oa_article.api.http;

import com.wul.oa_article.api.ApiManager;
import com.wul.oa_article.api.HttpService;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.bean.request.TaskModeRequest;
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


}
