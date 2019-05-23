package com.article.oa_article.util.phone;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2316:41
 * desc   :
 * version: 1.0
 */
public class PhoneDto {
    private String name;        //联系人姓名
    private String telPhone;    //电话号码


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public PhoneDto() {
    }

    public PhoneDto(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }
}
