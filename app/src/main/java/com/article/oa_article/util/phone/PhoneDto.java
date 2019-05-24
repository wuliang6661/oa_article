package com.article.oa_article.util.phone;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2316:41
 * desc   :
 * version: 1.0
 */
public class PhoneDto implements Serializable {
    private String name;        //联系人姓名
    private String telPhone;    //电话号码
    private String photo;     //头像


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

    public PhoneDto(String name, String telPhone, String photo) {
        this.name = name;
        this.telPhone = telPhone;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
