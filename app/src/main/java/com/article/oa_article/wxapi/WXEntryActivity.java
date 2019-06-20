package com.article.oa_article.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.article.oa_article.Config;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.bean.request.WechatRegisterRequest;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.view.main.MainActivity;
import com.article.oa_article.view.register.RegisterActivity;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    private SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyApplication.WXapi == null) {
            MyApplication.WXapi = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, true);
            MyApplication.WXapi.registerApp(Config.WX_APP_ID);
        }
        svProgressHUD = new SVProgressHUD(this);
        AppManager.getAppManager().addActivity(this);
        MyApplication.WXapi.handleIntent(getIntent(), this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.d("我进来啦！！！" + resp.errStr + resp.errCode);
        String result = "";
        if (resp != null) {
            resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case 1:
                        String code = ((SendAuth.Resp) resp).code;
                        showProgress();
                        /*
                         * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
                         */
                        WxHttpServiceIml.getWxMesage(Config.WX_APP_ID, Config.WX_SCREEN, code, "authorization_code")
                                .subscribe(new HttpResultSubscriber<WeChatBean>() {
                                    @Override
                                    public void onSuccess(WeChatBean response) {
                                        String access_token = response.getAccess_token();
                                        String openid = response.getOpenid();
                                        getUserInfo(access_token, openid);
                                    }

                                    @Override
                                    public void onFiled(String message) {
                                        stopProgress();
                                        Toast.makeText(WXEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                        break;
                    case 2:
                    default:
                        finish();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }

    }

    /**
     * 通过拼接的用户信息url获取用户信息
     */
    private void getUserInfo(String accentToken, String openId) {
        WxHttpServiceIml.getUserMessage(accentToken, openId).subscribe(new HttpResultSubscriber<WeChatUserBean>() {
            @Override
            public void onSuccess(WeChatUserBean response) {
                String openid = response.getOpenid();
                String nickname = response.getNickname();
                String headimgurl = response.getHeadimgurl();
                weChatLogin(openid, nickname, headimgurl);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                Toast.makeText(WXEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * 微信登录
     */
    private void weChatLogin(String openId, String nickName, String nickImg) {
        WechatRegisterRequest registerRequest = new WechatRegisterRequest();
        registerRequest.setOpenId(openId);
        HttpServerImpl.loginWeChat(registerRequest).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (StringUtils.isEmpty(s)) {
                    stopProgress();
                    Intent intent = new Intent(WXEntryActivity.this, RegisterActivity.class);
                    intent.putExtra("isWeChat", true);
                    intent.putExtra("openId", openId);
                    intent.putExtra("nickName", nickName);
                    intent.putExtra("nickImg", nickImg);
                    startActivity(intent);
                    finish();
                } else {
                    MyApplication.token = s;
                    getUserInfo();
                }
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                ToastUtils.showShort(message);
                finish();
            }
        });
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpServerImpl.getUserinfo().subscribe(new HttpResultSubscriber<UserBo>() {
            @Override
            public void onSuccess(UserBo s) {
                stopProgress();
                MyApplication.userBo = s;
                Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                startActivity(intent);
                AppManager.getAppManager().goHome();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                ToastUtils.showShort(message);
                finish();
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.WXapi.handleIntent(intent, this);
    }


    /**
     * 显示加载进度弹窗
     */
    protected void showProgress() {
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    /**
     * 停止弹窗
     */
    protected void stopProgress() {
        if (svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }

}