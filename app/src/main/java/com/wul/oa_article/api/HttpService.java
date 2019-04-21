package com.wul.oa_article.api;

import com.wul.oa_article.bean.AcceptedOrderBo;
import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.ComplanOrderBo;
import com.wul.oa_article.bean.HistoryBO;
import com.wul.oa_article.bean.MyOrderBO;
import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.SalesBo;
import com.wul.oa_article.bean.TaskBO;
import com.wul.oa_article.bean.TempleteBO;
import com.wul.oa_article.bean.TempleteInfoBo;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.bean.request.AddTempleteBo;
import com.wul.oa_article.bean.request.AsseptRequest;
import com.wul.oa_article.bean.request.ComplayRequest;
import com.wul.oa_article.bean.request.CreateOrderBO;
import com.wul.oa_article.bean.request.ForwordPassword;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.bean.request.PhoneRequest;
import com.wul.oa_article.bean.request.RegistUserRequest;
import com.wul.oa_article.bean.request.SelectRequest;
import com.wul.oa_article.bean.request.TempleteRequest;
import com.wul.oa_article.bean.request.TokenRequest;
import com.wul.oa_article.bean.request.UpdateOrderRequest;
import com.wul.oa_article.bean.request.WechatRegisterRequest;

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

    String URL = "http://47.98.53.141:8034/";   //测试服
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
    Observable<BaseResult<OrderQueryRequest>> addOrder(@Body CreateOrderBO createOrderBO);

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
     * 获取订单基础信息
     */
    @POST("industry_webservice/app/orderInfo/getOrderInfo")
    Observable<BaseResult<OrderInfoBo>> getOrderInfo(@Body OrderQueryRequest request);

    /**
     * 取消订单
     */
    @POST("industry_webservice/app/orderInfo/deleteOrderInfo")
    Observable<BaseResult<String>> cancleOrder(@Body OrderQueryRequest request);

    /**
     * 根据任务ID查询订单信息
     */
    @POST("industry_webservice/app/orderTask/getOrderAndTaskInfoByTaskId")
    Observable<BaseResult<TaskBO>> getOrderByTaskId(@Body OrderQueryRequest request);

    /**
     * 模糊查询模板
     */
    @POST("industry_webservice/app/taskTemplate/getTemplate")
    Observable<BaseResult<List<TempleteBO>>> getTemplateList(@Body TempleteRequest request);

    /**
     * 查询模板详情
     */
    @POST("industry_webservice/app/taskTemplate/getTemplateInfo")
    Observable<BaseResult<TempleteInfoBo>> getTemplateInfo(@Body OrderQueryRequest request);

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


}
