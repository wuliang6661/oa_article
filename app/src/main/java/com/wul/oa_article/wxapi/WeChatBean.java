package com.wul.oa_article.wxapi;

public class WeChatBean {


    /**
     * access_token : 20_pMNk-584WvprafTrooHx9S0LQVdkLqcRE3xXGFIZbQ2WARVujHqDTHMznNDvgSX0NFyYqhN-wsh0oYXU2KC4MRRSAUzJ5LltqK0x5yK3ly4
     * expires_in : 7200
     * refresh_token : 20_Lz-tyT7Xf8G1rl_znXYZ7GzPa3sdvJ0Gdo5vxh2uA9BpOceEkIsVAAjLlPbBWjwVPA53i5FAsg1A4OkhbDCGKavdcI6ChLtPFmeTiN3xbao
     * openid : oZpFg1TR9MlJgsCmA-sWpmADLlEA
     * scope : snsapi_userinfo
     * unionid : on7RU51d1InlqXrEkAE3WIRIXS5M
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
