package com.article.oa_article.wxapi;

import java.util.List;

public class WeChatUserBean {


    /**
     * openid : oZpFg1TR9MlJgsCmA-sWpmADLlEA
     * nickname : 我们的盆友小哪吒
     * sex : 1
     * language : zh_CN
     * city : Wuhan
     * province : Hubei
     * country : CN
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/6p7YWGXJhrHUbHvLxibwklgLM1ZGWT1uM2lDyjHTj44Dd6uDNjwNU8oibX7k5OiabdwcZcHIic8HibXW090ico6JOibxw/132
     * privilege : []
     * unionid : on7RU51d1InlqXrEkAE3WIRIXS5M
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
