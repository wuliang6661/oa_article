package com.wul.oa_article.api.http;

import com.wul.oa_article.bean.BaseResult;
import com.wul.oa_article.bean.BumenBO;
import com.wul.oa_article.bean.request.IdRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface PersonService {


    /**
     * 获取内部联系人列表
     */
    @POST("industry_webservice/app/userInfo/getInUsers")
    Observable<BaseResult<List<BumenBO>>> getInUsers(@Body IdRequest request);

    /**
     * 获取外部联系人列表
     */
    @POST("industry_webservice/app/userInfo/getOutUsers")
    Observable<BaseResult<List<BumenBO>>> getOutUsers(@Body IdRequest request);



}
