package com.article.oa_article.bean.request;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2315:16
 * desc   :
 * version: 1.0
 */
public class AddLableRequest {

    /**
     * companyId : 0
     * name : string
     * orderNum : 0
     * token : string
     */

    private int companyId;
    private String name;
    private int orderNum;
    private String token;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
