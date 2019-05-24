package com.article.oa_article.bean.request;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2315:27
 * desc   :
 * version: 1.0
 */
public class AddUserRequest implements Serializable {


    /**
     * companyId : 0
     * departId : 0
     * labelId : 0
     * phone : string
     * token : string
     */

    private int companyId;
    private int departId;
    private int labelId;
    private String phone;
    private String token;
    private String photo;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
