package com.wul.oa_article.wxapi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WxHttpService {


    /**
     * 微信的登陆获取数据请求
     */
    String WXurL = "https://api.weixin.qq.com/";

    @GET("sns/oauth2/access_token")
    Observable<WeChatBean> getWxMessage(@Query("appid") String appid,
                                        @Query("secret") String secret,
                                        @Query("code") String code,
                                        @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Observable<WeChatUserBean> getUserMessage(@Query("access_token") String access_token,
                                          @Query("openid") String openid);
}
