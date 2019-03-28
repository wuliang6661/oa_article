package com.wul.oa_article.wxapi;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface WxHttpService {


    /**
     * 微信的登陆获取数据请求
     */
    String WXurL = "https://api.weixin.qq.com/";

    @FormUrlEncoded
    @POST("sns/oauth2/access_token")
    Observable<JSONObject> getWxMessage(@Field("appid") String appid,
                                        @Field("secret") String secret,
                                        @Field("code") String code,
                                        @Field("grant_type") String grant_type);

    @FormUrlEncoded
    @POST("sns/userinfo")
    Observable<JSONObject> getUserMessage(@Field("access_token") String access_token,
                                      @Field("openid") String openid);
}
