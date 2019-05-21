package com.article.oa_article.api.http;

import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PageRequest;

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

}
