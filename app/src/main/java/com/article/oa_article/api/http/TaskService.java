package com.article.oa_article.api.http;

import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.CanEditTaskBO;
import com.article.oa_article.bean.MyOrderBO;
import com.article.oa_article.bean.TaskCenterBo;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.request.AddTaskRequest;
import com.article.oa_article.bean.request.CommitTaskRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.bean.request.OverdueTaskRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.bean.request.ShunYanRequest;
import com.article.oa_article.bean.request.TaskModeRequest;
import com.article.oa_article.bean.request.TaskNumRequest;

import java.util.List;

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
     * 任务下分配任务
     */
    @POST("industry_webservice/app/orderTask/addOrderTaskByTask")
    Observable<BaseResult<String>> addTaskByTask(@Body AddTaskRequest request);


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

    /**
     * 获取任务详情
     */
    @POST("industry_webservice/app/orderTask/getOrderToTaskInfo")
    Observable<BaseResult<TaskDetails>> getTaskInfo(@Body IdRequest request);

    /**
     * 修改完成数量
     */
    @POST("industry_webservice/app/orderTask/updateActualNum")
    Observable<BaseResult<String>> updateNum(@Body TaskNumRequest request);

    /**
     * 完成任务
     */
    @POST("industry_webservice/app/orderTask/completeTask")
    Observable<BaseResult<String>> completeTask(@Body CommitTaskRequest request);

    /**
     * 取消任务
     */
    @POST("industry_webservice/app/orderTask/deleteTask")
    Observable<BaseResult<String>> cancleTask(@Body IdRequest request);

    /**
     * 获取任务数据
     */
    @POST("industry_webservice/app/orderTask/getTaskInfo")
    Observable<BaseResult<List<TaskCenterBo>>> getTaskCenterList(@Body PageRequest request);

    /**
     * 任务列表是否可编辑
     */
    @POST("industry_webservice/app/orderTask/taskCanEdit")
    Observable<BaseResult<CanEditTaskBO>> taskCanEdit(@Body IdTypeRequest request);

    /**
     * 接受任务
     */
    @POST("industry_webservice/app/orderTask/acceptTask")
    Observable<BaseResult<String>> acceptTask(@Body IdRequest request);

    /**
     * 获取逾期任务
     */
    @POST("industry_webservice/app/orderTask/getOverdueTaskList")
    Observable<BaseResult<List<MyOrderBO>>> getOverdueTask(@Body OverdueTaskRequest request);

}
