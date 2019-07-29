package com.article.oa_article.api;

import com.article.oa_article.bean.AcceptedOrderBo;
import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.ComplanOrderBo;
import com.article.oa_article.bean.DateShemeBO;
import com.article.oa_article.bean.DateTaskBo;
import com.article.oa_article.bean.HistoryBO;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.MyOrderBO;
import com.article.oa_article.bean.OrderAndTaskInfoBO;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.OrderNumBO;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.SalesBo;
import com.article.oa_article.bean.TaskBO;
import com.article.oa_article.bean.TaskNumBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.TempleteInfoBo;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.bean.request.AddTempleteBo;
import com.article.oa_article.bean.request.AsseptRequest;
import com.article.oa_article.bean.request.ChoiceRequest;
import com.article.oa_article.bean.request.ClientInfoRequest;
import com.article.oa_article.bean.request.ComplayRequest;
import com.article.oa_article.bean.request.CreateOrderBO;
import com.article.oa_article.bean.request.DateScheduleRequest;
import com.article.oa_article.bean.request.DateTaskRequest;
import com.article.oa_article.bean.request.ForwordPassword;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.bean.request.OrderRequest;
import com.article.oa_article.bean.request.PhoneRequest;
import com.article.oa_article.bean.request.RegistUserRequest;
import com.article.oa_article.bean.request.ScanRequest;
import com.article.oa_article.bean.request.SelectRequest;
import com.article.oa_article.bean.request.TempleteRequest;
import com.article.oa_article.bean.request.TokenRequest;
import com.article.oa_article.bean.request.UpdateOrderRequest;
import com.article.oa_article.bean.request.WechatRegisterRequest;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    String URL = "http://47.98.108.34:8080/";   //正式服
//    String URL = "http://47.98.108.34:8081/";   //测试服
//    String URL = "http://mapi.platform.yinghezhong.com/";  //测试服2
//    String URL = "http://api.open.yinghezhong.com/";  //正式环境
//    String URL = "http://mapi.open.yinghezhong.com/";  //正式环境2


    /**
     * 注册账号
     */
    @POST("industry_webservice/app/userInfo/register")
    Observable<BaseResult<String>> register(@Body RegistUserRequest registUserRequest);

    /**
     * 发送短信验证码
     */
    @POST("industry_webservice/app/userInfo/sendMessageCode")
    Observable<BaseResult<String>> sendMessage(@Body PhoneRequest request);

    /**
     * 微信注册获取短信验证码
     */
    @POST("industry_webservice/app/userInfo/sendWXCode")
    Observable<BaseResult<String>> sendWxMessage(@Body PhoneRequest request);


    /**
     * 校验验证码正确
     */
    @POST("industry_webservice/app/userInfo/checkMesCode")
    Observable<BaseResult<String>> checkMesCode(@Body RegistUserRequest request);

    /**
     * 用户登录
     */
    @POST("industry_webservice/app/userInfo/userLogin")
    Observable<BaseResult<String>> login(@Body RegistUserRequest regist);

    /**
     * 微信登录
     */
    @POST("industry_webservice/app/userInfo/weixinLogin")
    Observable<BaseResult<String>> wechatLogin(@Body WechatRegisterRequest registerRequest);

    /**
     * 微信注册
     */
    @POST("industry_webservice/app/userInfo/weixinRegister")
    Observable<BaseResult<String>> weChatRegister(@Body WechatRegisterRequest request);


    /**
     * 获取用户信息
     */
    @POST("industry_webservice/app/userInfo/getUserInfo")
    Observable<BaseResult<UserBo>> getUserInfo(@Body TokenRequest request);

    /**
     * 忘记密码
     */
    @POST("industry_webservice/app/userInfo/forgetPasswordForPhone")
    Observable<BaseResult<String>> forwordPassword(@Body ForwordPassword password);

    /**
     * 根据任务状态查询任务列表
     */
    @POST("industry_webservice/app/orderTask/getOrderTaskList")
    Observable<BaseResult<List<MyOrderBO>>> getOrderByTask(@Body OrderRequest request);

    /**
     * 获取公司订单
     */
    @POST("industry_webservice/app/orderInfo/getOrderList")
    Observable<BaseResult<List<ComplanOrderBo>>> getComplayList(@Body ComplayRequest request);

    /**
     * 获取待接受列表
     */
    @POST("industry_webservice/app/orderTask/getAcceptOrderList")
    Observable<BaseResult<List<AcceptedOrderBo>>> getAcceptOrder(@Body AsseptRequest request);

    /**
     * 获取历史搜索记录
     */
    @POST("industry_webservice/app/userSearch/getUserSearchHistory")
    Observable<BaseResult<List<HistoryBO>>> getSearchHistory(@Body SelectRequest request);

    /**
     * 清除历史搜索记录
     */
    @POST("industry_webservice/app/userSearch/deleteSerachHistory")
    Observable<BaseResult<String>> clearSerachHistory(@Body SelectRequest request);

    /**
     * 获取常用搜索记录
     */
    @POST("industry_webservice/app/userSearch/getCommonlySerachHistory")
    Observable<BaseResult<List<SalesBo>>> getCommonLyHistory(@Body SelectRequest request);

    /**
     * 清空常用搜索记录
     */
    @POST("industry_webservice/app/userSearch/deleteCommonlySerachHistory")
    Observable<BaseResult<String>> clearCommonlyHistory(@Body SelectRequest request);

    /**
     * 创建订单
     */
    @POST("industry_webservice/app/orderInfo/addOrder")
    Observable<BaseResult<IdRequest>> addOrder(@Body CreateOrderBO createOrderBO);

    /**
     * 上传图片
     */
    @Multipart
    @POST("industry_webservice/app/file/fileUploadOss")
    Observable<BaseResult<String>> updateFile(@Part MultipartBody.Part file);

    /**
     * 编辑订单信息
     */
    @POST("industry_webservice/app/orderInfo/updateOrderInfo")
    Observable<BaseResult<String>> updateOrder(@Body UpdateOrderRequest createOrderBO);


    /**
     * 获取订单详情(根据任务id或订单id)
     */
    @POST("industry_webservice/app/orderInfo/getOrderInfoById")
    Observable<BaseResult<OrderInfoBo>> getOrderInfo(@Body IdTypeRequest request);

    /**
     * 获取任务列表
     */
    @POST("industry_webservice/app/orderTask/getOrderTaskListById")
    Observable<BaseResult<List<PenPaiTaskBO>>> getTaskList(@Body IdTypeRequest request);


    /**
     * 取消订单
     */
    @POST("industry_webservice/app/orderInfo/deleteOrderInfo")
    Observable<BaseResult<String>> cancleOrder(@Body IdRequest request);

    /**
     * 根据任务ID查询我的任务信息
     */
    @POST("industry_webservice/app/orderTask/getOrderAndTaskInfoByTaskId")
    Observable<BaseResult<TaskBO>> getOrderByTaskId(@Body IdRequest request);


    /**
     * 根据订单ID查询任务与订单信息
     */
    @POST("industry_webservice/app/orderTask/getOrderInfoAndTaskByOrderId")
    Observable<BaseResult<OrderAndTaskInfoBO>> getInfoByOrderId(@Body IdRequest request);

    /**
     * 根据任务ID查询任务与订单信息
     */
    @POST("industry_webservice/app/task/getOrderAndTaskByTaskId")
    Observable<BaseResult<OrderAndTaskInfoBO>> getInfoByTaskId(@Body IdRequest request);


    /**
     * 模糊查询模板
     */
    @POST("industry_webservice/app/taskTemplate/getTemplate")
    Observable<BaseResult<List<TempleteBO>>> getTemplateList(@Body TempleteRequest request);

    /**
     * 查询模板详情
     */
    @POST("industry_webservice/app/taskTemplate/getTemplateInfo")
    Observable<BaseResult<TempleteInfoBo>> getTemplateInfo(@Body IdRequest request);

    /**
     * 新增模板
     */
    @POST("industry_webservice/app/taskTemplate/addTemplate")
    Observable<BaseResult<String>> addTemplate(@Body AddTempleteBo addTempleteBo);

    /**
     * 修改模板
     */
    @POST("industry_webservice/app/taskTemplate/updateTemplate")
    Observable<BaseResult<String>> updateTemplete(@Body AddTempleteBo addTempleteBo);

    /**
     * 使用模板
     */
    @POST("industry_webservice/app/taskTemplate/getTemplateTasks")
    Observable<BaseResult<List<MuBanTaskBO>>> getTemplateTask(@Body IdRequest request);

    /**
     * 扫一扫获取模板
     */
    @POST("industry_webservice/app/file/getFileContent")
    Observable<BaseResult<List<MuBanTaskBO>>> getFileContent(@Body ScanRequest request);

    /**
     * 获取外部订单信息
     */
    @POST("industry_webservice/app/orderInfo/getClientInfo")
    Observable<BaseResult<ClientOrderBo>> getClientInfo(@Body ClientInfoRequest request);

    /**
     * 获取订单个数
     */
    @POST("industry_webservice/app/orderInfo/getOrderCountByType")
    Observable<BaseResult<OrderNumBO>> getOrdercount(@Body IdRequest request);

    /**
     * 获取任务统计数目
     */
    @POST("industry_webservice/app/orderTask/getTaskCountByType")
    Observable<BaseResult<TaskNumBO>> getTaskCount(@Body IdRequest request);

    /**
     * （造小蜜）查询含有日程的日期
     */
    @POST("industry_webservice/app/userHoney/getOrderTaskToScheduleList")
    Observable<BaseResult<List<DateShemeBO>>> getDateBySchedule(@Body DateScheduleRequest request);

    /**
     * （造小蜜）查询日期下的任务列表
     */
    @POST("industry_webservice/app/userHoney/getOrderTaskListByDay")
    Observable<BaseResult<List<DateTaskBo>>> getTaskByDate(@Body DateTaskRequest request);

    /**
     * 接受外部订单是调用接口
     */
    @POST("industry_webservice/app/orderTask/choiceCompany")
    Observable<BaseResult<String>> choiceCompany(@Body ChoiceRequest request);

    /**
     * 获取电脑上传url
     */
    @POST("industry_webservice/app/taskTemplate/getComputerUploadUrl")
    Observable<BaseResult<String>> getUploadUrl(@Body TokenRequest request);


}
