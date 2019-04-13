package com.wul.oa_article.api.http;

import com.wul.oa_article.api.ApiManager;
import com.wul.oa_article.api.HttpService;


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






}
