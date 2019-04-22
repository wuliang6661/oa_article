package com.wul.oa_article.api.http;

import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.bean.request.ShunYanRequest;
import com.wul.oa_article.bean.request.TaskModeRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface TaskService {

    /**
     * 订单下分派任务
     */
    @POST("industry_webservice/app/orderTask/addOrderTaskByOrder")
    Observable<BaseResult<String>> addTaskByOrder(@Body AddTaskRequest request);


    /**
     * 任务是自己完成还是向下分配
     */
    @POST("industry_webservice/app/orderTask/addOrderTaskByAcceptToTask")
    Observable<BaseResult<String>> setTaskMode(@Body TaskModeRequest request);

    /**
     * 一键顺延
     */
    @POST("industry_webservice/app/orderTask/updateOrderTaskByPlanDate")
    Observable<BaseResult<String>> updateOrderPlanDate(@Body ShunYanRequest request);


}
