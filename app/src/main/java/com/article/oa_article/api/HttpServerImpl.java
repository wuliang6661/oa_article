package com.article.oa_article.api;

import com.article.oa_article.bean.OrderNumBO;
import com.article.oa_article.bean.TaskNumBO;
import com.blankj.utilcode.util.Utils;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AcceptedOrderBo;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.ComplanOrderBo;
import com.article.oa_article.bean.HistoryBO;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.MyOrderBO;
import com.article.oa_article.bean.OrderAndTaskInfoBO;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.SalesBo;
import com.article.oa_article.bean.TaskBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.TempleteInfoBo;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.bean.request.AddTempleteBo;
import com.article.oa_article.bean.request.AsseptRequest;
import com.article.oa_article.bean.request.ClientInfoRequest;
import com.article.oa_article.bean.request.ComplayRequest;
import com.article.oa_article.bean.request.CreateOrderBO;
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
import com.article.oa_article.util.MD5;
import com.article.oa_article.util.rx.RxResultHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }

    /**
     * 注册账号
     */
    public static Observable<String> register(String phone, String code, String password, int type) {
        RegistUserRequest request = new RegistUserRequest();
        request.setCode(code);
        request.setPassword(MD5.strToMd5Low32(MD5.strToMd5Low32(password) + "zxq"));
        request.setPhone(phone);
        request.setType(type);
        return getService().register(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 发送短信验证码
     */
    public static Observable<String> sendMessage(String phone, int type) {
        PhoneRequest request = new PhoneRequest();
        request.phone = phone;
        request.type = type;
        return getService().sendMessage(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 微信注册获取短信验证码
     */
    public static Observable<String> sendWxMessage(String phone, int type) {
        PhoneRequest request = new PhoneRequest();
        request.phone = phone;
        request.type = type;
        return getService().sendWxMessage(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 校验验证码正确
     */
    public static Observable<String> checkModeMsg(String phone, String code, int type) {
        RegistUserRequest request = new RegistUserRequest();
        request.setCode(code);
        request.setPhone(phone);
        request.setType(type);
        return getService().checkMesCode(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 登录
     */
    public static Observable<String> login(String phone, String password) {
        RegistUserRequest request = new RegistUserRequest();
        request.setPhone(phone);
        request.setPassword(MD5.strToMd5Low32(MD5.strToMd5Low32(password) + "zxq"));
        return getService().login(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 微信注册
     */
    public static Observable<String> registerWeChat(WechatRegisterRequest registerRequest) {
        return getService().weChatRegister(registerRequest).compose(RxResultHelper.httpRusult());
    }


    /**
     * 微信登录
     */
    public static Observable<String> loginWeChat(WechatRegisterRequest registerRequest) {
        return getService().wechatLogin(registerRequest).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取用户信息
     */
    public static Observable<UserBo> getUserinfo() {
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getUserInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 忘记密码
     */
    public static Observable<String> fordPassword(String newPassword, String confirmPassword) {
        ForwordPassword password = new ForwordPassword();
        password.setConfirmPassword(MD5.strToMd5Low32(MD5.strToMd5Low32(confirmPassword) + "zxq"));
        password.setNewPassword(MD5.strToMd5Low32(MD5.strToMd5Low32(newPassword) + "zxq"));
        password.setToken(MyApplication.token);
        return getService().forwordPassword(password).compose(RxResultHelper.httpRusult());
    }


    /**
     * 根据任务状态查询任务列表
     */
    public static Observable<List<MyOrderBO>> getOrderByTask(OrderRequest request) {
        request.setToken(MyApplication.token);
        return getService().getOrderByTask(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询公司订单列表
     */
    public static Observable<List<ComplanOrderBo>> getComplayList(ComplayRequest request) {
        request.setToken(MyApplication.token);
        return getService().getComplayList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取待接受列表
     */
    public static Observable<List<AcceptedOrderBo>> getAsseptOrder(AsseptRequest request) {
        request.setToken(MyApplication.token);
        return getService().getAcceptOrder(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取历史搜索记录
     */
    public static Observable<List<HistoryBO>> selectHistory(SelectRequest request) {
        request.setToken(MyApplication.token);
        return getService().getSearchHistory(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 清空历史搜索记录
     */
    public static Observable<String> clearHistory(SelectRequest request) {
        request.setToken(MyApplication.token);
        return getService().clearSerachHistory(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取常用搜索记录
     */
    public static Observable<List<SalesBo>> getCommonLyHistory(SelectRequest request) {
        request.setToken(MyApplication.token);
        return getService().getCommonLyHistory(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 清空常用搜索记录
     */
    public static Observable<String> clearCommonLyHistory(SelectRequest request) {
        request.setToken(MyApplication.token);
        return getService().clearCommonlyHistory(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 创建订单
     */
    public static Observable<IdRequest> createOrder(CreateOrderBO createOrderBO) {
        createOrderBO.setToken(MyApplication.token);
        return getService().addOrder(createOrderBO).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交图片
     */
    public static Observable<String> updateFile(File file) {
        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = file;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }


    /**
     * 编辑订单
     */
    public static Observable<String> updateOrder(UpdateOrderRequest orderBO) {
        orderBO.setToken(MyApplication.token);
        return getService().updateOrder(orderBO).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取订单基础信息
     */
    public static Observable<OrderInfoBo> getOrderInfo(IdTypeRequest request) {
        request.setToken(MyApplication.token);
        return getService().getOrderInfo(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取任务列表
     */
    public static Observable<List<PenPaiTaskBO>> getTaskList(IdTypeRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTaskList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 取消订单
     */
    public static Observable<String> cancleOrder(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().cancleOrder(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 根据任务id获取我的任务信息
     */
    public static Observable<TaskBO> getOrderByTaskId(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getOrderByTaskId(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 根据订单ID获取订单与任务信息
     */
    public static Observable<OrderAndTaskInfoBO> getInfoByOrderId(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getInfoByOrderId(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 根据任务ID获取订单与任务信息
     */
    public static Observable<OrderAndTaskInfoBO> getInfoByTaskId(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getInfoByTaskId(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 模糊查询模板列表
     */
    public static Observable<List<TempleteBO>> getTempleteList(TempleteRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTemplateList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询模板详情
     */
    public static Observable<TempleteInfoBo> getTempleteInfo(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTemplateInfo(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 新增模板
     */
    public static Observable<String> addTemplete(AddTempleteBo addTempleteBo) {
        addTempleteBo.setToken(MyApplication.token);
        return getService().addTemplate(addTempleteBo).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改模板
     */
    public static Observable<String> editTemplete(AddTempleteBo addTempleteBo) {
        addTempleteBo.setToken(MyApplication.token);
        return getService().updateTemplete(addTempleteBo).compose(RxResultHelper.httpRusult());
    }

    /**
     * 使用模板
     */
    public static Observable<List<MuBanTaskBO>> makeMuBan(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getTemplateTask(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 扫一扫获取模板
     */
    public static Observable<List<MuBanTaskBO>> getFileContent(ScanRequest request) {
        return getService().getFileContent(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取外部订单信息
     */
    public static Observable<ClientOrderBo> getClientInfo(ClientInfoRequest request) {
        request.setToken(MyApplication.token);
        return getService().getClientInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取订单统计数目
     */
    public static Observable<OrderNumBO> getOrderNum(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        request.setToken(MyApplication.token);
        return getService().getOrdercount(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 统计任务数目
     */
    public static Observable<TaskNumBO> getTaskNum(int id) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        return getService().getTaskCount(request).compose(RxResultHelper.httpRusult());
    }


}
