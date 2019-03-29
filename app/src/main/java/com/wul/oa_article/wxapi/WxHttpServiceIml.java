package com.wul.oa_article.wxapi;

import com.wul.oa_article.api.ApiManager;

import org.json.JSONObject;

import rx.Observable;

public class WxHttpServiceIml {


    private static WxHttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static WxHttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(WxHttpService.class, WxHttpService.WXurL);
        return service;
    }


    /**
     * 获取微信数据
     *
     * @param appid
     * @param sercret
     * @param code
     * @param grant_type
     * @return
     */
    public static Observable<JSONObject> getWxMesage(String appid, String sercret, String code, String grant_type) {
        return getService().getWxMessage(appid, sercret, code, grant_type);
    }

    /**
     * 获取微信用户数据
     */
    public static Observable<JSONObject> getUserMessage(String access_token, String openid) {
        return getService().getUserMessage(access_token, openid);
    }

}