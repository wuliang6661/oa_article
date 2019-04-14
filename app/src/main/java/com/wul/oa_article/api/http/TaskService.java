package com.wul.oa_article.api.http;

import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.request.AddTaskRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface TaskService {

    /**
     * 分派任务
     */
    @POST("industry_webservice/app/orderTask/addOrderTaskByOrder")
    Observable<BaseResult<String>> addTaskByOrder(@Body AddTaskRequest request);




}
