package com.wul.oa_article.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wul.oa_article.Config;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.UserBo;
import com.wul.oa_article.bean.request.WechatRegisterRequest;
import com.wul.oa_article.util.AppManager;
import com.wul.oa_article.view.main.MainActivity;
import com.wul.oa_article.view.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private BaseResp resp = null;
    private String WX_APP_ID = Config.WX_APP_ID;
    private String WX_APP_SECRET = Config.WX_SCREEN;
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                String code = ((SendAuth.Resp) resp).code;
                /*
                 * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
                 */
                WxHttpServiceIml.getWxMesage(WX_APP_ID, WX_APP_SECRET, code, "authorization_code")
                        .subscribe(new HttpResultSubscriber<JSONObject>() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                try {
                                    if (!"".equals(response)) {
                                        String access_token = null;
                                        access_token = response.getString("access_token");
                                        String openid = response.getString("openid");
                                        getUserInfo(access_token, openid);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFiled(String message) {
                                Toast.makeText(WXEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
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
        WxHttpServiceIml.getUserMessage(accentToken, openId).subscribe(new HttpResultSubscriber<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    System.out.println("获取用户信息:" + response);
                    if (!response.equals("")) {
                        String openid = response.getString("openid");
                        String nickname = response.getString("nickname");
                        String headimgurl = response.getString("headimgurl");
                        weChatLogin(openid, nickname, headimgurl);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFiled(String message) {
                Toast.makeText(WXEntryActivity.this, message, Toast.LENGTH_SHORT).show();
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
                ToastUtils.showShort(message);
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
                MyApplication.userBo = s;
                Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                startActivity(intent);
                AppManager.getAppManager().goHome();
            }

            @Override
            public void onFiled(String message) {
                ToastUtils.showShort(message);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }


    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}