package com.article.oa_article.api.http;

import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.bean.BaseResult;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.CountNumBO;
import com.article.oa_article.bean.FankuiTypeBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.bean.request.AddLableRequest;
import com.article.oa_article.bean.request.AddOutRequest;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.bean.request.AddUsersRequest;
import com.article.oa_article.bean.request.BuMenRequest;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.bean.request.FanKuiRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.LableRequest;
import com.article.oa_article.bean.request.PersonImgRequest;
import com.article.oa_article.bean.request.PersonNameRequest;
import com.article.oa_article.bean.request.PersonPasswordRequest;
import com.article.oa_article.bean.request.PersonPhoneRequest;
import com.article.oa_article.bean.request.PhoneRequest;
import com.article.oa_article.bean.request.ScopeRequest;
import com.article.oa_article.bean.request.TokenRequest;
import com.article.oa_article.bean.request.UpdateDepartRequest;
import com.article.oa_article.bean.request.UpdateShiliRequest;
import com.article.oa_article.bean.request.UpdateUnitRequest;
import com.article.oa_article.bean.request.UpdateZiYuanRequest;
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
     * 修改手机号码
     */
    @POST("industry_webservice/app/userInfo/updatePhone")
    Observable<BaseResult<String>> updatePhone(@Body PersonPhoneRequest request);

    /**
     * 发送短信验证码(修改手机号)
     */
    @POST("industry_webservice/app/userInfo/getNewPhoneCode")
    Observable<BaseResult<String>> sendMessage(@Body PhoneRequest request);

    /**
     * 查询部门列表
     */
    @POST("industry_webservice/app/depart/getDepartsByName")
    Observable<BaseResult<List<BuMenFlowBO>>> getDeparts(@Body BuMenRequest request);

    /**
     * 删除部门
     */
    @POST("industry_webservice/app/depart/deleteDepart")
    Observable<BaseResult<String>> deleteDepart(@Body IdRequest request);

    /**
     * 修改部门名称
     */
    @POST("industry_webservice/app/depart/updateDepartName")
    Observable<BaseResult<String>> updateDepartName(@Body IdRequest request);

    /**
     * 查询产能折线图
     */
    @POST("industry_webservice/app/output/getOutPutByUserId")
    Observable<BaseResult<List<ChartBO>>> getOutPutByUserId(@Body ChartRequest request);

    /**
     * 修改计划产量
     */
    @POST("industry_webservice/app/output/addOutput")
    Observable<BaseResult<String>> addOutPut(@Body AddOutRequest request);

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

    /**
     * 获取反馈类型
     */
    @POST("industry_webservice/app/feedback/getFeedBackType")
    Observable<BaseResult<List<FankuiTypeBO>>> getFeedType(@Body TokenRequest request);

    /**
     * 新增用户反馈
     */
    @POST("industry_webservice/app/feedback/addFeedBack")
    Observable<BaseResult<String>> addFeed(@Body FanKuiRequest request);

    /**
     * 获取公司数量
     */
    @POST("industry_webservice/app/companyInfo/getCounts")
    Observable<BaseResult<CountNumBO>> getCounts(@Body IdRequest request);

    /**
     * 查询企业认证信息
     */
    @POST("industry_webservice/app/companyInfo/getCompanyInfo")
    Observable<BaseResult<ComplanBO>> getComplanMsg(@Body IdRequest request);

    /**
     * 获取外部联系人标签
     */
    @POST("industry_webservice/app/label/getAllLabels")
    Observable<BaseResult<LableBo>> getAllLabels(@Body LableRequest request);

    /**
     * 删除标签
     */
    @POST("industry_webservice/app/label/deleteCustomLabel")
    Observable<BaseResult<String>> deleteLable(@Body IdRequest request);

    /**
     * 新增标签
     */
    @POST("industry_webservice/app/label/addLabel")
    Observable<BaseResult<String>> addLable(@Body AddLableRequest request);

    /**
     * 添加好友
     */
    @POST("industry_webservice/app/userInfo/addFriendUser")
    Observable<BaseResult<String>> addUser(@Body AddUserRequest request);

    /**
     * 添加多个好友
     */
    @POST("industry_webservice/app/userInfo/addFriendUsers")
    Observable<BaseResult<String>> addUsers(@Body AddUsersRequest requests);

    /**
     * 修改自定义标签
     */
    @POST("industry_webservice/app/label/updateCustomLabel")
    Observable<BaseResult<String>> updateLable(@Body AddLableRequest request);

    /**
     * 新增部门
     */
    @POST("industry_webservice/app/depart/addDepart")
    Observable<BaseResult<String>> addDepart(@Body IdRequest request);

    /**
     * 修改内部联系人的部门
     */
    @POST("industry_webservice/app/userInfo/updateUserDepart")
    Observable<BaseResult<String>> updateUserDepart(@Body UpdateDepartRequest request);

    /**
     * 新增企业
     */
    @POST("industry_webservice/app/companyInfo/addCompanyName")
    Observable<BaseResult<String>> addComplanName(@Body IdRequest request);

    /**
     * 新增企业认证
     */
    @POST("industry_webservice/app/companyInfo/addCompanyInfo")
    Observable<BaseResult<String>> addComplanInfo(@Body AddComplanRequest request);

    /**
     * 编辑公司信息
     */
    @POST("industry_webservice/app/companyInfo/updateCompanyInfo1")
    Observable<BaseResult<String>> updateCompanyInfo1(@Body AddComplanRequest.CompanyInfoBean request);

    /**
     * 编辑公司资源信息
     */
    @POST("industry_webservice/app/companyInfo/updateCompanyInfo2")
    Observable<BaseResult<String>> updateCompanyInfo2(@Body UpdateZiYuanRequest request);

    /**
     * 编辑公司实力信息
     */
    @POST("industry_webservice/app/companyInfo/updateCompanyInfo3")
    Observable<BaseResult<String>> updateCompanyInfo3(@Body UpdateShiliRequest request);

    /**
     * 编辑单位
     */
    @POST("industry_webservice/app/companyInfo/updateUnit")
    Observable<BaseResult<String>> updateUnit(@Body UpdateUnitRequest request);

}
