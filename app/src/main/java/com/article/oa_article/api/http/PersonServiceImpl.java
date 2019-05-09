package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PersonImgRequest;
import com.article.oa_article.bean.request.PersonNameRequest;
import com.article.oa_article.bean.request.PersonPasswordRequest;
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


    /**
     * 修改昵称
     */
    public static Observable<String> updateNikeName(String name) {
        PersonNameRequest request = new PersonNameRequest();
        request.setNewNickName(name);
        request.setToken(MyApplication.token);
        return getService().updateNickName(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 更换头像
     */
    public static Observable<String> updateImg(String imageUrl) {
        PersonImgRequest request = new PersonImgRequest();
        request.setToken(MyApplication.token);
        request.setNewImage(imageUrl);
        return getService().updateImg(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 更换密码
     */
    public static Observable<String> updatePassword(PersonPasswordRequest request) {
        request.setToken(MyApplication.token);
        return getService().updatePassword(request).compose(RxResultHelper.httpRusult());
    }

}
