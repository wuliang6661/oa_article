package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.bean.ApplyComplanBO;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.CountNumBO;
import com.article.oa_article.bean.FankuiTypeBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.bean.request.AddLableRequest;
import com.article.oa_article.bean.request.AddOutRequest;
import com.article.oa_article.bean.request.AddUserRequest;
import com.article.oa_article.bean.request.AddUsersRequest;
import com.article.oa_article.bean.request.ApplyComplanRequest;
import com.article.oa_article.bean.request.BuMenRequest;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.bean.request.ComplanIdRequest;
import com.article.oa_article.bean.request.FanKuiRequest;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.KeyRequest;
import com.article.oa_article.bean.request.LableRequest;
import com.article.oa_article.bean.request.PersonImgRequest;
import com.article.oa_article.bean.request.PersonNameRequest;
import com.article.oa_article.bean.request.PersonPasswordRequest;
import com.article.oa_article.bean.request.PersonPhoneRequest;
import com.article.oa_article.bean.request.PhoneRequest;
import com.article.oa_article.bean.request.ScopeRequest;
import com.article.oa_article.bean.request.TokenRequest;
import com.article.oa_article.bean.request.UpdateCompanyUnitRequest;
import com.article.oa_article.bean.request.UpdateDepartRequest;
import com.article.oa_article.bean.request.UpdateShiliRequest;
import com.article.oa_article.bean.request.UpdateUnitRequest;
import com.article.oa_article.bean.request.UpdateZiYuanRequest;
import com.article.oa_article.bean.request.UserInInfoRequest;
import com.article.oa_article.bean.request.UserOutRequest;
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
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
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
     * 修改手机号码
     */
    public static Observable<String> updatePhone(String phone, String code) {
        PersonPhoneRequest request = new PersonPhoneRequest();
        request.setCode(code);
        request.setNewPhone(phone);
        request.setToken(MyApplication.token);
        return getService().updatePhone(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改手机号时获取短信验证码
     */
    public static Observable<String> updatePhoneSendMessage(String phone, int type) {
        PhoneRequest request = new PhoneRequest();
        request.phone = phone;
        request.type = type;
        request.token = MyApplication.token;
        return getService().sendMessage(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 更换密码
     */
    public static Observable<String> updatePassword(PersonPasswordRequest request) {
        request.setToken(MyApplication.token);
        return getService().updatePassword(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取部门列表
     */
    public static Observable<List<BuMenFlowBO>> getBumenList(BuMenRequest request) {
        request.setToken(MyApplication.token);
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().getDeparts(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取产能折线图
     */
    public static Observable<List<ChartBO>> getChartData(ChartRequest request) {
        request.setToken(MyApplication.token);
//        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().getOutPutByUserId(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改计划产量
     */
    public static Observable<String> addOutPut(AddOutRequest request) {
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().addOutPut(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取内部联系人详情
     */
    public static Observable<UserInInfoBo> getUserInInfo(UserInInfoRequest request) {
        request.setToken(MyApplication.token);
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().getUserInInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取外部联系人详情
     */
    public static Observable<UserOutInfo> getOutUserInfo(UserOutRequest request) {
        request.setToken(MyApplication.token);
        return getService().getUserOutInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取已收到的评价列表
     */
    public static Observable<List<ScopeBO>> getScopeList() {
        ComplanIdRequest request = new ComplanIdRequest();
        request.token = MyApplication.token;
        request.companyId = Integer.parseInt(MyApplication.getCommonId());
        return getService().getMyScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取已评价列表
     */
    public static Observable<List<AlreadyScopeBO>> getHaveScope() {
        ComplanIdRequest request = new ComplanIdRequest();
        request.token = MyApplication.token;
        request.companyId = Integer.parseInt(MyApplication.getCommonId());
        return getService().getHaveScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 待评价列表
     */
    public static Observable<List<AlreadyScopeBO>> getToScope() {
        ComplanIdRequest request = new ComplanIdRequest();
        request.token = MyApplication.token;
        request.companyId = Integer.parseInt(MyApplication.getCommonId());
        return getService().getToScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 评价
     */
    public static Observable<String> scope(ScopeRequest request) {
        request.setToken(MyApplication.token);
        request.setEvaluateCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().addScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取反馈类型
     */
    public static Observable<List<FankuiTypeBO>> getOptionType() {
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getFeedType(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交反馈
     */
    public static Observable<String> addFeed(FanKuiRequest request) {
        request.setToken(MyApplication.token);
        return getService().addFeed(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取公司数量
     */
    public static Observable<CountNumBO> getCounts() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().getCounts(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询企业认证信息
     */
    public static Observable<ComplanBO> getComplanMsg() {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().getComplanMsg(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询外部联系人详情
     */
    public static Observable<ComplanBO> getOutComplanInfo(int complanId) {
        IdRequest request = new IdRequest();
        request.setId(complanId);
        request.setToken(MyApplication.token);
        return getService().getComplanMsg(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取外部联系人标签
     */
    public static Observable<LableBo> getAllLables() {
        LableRequest request = new LableRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().getAllLabels(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 删除标签
     */
    public static Observable<String> deleteLable(int id) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        return getService().deleteLable(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 新增标签
     */
    public static Observable<String> addLable(String name) {
        AddLableRequest request = new AddLableRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setName(name);
        request.setOrderNum(1);
        request.setToken(MyApplication.token);
        return getService().addLable(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 更新标签
     */
    public static Observable<String> updateLable(String name, int orderNum, int id) {
        AddLableRequest request = new AddLableRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setName(name);
        request.setOrderNum(orderNum);
        request.setId(id);
        request.setToken(MyApplication.token);
        return getService().updateLable(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 添加好友
     */
    public static Observable<String> addUser(AddUserRequest request) {
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().addUser(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 添加多个好友
     */
    public static Observable<String> addUsers(List<AddUserRequest> request) {
        AddUsersRequest requests = new AddUsersRequest();
        requests.setToken(MyApplication.token);
        requests.setUsers(request);
        return getService().addUsers(requests).compose(RxResultHelper.httpRusult());
    }

    /**
     * 新增部门
     */
    public static Observable<String> addDeart(String name) {
        IdRequest request = new IdRequest();
        request.setName(name);
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().addDepart(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改联系人部门
     */
    public static Observable<String> updateDeart(int userId, int deartId) {
        UpdateDepartRequest request = new UpdateDepartRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setDepartId(deartId);
        request.setToken(MyApplication.token);
        request.setUserId(userId);
        return getService().updateUserDepart(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 新增企业
     */
    public static Observable<String> addComplanName(String name) {
        IdRequest request = new IdRequest();
        request.setName(name);
        request.setToken(MyApplication.token);
        return getService().addComplanName(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 新增企业认证
     */
    public static Observable<String> addComplanInfo(AddComplanRequest request) {
        request.setToken(MyApplication.token);
        return getService().addComplanInfo(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 修改公司信息
     */
    public static Observable<String> updateComplanyinfo1(AddComplanRequest.CompanyInfoBean request) {
        request.setToken(MyApplication.token);
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().updateCompanyInfo1(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改公司资源
     */
    public static Observable<String> updateComplanInfo2(UpdateZiYuanRequest request) {
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().updateCompanyInfo2(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改公司实力
     */
    public static Observable<String> updateComplanInfo3(UpdateShiliRequest request) {
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().updateCompanyInfo3(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改公司单位
     */
    public static Observable<String> updateUnit(String unit) {
        UpdateUnitRequest request = new UpdateUnitRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setUnit(unit);
        request.setToken(MyApplication.token);
        return getService().updateUnit(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 编辑公司单位
     */
    public static Observable<String> updateComplanUnit(int unitId){
        UpdateCompanyUnitRequest request = new UpdateCompanyUnitRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setCompanyUnitId(unitId);
        request.setToken(MyApplication.token);
        return getService().updateCompanyUserUnit(request).compose(RxResultHelper.httpRusult());
    }


    /**
     * 删除部门
     */
    public static Observable<String> deleteDeart(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        request.setToken(MyApplication.token);
        return getService().deleteDepart(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 修改部门名称
     */
    public static Observable<String> updateDeartName(int id, String name) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        request.setName(name);
        return getService().updateDepartName(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 模糊查询公司列表
     */
    public static Observable<List<ApplyComplanBO>> getComplanList(String name) {
        KeyRequest request = new KeyRequest();
        request.setName(name);
        request.setToken(MyApplication.token);
        return getService().getCompanyList(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 申请加入公司
     */
    public static Observable<String> addComplan(int id) {
        ApplyComplanRequest request = new ApplyComplanRequest();
        request.setHisCompanyId(id);
        request.setMyCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().applyAddComplan(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取微信分享数据
     */
    public static Observable<ShareBo> getShareMsg() {
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getShareMessage(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 同意加入公司
     */
    public static Observable<String> agreeAddComplan(int id) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        return getService().agreeAddCompany(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 删除任务模板
     */
    public static Observable<String> deleteTemple(int id) {
        IdRequest request = new IdRequest();
        request.setToken(MyApplication.token);
        request.setId(id);
        return getService().deleteTemplate(request).compose(RxResultHelper.httpRusult());
    }

}
