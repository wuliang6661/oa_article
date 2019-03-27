package com.wul.oa_article.api;

import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.bean.request.ForwordPassword;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.bean.request.PhoneRequest;
import com.wul.oa_article.bean.request.RegistUserRequest;
import com.wul.oa_article.bean.request.TokenRequest;
import com.wul.oa_article.util.MD5;
import com.wul.oa_article.util.rx.RxResultHelper;

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
    public static Observable<String> getOrderByTask(OrderRequest request) {
        request.setCompanyId(1);
        request.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxODg5Nzk0MDgxOSIsImNyZWF0ZWQiOjE1NTA4MDgxNTY4NzcsImV4cCI6MTU1OTQ0ODE1Nn0.C2igVm-makQT38CbaN4eJzLyo9YWRJvqZ3wcDcEtaCwGjEetiC97bi7PWVP9nt1VB1Q0tv0hVopcjrBDVww8tA");
        return getService().getOrderByTask(request).compose(RxResultHelper.httpRusult());
    }


}
