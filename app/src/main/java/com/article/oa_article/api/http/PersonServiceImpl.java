package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.util.rx.RxResultHelper;

import java.util.List;

import rx.Observable;

public class PersonServiceImpl {

    private static PersonService service;

    /**
     * 获取代理对象
     */
    public static PersonService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(PersonService.class, HttpService.URL);
        return service;
    }


    /**
     * 获取内部联系人列表
     */
    public static Observable<List<BumenBO>> getNeiPersonList(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getInUsers(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取外部联系人列表
     */
    public static Observable<List<BumenBO>> getOutPersonList(IdRequest request) {
        request.setToken(MyApplication.token);
        return getService().getOutUsers(request).compose(RxResultHelper.httpRusult());
    }


}
