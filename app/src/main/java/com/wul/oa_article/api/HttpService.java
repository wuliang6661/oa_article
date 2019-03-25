package com.wul.oa_article.api;

import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.request.PhoneRequest;
import com.wul.oa_article.bean.request.RegistUserRequest;
import com.wul.oa_article.bean.request.TokenRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
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
     * 获取用户信息
     */
    @POST("industry_webservice/app/userInfo/getUserInfo")
    Observable<BaseResult<String>> getUserInfo(@Body TokenRequest request);


}
