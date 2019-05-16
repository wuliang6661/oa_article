package com.article.oa_article.api.http;

import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.bean.request.BuMenRequest;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PersonImgRequest;
import com.article.oa_article.bean.request.PersonNameRequest;
import com.article.oa_article.bean.request.PersonPasswordRequest;
import com.article.oa_article.bean.request.ScopeRequest;
import com.article.oa_article.bean.request.TokenRequest;
import com.article.oa_article.bean.request.UserInInfoRequest;
import com.article.oa_article.bean.request.UserOutRequest;

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

    /**
     * 修改昵称
     */
    @POST("industry_webservice/app/userInfo/updateNickName")
    Observable<BaseResult<String>> updateNickName(@Body PersonNameRequest request);

    /**
     * 更换头像
     */
    @POST("industry_webservice/app/userInfo/updateImage")
    Observable<BaseResult<String>> updateImg(@Body PersonImgRequest request);

    /**
     * 修改密码
     */
    @POST("industry_webservice/app/userInfo/updatePassword")
    Observable<BaseResult<String>> updatePassword(@Body PersonPasswordRequest request);

    /**
     * 查询部门列表
     */
    @POST("industry_webservice/app/depart/getDepartsByName")
    Observable<BaseResult<List<BuMenFlowBO>>> getDeparts(@Body BuMenRequest request);

    /**
     * 查询产能折线图
     */
    @POST("industry_webservice/app/output/getOutPutByUserId")
    Observable<BaseResult<List<ChartBO>>> getOutPutByUserId(@Body ChartRequest request);

    /**
     * 获取内部联系人详情
     */
    @POST("industry_webservice/app/userInfo/getUserInInfo")
    Observable<BaseResult<UserInInfoBo>> getUserInInfo(@Body UserInInfoRequest request);

    /**
     * 获取外部联系人详情
     */
    @POST("industry_webservice/app/userInfo/getUserOutInfo")
    Observable<BaseResult<UserOutInfo>> getUserOutInfo(@Body UserOutRequest request);

    /**
     * 收到的评价列表
     */
    @POST("industry_webservice/app/evaluate/getMyEvaluates")
    Observable<BaseResult<List<ScopeBO>>> getMyScope(@Body TokenRequest request);

    /**
     * 已评价列表
     */
    @POST("industry_webservice/app/evaluate/getHaveEvaluates")
    Observable<BaseResult<List<AlreadyScopeBO>>> getHaveScope(@Body TokenRequest request);

    /**
     * 待评价列表
     */
    @POST("industry_webservice/app/evaluate/getToBeEvaluates")
    Observable<BaseResult<List<AlreadyScopeBO>>> getToScope(@Body TokenRequest request);

    /**
     * 评价
     */
    @POST("industry_webservice/app/evaluate/addEvaluate")
    Observable<BaseResult<String>> addScope(@Body ScopeRequest request);

}
