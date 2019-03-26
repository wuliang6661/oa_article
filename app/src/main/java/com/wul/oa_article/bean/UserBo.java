package com.wul.oa_article.bean;

import java.util.List;

public class UserBo {


    /**
     * id : 10
     * phone : 13067980276
     * companys : []
     */

    private int id;
    private String phone;
    private List<?> companys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<?> getCompanys() {
        return companys;
    }

    public void setCompanys(List<?> companys) {
        this.companys = companys;
    }
}
