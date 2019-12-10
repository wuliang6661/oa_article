package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AggentUserBO;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.bean.request.AgreeUserRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.MsgTypeRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.bean.request.TokenRequest;
import com.article.oa_article.util.rx.RxResultHelper;

import java.util.List;

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
    public static Observable<List<MsgBO>> getMessageList(PageRequest request) {
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


    /**
     * 未同意的好友申请列表
     */
    public static Observable<List<AggentUserBO>> getToAggentUser() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().getAgreeUserInfo(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查看更多
     */
    public static Observable<List<AggentUserBO>> getMoreInfo() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().getMoreInfo(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 同意好友请求
     */
    public static Observable<String> agreeUser(int id) {
        AgreeUserRequest request = new AgreeUserRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        request.setObjectId(id);
        return getService().agreeFriendUser(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 设置消息已读未读
     */
    public static Observable<String> readMsgType(String ids, int readStatus) {
        MsgTypeRequest request = new MsgTypeRequest();
        request.setIds(ids);
        request.setToken(MyApplication.token);
        request.setReadStatus(readStatus);
        return getService().readManyMessage(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 一键已读
     */
    public static Observable<String> readAllMsg() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().readAllMessage(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 一键删除
     */
    public static Observable<String> delAllMsg() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().delAllMessage(request).compose(RxResultHelper.httpRusult());
    }

}
