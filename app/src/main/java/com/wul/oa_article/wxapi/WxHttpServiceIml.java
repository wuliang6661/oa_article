package com.wul.oa_article.wxapi;

import com.wul.oa_article.api.ApiManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public static Observable<WeChatBean> getWxMesage(String appid, String sercret, String code, String grant_type) {
        return getService().getWxMessage(appid, sercret, code, grant_type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取微信用户数据
     */
    public static Observable<WeChatUserBean> getUserMessage(String access_token, String openid) {
        return getService().getUserMessage(access_token, openid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
