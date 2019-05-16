package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.util.rx.RxResultHelper;

import rx.Observable;

public class MessageServiceImpl {

    private static MessageService service;

    /**
     * 获取代理对象
     */
    public static MessageService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(MessageService.class, HttpService.URL);
        return service;
    }


    /**
     * 获取消息列表
     */
    public static Observable<String> getMessageList(PageRequest request) {
        request.setToken(MyApplication.token);
        request.setPageNum(1);
        request.setPageSize(1000);
        return getService().getMessageList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 未读消息统计
     */
    public static Observable<Integer> getNoReadCounts(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getNoReadCounts(request).compose(RxResultHelper.httpRusult());
    }


}