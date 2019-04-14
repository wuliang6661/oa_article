package com.wul.oa_article.api.http;

import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.request.OrderQueryRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface PersonService {


    /**
     * 获取内部联系人列表
     */
    @POST("industry_webservice/app/userInfo/getInUsers")
    Observable<BaseResult<String>> getInUsers(@Body OrderQueryRequest request);

    /**
     * 获取外部联系人列表
     */
    @POST("industry_webservice/app/userInfo/getOutUsers")
    Observable<BaseResult<String>> getOutUsers(@Body OrderQueryRequest request);



}
