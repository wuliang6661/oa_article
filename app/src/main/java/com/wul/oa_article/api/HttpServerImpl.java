package com.wul.oa_article.api;

import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.AcceptedOrderBo;
import com.wul.oa_article.bean.ComplanOrderBo;
import com.wul.oa_article.bean.HistoryBO;
import com.wul.oa_article.bean.MyOrderBO;
import com.wul.oa_article.bean.SalesBo;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.bean.request.AsseptRequest;
import com.wul.oa_article.bean.request.ComplayRequest;
import com.wul.oa_article.bean.request.ForwordPassword;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.bean.request.PhoneRequest;
import com.wul.oa_article.bean.request.RegistUserRequest;
import com.wul.oa_article.bean.request.SelectRequest;
import com.wul.oa_article.bean.request.TokenRequest;
import com.wul.oa_article.bean.request.WechatRegisterRequest;
import com.wul.oa_article.util.MD5;
import com.wul.oa_article.util.rx.RxResultHelper;

import java.util.List;

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


}
