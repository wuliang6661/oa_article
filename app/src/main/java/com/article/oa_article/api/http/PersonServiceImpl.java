package com.article.oa_article.api.http;

import com.article.oa_article.api.ApiManager;
import com.article.oa_article.api.HttpService;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.AlreadyScopeBO;
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
import com.article.oa_article.bean.request.AddLableRequest;
import com.article.oa_article.bean.request.AddUserRequest;
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
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        return getService().getOutPutByUserId(request).compose(RxResultHelper.httpRusult());
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
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getMyScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取已评价列表
     */
    public static Observable<List<AlreadyScopeBO>> getHaveScope() {
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getHaveScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 待评价列表
     */
    public static Observable<List<AlreadyScopeBO>> getToScope() {
        TokenRequest request = new TokenRequest();
        request.token = MyApplication.token;
        return getService().getToScope(request).compose(RxResultHelper.httpRusult());
    }

    /**
     * 评价
     */
    public static Observable<String> scope(ScopeRequest request) {
        request.setToken(MyApplication.token);
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
     * 添加好友
     */
    public static Observable<String> addUser(AddUserRequest request) {
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setToken(MyApplication.token);
        return getService().addUser(request).compose(RxResultHelper.httpRusult());
    }


}
