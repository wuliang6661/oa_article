package com.article.oa_article.api.http;

import com.article.oa_article.bean.AggentUserBO;
import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.bean.request.AgreeUserRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.MsgTypeRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.bean.request.TokenRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface MessageService {


    /**
     * 获取消息列表
     */
    @POST("industry_webservice/app/userMessage/getUserMessageList")
    Observable<BaseResult<List<MsgBO>>> getMessageList(@Body PageRequest request);

    /**
     * 获取未读数量统计
     */
    @POST("industry_webservice/app/userMessage/getNoReadCounts")
    Observable<BaseResult<Integer>> getNoReadCounts(@Body IdRequest request);


    /**
     * 获取所有未同意的好友信息
     */
    @POST("industry_webservice/app/userMessage/getToAgreeUserInfo")
    Observable<BaseResult<List<AggentUserBO>>> getAgreeUserInfo(@Body IdRequest request);

    /**
     * 查看更多
     */
    @POST("industry_webservice/app/userMessage/getMoreInfo")
    Observable<BaseResult<List<AggentUserBO>>> getMoreInfo(@Body IdRequest request);


    /**
     * 同意好友请求
     */
    @POST("industry_webservice/app/userInfo/agreeAddFriendUser")
    Observable<BaseResult<String>> agreeFriendUser(@Body AgreeUserRequest request);

    /**
     * 设置多个消息已读或者未读
     */
    @POST("industry_webservice/app/userMessage/readManyUserMessage")
    Observable<BaseResult<String>> readManyMessage(@Body MsgTypeRequest request);

    /**
     * 一键已读
     */
    @POST("industry_webservice/app/userMessage/readAllUserMessages")
    Observable<BaseResult<String>> readAllMessage(@Body IdRequest request);

    /**
     * 一键清空
     */
    @POST("industry_webservice/app/userMessage/delAllUserMessages")
    Observable<BaseResult<String>> delAllMessage(@Body IdRequest request);

}
